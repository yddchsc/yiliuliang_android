package com.android.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.DB.DBHP;
import com.android.adapter.CartListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AbsListView.OnScrollListener;

public class CartActivity extends Activity{
	private ListView listView ;
	ProgressDialog dialog ;
	static int pageNo = 1 ;
	static int page = 0 ;
	boolean isScroll = false ;
	Cursor cursor;
	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>() ;
	private DBHP db;
	private ImageButton delete_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.cart);
		
		dialog = new ProgressDialog(this) ;
		dialog.setTitle("加载提示框") ;
		dialog.setMessage("正在加载中......") ;
		new CartListAdapter(this);
		listView = (ListView) findViewById(R.id.listView1) ;
		delete_btn = (ImageButton) findViewById(R.id.imageButton1) ;
		
		delete_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		List<Map<String, String>> listItemsList=new ArrayList<Map<String,String>>();
		db = new DBHP(this, "bill.db", null, 2);
		cursor=db.getReadableDatabase().rawQuery("select  * from bill" , null);
		String from[]=new String[]{"number","address","company","money"};
        int to[]=new int[]{R.id.textView1,R.id.textView3,R.id.textView4,R.id.textView2};
        SimpleAdapter ada=new SimpleAdapter(this, listItemsList , R.layout.cart_item, from, to);
        while(cursor.moveToNext()){
        	Map<String,String> map=new HashMap<String, String>();
        	map.put("number", cursor.getString(cursor.getColumnIndex("number")));
        	map.put("address", cursor.getString(cursor.getColumnIndex("address")));
        	map.put("company", cursor.getString(cursor.getColumnIndex("company")));
        	map.put("money", cursor.getString(cursor.getColumnIndex("money")));
        	listItemsList.add(map);
        }
        listView.setAdapter(ada);
	}
}
