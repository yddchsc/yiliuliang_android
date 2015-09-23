package com.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAct extends Activity implements Callback, android.view.View.OnClickListener {
	//private Button reset;
	private ImageButton register;
	private ImageButton justlogin;
	private Button fasong;
	// 用户名
	private EditText uNameEditText;
	// 密码
	private EditText pwdEditText1;
	// 重复密码
	private EditText pwdEditText2;
	private String userName;
	private String password1;
	private String password2;
	private String host = "registerandlogin.duapp.com";
	private Bundle bundle;
	private String httpStr;
	private String postStr;
	private String questStr;
	private Intent intent;
	// 填写从短信SDK应用后台注册得到的APPKEY
		private static String APPKEY = "9cd5d6cfcc56";

		// 填写从短信SDK应用后台注册得到的APPSECRET
		private static String APPSECRET = "e52f1292b88813904d6e82a7bd7a155e";


		// 短信注册，随机产生头像
		private static final String[] AVATARS = {
			"http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
			"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
			"http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
			"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
			"http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
			"http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
			"http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
			"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
			"http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
			"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
		};

		private boolean ready;
		private Dialog pd;
		private TextView tvNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		setContentView(R.layout.register);
//		reset = (Button) findViewById(R.id.reset);
		justlogin = (ImageButton) findViewById(R.id.justlogin);
		justlogin.getBackground().setAlpha(0);
		intent = new Intent(RegisterAct.this, LoginAct.class);
		justlogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					startActivity(intent);
					RegisterAct.this.finish();
			}

		});
		
		fasong = (Button) findViewById(R.id.fasong);
		fasong.setOnClickListener(this);
		initSDK();
		/*reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				uNameEditText = (EditText) findViewById(R.id.username);
				pwdEditText1 = (EditText) findViewById(R.id.password1);
				pwdEditText2 = (EditText) findViewById(R.id.password2);
				uNameEditText.setText("");
				pwdEditText1.setText("");
				pwdEditText2.setText("");
			}
		});*/
	}


	/*
	 * 判断用户注册输入是否规范 录入信息验证 验证是否通过
	 */

	private boolean registerIsSuccsee(){
    	EditText name=(EditText)findViewById(R.id.username);
    	EditText pwd1=(EditText)findViewById(R.id.password1);
    	EditText pwd2=(EditText)findViewById(R.id.password2);
    	//获取用户输入的信息
    	String userName=name.getText().toString();
    	String password1=pwd1.getText().toString();
    	String password2=pwd2.getText().toString();
    	
    	if("".equals(userName)){
    		//用户输入用户名为空
    		Toast.makeText(RegisterAct.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if("".equals(password1)){
    		//密码不能为空
    		Toast.makeText(RegisterAct.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if(!password1.equals(password2)){
    		Toast.makeText(RegisterAct.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	return true;
    }
	

	/**
	 * 注册
	 */
	private void register() {
		uNameEditText = (EditText) findViewById(R.id.username);
		pwdEditText1 = (EditText) findViewById(R.id.password1);
		pwdEditText2 = (EditText) findViewById(R.id.password2);
		userName = uNameEditText.getText().toString();
		password1 = pwdEditText1.getText().toString();
		password2 = pwdEditText2.getText().toString();
		
		httpStr = "http://";
		postStr = httpStr + host
				+ "/RegisterAndLogin/register";
		questStr = "{REGISTER:{username:'" + userName + "',password1:'"
				+ password1 + "',password2:'" + password1 + "'}}";
		System.out.println("====questStr====" + questStr);
		System.out.println("====postStr====" + postStr);
		try {
			HttpParams httpParams = new BasicHttpParams();
			// 设置连接超时
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParams,
					timeoutConnection);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost httpPost = new HttpPost(postStr);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", userName));
			nvps.add(new BasicNameValuePair("password1", password1));
			nvps.add(new BasicNameValuePair("password2", password1));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream is=entity.getContent();
			String isUser = ConvertStreamToString(is);
			
			System.out.println(isUser);
			if("success".equals(isUser)){
				//表示用户注册成功
				AlertDialog.Builder builder=new Builder(RegisterAct.this);
				builder.setMessage("恭喜你！注册成功");
				builder.setTitle("提示");
				 builder.setPositiveButton("确认", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i=new Intent(RegisterAct.this,LoginAct.class);
						dialog.dismiss();
						startActivity(i);
					}
				}).show();
			}else if("userExist".equals(isUser)){
				//用户已经被注册
				AlertDialog.Builder builder=new Builder(RegisterAct.this);
				builder.setMessage("抱歉！该用户已被注册！");
				builder.setTitle("提示");
				builder.setPositiveButton("确认", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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

		System.out.println(result);
		return result;
	}
	private void initSDK() {
		// 初始化短信SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		final Handler handler = new Handler(this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;
	}

	protected void onDestroy() {
		if (ready) {
			// 销毁回调监听接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fasong:
			// 打开注册页面
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");
						// 提交用户信息
						registerUser(country, phone);
					}
				}
			});
			registerPage.show(this);
			break;
		}
	}

	public boolean handleMessage(Message msg) {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}

		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
			// 短信注册成功后，返回MainActivity,然后提示新好友
			register = (ImageButton) findViewById(R.id.register1);
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
					if (registerIsSuccsee()) {

						register();
					}
					
				}

			});
			if (result == SMSSDK.RESULT_COMPLETE) {
				Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
			} else {
				((Throwable) data).printStackTrace();
			}
		} 
		return false;
	}
	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "SmsSDK_User_" + uid;
		String avatar = AVATARS[id % 12];
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
	}
}
