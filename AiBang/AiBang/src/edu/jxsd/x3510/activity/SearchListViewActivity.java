package edu.jxsd.x3510.activity;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SearchListViewActivity extends Activity implements OnScrollListener{
	
	    // ListView��Adapter    
		private SimpleAdapter mSimpleAdapter;    
		private ListView lv;    
		private Button bt;    
		private ProgressBar pg;    
		private ArrayList<HashMap<String,String>> list;    
		// ListView�ײ�View    
		private View moreView;    
		private Handler handler;    
		// ����һ�������������������������ټ���    
		private int MaxDateNum;    
		// ���ɼ���Ŀ������    
		private int lastVisibleIndex;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_page_listview);
		Button rbutton = (Button) this.findViewById(R.id.activity_search_page_listview_return);
		
		rbutton.setOnClickListener(new rButtonClickListener());
		
		MaxDateNum = 22; // ���������������
		lv = (ListView) findViewById(R.id.activity_search_page_listview_lv);
		// ʵ�����ײ�����        
		moreView = getLayoutInflater().inflate(R.layout.activity_search_page_moreddata, null); 
		bt = (Button) moreView.findViewById(R.id.activity_search_page_moreddata_bt_load);        
	    pg = (ProgressBar) moreView.findViewById(R.id.activity_search_page_moreddata_pb);        
		handler = new Handler();
		// ��map��װ�����ݣ���ʼ��10������        
		list = new ArrayList <HashMap <String,String>>();        
		for (int i = 0; i < 10; i++) {            
			HashMap <String, String> map = new HashMap<String,String>();
			map.put("imageView",String.valueOf(R.drawable.girl));
			map.put("textViewName","С���");
			map.put("textViewPersonfocus","��ע");
			map.put("textViewNum1", "200");
			map.put("textViewFans", "��˿");
			map.put("textViewNum2", "200");
			map.put("textViewSchool", "�廪��ѧ");
			list.add(map);
			}
		    // ʵ����SimpleAdapter        
			mSimpleAdapter = new SimpleAdapter(this, list, R.layout.activity_search_page,
					new String[]{"imageView", "textViewName","textViewPersonfocus","textViewNum1","textViewFans","textViewNum2","textViewSchool"},
					new int[] { R.id.activity_search_page_iv_personalphoto, R.id.activity_search_page_tv_name ,R.id.activity_search_page_tv_personal_focus,R.id.activity_search_page_tv_num1,R.id.activity_search_page_tv_personal_fans,R.id.activity_search_page_tv_num2,R.id.activity_search_page_tv_school});
			// ���ϵײ�View��ע��Ҫ����setAdapter����ǰ        
			lv.addFooterView(moreView);        
			lv.setAdapter(mSimpleAdapter);
			// �󶨼�����       
			lv.setOnScrollListener(this);
			bt.setOnClickListener(new OnClickListener() {                        
				 public void onClick(View v) {                
					 pg.setVisibility(View.VISIBLE);
					 // ���������ɼ�                
					 bt.setVisibility(View.GONE);
					 // ��ť���ɼ�               
					 handler.postDelayed(new Runnable() {                                        
						 public void run() {                        
							 loadMoreDate();// ���ظ�������                        
							 bt.setVisibility(View.VISIBLE);                        
							 pg.setVisibility(View.GONE);                        
							 mSimpleAdapter.notifyDataSetChanged();// ֪ͨlistViewˢ������                  
							 }               
						 }, 2000);           
					 }        
				 });
	}
	private void loadMoreDate() {        
		int count = mSimpleAdapter.getCount();        
		if (count + 5 < MaxDateNum) {            
			// ÿ�μ���5��            
			for (int i = count; i < count + 5; i++) {                
				HashMap<String, String> map = new HashMap<String, String>();                
				map.put("imageView",String.valueOf(R.drawable.girl));
				map.put("textViewName","С���");
				map.put("textViewPersonfocus","��ע");
				map.put("textViewNum1", "200");
				map.put("textViewFans", "��˿");
				map.put("textViewNum2", "200");
				map.put("textViewSchool", "�廪��ѧ");                
				list.add(map);            
				}        
			} else {            
				// �����Ѿ�����5��            
				for (int i = count; i < MaxDateNum; i++) {                
					HashMap<String, String> map = new HashMap<String, String>();                
					map.put("imageView",String.valueOf(R.drawable.girl));
					map.put("textViewName","С���");
					map.put("textViewPersonfocus","��ע");
					map.put("textViewNum1", "200");
					map.put("textViewFans", "��˿");
					map.put("textViewNum2", "200");
					map.put("textViewSchool", "�廪��ѧ");                 
					list.add(map);            
					}        
				}    
		}
	public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		// �������ɼ���Ŀ������       
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;        
		// ���е���Ŀ�Ѿ������������ȣ����Ƴ��ײ���View        
		if (totalItemCount == MaxDateNum + 1) {            
			lv.removeFooterView(moreView);            
			Toast.makeText(this, "����ȫ��������ɣ�û�и������ݣ�", Toast.LENGTH_LONG).show();       
			}
	}
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// �����ײ����Զ����أ��ж�listview�Ѿ�ֹͣ�������������ӵ���Ŀ����adapter����Ŀ        
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleIndex == mSimpleAdapter.getCount()) {            
			// �������ײ�ʱ�Զ�����            
			// pg.setVisibility(View.VISIBLE);            
			// bt.setVisibility(View.GONE);            
			// handler.postDelayed(new Runnable() {            
			//            
			// @Override            
			// public void run() {            
			// loadMoreDate();            
			// bt.setVisibility(View.VISIBLE);            
			// pg.setVisibility(View.GONE);            
			// mSimpleAdapter.notifyDataSetChanged();            
			// }            
			//            
			// }, 2000);        
			}
		}
	class rButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent(SearchListViewActivity.this, SearchActivity.class);
			startActivity(intent);
		}
		
	}
}
