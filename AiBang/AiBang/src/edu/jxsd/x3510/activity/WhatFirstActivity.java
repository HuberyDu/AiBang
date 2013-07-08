package edu.jxsd.x3510.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatFirstActivity extends Activity {
   private ImageView btn;
   private TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_what_first);
		btn = (ImageView)findViewById(R.id.whats1_btn);
		btn.setOnClickListener(new Blisteber());
		text = (TextView)findViewById(R.id.what1_text);
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/huawenheti.ttf");
		text.setTypeface(typeface); 
		Animation animation = AnimationUtils.loadAnimation(WhatFirstActivity.this, R.anim.alpha);
		text.startAnimation(animation);
	}

	class Blisteber implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent (WhatFirstActivity.this,WhatSecondActivity.class);			
			startActivity(intent);	
			overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.what1, menu);
		return true;
	}

}
