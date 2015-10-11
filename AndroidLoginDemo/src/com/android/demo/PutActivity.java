package com.android.demo;

import java.util.ArrayList;

import com.spinner.test.CustomerSpinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PutActivity extends Activity {
	public static ArrayList<String> list = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	private CustomerSpinner spinner;
	private EditText number;
	private EditText money;
	private EditText address;
	private ImageButton put;
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put);
        number = (EditText)findViewById(R.id.editText1);
        money = (EditText)findViewById(R.id.editText2);
        address = (EditText)findViewById(R.id.editText3);
        put = (ImageButton)findViewById(R.id.imageButton1);
        put.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.put_press));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.put_btn));     
		            }  
		            return false;       
		    }       
		});  
        init();
        spinner = (CustomerSpinner)findViewById(R.id.spinner);
        spinner.setList(list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
        put.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PutActivity.this.putIsSuccsee()) {
					// 用户输入正确
					put();
				}
			}
		});
    }
    
    protected void put() {
		// TODO Auto-generated method stub
		
	}

	private boolean putIsSuccsee() {
		boolean t1 = false;
		boolean t2 = false;
		boolean t3 = false;
		String numb = number.getText().toString();
		String mone = money.getText().toString();
		String company = spinner.getText().toString();
		String addre = address.getText().toString();
		if(isNumeric(numb) && isNumeric(mone))
			t1 = true;
		else
			Toast.makeText(PutActivity.this, "流量和价格只能为数字哦", Toast.LENGTH_SHORT).show();
		if(addre != null)
			t2 = true;
		else
			Toast.makeText(PutActivity.this, "请填写地址，方便别人购买哦", Toast.LENGTH_SHORT).show();
		if(company == "运营商")
			t3 = true;
		else
			Toast.makeText(PutActivity.this, "请选择运营商", Toast.LENGTH_SHORT).show();
		if(t1 && t2 && t3)
			return true;
		else
			return false;
	}
	public static boolean isNumeric(String str){
		 for (int i = str.length();--i>=0;){   
			 if (!Character.isDigit(str.charAt(i))){
				 return false;
			 }
		 }
		 return true;
	}
	public void init(){
    	list.add("运营商");
    	list.add("中国移动");
    	list.add("中国联通");
    	list.add("中国电信");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		list.clear();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}