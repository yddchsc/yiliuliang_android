package com.example.div_listview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.div_listview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	List<Map<String,Object>> list ;
	LayoutInflater inflater ;
	public void setData(List<Map<String,Object>> data){
		this.list = data ;
	}
	
	public ListAdapter(Context context) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vhHolder ;
		if(convertView == null){
			vhHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.list_item, null) ;
			vhHolder.tv0 = (TextView) convertView.findViewById(R.id.textView0) ;
			vhHolder.tv1 = (TextView) convertView.findViewById(R.id.textView1) ;
			vhHolder.tv2 = (TextView) convertView.findViewById(R.id.textView2) ;
			vhHolder.img0 = (ImageView) convertView.findViewById(R.id.imageView1) ;
			vhHolder.imgbtn0 = (ImageButton) convertView.findViewById(R.id.imageButton1) ;
			
			vhHolder.imgbtn2 = (ImageButton) convertView.findViewById(R.id.imageButton3) ;
			convertView.setTag(vhHolder) ;
		}else{
			vhHolder = (ViewHolder) convertView.getTag() ;
		}
		vhHolder.tv1.setText(list.get(position).get("id")+" M") ;
		vhHolder.tv0.setText(list.get(position).get("name").toString()+" ") ;
		vhHolder.tv2.setText("￥ "+list.get(position).get("address")) ;
		return convertView ;
	}

	static class ViewHolder{
		TextView tv0 ;
		TextView tv1 ;
		TextView tv2 ;
		ImageView img0;
		ImageButton imgbtn0;
		ImageButton imgbtn2;
	}
}
