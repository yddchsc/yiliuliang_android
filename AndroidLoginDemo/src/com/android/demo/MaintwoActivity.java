package com.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.adapter.ListAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

public class MaintwoActivity extends Activity {
	private ListView listView ;
	private ListAdapter adapter ;
	private String count = "30" ;
	ProgressDialog dialog ;
	static int pageNo = 1 ;
	String url = "http://registerandlogin.duapp.com/RegisterAndLogin/scenery_list.action?pageNo=" ;
	boolean isScroll = false ;
	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		
		dialog = new ProgressDialog(this) ;
		dialog.setTitle("加载提示框") ;
		dialog.setMessage("正在加载中......") ;
		adapter = new ListAdapter(this) ;
		listView = (ListView) findViewById(R.id.listView1) ;
		new DownloadTask().execute(url,Integer.toString(pageNo) ) ; 
	//	listView.setAdapter(adapter) ;
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(isScroll && (scrollState == SCROLL_STATE_IDLE)){     // ״̬��0    �������ײ�
						new DownloadTask().execute(url,Integer.toString(pageNo)+"/"+count ) ;
					
				}else if(listView.getFirstVisiblePosition() == 0 && (scrollState == SCROLL_STATE_IDLE)){
					
					new DownloadTask().execute(url,Integer.toString(1)+"/"+count) ;
					pageNo=0;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				isScroll = ((firstVisibleItem + visibleItemCount) == totalItemCount) ;
				System.out.println("------->"+totalItemCount) ;
			}
		}) ;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class DownloadTask extends AsyncTask<String, Void, List<Map<String, Object>>>{

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>() ;
			Map<String, Object> map ;
			HttpClient client = new DefaultHttpClient() ;
			HttpGet get = new HttpGet(params[0]+params[1]) ;
			try {
				HttpResponse response = client.execute(get) ;
				
				if(response.getStatusLine().getStatusCode() == 200){
					String jsonData = EntityUtils.toString(response.getEntity(), "UTF-8") ;
					try {
						JSONObject jsonObject = new JSONObject(jsonData) ;
						JSONArray jsonArray = jsonObject.getJSONArray("list") ;
						if(!jsonArray.isNull(0)){
							for(int i = 0 ;i<jsonArray.length() ;i++){
								JSONObject jsonObject2 = jsonArray.getJSONObject(i) ;
								map = new HashMap<String, Object>() ;
								Iterator iterator = jsonObject2.keys() ;
								while(iterator.hasNext()){
									String key =  (String) iterator.next() ;
								    Object value = jsonObject2.get(key) ;
								    map.put(key, value) ; 
								}
								list.add(map) ;
							}
							return list ;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
					}
					
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("---1--->"+list.size());
			return list ;
			
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.size() > 0 && pageNo>0){
				lists.addAll(result) ;
				adapter.setData(lists) ;
				if(pageNo == 1){
					listView.setAdapter(adapter) ;
					count = adapter.count;
				}
				
				adapter.notifyDataSetChanged() ;
				pageNo ++ ;
			}else if(pageNo==0){
				lists = new ArrayList<Map<String,Object>>();
				lists.addAll(result) ;
				adapter.setData(lists) ;
				listView.setAdapter(adapter) ;
				adapter.notifyDataSetChanged() ;
				count = adapter.count;
				pageNo = 2 ;
			}else{
				dialog.dismiss() ;
				Toast.makeText(getApplicationContext(), "没有更多了....", 0).show() ;
			}
			dialog.dismiss() ;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.show() ;
		//	System.out.println("--pageNo---"+pageNo);
			super.onPreExecute();
		}
		
	}

}
