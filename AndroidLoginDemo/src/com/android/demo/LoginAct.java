package com.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import com.android.DB.DBHP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginAct extends Activity {
	private EditText name;
	private EditText pwd;
	private ImageButton login;
	private ImageButton register;
	private ImageButton forget;
	private String questStr = "";
	private String postStr = "";
	private String host="registerandlogin.duapp.com";
	String requestUrl = "http://registerandlogin.duapp.com/RegisterAndLogin/Login";
	private Intent intent;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	/** Called when the activity is first created. */
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		setContentView(R.layout.login);
		name = (EditText) findViewById(R.id.UserName);
		pwd = (EditText) findViewById(R.id.pwd);
		login = (ImageButton) findViewById(R.id.login);
		register = (ImageButton) findViewById(R.id.register);
		forget = (ImageButton) findViewById(R.id.forget);
		forget.getBackground().setAlpha(0);
		intent = new Intent(LoginAct.this, RegisterAct.class);
		login.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.go_pressed));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.go));     
		            }  
		            return false;       
		    }       
		});  
		forget.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
	            if(event.getAction() == MotionEvent.ACTION_DOWN){       
	               //重新设置按下时的背景图片  
	               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.forget_pressed));                              
	            }else if(event.getAction() == MotionEvent.ACTION_UP){       
	                //再修改为抬起时的正常图片  
	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.forget));     
	            }  
	            return false;       
	    }       
		});  
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (LoginAct.this.loginIsSuccsee()) {
					// 用户输入正确
					login();
				}
			}
		});
		register.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.creatcount_pressed));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.creatcount));     
		            }  
		            return false;       
		    }       
		});  
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// name.setText("");
				// pwd.setText("");
				startActivity(intent);
			}
		});
		sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
		editor = sharedPreferences.edit();
		name.setText(sharedPreferences.getString("username", ""));
		pwd.setText(sharedPreferences.getString("password", ""));
	}

	/*
	 * 
	 * 取得用户信息处理登录
	 */
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	 
	    unbindDrawables(findViewById(R.id.RootView));
	    System.gc();
	}
	private void unbindDrawables(View view) {
	    if (view.getBackground() != null) {
	        view.getBackground().setCallback(null);
	    }
	    if (view instanceof ViewGroup) {
	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	            unbindDrawables(((ViewGroup) view).getChildAt(i));
	        }
	        ((ViewGroup) view).removeAllViews();
	    }
	}

	/*
	 * 判断用户输入是否规范 录入信息验证 验证是否通过
	 */

	private boolean loginIsSuccsee() {
		EditText name = (EditText) findViewById(R.id.UserName);
		EditText pwd = (EditText) findViewById(R.id.pwd);
		// 获取用户输入的信息
		String userName = name.getText().toString();
		String password = pwd.getText().toString();
		if ("".equals(userName)) {
			// 用户输入用户名为空
			Toast.makeText(LoginAct.this, "用户名不能为空!", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if ("".equals(password)) {
			// 密码不能为空
			Toast.makeText(LoginAct.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void login() {
		// 获取用户输入的用户名,密码
		EditText name = (EditText) findViewById(R.id.UserName);
		EditText pwd = (EditText) findViewById(R.id.pwd);
		String username = name.getText().toString();
		String password = pwd.getText().toString();
		String httpstr = "http://";

		postStr = httpstr + host
				+ "/RegisterAndLogin/login";
		// questStr = "{LOGIN:{username:'}" + username + "',password:'"
		// + password + "'}}";
		questStr = "{LOGIN:{username:'" + username + "',password:'" + password
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
			nvps.add(new BasicNameValuePair("password", password));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String isUser = ConvertStreamToString(is);
			System.out.println("asdfsdf" + isUser);
			String tb=isUser.substring(isUser.length()-4);
			System.out.println(tb);
			if ("true".equals(tb)) {
				// 登录成功
				// 获取所有用户的数量
				editor.putString("password", pwd.getText()
						.toString().trim());
				editor.putString("username", name.getText()
						.toString().trim());
				editor.commit();
				Intent intent =new Intent(LoginAct.this,MainActivity.class);
				startActivity(intent);
				this.finish();
				onDestroy();

			} else if ("false".equals(isUser)) {
				AlertDialog.Builder builder = new Builder(LoginAct.this);
				builder.setMessage("用户名或密码输入错误！请重新登录");
				builder.setTitle("提示");
				builder.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Intent i=new
						// Intent(LoginActivity.this,LoginAct.class);
						// LoginActivity.this.finish();
						// startActivity(i);
//						EditText name = (EditText) findViewById(R.id.UserName);
//						EditText pwd = (EditText) findViewById(R.id.pwd);
//						name.setText("");
//						pwd.setText("");
						dialog.dismiss();
					}
				}).show();
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
}