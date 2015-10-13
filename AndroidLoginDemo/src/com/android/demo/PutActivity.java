package com.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.spinner.test.CustomerSpinner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
	private String questStr = "";
	private String postStr = "";
	private String host="registerandlogin.duapp.com";
	private ImageButton return1;
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put);
        
        return1 = (ImageButton)findViewById(R.id.return1);
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
        /*return1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(PutActivity.this,MaintwoActivity.class);
				startActivity(intent);
				PutActivity.this.finish();
			}
		});*/
    }
    @Override 
    public void onConfigurationChanged(Configuration config) { 
        super.onConfigurationChanged(config); 
    }
    private void put() {
    	number = (EditText)findViewById(R.id.editText1);
        money = (EditText)findViewById(R.id.editText2);
        address = (EditText)findViewById(R.id.editText3);
        spinner = (CustomerSpinner)findViewById(R.id.spinner);
    	String numb = number.getText().toString();
		String mone = money.getText().toString();
		String company = spinner.getText().toString();
		String addre = address.getText().toString();
		SharedPreferences sharedPreferences = this.getSharedPreferences("info",this.MODE_PRIVATE);
		String username = sharedPreferences.getString("username", "");
		String httpstr = "http://";

		postStr = httpstr + host
				+ "/RegisterAndLogin/put";
		
		questStr = "{PUT:{username:'" + username + "',number:'" + numb + "',address:'" + addre + "',company:'" + company + "',money:'" + mone
				+ "'}}";

		System.out.println("====questStr====" + questStr);
		System.out.println("====postStr====" + postStr);

		try {
			// 设置连接超时
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httpPost = new HttpPost(postStr);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("number", numb));
			nvps.add(new BasicNameValuePair("address", addre));
			nvps.add(new BasicNameValuePair("company", company));
			nvps.add(new BasicNameValuePair("money", mone));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String isUser = ConvertStreamToString(is);
			if ("success".equals(isUser)) {
				Toast.makeText(PutActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
			} 

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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

 		// System.out.println(result);
 		return result;
 	}
	private boolean putIsSuccsee() {
		boolean t1 = false;
		boolean t2 = false;
		boolean t3 = false;
		number = (EditText)findViewById(R.id.editText1);
        money = (EditText)findViewById(R.id.editText2);
        address = (EditText)findViewById(R.id.editText3);
        spinner = (CustomerSpinner)findViewById(R.id.spinner);
		String numb = number.getText().toString();
		String mone = money.getText().toString();
		String company = spinner.getText().toString();
		//String addre = address.getText().toString();
		if(isNumeric(numb) && isNumeric(mone) && numb != "" && mone != ""){
			t1 = true;
		}
		else{
			Toast.makeText(PutActivity.this, "流量和价格不能为零", Toast.LENGTH_SHORT).show();
		}
		if("".equals(address.getText().toString().trim())){
			
			Toast.makeText(PutActivity.this, "请填写地址，方便别人购买", Toast.LENGTH_SHORT).show();
		}
		else{
			t2 = true;
		}
		if(company != "运营商"){
			t3 = true;
		}
		else{
			Toast.makeText(PutActivity.this, "请选择运营商", Toast.LENGTH_SHORT).show();
		}
		if(t1 && t2 && t3){
			return true;
		}
		else{
			return false;
		}
	}
	public static boolean isNumeric(String str){
		System.out.println(str);
		Pattern pattern = Pattern.compile("^[1-9]\\d*$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
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