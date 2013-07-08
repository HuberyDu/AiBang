package edu.jxsd.x3510.activity;

import edu.jxsd.x3510.activity.R.id;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class WhatForthActivity extends Activity {
   private TextView help;
   private TextView text;
   private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_what_fourth);
		help = (TextView)findViewById(R.id.help);
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeueLTPro-ThEx.otf");
		help.setTypeface(typeface);
		Animation animation = AnimationUtils.loadAnimation(WhatForthActivity.this, R.anim.alpha);
		help.startAnimation(animation);
		text = (TextView)findViewById(R.id.what4_text);
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/huawenheti.ttf");
		text.setTypeface(typeface1);
		text.startAnimation(animation);
		btn = (Button)findViewById(R.id.whats4_btn);
		btn.setOnClickListener(new BListener());
	}
	class BListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent (WhatForthActivity.this,LoginActivity.class);			
			startActivity(intent);	
		}
		
	}


}
