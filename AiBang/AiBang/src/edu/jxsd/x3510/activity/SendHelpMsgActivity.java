package edu.jxsd.x3510.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.jxsd.x3510.activity.LoginActivity.HandlerHTTP;
import edu.jxsd.x3510.http.HttpUtility;

public class SendHelpMsgActivity extends Activity {
	public static String TAG = "SendHelpMsgActivity";
	public static String PATH = "http://192.168.1.103:8080/AiBangServer/sendHelpMessage";
	
	private final int REQ_CODE_CAMERA = 0;
	private final int REQ_CODE_PICTURE = 1;
	private final int REQ_CODE_CANCLE = 2;
	public static final int PICTURE_TYPE = 3;
	public static final int GIFT_TYPE = 4;
	public static final int MAX = 140;
	private Button cancleButton;
	private ImageView picture_iv;
	private ImageView gift_iv;
	private ImageView location_iv;
	private EditText editHelp;
	private ImageView sendMsg;
	private TextView limit;
	private TextView locationName;
	private TextView gift;

	// private static final String TAG = "CameraActivity";
	private FormatPhotoName formatPhotoName;
	private Uri photoUri;
	private File photoDir;
	private File photoAbsoluteDir;
	private String photoName;

	private String editHelpString;
	private String giftString;
	private String locationNameString;
	private double latitude;
	private double longitude;
	
	private String userID = null;

