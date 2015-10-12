package com.android.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

public class Welcome extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new Handler().postDelayed(new Runnable(){ 
			   
	         @Override
	         public void run() { 
	             Intent mainIntent = new Intent(Welcome.this,LoginAct.class); 
	             Welcome.this.startActivity(mainIntent); 
	             Welcome.this.finish(); 
	             onDestroy();
	         } 
	             
	        }, 3000); 
	}
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	 
	    unbindDrawables(findViewById(R.id.welcom));
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
}


