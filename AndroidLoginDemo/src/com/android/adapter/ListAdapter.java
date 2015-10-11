package com.android.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.DB.DBHP;
import com.example.div_listview.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	private Context mContext;
	public String count;
	List<Map<String,Object>> list ;
	LayoutInflater inflater ;
	private DBHP db = null;
	private SQLiteDatabase mDatabase;
	private SharedPreferences sharedPreferences;
	
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vhHolder ;
		if(convertView == null){
			vhHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.list_item, null) ;
			vhHolder.tv0 = (TextView) convertView.findViewById(R.id.textView0) ;
			vhHolder.tv1 = (TextView) convertView.findViewById(R.id.textView1) ;
			vhHolder.tv2 = (TextView) convertView.findViewById(R.id.textView2) ;
			vhHolder.tv3 = (TextView) convertView.findViewById(R.id.textView3) ;
			
			convertView.findViewById(R.id.imageView2).setBackgroundDrawable(new BitmapDrawable(readBitMap(mContext, R.drawable.address)));
			convertView.findViewById(R.id.imageView3).setBackgroundDrawable(new BitmapDrawable(readBitMap(mContext, R.drawable.company)));
			convertView.findViewById(R.id.imageView1).setBackgroundDrawable(new BitmapDrawable(readBitMap(mContext, R.drawable.image)));
			
			//vhHolder.img0 = (ImageView) convertView.findViewById(R.id.imageView1) ;
			//vhHolder.img1 = (ImageView) convertView.findViewById(R.id.imageView2) ;
			//vhHolder.img2 = (ImageView) convertView.findViewById(R.id.imageView3) ;
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
				AlertDialog.Builder builder = new AlertDialog.Builder(
						mContext);
				builder.setTitle("提示！");
				builder.setMessage("你要确定要加入购物车吗？");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								db = new DBHP((Activity)mContext, "bill.db", null, 1);
								mDatabase = db.getWritableDatabase();
								sharedPreferences = mContext.getSharedPreferences("info",mContext.MODE_PRIVATE);
								String insertStr = "INSERT INTO bill (username,number,address,company,money) VALUES (?,?,?,?,?)";
								String[] insertValue = {sharedPreferences.getString("username", ""),list.get(position).get("number").toString(),
										list.get(position).get("address").toString(),list.get(position).get("company").toString(),list.get(position).get("money").toString() };
								mDatabase.execSQL(insertStr, insertValue);
							}
				});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				AlertDialog dialog = builder.create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
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
	public static Bitmap readBitMap(Context context, int resId){ 
	         BitmapFactory.Options opt = new BitmapFactory.Options(); 
	         opt.inPreferredConfig = Bitmap.Config.RGB_565; 
	         opt.inPurgeable = true; 
	         opt.inInputShareable = true; 
	         opt.inSampleSize = computeSampleSize(opt, -1, 128*128);  //计算出图片使用的inSampleSize
	         opt.inJustDecodeBounds = false; 
	        //获取资源图片 
	         InputStream is = context.getResources().openRawResource(resId); 
	         return BitmapFactory.decodeStream(is,null,opt); 
	         } 
	     
	     public static int computeSampleSize(BitmapFactory.Options options,
	             int minSideLength, int maxNumOfPixels) {
	         int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);
	 
	         int roundedSize;
	         if (initialSize <= 8 ) {
	             roundedSize = 1;
	             while (roundedSize < initialSize) {
	                 roundedSize <<= 1;
	             }
	         } else {
	             roundedSize = (initialSize + 7) / 8 * 8;
	         }
	 
	         return roundedSize;
	     }
	 
	     private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
	         double w = options.outWidth;
	         double h = options.outHeight;
	 
	         int lowerBound = (maxNumOfPixels == -1) ? 1 :
	                 (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	         int upperBound = (minSideLength == -1) ? 128 :
	                 (int) Math.min(Math.floor(w / minSideLength),
	                 Math.floor(h / minSideLength));
	 
	         if (upperBound < lowerBound) {
	             // return the larger one when there is no overlapping zone.
	             return lowerBound;
	         }
	 
	         if ((maxNumOfPixels == -1) &&
	                 (minSideLength == -1)) {
	             return 1;
	         } else if (minSideLength == -1) {
	             return lowerBound;
	         } else {
	             return upperBound;
	         }
	     }
}
