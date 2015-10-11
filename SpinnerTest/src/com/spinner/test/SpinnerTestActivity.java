package com.spinner.test;

import java.sql.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class SpinnerTestActivity extends Activity {
	public static ArrayList<String> list = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	private CustomerSpinner spinner;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        spinner = (CustomerSpinner)findViewById(R.id.spinner);
        spinner.setList(list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
    }
    public void init(){
    	list.add("路飞");
    	list.add("索隆");
    	list.add("山治");
    	list.add("乔巴");
    	list.add("罗宾");
    	list.add("娜美");
    	list.add("乌索普");
    	list.add("弗兰奇");
    	list.add("布鲁克");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		list.clear();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}