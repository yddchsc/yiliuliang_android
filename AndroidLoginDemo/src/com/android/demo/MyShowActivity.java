package com.android.demo;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Window;
import android.widget.TextView;

public class MyShowActivity extends Activity implements ServiceConnection{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		setContentView(R.layout.userinfo);
		Intent i = new Intent(this, CountService.class);
		bindService(i, this, Context.BIND_AUTO_CREATE);
		unbindService(this);
		
		SharedPreferences sp=getSharedPreferences("SETTING_Infos", 0);
		String username=sp.getString("username", "");
		String password=sp.getString("password","");
		TextView name=(TextView) findViewById(R.id.TextView3);
		name.setText(username);
		TextView pwd=(TextView) findViewById(R.id.TextView5);
		pwd.setText(password);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onServiceConnected(ComponentName paramComponentName,
			IBinder paramIBinder) {
		// TODO Auto-generated method stub
		if (paramComponentName.getShortClassName().endsWith("CountService")) {
			try {
				Parcel inSend = Parcel.obtain();
				inSend.writeString("CountService");
				paramIBinder.transact(111, inSend, null, IBinder.FLAG_ONEWAY);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void onServiceDisconnected(ComponentName paramComponentName) {
		// TODO Auto-generated method stub
		unbindService(this);
	}
}