	HttpUtility hu = new HttpUtility();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendhelpmsg);
		PATH = this.getResources().getString(R.string.ip) +"AiBangServer/sendHelpMessage";
		cancleButton = (Button) findViewById(R.id.activity_sendhelpmsg_btn_cancleButton);
		cancleButton.setOnClickListener(new cancleListener());
		picture_iv = (ImageView) findViewById(R.id.activity_sendhelpmsg_iv_picture_btn);
		picture_iv.setOnClickListener(new PListener());
		gift_iv = (ImageView) findViewById(R.id.activity_sendhelpmsg_iv_gift_btn);
		gift_iv.setOnClickListener(new GListener());
		location_iv = (ImageView) findViewById(R.id.activity_sendhelpmsg_iv_location_btn);
		location_iv.setOnClickListener(new LListener());
		limit = (TextView) findViewById(R.id.activity_sendhelpmsg_tv_text_limit);
		limit.setText(MAX + "");
		editHelp = (EditText) findViewById(R.id.activity_sendhelpmsg_et_edit_help);
		sendMsg = (ImageView) findViewById(R.id.activity_sendhelpmsg_iv_sendButton);
		locationName = (TextView) findViewById(R.id.activity_sendhelpmsg_tv_mapname);
		gift = (TextView) findViewById(R.id.activity_sendhelpmsg_tv_gift);
		
		userID =  getIntent().getStringExtra(LoginActivity.USERID);
		
		sendMsg.setOnClickListener(new sendMsgOnClickListener());

		editHelp.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int number = MAX - s.length();
				limit.setText("" + number);
				selectionStart = editHelp.getSelectionStart();
				selectionEnd = editHelp.getSelectionEnd();
				if (temp.length() > MAX) {
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					editHelp.setText(s);
					editHelp.setSelection(tempSelection);
				}
			}
		});
	}

	class sendMsgOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			editHelpString = editHelp.getText().toString();
			giftString = gift.getText().toString();
			locationNameString = locationName.getText().toString();

			Time time = new Time("GMT+8");
			time.setToNow();

			Map<String, String> params = new HashMap<String, String>();
			params.put("helpContent", editHelpString);
			params.put("locationName", locationNameString);
			params.put("gift", giftString);
			params.put("latitude", Double.toString(latitude));
			params.put("longitude", Double.toString(longitude));
			params.put("time", time.toString());
			params.put("userID",userID);
			HandlerHTTP handler = new HandlerHTTP(v);
			new SendHelpMsgActivity().getResponseThread(params,handler);
		}
	}

	private void getResponseThread(final Map<String, String> params,
			final HandlerHTTP handlerHTTP) {
		new Thread(new Runnable() {

			public void run() {
				String resultHTTP = null;
				try {
					resultHTTP = hu.doPost(PATH, params);
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
		View v;

		public HandlerHTTP(View v) {
			this.v = v;
		}

		public HandlerHTTP() {

		}

		public void handleMessage(Message msg) {
			if (msg.obj == null) {
				Toast.makeText(SendHelpMsgActivity.this.getApplicationContext(),
						"connect failed", Toast.LENGTH_SHORT).show();
			} else if (msg.obj.toString().equals("failed")) {
				Toast.makeText(SendHelpMsgActivity.this.getApplicationContext(),
						"send failed", Toast.LENGTH_SHORT).show();
			} else if (msg.obj.toString().equals("success")) {
				Toast.makeText(SendHelpMsgActivity.this.getApplicationContext(),
						"send success", Toast.LENGTH_SHORT).show();
			startIndex(v);
			}
		}
	}
	
	public void startIndex(View v) {
		Intent intent = new Intent(getApplicationContext(),
				LeftAndRightActivity.class);
		Log.e(TAG, userID);
		intent.putExtra(LoginActivity.USERID, userID);
		startActivity(intent);

		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	class cancleListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			/*
			 * Intent intent = new
			 * Intent(SendHelpMsgActivity.this,RegisterActivity.class);
			 * startActivity(intent);
			 */
		}

	}

	class PListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*
			 * DialogType newDialog = DialogType.newInstance(PICTURE_TYPE,
			 * picture_iv); newDialog.show(getFragmentManager(), "alert msg");
			 */
			new AlertDialog.Builder(SendHelpMsgActivity.this)
					.setTitle("上传图片")
					.setItems(R.array.picture_type,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String sdState = Environment
											.getExternalStorageState();// 获得sd卡的状态
									if (!sdState
											.equals(Environment.MEDIA_MOUNTED)) { // 判断SD卡是否存在
										Toast.makeText(
												SendHelpMsgActivity.this,
												R.string.sd_card,
												Toast.LENGTH_SHORT).show();// 提示sd卡不存在
										Log.i(TAG, "" + R.string.sd_card);
										return;
									}
									photoDir = new File(Environment
											.getExternalStorageDirectory()
											.getAbsolutePath()
											+ "/DCIM/Camera");// 照片存储路径
									Log.e(TAG, "photoDir is :" + photoDir);
									if (!photoDir.exists()) {
										photoDir.mkdirs();// 如果文件不存在创建
									}
									formatPhotoName = new FormatPhotoName();// 实例化对象
									photoName = formatPhotoName
											.getPhotoName(new Date()); // 获得当前时间的字符串表示
									photoAbsoluteDir = new File(photoDir,
											photoName);// 照片的绝对路径
									Log.e(TAG, "photoAbsoluteDir is :"
											+ photoAbsoluteDir);
									switch (which) {
									case 0:
										Intent i = new Intent(
												MediaStore.ACTION_IMAGE_CAPTURE);
										// i.putExtra(MediaStore.Images.Media.,
										// 0);
										photoUri = Uri
												.fromFile(photoAbsoluteDir);
										i.putExtra(MediaStore.EXTRA_OUTPUT,
												photoUri);// 设置图像的Uri存储地址
										startActivityForResult(i,
												REQ_CODE_CAMERA);
										break;
									case 1:
										Intent intent = new Intent(
												Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
										String contentType = "image/*";
										intent.setType(contentType); // 查看类型
																		// String
																		// IMAGE_UNSPECIFIED
																		// =
																		// "image/*";
										Intent wrapperIntent = Intent
												.createChooser(intent, null);
										startActivityForResult(wrapperIntent,
												REQ_CODE_PICTURE);
										break;
									case 2:
										Intent intent1 = new Intent();
										startActivityForResult(intent1,
												REQ_CODE_CANCLE);
									}
								}
							}).create().show();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ImageView image = (ImageView) findViewById(R.id.activity_sendhelpmsg_pic_view);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQ_CODE_CAMERA: // 相机拍照
				/*
				 * Bitmap camerabmp = (Bitmap) data.getExtras().get("data");
				 * image.setImageBitmap(camerabmp);
				 * image.setVisibility(View.VISIBLE);
				 */
				Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT)
						.show();// 提示图片已经保存
				// 发送一个广播更新媒体数据库信息
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse("file://"
								+ Environment.getExternalStorageDirectory())));
				// Bitmap camerabmp = (Bitmap) data.getExtras().get(photoName);
				image.setImageURI(photoUri);
				// PictureUtil.getSmallBitmap(photoName);
				image.setVisibility(View.VISIBLE);
				break;
			case REQ_CODE_PICTURE: // 从相册中获取图片
				try {
					Uri uri = data.getData();
					Log.e("uri", uri.toString());
					ContentResolver cr = this.getContentResolver();
					try {
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						image.setImageBitmap(bitmap);
						image.setVisibility(View.VISIBLE);
					} catch (FileNotFoundException e) {
						Log.e("Exception", e.getMessage(), e);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case REQ_CODE_CANCLE:// 取消照片
				image.setImageURI(null);

			}

		}

	}

	class GListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView gift_tv = (TextView) findViewById(R.id.activity_sendhelpmsg_tv_gift);
			DialogType newDialog = DialogType.newInstance(GIFT_TYPE, gift_tv);
			newDialog.show(getFragmentManager(), "alert msg");
		}

	}

	class LListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			openGPSSettings();
			getLocation();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			return;
		}

		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面

	}

	private void getLocation() {
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		updateToNewLocation(location);
		/*
		 * // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
		 * locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
		 * locationListener);
		 */
	}

	private void updateToNewLocation(Location location) {

		TextView mapName;
		mapName = (TextView) this
				.findViewById(R.id.activity_sendhelpmsg_tv_mapname);
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			String address = null;
			try {
				address = queryLocationG(latitude, longitude);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapName.setVisibility(View.VISIBLE);
			mapName.setText(address);
		} else {
			mapName.setText("无法获取地理信息");
		}
	}

	public String queryLocationG(double lat, double lng) {
		Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
		String address = "地址没找到";
		StringBuilder sb = new StringBuilder();
		try {
			List<Address> add = gc.getFromLocation(lat, lng, 1);
			StringBuilder bb = new StringBuilder();
			if (add.size() > 0) {
				Address ad = add.get(0);
				/* bb.append(ad.getAddressLine(0)).append("\n"); */
				/* bb.append(ad.getAddressLine(1)); */
				bb.append(ad.getAddressLine(2));
				sb.append(bb);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		address = sb.toString();
		Log.e(TAG, address);
		return address;
	}
}
