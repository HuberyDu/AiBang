package edu.jxsd.x3510.activity;

import java.util.HashMap;
import java.util.Map;

import edu.jxsd.x3510.activity.R;
import edu.jxsd.x3510.activity.LeftAndRightActivity.HandlerHTTP;
import edu.jxsd.x3510.http.HttpUtility;
import edu.jxsd.x3510.view.MyListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LoginActivity extends Activity {
	public static String TAG = "LoginActivity";
	// public static final String PATH ="http://192.168.1.103:8080/AiBangServer/login.action";

	public static String PATH ;
	public static String USERID = "edu.jxsd.x3510.activity.USERID";
	private EditText loginEmail;
	private EditText loginPassword;
	private Button loginButton;
	private TextView loginToRegister;
	private String loginEmailString;
	private String loginPasswordString;
	private String userID = null;
	private String resultHTTP = null;
	HttpUtility hu = new HttpUtility();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PATH = this.getResources().getString(R.string.ip) + "AiBangServer/login.action";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login); //
		loginEmail = (EditText) findViewById(R.id.activity_login_et_login_email);
		loginPassword = (EditText) findViewById(R.id.activity_login_et_login_password);

		loginButton = (Button) findViewById(R.id.activity_login_btn_login_submit);
		loginButton.setOnClickListener(new bOnClickListener());

		loginToRegister = (TextView) findViewById(R.id.activity_login_tv_login_to_register);
		loginToRegister.setOnClickListener(new rOnClickListener());

	}

	class bOnClickListener implements OnClickListener {
		public void onClick(View v) {
			loginEmailString = loginEmail.getText().toString();
			loginPasswordString = loginPassword.getText().toString();
			Map<String, String> params = new HashMap<String, String>();
			params.put("loginEmail", loginEmailString);
			params.put("loginPassword", loginPasswordString);
			HandlerHTTP handler = new HandlerHTTP(v);
			new LoginActivity().getResponseThread(params, handler);
			/*
			 * if (resultHTTP.equals("success")) { startIndex(v); } else {
			 * Toast.makeText(LoginActivity.this.getApplicationContext(),
			 * "你输入的账号或者密码有误，请重新输入", Toast.LENGTH_LONG).show(); }
			 */

		}

	}

	public void getResponseThread(final Map<String, String> params,
			final HandlerHTTP handlerHTTP) {
		new Thread(new Runnable() {
			public void run() {
				try {
					resultHTTP = hu.doPost(PATH, params);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.obj = resultHTTP;
				//Log.e(TAG, msg.obj.toString());
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
				Toast.makeText(LoginActivity.this.getApplicationContext(),
						"connect failed", Toast.LENGTH_SHORT).show();
			} else if (msg.obj.toString().equals("failed")) {
				Toast.makeText(LoginActivity.this.getApplicationContext(),
						"login failed", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(LoginActivity.this.getApplicationContext(),
						"login success", Toast.LENGTH_SHORT).show();
				userID = msg.obj.toString();
				startIndex(v);
			}
		}
	}

	/*
	 * @SuppressLint("HandlerLeak") private Handler handlerHTTP = new Handler()
	 * { public void handleMessage(Message msg) {
	 * 
	 * Log.e(TAG, msg.obj.toString()); String result = msg.obj.toString();
	 * if(result.equals("success")){ Intent intent = new Intent();
	 * intent.setClass(LoginActivity.this, LeftAndRightActivity.class);
	 * LoginActivity.this.startActivity(intent); }else{
	 * Log.e(TAG,"login error"); }
	 * 
	 * } };
	 */

	class rOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startRegister(v);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void startRegister(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public void startIndex(View v) {
		Intent intent = new Intent(getApplicationContext(),
				LeftAndRightActivity.class);
		Log.e(TAG, userID);
		intent.putExtra(USERID, userID);
		startActivity(intent);

		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
