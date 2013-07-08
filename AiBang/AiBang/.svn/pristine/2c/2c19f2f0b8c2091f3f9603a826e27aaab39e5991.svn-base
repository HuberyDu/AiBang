package edu.jxsd.x3510.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoUpload extends Activity {
	// =============================================
	// private Bitmap photoCaptured;
	private String photoPath = "/sdcard/IMAGE_100225083437.jpg"; // "/sdcard/1.txt";

	private static final int TAKE_PHOTO_ID = Menu.FIRST;
	private static final int UPLOAD_PHOTO_ID = Menu.FIRST + 1;
	private static final int BACK_ID = Menu.FIRST + 3;

	private static final String HTTP_PROTOCOL = "http://";
	private static final String FILE_UPLOADER_URL = "/fileuploader/system/fileUpload";

	private String SD_CARD_TEMP_DIR = Environment.getExternalStorageDirectory()
			+ File.separator + "IMG.jpg";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("info", "enter LoginWindow.onCreate()!");
		super.onCreate(savedInstanceState);

		//setContentView(R.layout.func_selector);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (TAKE_PHOTO_ID == requestCode) {
			if (resultCode != RESULT_OK)
				return;
			Bundle extras = data.getExtras();
			try {
				Bitmap photoCaptured = (Bitmap) extras.get("data");
				ImageView img = new ImageView(this);
				img.setImageBitmap(photoCaptured);
				setContentView(img);

				// store to sd card
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				photoCaptured.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photoBytes = baos.toByteArray();

				File aFile = new File(getDatedFName(SD_CARD_TEMP_DIR));
				photoPath = aFile.getAbsolutePath();

				boolean b;
				if (aFile.exists())
					b = aFile.delete();
				// f.mkdirs();
				aFile.createNewFile(); // need add permission to manifest

				FileOutputStream fos = new FileOutputStream(aFile);
				fos.write(photoBytes);
				fos.close();

				Log.d("info", "onPictureTaken - wrote bytes: "
						+ photoBytes.length);

				Uri capturedImage = Uri
						.parse(android.provider.MediaStore.Images.Media
								.insertImage(getContentResolver(),
										aFile.getAbsolutePath(), null, null));
				Log.i("camera", "Selected image: " + capturedImage.toString());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (UPLOAD_PHOTO_ID == requestCode) {

		}
	}

	/*public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, TAKE_PHOTO_ID, 0, R.string.take_photo);
		menu.add(0, UPLOAD_PHOTO_ID, 0, R.string.upload_photo);
		menu.add(0, BACK_ID, 0, R.string.back_button);

		return true;
	}
*/
	// @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		boolean result = true;

		switch (item.getItemId()) {
		case TAKE_PHOTO_ID:
			Log.i("info", "ready to take photos!");

			Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(i, TAKE_PHOTO_ID);
			result = true;
			break;

		case UPLOAD_PHOTO_ID:
			uploadFile2Svr();
			break;

		case BACK_ID:
			this.finish();
			break;

		default:
			result = super.onMenuItemSelected(featureId, item);
			break;
		}

		return result;
	}

	public void uploadFile2Svr() {
		HttpClient httpclient = new DefaultHttpClient();
		String urlStr = new StringBuffer().append(HTTP_PROTOCOL)
				.append(/* SERVER_IP */ConfigWindow.getServerIp()).append(':')
				.append(/* SERVER_PORT */ConfigWindow.getServerPort())
				.append(FILE_UPLOADER_URL).toString();
		HttpPost httppost = new HttpPost(urlStr);

		String uploadMsg = "上传 照片失败！";
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// Your DATA
			nameValuePairs
					.add(new BasicNameValuePair("filename", ("IMAGE.jpg")));
			// nameValuePairs.add(new BasicNameValuePair("orderno", "1"));
			// nameValuePairs.add(new BasicNameValuePair("userid", "123"));
			// nameValuePairs.add(new BasicNameValuePair("attach_type", "1"));
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			File aFile = new File(photoPath);
			Log.i("info -- photoPath: ", photoPath);
			FileEntity fileEty = new FileEntity(aFile, "binary/octet-stream");
			httppost.setEntity(fileEty);
			httppost.addHeader("filename", /* ("IMAGE.jpg") */aFile.getName());

			HttpResponse response;
			response = httpclient.execute(httppost);
			// Log.i("info -- response: ",
			// response.getStatusLine().getReasonPhrase());

			Header[] headers = response.getAllHeaders();
			headers = response.getHeaders("resultcode");
			if (headers[0].getValue().equals("0")) {
				uploadMsg = "上传照片成功！";
			}

		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			uploadMsg += e.toString();
			Log.e("exception", e.toString());
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			uploadMsg += e.toString();
			Log.e("exception", e.toString());
		} catch (IOException e) {
			// e.printStackTrace();
			uploadMsg += e.toString();
			Log.e("exception", e.toString());
		} finally {
			Toast.makeText(PhotoUpload.this, uploadMsg, Toast.LENGTH_LONG)
					.show();
			httpclient.getConnectionManager().shutdown();
		}
	}

	public void uploadFile2Svr2() {
		BufferedReader in = null;

		HttpClient httpclient = new DefaultHttpClient();
		String urlStr = new StringBuffer().append(HTTP_PROTOCOL)
				.append(ConfigWindow.getServerIp())
				.append(ConfigWindow.getServerPort()).append(FILE_UPLOADER_URL)
				.toString();

		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		HttpURLConnection conn = null;
		DataOutputStream dos = null;

		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int maxBufferSize = 16 * 1024;

		try {
			// List<NameValuePair> nameValuePairs = new
			// ArrayList<NameValuePair>();
			// // Your DATA
			// nameValuePairs.add(new BasicNameValuePair("filename",
			// getDatedFName("IMAGE.jpg")) );
			// nameValuePairs.add(new BasicNameValuePair("orderno", "1"));
			// nameValuePairs.add(new BasicNameValuePair("userid", "123"));
			// nameValuePairs.add(new BasicNameValuePair("attach_type", "1"));
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(120000);

			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);

			// Use a post method.
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Connection", "Keep-Alive");

			conn.setRequestProperty("Content-Type",
			// "multipart/form-data;boundary=" + boundary);
					"application/x-www-form-urlencoded");

			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6 GTB6");
			// conn.setRequestProperty("accept-language", "zh-cn,zh;q=0.5");
			// conn.setRequestProperty("Content-Type",
			// "multipart/form-data;boundary="+ boundary);

			conn.connect();

			// OutputStream connOs = conn.getOutputStream();
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ "exsistingFileName" + "\"" + lineEnd);
			// dos.writeBytes(lineEnd);

			Log.i("info", "Headers are written");

			// upload file to webserver via http
			FileInputStream fileInputStream = new FileInputStream(photoPath);
			// create a buffer of maximum size
			int bytesAvailable = fileInputStream.available();
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];

			// read file and write it into form...

			int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.e("info", "File is written");
			fileInputStream.close();

			dos.flush();
			dos.close();
			dos = null;

			// response
			// HttpResponse response;
			// response = httpclient.execute(httppost);
			// response = httpclient.execute(conn.get);

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String result = sb.toString();
			Log.i("info", result);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					Log.e("error", ioe.toString());
				}
			}
		}
	}

	public static String getDatedFName(String fname) {
		StringBuffer result = new StringBuffer();

		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		String dateSfx = "_" + df.format(new Date());

		int idx = fname.lastIndexOf('.');
		if (idx != -1) {
			result.append(fname.substring(0, idx));
			result.append(dateSfx);
			result.append(fname.substring(idx));
		} else {
			result.append(fname);
			result.append(dateSfx);
		}

		return result.toString();
	}



}
