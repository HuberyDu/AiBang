package edu.jxsd.x3510.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import edu.jxsd.x3510.activity.UploadUtil.OnUploadProcessListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class PersonalInformationActivity extends Activity implements OnClickListener,OnUploadProcessListener{

	private static String  requestURL = "http://10.3.131.171:8080/AiBangServer/file.action";
	public static final int SEX_TYPE = 0;
	public static final int SCHOOL_TYPE = 1;
	public static final int AGE_TYPE = 2;

	public static final int PICTURE_TYPE = 3;
	public static final int GIFT_TYPE = 4;

	private final int REQ_CODE_CAMERA = 5;
	private final int REQ_CODE_PICTURE = 6;
	private final int PHOTO_REQUEST_CUT = 7;
	private static final int CROP_SMALL_PICTURE = 8;
	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 9;  
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 10;  //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 11;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 12;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 13;
	public static final int MAX = 140;

	private RelativeLayout changePersonalPhoto;
	private RelativeLayout editName;
	private RelativeLayout chooseAge;
	private RelativeLayout chooseSex;
	private RelativeLayout chooseSchool;
	private RelativeLayout editPersonlized_signature;

	private Button modifyBcak;

	private static final String TAG = "CameraActivity";
	private FormatPhotoName formatPhotoName;
	private Uri photoUri;
	private File photoDir;
	private File photoAbsoluteDir;
	private String photoName;
    /**
     *从Intent获取图片路径的Key
     */
	public static final String KEY_PHOTO_PATH = "photo_path";
    /**获取图片的路径*/
	private String picPath;
	private Intent lastIntent;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_information);

		changePersonalPhoto = (RelativeLayout) this.findViewById(R.id.activity_modify_infomation_rv_personal_photo);
		editName = (RelativeLayout) this.findViewById(R.id.activity_modify_infomation_rl_nameEdit);
		chooseAge = (RelativeLayout) this.findViewById(R.id.activity_modify_infomation_rl_chooseAge);
		chooseSex = (RelativeLayout) this.findViewById(R.id.activity_modify_infomation_rl_chooseSex);
		chooseSchool = (RelativeLayout) this.findViewById(R.id.activity_modify_infomation_rl_chooseSchool);
		editPersonlized_signature = (RelativeLayout) this.findViewById(R.id.acticity_modify_infomation_rl_personalized_signatureEdit);
		modifyBcak = (Button) findViewById(R.id.activity_modify_infomation_btn_modifyBack);

		changePersonalPhoto.setOnClickListener(new imageOnClickListener());
		editName.setOnClickListener(new nameOnClickListener());
		chooseAge.setOnClickListener(new ageOnClickListener());
		chooseSex.setOnClickListener(new sexOnClickListener());
		chooseSchool.setOnClickListener(new schoolOnClickListener());
		editPersonlized_signature.setOnClickListener(new psOnClickListener());
		modifyBcak.setOnClickListener(new backOnclickListener());
		
		lastIntent = getIntent();
	}
	class backOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 8);
		intent.putExtra("aspectY", 8);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public final class imageOnClickListener implements OnClickListener {
		public void onClick(View v) {
			new AlertDialog.Builder(PersonalInformationActivity.this)
					.setTitle("上传图片")
					.setItems(R.array.picture_type,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int which) {
									String sdState = Environment.getExternalStorageState();// 获得sd卡的状态
									if (!sdState.equals(Environment.MEDIA_MOUNTED)) { // 判断SD卡是否存在
										Toast.makeText(PersonalInformationActivity.this,R.string.sd_card,Toast.LENGTH_SHORT).show();// 提示sd卡不存在
										Log.i(TAG, "" + R.string.sd_card);
										return;
									}
									switch (which) {
									case 0://实现拍照获取图片
										Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
										i.putExtra(MediaStore.Images.Media.ORIENTATION,0);
										photoDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DCIM/Camera");// 照片存储路径
										if (!photoDir.exists()) {
											photoDir.mkdirs();// 如果文件不存在创建
										}
										formatPhotoName = new FormatPhotoName();// 实例化对象
										photoName = formatPhotoName.getPhotoName(new Date()); // 获得当前时间的字符串表示
										photoAbsoluteDir = new File(photoDir,photoName);// 照片的绝对路径
										Log.e(TAG, "photeabsolutedir is :"+ photoAbsoluteDir);
										photoUri = Uri.fromFile(photoAbsoluteDir);
										i.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);// 设置图像的Uri存储地址
										// scalePicture(photoName,100,100);//对图片进行压缩
										startActivityForResult(i,REQ_CODE_CAMERA);
										break;
									case 1:
										Intent intent = new Intent(); // "android.intent.action.GET_CONTENT"
										intent.setAction(Intent.ACTION_GET_CONTENT);
										String contentType = "image/*";
										intent.setType(contentType); // 查看类型 String IMAGE_UNSPECIFIED = "image/*";						
										Intent wrapperIntent = Intent.createChooser(intent, null);
										intent.putExtra("crop", "true");
										intent.putExtra("aspectX", 2);
										intent.putExtra("aspectY", 1);
										intent.putExtra("outputX", 150);
										intent.putExtra("outputY", 150);
										intent.putExtra("scale", true);
										intent.putExtra("return-data", true);
										intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
										intent.putExtra("noFaceDetection", true); // no face detection
										startActivityForResult(wrapperIntent,REQ_CODE_PICTURE);
										break;
									}
								}
							}).create().show();
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ImageView image = (ImageView) findViewById(R.id.activity_modify_infomation_iv_personal_photo);
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode,data);
			switch (requestCode) {
			case REQ_CODE_CAMERA: // 相机拍照好后进行保存
				cropImageUri(photoUri, 150, 150, CROP_SMALL_PICTURE);
				Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();// 提示图片已经保存
				// 发送一个广播更新媒体数据库信息
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+ Environment.getExternalStorageDirectory().getAbsolutePath())));
				image.setImageURI(photoUri);
				
				break;
			case CROP_SMALL_PICTURE:
				if (photoUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(photoUri);
					image.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				break;
			case REQ_CODE_PICTURE: // 从相册中获取图片
				if(data == null){
					Log.e(TAG, "REQ_CODE_PICTURE:" + data);
					Toast.makeText(this, "选择文件00出错", Toast.LENGTH_LONG).show();
					return;
				}
				photoUri = data.getData();
		    
	             //显得到bitmap图片
	  
				if(photoUri == null){
					Toast.makeText(this, "选择文件11出错", Toast.LENGTH_LONG).show();
					return;
				}
				String [] pojo = {MediaStore.Images.Media.DATA};
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
				if(cursor != null){
					int columIndex = cursor.getColumnIndex(pojo[0]);
					cursor.moveToFirst();
					picPath = cursor.getString(columIndex);
					cursor.close();
				}
				Log.i(TAG, "imagePath = "+picPath);
				if(picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))){
					lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
					setResult(Activity.RESULT_OK, lastIntent);
					finish();
				}else{
					Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
				}
				if (data != null){
					Bitmap bm = BitmapFactory.decodeFile(picPath);
					image.setImageBitmap(bm);
										
					if(picPath != null){
						handler.sendEmptyMessage(TO_UPLOAD_FILE);
					}else{
						Toast.makeText(this, "上传路径出错", Toast.LENGTH_LONG).show();
					}
					
				}
				/*if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");//裁剪功能
					image.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}*/
				break;
			case PHOTO_REQUEST_CUT:
				break;
			}

		}

	}
	private void doPhoto(int requestCode, Intent data) {
		if(requestCode == REQ_CODE_PICTURE){
			if(data == null){
				Log.e(TAG, "REQ_CODE_PICTURE:" + data);
				Toast.makeText(this, "选择文件00出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();  
			if(photoUri == null){
				Toast.makeText(this, "选择文件11出错", Toast.LENGTH_LONG).show();
				return;
			}
			String [] pojo = {MediaStore.Images.Media.DATA};
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
			if(cursor != null){
				int columIndex = cursor.getColumnIndex(pojo[0]);
				cursor.moveToFirst();
				picPath = cursor.getString(columIndex);
				cursor.close();
			}
			Log.i(TAG, "imagePath = "+picPath);
			if(picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))){
				lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
				setResult(Activity.RESULT_OK, lastIntent);
				finish();
			}else{
				Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
			}
		}
		
	}

	private void toUploadFile(){
		String filekey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this);
		Map <String, String> params = new HashMap <String, String>();
		params.put("orderId","11111");
		uploadUtil.uploadFile(picPath, filekey, requestURL, params);
	}
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg){
    		switch (msg.what) {
    		case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			case UPLOAD_INIT_PROCESS:
				//progressBar.setMax(msg.arg1);
				break;
			case UPLOAD_IN_PROCESS:
				//progressBar.setProgress(msg.arg1);
				break;
			case UPLOAD_FILE_DONE:
				@SuppressWarnings("unused")
				String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
				//uploadImageResult.setText(result);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
    	}
    };
    /**
	 * 上传服务器响应回调
	 */
	@Override
	public void onUploadDone(int responseCode, String message) {
		//progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}
@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg );
	}
	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg );
	}
	// 读取图片进行压缩：
	public static Bitmap scalePicture(String filename, int maxWidth,
			int maxHeight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			BitmapFactory.decodeFile(filename, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int desWidth = 0;
			int desHeight = 0;
			// 缩放比例
			double ratio = 0.0;
			if (srcWidth > srcHeight) {
				ratio = srcWidth / maxWidth;
				desWidth = maxWidth;
				desHeight = (int) (srcHeight / ratio);
			} else {
				ratio = srcHeight / maxHeight;
				desHeight = maxHeight;
				desWidth = (int) (srcWidth / ratio);
			}
			// 设置输出宽度、高度
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inSampleSize = (int) (ratio) + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outWidth = desWidth;
			newOpts.outHeight = desHeight;
			bitmap = BitmapFactory.decodeFile(filename, newOpts);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	// 读取头像的大图
	public void show_click(View v) {
		startActivity(new Intent(this, PersonalimageShower.class));
	}

	// 选择年龄
	public final class ageOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		public void onClick(View v) {
			TextView age = (TextView) findViewById(R.id.activity_modify_infomation_tv_ageEdit);
			DialogType newDialog = DialogType.newInstance(AGE_TYPE, age);
			newDialog.show(getFragmentManager(), "alert msg");
		}
	}

	public final class schoolOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		public void onClick(View v) {
			TextView school = (TextView) findViewById(R.id.activity_modify_infomation_tv_schoolEdit);
			DialogType newDialog = DialogType.newInstance(SCHOOL_TYPE, school);
			newDialog.show(getFragmentManager(), "alert msg");
		}
	}

	public final class sexOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		public void onClick(View v) {
			TextView sex = (TextView) findViewById(R.id.activity_modify_infomation_tv_sexEdit);
			DialogType newDialog = DialogType.newInstance(SEX_TYPE, sex);
			newDialog.show(getFragmentManager(), "alert msg");
		}
	}

	public final class psOnClickListener implements OnClickListener {

		public void onClick(View v) {
			editPersonlized_signature(v);
		}
	}

	public void editPersonlized_signature(View v) {
		Intent intent = new Intent(this, Personlized_signature.class);
		startActivity(intent);

	}

	public final class nameOnClickListener implements OnClickListener {
		public void onClick(View v) {
			editName(v);
		}
	}

	public void editName(View v) {
		Intent intent = new Intent(this, NameEdit.class);
		startActivity(intent);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
