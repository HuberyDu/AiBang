package edu.jxsd.x3510.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PersonalPageActivity extends Activity {
	    private TextView countThs;
	    private TextView countConcern;
	    private TextView countFans;
	    private Button personalPage;
	    private Button modify;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_page);
		countThs = (TextView)findViewById(R.id.activity_personal_page_tv_count_ths);
		countConcern = (TextView)findViewById(R.id.activity_personal_page_tv_count_concern);
		countFans = (TextView)findViewById(R.id.activity_personal_page_tv_count_fans);
        personalPage = (Button)findViewById(R.id.activity_personal_page_btn_cancleButton);
        personalPage.setOnClickListener(new pOnclickListener());
        modify = (Button)findViewById(R.id.avtivity_presonal_page_btn_modify);
        modify.setOnClickListener(new sOnclickListener());
         
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeueLTPro-ThEx.otf");
        countThs.setTypeface(typeface);
        countConcern.setTypeface(typeface);
        countFans.setTypeface(typeface);
        
	
	}
		
class pOnclickListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
	
}

      class sOnclickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(PersonalPageActivity.this, PersonalInformationActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
		}
    	  
      }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_page, menu);
		return true;
	}

}
