package edu.jxsd.x3510.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.jxsd.x3510.activity.LoginActivity.HandlerHTTP;
import edu.jxsd.x3510.http.HttpUtility;

@SuppressLint("ShowToast")
public class RegisterActivity extends Activity {

	public static String TAG = "RegisterActivity";
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public static String PATH = "http://10.3.129.225:8080/AiBangServer/register.action";
	/*
	 * public static final String PATH =
	 * "http://192.168.1.101:8080/AiBangServer/register.action";
	 */
	public static final int SEX_TYPE = 0;
	public static final int SCHOOL_TYPE = 1;
	public static final int AGE_TYPE = 2;
	private EditText email;
	private EditText password;
	private LinearLayout school;
	private LinearLayout age;
	private LinearLayout sex;
	private EditText userName;
	private Button submit;
	private TextView tvAge;
	private TextView tvSchool;
	private TextView tvSex;

	private String emailString;
	private String passwordString;
	private String schoolString;
	private String sexString;
	private String userNameString;
	private String ageString;

	HttpUtility hu = new HttpUtility();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PATH = this.getResources().getString(R.string.ip) +"AiBangServer/register.action";
		setContentView(R.layout.activity_register);
		findEditTextID();
		sex.setOnClickListener(new sexOnClickListener());
		school.setOnClickListener(new schoolOnClickListener());
		age.setOnClickListener(new ageOnClickListener());
		submit.setOnClickListener(new submitOnClickListener());
	}

	class sexOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView sex = (TextView) findViewById(R.id.activity_register_tv_choice_sex);
			DialogType newDialog = DialogType.newInstance(SEX_TYPE, sex);
			newDialog.show(getFragmentManager(), "alert msg");
		}

	}

	class schoolOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView school = (TextView) findViewById(R.id.activity_register_tv_choice_school);
			DialogType newDialog = DialogType.newInstance(SCHOOL_TYPE, school);
			newDialog.show(getFragmentManager(), "alert msg");
		}

	}

	class ageOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView age = (TextView) findViewById(R.id.activity_register_tv_choice_age);
			DialogType newDialog = DialogType.newInstance(AGE_TYPE, age);
			newDialog.show(getFragmentManager(), "alert msg");
		}

	}

	class submitOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			emailString = email.getText().toString();
			passwordString = password.getText().toString();
			userNameString = userName.getText().toString();
			ageString = tvAge.getText().toString();
			schoolString = tvSchool.getText().toString();
			sexString = tvSex.getText().toString();

			Map<String, String> params = new HashMap<String, String>();
			params.put("actionType", "register");
			params.put("userName", userNameString);
			params.put("password", passwordString);
			params.put("age", ageString);
			params.put("school", schoolString);
			params.put("sex", sexString);
			params.put("email", emailString);
			HandlerHTTP handler = new HandlerHTTP(v);
			try {
				new RegisterActivity().getResponseThread(params,handler);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void getResponseThread(final Map<String, String> params,final HandlerHTTP handlerHTTP) {
		new Thread(new Runnable() {
			public void run() {
				String resultHTTP = null;
				try {
					resultHTTP = hu.doPost(PATH, params);
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
		View v;

		public HandlerHTTP(View v) {
			this.v = v;
		}

		public HandlerHTTP() {

		}

		public void handleMessage(Message msg) {
			if(msg.obj == null){
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "connect failed",Toast.LENGTH_SHORT).show();
			}else if(msg.obj.toString().equals("failed")){				
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "register failed",Toast.LENGTH_SHORT).show();
			}else if(msg.obj.toString().equals("email")){				
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "邮箱已注册",Toast.LENGTH_SHORT).show();
			}else if(msg.obj.toString().equals("userName")){				
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "用户名已注册",Toast.LENGTH_SHORT).show();
			}else if(msg.obj.toString().equals("success")){
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "register success",Toast.LENGTH_SHORT).show();
				startIndex(v);
			}			
		}
	}
	
	/*private Handler handlerHTTP = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.obj == null){
				Toast.makeText(RegisterActivity.this.getApplicationContext(), "connect failed",Toast.LENGTH_SHORT).show();
			}
		}
	};*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

	public void findEditTextID() {
		email = (EditText) findViewById(R.id.activity_register_et_register_email);
		password = (EditText) findViewById(R.id.activity_register_et_register_password);
		userName = (EditText) findViewById(R.id.activity_register_et_register_user_name);
		age = (LinearLayout) findViewById(R.id.activity_register_ll_register_age);
		sex = (LinearLayout) findViewById(R.id.activity_register_ll_register_sex);
		school = (LinearLayout) findViewById(R.id.activity_register_ll_register_school);
		submit = (Button) findViewById(R.id.activity_register_bt_submit);
		tvAge = (TextView) findViewById(R.id.activity_register_tv_choice_age);
		tvSchool = (TextView) findViewById(R.id.activity_register_tv_choice_school);
		tvSex = (TextView) findViewById(R.id.activity_register_tv_choice_sex);
	}

	public void startIndex(View v) {
		Intent intent = new Intent(getApplicationContext(),
				LoginActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
}
