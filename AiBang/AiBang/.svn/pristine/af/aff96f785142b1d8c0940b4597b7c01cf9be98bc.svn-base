package edu.jxsd.x3510.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WhatThirdActivity extends Activity {
	private ImageView btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_what_third);
		btn = (ImageView)findViewById(R.id.whats3_btn);
		btn.setOnClickListener(new Blisteber());
	}
	class Blisteber implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent (WhatThirdActivity.this,WhatForthActivity.class);			
			startActivity(intent);	
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.what_third, menu);
		return true;
	}

}
