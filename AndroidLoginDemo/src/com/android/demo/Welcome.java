package com.android.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
	         } 
	             
	        }, 3000); 
	}
}
