package edu.jxsd.x3510.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class PersonalimageShower extends Activity {
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalimageshower);

		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 两秒后关闭后dialog
		new Handler().postDelayed(new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 2);
	}
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
