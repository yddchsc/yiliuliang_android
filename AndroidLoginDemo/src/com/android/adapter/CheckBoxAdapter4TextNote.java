package com.android.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CheckBoxAdapter4TextNote extends SimpleCursorAdapter     {         
	private ArrayList<Integer> selection = new ArrayList<Integer>();//记录被选中条目id         
	private int mCheckBoxId = 0; //listView条目的样式对应的xml资源文件名（必须包含checkbox）         
	private String mIdColumn;//数据库表的id名称        
	private Context context;
	
	public CheckBoxAdapter4TextNote(Context context, int layout, Cursor c, String[] from, int[] to, int checkBoxId, String idColumn){             
		super(context, layout, c, from, to);             
		mCheckBoxId = checkBoxId;             
		mIdColumn = idColumn;         
}         
	@Override         
	public int getCount(){             
		return super.getCount();         
	}         
	@Override         
	public Object getItem(int position){             
		return super.getItem(position);         
	}         
	@Override         
	public long getItemId(int position){             
		return super.getItemId(position);        
	}         
	@Override         
	public View getView(final int position, View convertView, ViewGroup parent){             
		View view = super.getView(position, convertView, parent);             
		final CheckBox checkbox = (CheckBox) view.findViewById(mCheckBoxId);             
		checkbox.setOnClickListener(new OnClickListener(){                 
			
			public void onClick(View v){                     
				Cursor cursor = getCursor();                     
				cursor.moveToPosition(position);                                          
				checkbox.setChecked(checkbox.isChecked());                     
				if(checkbox.isChecked()){  //如果被选中则将id保存到集合中                     
					                       
					selection.add(cursor.getInt(cursor.getColumnIndex(mIdColumn)));                     
				}else{                         
					selection.remove(new Integer(cursor.getInt(cursor.getColumnIndex(mIdColumn))));                        
					Toast.makeText(context, "has removed " + cursor.getInt(cursor.getColumnIndex(mIdColumn)), 0).show();                     
				}                 
			}             
		});                          
		return view;        
	}  
	//返回集合         
	public ArrayList<Integer> getSelectedItems() {             
		return selection;        
	}     
}
