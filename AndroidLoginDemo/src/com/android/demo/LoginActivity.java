package com.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private Bundle bundle = null;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		setContentView(R.layout.loginerror);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		// 获取用户数量
		int userNum = bundle.getInt("userNum");
		TextView text = (TextView) findViewById(R.id.TextView);
		text.setText("登陆成功");
		btn = (Button) findViewById(R.id.btn1);
		// 消息推送
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View paramView) {

				startService();
			}
		});
	}

	// 读取字符流
	public String ConvertStreamToString(InputStream is) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String returnStr = "";
		try {
			while ((returnStr = br.readLine()) != null) {
				sb.append(returnStr);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		final String result = sb.toString();

		return result;
	}

	/**
	 * 启动service
	 */

	private void startService() {
		Intent i = new Intent(LoginActivity.this, CountService.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Bundle bundle1=new Bundle();
		// bundle1.putString("username",username);
		// bundle1.putString("password",password);
		// bundle1.putString("serverPort", serverPort);
		// bundle1.putString("serverIp",serverIp);
		// i.putExtras(bundle1);
		this.startService(i);
	}

	private void stopService() {
		Intent i = new Intent(LoginActivity.this, CountService.class);
		this.stopService(i);
	}

}
