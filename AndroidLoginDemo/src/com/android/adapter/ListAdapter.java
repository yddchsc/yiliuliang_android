package com.android.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.div_listview.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends BaseAdapter {
	private Context mContext;
	public String count;
	List<Map<String,Object>> list ;
	LayoutInflater inflater ;
	public void setData(List<Map<String,Object>> data){
		this.list = data ;
	}
	
	public ListAdapter(Context context) {
		mContext = context;
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>() ;
		inflater = LayoutInflater.from(context) ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position) ;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vhHolder ;
		if(convertView == null){
			vhHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.list_item, null) ;
			vhHolder.tv0 = (TextView) convertView.findViewById(R.id.textView0) ;
			vhHolder.tv1 = (TextView) convertView.findViewById(R.id.textView1) ;
			vhHolder.tv2 = (TextView) convertView.findViewById(R.id.textView2) ;
			vhHolder.tv3 = (TextView) convertView.findViewById(R.id.textView3) ;
			vhHolder.img0 = (ImageView) convertView.findViewById(R.id.imageView1) ;
			vhHolder.img1 = (ImageView) convertView.findViewById(R.id.imageView2) ;
			vhHolder.img2 = (ImageView) convertView.findViewById(R.id.imageView3) ;
			vhHolder.imgbtn0 = (ImageButton) convertView.findViewById(R.id.imageButton1) ;
			
			vhHolder.imgbtn2 = (ImageButton) convertView.findViewById(R.id.imageButton3) ;
			convertView.setTag(vhHolder) ;
		}else{
			vhHolder = (ViewHolder) convertView.getTag() ;
		}
		vhHolder.tv1.setText(list.get(position).get("id")+" M") ;
		vhHolder.tv0.setText(list.get(position).get("address").toString()) ;
		vhHolder.tv2.setText("￥ "+list.get(position).get("money")) ;
		vhHolder.tv3.setText(list.get(position).get("company").toString()) ;
		count = list.get(position).get("count").toString();
		vhHolder.imgbtn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText((Activity)mContext, "没有更多了....", 0).show() ;
			}
		});
		return convertView ;
	}
	static class ViewHolder{
		TextView tv0 ;
		TextView tv1 ;
		TextView tv2 ;
		TextView tv3 ;
		ImageView img0;
		ImageView img1;
		ImageView img2;
		ImageButton imgbtn0;
		
		ImageButton imgbtn2;
	}
}
