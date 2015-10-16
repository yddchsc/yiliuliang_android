package com.android.demo;

import com.android.DB.DBHP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;

public class GoCartActivity extends Activity {
	private DBHP db = null;
	private Cursor cursor;
	private CursorAdapter mAdapter;
	private ImageButton gotoshop;
	private SharedPreferences sharedPreferences;
	public static final String TAG="GoCartActivity";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gocart);
		
		gotoshop = (ImageButton) findViewById(R.id.imageButton1);
		
		gotoshop.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
	            if(event.getAction() == MotionEvent.ACTION_DOWN){       
	               //重新设置按下时的背景图片  
	               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.sentencr_btnpressed));                              
	            }else if(event.getAction() == MotionEvent.ACTION_UP){       
	                //再修改为抬起时的正常图片  
	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.sentence_btn));     
	            }  
	            return false;       
		    }       
		});
		gotoshop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(GoCartActivity.this,MainActivity.class); 
				GoCartActivity.this.startActivity(mainIntent); 
				GoCartActivity.this.finish(); 
	            onDestroy();
			}
		});
		
		db = new DBHP(GoCartActivity.this, "bil.db", null, 4);
		sharedPreferences = GoCartActivity.this.getSharedPreferences("info",GoCartActivity.this.MODE_PRIVATE);
		cursor=db.getReadableDatabase().rawQuery("SELECT * FROM bill where username = '"+sharedPreferences.getString("username", "")+"'", null);
		if( cursor.getCount() != 0){
			Intent mainIntent = new Intent(GoCartActivity.this,CartActivity.class); 
			GoCartActivity.this.startActivity(mainIntent); 
			GoCartActivity.this.finish(); 
            onDestroy();
		}
		db.close();   
	}
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (!cursor.isClosed()) {  
	        cursor.close();  
	    }  
	    db.close();  
	    
	    unbindDrawables(findViewById(R.id.gocart));
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