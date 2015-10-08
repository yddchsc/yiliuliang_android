package com.android.demo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

	private long mExitTime;

	private TabHost tabHost;
	/**
	 * ����ͼƬ
	 */
	private ImageView mTabImg;
	/**
	 * ����ͼƬƫ����
	 */
	private int zero = 0;
	/**
	 * ��һ��ˮƽ����ƽ�ƴ�С
	 */
	private int one = 0;
	/**
	 * ��ǰҳ�����
	 */
	private int currIndex = 0;
	private Animation animation;
	private RadioButton guide_home, guide_tfaccount, guide_account, guide_cart,
			guide_put;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_one);
		initTab();
		init();
	}

	private void initTab() {
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("guide_home")
				.setIndicator("guide_home")
				.setContent(new Intent(this, MaintwoActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("guide_tfaccount")
				.setIndicator("guide_tfaccount")
				.setContent(new Intent(this, MaintwoActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("guide_account")
				.setIndicator("guide_account")
				.setContent(new Intent(this, MaintwoActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("guide_cart")
				.setIndicator("guide_cart")
				.setContent(new Intent(this, CartActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("guide_put")
				.setIndicator("guide_put")
				.setContent(new Intent(this, PutActivity.class)));
	}

	private void init() {
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		Display currDisplay = getWindowManager().getDefaultDisplay();// ��ȡ��Ļ��ǰ�ֱ���
		int displayWidth = currDisplay.getWidth();
		one = displayWidth / 5;

		guide_home = (RadioButton) findViewById(R.id.guide_home);
		guide_tfaccount = (RadioButton) findViewById(R.id.guide_tfaccount);
		guide_account = (RadioButton) findViewById(R.id.guide_account);
		guide_cart = (RadioButton) findViewById(R.id.guide_cart);
		guide_put = (RadioButton) findViewById(R.id.guide_put);
		guide_home.setOnClickListener(new MyOnPageChangeListener());
		guide_tfaccount.setOnClickListener(new MyOnPageChangeListener());
		guide_account.setOnClickListener(new MyOnPageChangeListener());
		guide_cart.setOnClickListener(new MyOnPageChangeListener());
		guide_put.setOnClickListener(new MyOnPageChangeListener());
	}

	private class MyOnPageChangeListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			animation = null;
			switch (v.getId()) {
			case R.id.guide_home:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(one * 2, 0, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(one * 3, 0, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(one * 4, 0, 0, 0);
				} 
				currIndex = 0;
				tabHost.setCurrentTabByTag("guide_home");
				break;
			case R.id.guide_account:
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(one * 2, one, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(one * 3, one, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(one * 4, one, 0, 0);
				} 
				currIndex = 1;
				tabHost.setCurrentTabByTag("guide_account");
				break;
			case R.id.guide_put:
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one * 2, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, one * 2, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(one * 3, one * 2, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(one * 4, one * 2, 0, 0);
				} 
				currIndex = 2;
				tabHost.setCurrentTabByTag("guide_put");
				break;
			case R.id.guide_cart:
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one * 3, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, one * 3, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(one * 2, one * 3, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(one * 4, one * 3, 0, 0);
				} 
				currIndex = 3;
				tabHost.setCurrentTabByTag("guide_cart");
				break;
			case R.id.guide_tfaccount:
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one * 4, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, one * 4, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(one * 2, one * 4, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(one * 3, one * 4, 0, 0);
				}
				currIndex = 4;
				tabHost.setCurrentTabByTag("guide_tfaccount");
				break;	
			}
			if (animation != null) {
				animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
				animation.setDuration(150);
				mTabImg.startAnimation(animation);
			}
		}

	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				if (currIndex != 0) {
					new MyOnPageChangeListener().onClick(guide_home);
					guide_home.setChecked(true);
				} else {
					if ((System.currentTimeMillis() - mExitTime) > 2000) {// ������ΰ���ʱ��������2000���룬���˳�
						Toast.makeText(this, "退出程序", Toast.LENGTH_SHORT)
								.show();
						mExitTime = System.currentTimeMillis();// ����mExitTime
					} else {

						int id = android.os.Process.myPid();
						if (id != 0) {
							android.os.Process.killProcess(id);
						}
					}
				}
				return true;
			}
		}

		return super.dispatchKeyEvent(event);

	}
}