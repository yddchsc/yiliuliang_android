package com.android.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.DB.DBHP;
import com.android.adapter.CartListAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup; 
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener; 

public class CartActivity extends Activity{
	private ListView listView ;
	ProgressDialog dialog ;
	static int pageNo = 1 ;
	static int page = 0 ;
	boolean isScroll = false ;
	Cursor cursor;
	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>() ;
	private ArrayList<String> flags = new ArrayList<String>(); 
	List<Map<String, Object>> listItemsList=new ArrayList<Map<String,Object>>();
	private DBHP db;
	private ImageButton goback;
	private SharedPreferences sharedPreferences;
	private CheckBox checkBox;
	private ImageButton checkall;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.cart);
		
		dialog = new ProgressDialog(this) ;
		dialog.setTitle("加载提示框") ;
		dialog.setMessage("正在加载中......") ;
		new CartListAdapter(this);
		listView = (ListView) findViewById(R.id.listView1) ; 
		
		String from[]=new String[]{"check","number","address","company","money","flag"};
        int to[]=new int[]{R.id.checkBox1,R.id.textView1,R.id.textView3,R.id.textView4,R.id.textView2,R.id.textView5};
        
        goback = (ImageButton) findViewById(R.id.imageButton3);
        goback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(CartActivity.this,MainActivity.class); 
				CartActivity.this.startActivity(mainIntent); 
				CartActivity.this.finish(); 
	            onDestroy();
			}
		});
        checkall = (ImageButton) findViewById(R.id.imageButton2);
        checkall.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {               
		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
		               //重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.checkbox_checked));                              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		                //再修改为抬起时的正常图片  
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.checkbox));     
		            }  
		            return false;       
		    }       
		});  
        checkall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listItemsList.clear();
				db = new DBHP(CartActivity.this, "bil.db", null, 4);
				sharedPreferences = CartActivity.this.getSharedPreferences("info",CartActivity.this.MODE_PRIVATE);
				cursor=db.getReadableDatabase().rawQuery("select  * from bill where username = '"+sharedPreferences.getString("username", "")+"'" , null);
				while(cursor.moveToNext()){
		        	Map<String,Object> map=new HashMap<String, Object>();
		        	map.put("number", cursor.getString(cursor.getColumnIndex("number")));
		        	map.put("address", cursor.getString(cursor.getColumnIndex("address")));
		        	map.put("company", cursor.getString(cursor.getColumnIndex("company")));
		        	map.put("money", cursor.getString(cursor.getColumnIndex("money")));
		        	map.put("flag", cursor.getString(cursor.getColumnIndex("flag")));
		        	map.put("check", true);  
		        	listItemsList.add(map);
		        }
				db.close();  
            }  
		});
        
        
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {  
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
            	if(checkBox.isChecked()){      
            		checkBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkbox_checked));
				}else{
					checkBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkbox));
				}    
            }
        });  
        refresh();

        SimpleAdapter ada = new SimpleAdapter(this,listItemsList,R.layout.cart_item,from,to){
        	@Override    
        	public View getView(final int position, View convertView, ViewGroup parent) {  
        		View view = super.getView(position, convertView, parent);  
        		@SuppressWarnings("unchecked")
        		final HashMap<String,Object> map = (HashMap<String, Object>) this.getItem(position);  
        		//获取相应View中的Checkbox对象  
        		CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox1);  
        		checkBox.setChecked((Boolean) map.get("check"));  
        		//添加单击事件,在map中记录状态  
        		//通过判断checkbox是否被选中来确定联系人是否被放在names和numbers两个数组中。
                  
        		checkBox.setOnClickListener(new View.OnClickListener() {  
        			@Override  
        			public void onClick(View view) { 

        				map.put("check", ((CheckBox)view).isChecked());  
        				if(((CheckBox)view).isChecked()){    
        					flags.add((String) map.get("flag"));  
        					((CheckBox)view).setBackgroundDrawable(getResources().getDrawable(R.drawable.checkbox_checked));
        				}else{
        					flags.remove(map.get("flag"));  
        					((CheckBox)view).setBackgroundDrawable(getResources().getDrawable(R.drawable.checkbox));
        				}       
        			}  
        		});
        		
        		ImageButton delete_btn = (ImageButton) view.findViewById(R.id.imageButton1) ;
        		delete_btn.setOnClickListener(new View.OnClickListener() {
					@Override
        			public void onClick(View v) {
						listItemsList.clear();
        				db = new DBHP(CartActivity.this, "bil.db", null, 4);
        				SQLiteDatabase mDatabase = db.getWritableDatabase();  
        				sharedPreferences = CartActivity.this.getSharedPreferences("info",CartActivity.this.MODE_PRIVATE);
        				mDatabase.execSQL("delete from bill where flag = '" + (String) map.get("flag") +"' "+ "AND username = '"+sharedPreferences.getString("username", "")+"';");
        				refreshSecond();
        				db.close();
        			}
        		});
        		return view;
        	}
        };
        listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(listView.getFirstVisiblePosition() == 0 && (scrollState == SCROLL_STATE_IDLE)){
					refreshSecond();
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				isScroll = ((firstVisibleItem + visibleItemCount) == totalItemCount) ;
				//System.out.println("------->"+totalItemCount) ;
			}
		}) ;
       	listView.setAdapter(ada);
       	
       	onDestroy();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!cursor.isClosed()) {  
	        cursor.close();  
	    }  
	    db.close();   
	}
	public void refresh() {
		db = new DBHP(CartActivity.this, "bil.db", null, 4);
		sharedPreferences = CartActivity.this.getSharedPreferences("info",CartActivity.this.MODE_PRIVATE);
		cursor=db.getReadableDatabase().rawQuery("select  * from bill where username = '"+sharedPreferences.getString("username", "")+"'" , null);
		while(cursor.moveToNext()){
        	Map<String,Object> map=new HashMap<String, Object>();
        	map.put("number", cursor.getString(cursor.getColumnIndex("number")));
        	map.put("address", cursor.getString(cursor.getColumnIndex("address")));
        	map.put("company", cursor.getString(cursor.getColumnIndex("company")));
        	map.put("money", cursor.getString(cursor.getColumnIndex("money")));
        	map.put("flag", cursor.getString(cursor.getColumnIndex("flag")));
        	map.put("check", false);  
        	listItemsList.add(map);
        }
		db.close();
	}
	private void refreshSecond() {  
        finish();  
        Intent intent = new Intent(CartActivity.this, CartActivity.class);  
        startActivity(intent);  
    }  
}
