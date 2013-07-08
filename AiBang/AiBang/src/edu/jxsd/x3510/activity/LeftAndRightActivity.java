package edu.jxsd.x3510.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.DialogInterface;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.slidingmenu.lib.SlidingMenu;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.slidingmenu.lib.SlidingMenu;

import edu.jxsd.x3510.activity.Constants.Extra;
import edu.jxsd.x3510.bean.HelpMessage;
import edu.jxsd.x3510.fragments.Map;

import edu.jxsd.x3510.fragments.Map;
import edu.jxsd.x3510.http.HttpUtility;
import edu.jxsd.x3510.utlis.PictureUtlis;
import edu.jxsd.x3510.view.MyListView;
import edu.jxsd.x3510.view.MyListView.OnRefreshListener;

@SuppressLint("NewApi")
public class LeftAndRightActivity extends BaseActivity {

	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public static String PATH = "http://10.3.129.225:8080/AiBangServer/getAllHelpMessage.action";
	public static String PATH2 = "http://10.3.129.225:8080/AiBangServer/getLastHelpMessage.action";
	//public static final String PATH = "http://192.168.1.103:8080/AiBangServer/getAllHelpMessage.action";
	public static String TAG = "LeftAndRightActivity";
	public final static String EXTRA_MESSAGE = "edu.jxsd.x3510.activity.MESSAGE";

	public static String IMAGES = "edu.jxsd.x3510.activity.IMAGES";
	public static String IMAGES_POSITION = "edu.jxsd.x3510.activity.IMAGE_POSITION";
	private List<HelpMessage> data = null;
	private BaseAdapter adapter;
	private Button helpButton;
	private Button personalmsgButton;
	private Button commentButton;
	private Button personalPageButton;
	private Button mapButton;
	private ImageView search;
	private HttpUtility hu = new HttpUtility();
	private MyListView listView;
	private String userID = null;
	private  Spinner spinner;
	private String school;
	String[] imageUrls;

	/*
	 * public View mMapViewContainer; public MapView mMapView;
	 */

