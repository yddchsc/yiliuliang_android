package com.android.demo;

import com.android.DB.DBHP;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class GoCartActivity extends Activity {
	private DBHP db = null;
	private SQLiteDatabase mDatabase;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gocart);
		db = new DBHP(GoCartActivity.this, "bill.db", null, 2);
		mDatabase = db.getWritableDatabase();
		mDatabase.execSQL("");
	}
}