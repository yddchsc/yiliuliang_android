package com.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * 服务器ip 端口号写死了 可以通过传值 获取到 服务器的 用户名密码
 * 
 * @author Administrator
 * 
 */
public class CountService extends Service {
	// 等待时间间隔
	private long wait = 10000L;
	// 服务器ip
	private String host = "registerandlogin.duapp.com";
	// 服务器端口号
	//private String serverPort = "30057";
	// 用户名
	private String username;
	// 用户密码
	private String password;
	// 用户数量
	private int userCount;
	// 设置与服务器通讯的时间长度
	private Long time = (long) (1 * 60 * 1000);
	// 新注册用户的数量
	private int newUserNum = 0;
	private static final String TAG = "CountService";
	private Bundle bundle = null;
	private Notification notification;
	private NotificationManager mNotificationManager;

	private Runnable mTask = new Runnable() {

		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			// 获取三分钟以前的用户数量

			// 为true 说明有新用户注册
			if (isAddUser("registerandlogin.duapp.com", username, password, userCount)) {
				newUserNum = addUserNum("registerandlogin.duapp.com", username, password,
						userCount);
				msg.arg1 = newUserNum;
			} else {
				msg.arg1 = userCount;
			}

			handler.sendMessage(msg);
			handler.postDelayed(mTask, time);
		}
	};
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 显示用户数量
			if (0 != msg.arg1) {
				// 如果有新用户注册，让通知栏显示“有 2个新用户注册”
				// 如果没用新用户注册，让通知栏显示“当前用户数量为20个”
				if (isAddUser("registerandlogin.duapp.com", username, password, userCount)) {
					showNotification("有" + String.valueOf(msg.arg1) + "个新用户！");
					// userCount=userCount+msg.arg1;
				} else {
					// 没有新用户注册
					showNotification("当前有" + String.valueOf(msg.arg1) + "个用户！");

				}
			} else {
				showNotification("消息推送服务已启动！");
			}
		};

	};

	public void onCreate() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		showNotification("消息推送服务已启动！");
		// 启动时候获取所有用户的数量
		userCount = getUserCount("registerandlogin.duapp.com", username, password);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"SETTING_Infos", 0);
		
		host = sharedPreferences.getString("host", "");
		username = sharedPreferences.getString("username", "");
		password = sharedPreferences.getString("password", "");
		handler.postDelayed(mTask, time);
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		userCount = getUserCount("registerandlogin.duapp.com", username, password);
		return super.onUnbind(intent);
	}

	public class LocalBinder extends Binder {
		public CountService getService() {
			return CountService.this;
		}
	}

	/**
	 * 获取用户数量
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param username
	 * @param password
	 * @return
	 */
	private int getUserCount(String host,
			String username, String password) {
		int num = 0;
		// 连接服务器
		String httpStr = "http://";
		String postStr = httpStr + host
				+ "/RegisterAndLogin/login";
		String questStr = "{LOGIN:{username:'" + username + "',password:'"
				+ password + "'}}";
		HttpParams httpParams = new BasicHttpParams();
		// 设置超时时间
		int timeoutConnection = 3000;
		HttpConnectionParams
				.setConnectionTimeout(httpParams, timeoutConnection);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(postStr);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String isUser = ConvertStreamToString(is);
			String tb = isUser.substring(isUser.length() - 4);
			System.out.println("tb" + tb);
			if ("true".equals(tb)) {
				// 登录成功
				// 获取所有用户的数量
				String numStr = isUser.substring(0, isUser.indexOf("t"));
				num = Integer.parseInt(numStr);

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return num;
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

	/**
	 * 通知栏显示的内容
	 * 
	 * @param message
	 */

	private void showNotification(String message) {
		Notification notification = new Notification(R.drawable.icon, message,
				System.currentTimeMillis());
		// 指定标志和声音
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 获取一个SharedPreferences对象
		SharedPreferences settings = getSharedPreferences("SETTING_Infos", 0);
		String uName = settings.getString("username", "");
		String pwd = settings.getString("password", "");
		//

		Intent intent = new Intent(this, MyShowActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		notification.setLatestEventInfo(this, "用户信息", message, contentIntent);
		mNotificationManager.notify(R.string.ok, notification);
	}

	/**
	 * 获取3分钟以前的用户数量
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param username
	 * @param password
	 * @return
	 */

	private int getOldUser(String host, String username,
			String password) {
		return getUserCount(host, username, password);
	}

	/**
	 * 判断三分钟内 是否有新注册用户 有 返回true 没有 返回false userNum 3分钟以前的用户数量
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean isAddUser(String host,
			String username, String password, int userNum) {
		// 用户现有的数量
		int userNewNum = getUserCount(host, username, password);
		// 如果现有用户的数量不等于3分钟以前用户的数量
		// 说明有用户注册
		if (userNewNum != userNum) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 如果有新用户注册 获取到新注册用户的数量
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param username
	 * @param password
	 *            3分钟以前的用户数量
	 * @param userNum
	 * @return
	 */

	private int addUserNum(String host, String username,
			String password, int userNum) {
		return getUserCount(host, username, password) - userNum;
	}

}