package com.android.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.demo.R;

import android.content.Context;
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

public class CartListAdapter extends BaseAdapter {

	List<Map<String,Object>> list ;
	LayoutInflater inflater ;
	private Context mContext;
	public void setData(List<Map<String,Object>> data){
		this.list = data ;
	}
	
	public CartListAdapter(Context context) {
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>() ;
		inflater = LayoutInflater.from(context) ;
		mContext = context;
	}

	@Override
	public int getCount() {
		return list.size() ;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position) ;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vhHolder ;
		if(convertView == null){
			vhHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.cart_item, null) ;
			
			vhHolder.tv0 = (TextView) convertView.findViewById(R.id.textView3) ;
			vhHolder.tv1 = (TextView) convertView.findViewById(R.id.textView1) ;
			vhHolder.tv2 = (TextView) convertView.findViewById(R.id.textView2) ;
			vhHolder.tv3 = (TextView) convertView.findViewById(R.id.textView4) ;
			
			convertView.findViewById(R.id.imageView1).setBackgroundDrawable(new BitmapDrawable(readBitMap(mContext, R.drawable.address_one)));
			convertView.findViewById(R.id.imageView2).setBackgroundDrawable(new BitmapDrawable(readBitMap(mContext, R.drawable.company)));
			
			vhHolder.imgbtn0 = (ImageButton) convertView.findViewById(R.id.imageButton1) ;
			
			convertView.setTag(vhHolder) ;
		}else{
			vhHolder = (ViewHolder) convertView.getTag() ;
		}
		vhHolder.tv1.setText(list.get(position).get("id")+" M") ;
		vhHolder.tv0.setText(list.get(position).get("address").toString()) ;
		vhHolder.tv2.setText("￥ "+list.get(position).get("money")) ;
		vhHolder.tv3.setText(list.get(position).get("company").toString()) ;
		return convertView ;
	}

	static class ViewHolder{
		TextView tv0 ;
		TextView tv1 ;
		TextView tv2 ;
		TextView tv3 ;
		ImageView img0;
		ImageView img1;
		ImageButton imgbtn0;
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