	public LeftAndRightActivity() {
		super(R.string.left_and_right);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 PATH = this.getResources().getString(R.string.ip) +"AiBangServer/getAllHelpMessage.action";
		 PATH2 = this.getResources().getString(R.string.ip) +"AiBangServer/getLastHelpMessage.action";
		helpButton = (Button) findViewById(R.id.left_behind_btn_help);
		helpButton.setOnClickListener(new hOnclickListener());

		mapButton = (Button) findViewById(R.id.left_behind_btn_map);
		mapButton.setOnClickListener(new mOnclickListener());

		personalmsgButton = (Button) findViewById(R.id.left_behind_btn_personalmsg);

		personalmsgButton = (Button) findViewById(R.id.left_behind_btn_personalmsg);

		personalmsgButton.setOnClickListener(new msgOnclickListener());
		commentButton = (Button) findViewById(R.id.left_behind_btn_comment);
		commentButton.setOnClickListener(new cOnclickListener());
		personalPageButton = (Button) findViewById(R.id.left_behind_btn_personalPage);
		personalPageButton.setOnClickListener(new pageOnclickListener());
		search = (ImageView) findViewById(R.id.activity_home_page_tv_search);

		// search.setOnClickListener(new sOnclickListener());

		setSlidingMenu();
		/*
		 * try { initData(); } catch (JSONException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); }
		 */

		spinner = (Spinner) findViewById(R.id.activity_home_page_spinner_school);
		
		final TextView edit = (TextView) findViewById(R.id.activity_home_page_tv_edit);
		listView = (MyListView) findViewById(R.id.activity_home_page_mlv_listview);

		edit.setOnClickListener(new eOnClickListener());

		ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter
				.createFromResource(this, R.array.school_type,
						R.layout.my_spinner);
		// Specify the layout to use when the list of choices appears
		adapterSpinner.setDropDownViewResource(R.layout.my_spinne2);
		spinner.setAdapter(adapterSpinner);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HandlerHTTP handler = new HandlerHTTP(listView);
				school = (String) spinner.getItemAtPosition(position);
				new LeftAndRightActivity().getResponseThread(listView, handler, PATH,school);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		school = spinner.getSelectedItem().toString();
		Log.e(TAG, school);
		
		HandlerHTTP handler = new HandlerHTTP(listView);

		new LeftAndRightActivity().getResponseThread(listView, handler, PATH,school);

		/*
		 * listView.setDividerHeight(1); adapter = new MyAdapter();
		 * listView.setAdapter(adapter);
		 */

		listView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						HandlerHTTP handler = new HandlerHTTP(listView);
						new LeftAndRightActivity().getResponseThread(listView,
								handler, PATH,school);
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

				}.execute();
			}
		});
	}

	class hOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(LeftAndRightActivity.this,
					LeftAndRightActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}

	}

	class mOnclickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String school = null;
			final Spinner spinner = (Spinner) findViewById(R.id.activity_home_page_spinner_school);
			school = spinner.getSelectedItem().toString();
			Intent intent = null;
			if(school.equals("江西师范大学")){
				intent = new Intent(LeftAndRightActivity.this,
						MapActivity.class);
			}else if(school.equals("南昌大学")){
				intent = new Intent(LeftAndRightActivity.this,
						Map2Activity.class);
			}
			
			intent.putExtra(EXTRA_MESSAGE,school);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	class msgOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LeftAndRightActivity.this, null);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}

	}

	class cOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LeftAndRightActivity.this, null);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}

	}

	class pageOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LeftAndRightActivity.this,
					PersonalPageActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}

	}

	class sOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LeftAndRightActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	// 初始化SlidingMenu
	public void setSlidingMenu() {
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setContentView(R.layout.activity_home_page);

		getSlidingMenu().setSecondaryMenu(R.layout.right_behind);
		getSlidingMenu().setSecondaryShadowDrawable(
				R.drawable.shadowright_shape);

		getSlidingMenu().setSecondBehindOffsetRes(
				R.dimen.slidingmenu_right_offset);
	}

	class MyAdapter extends BaseAdapter {
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.activity_home_page_item, null);

			TextView tv = (TextView) convertView
					.findViewById(R.id.activity_home_page_item_tv_user_name);
			tv.setText(data.get(position).getUserName());

			tv = (TextView) convertView
					.findViewById(R.id.activity_home_page_item_tv_location_name);
			tv.setText(data.get(position).getAddressName());

			tv = (TextView) convertView
					.findViewById(R.id.activity_home_page_item_tv_time);
			tv.setText(data.get(position).getHelpTime());

			tv = (TextView) convertView
					.findViewById(R.id.activity_home_page_item_tv_content);
			tv.setText(data.get(position).getHelpContent());

			tv = (TextView) convertView
					.findViewById(R.id.activity_home_page_item_tv_countmsg);
			tv.setText(data.get(position).getComment());
			
			tv = (TextView) convertView.findViewById(R.id.activity_home_page_tv_gift);
			tv.setText(data.get(position).getGift());

			ImageView iv = (ImageView) convertView
					.findViewById(R.id.activity_home_page_item_head_portrait);
			/*
			 * iv.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub startImagePagerActivity(position); }
			 * 
			 * });
			 */

			Drawable drawable = getResources().getDrawable(
					data.get(position).getPhotoPotrait());
			setHeadPortrait(iv, drawable);

			LinearLayout ll = (LinearLayout) convertView
					.findViewById(R.id.activity_home_page_item_ll);
			ll.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HelpMessage hp = data.get(position);
					Intent intent = new Intent(LeftAndRightActivity.this,
							HelpContentActivity.class);

					intent.putExtra(EXTRA_MESSAGE, hp);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
				}
			});

			/*
			 * ImageView ivPic = (ImageView)
			 * convertView.findViewById(R.id.activity_home_page_item_iv_pic);
			 * Drawable drawablePic =
			 * getResources().getDrawable(R.drawable.girl);
			 * setHeadPortrait(ivPic,drawablePic);
			 */

			return convertView;
		}

		public long getItemId(int position) {
			return position;
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public int getCount() {
			return data.size();
		}
	}

	public void setHeadPortrait(ImageView iv, Drawable drawable) {

		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();

		BitmapDrawable bbb = new BitmapDrawable(PictureUtlis.toRoundCorner(
				bitmap, 10));
		iv.setImageDrawable(bbb);
	}

	class eOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
			Intent intent = new Intent(LeftAndRightActivity.this,
					SendHelpMsgActivity.class);
			userID = getIntent().getStringExtra(LoginActivity.USERID);
			Log.e(TAG, userID);
			intent.putExtra(LoginActivity.USERID, userID);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	public void initData(JSONArray jsonArray) throws JSONException {
		data = new LinkedList<HelpMessage>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			HelpMessage hp = new HelpMessage();
			hp.setUserName(jsonObject.getString("userName"));
			hp.setHelpTime(jsonObject.getString("helpTime"));
			hp.setHelpContent(jsonObject.getString("helpContent"));
			hp.setAddressName(jsonObject.getString("locationName"));
			hp.setGift(jsonObject.getString("gift"));
			hp.setComment(jsonObject.getString("comment"));
			hp.setX(Float.valueOf(jsonObject.getString("x")));
			hp.setY(Float.valueOf(jsonObject.getString("y")));
			hp.setPhotoPotrait(R.drawable.a102);
			data.add(hp);
		}
		
		
		
	}

	private void getResponseThread(final MyListView listView,
			final HandlerHTTP handlerHTTP, final String PATH,final String school) {
		new Thread(new Runnable() {
			public void run() {
				String resultHTTP = null;
				HashMap<String, String> params = new HashMap<String, String>();
				Log.e(TAG, school);
				params.put("school", school);
				try {
					resultHTTP = hu.doPost(PATH,params);
					Log.e(TAG, resultHTTP);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.obj = resultHTTP;
				handlerHTTP.sendMessage(msg);
			}
		}).start();
	}

	class HandlerHTTP extends Handler {
		MyListView listView;

		public HandlerHTTP(MyListView listView) {
			this.listView = listView;
		}

		public HandlerHTTP() {

		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.obj == null) {
				Toast.makeText(
						LeftAndRightActivity.this.getApplicationContext(),
						"connect failed", Toast.LENGTH_SHORT).show();
			} else {
				String json = msg.obj.toString();
				Log.e(TAG, json);
				try {
					JSONArray jsonArray = new JSONArray(json);
					try {
							initData(jsonArray);					

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listView.setDividerHeight(1);
					adapter = new MyAdapter();
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePageActivity.class);
		intent.putExtra(IMAGES, imageUrls);
		intent.putExtra(IMAGES_POSITION, position);
		startActivity(intent);
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
