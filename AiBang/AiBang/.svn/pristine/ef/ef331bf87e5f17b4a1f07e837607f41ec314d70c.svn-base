package edu.jxsd.x3510.activity;
import java.util.ArrayList;
import edu.jxsd.x3510.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WhatsNewActivity extends Activity {
	// ��ҳ�ؼ�
	private ViewPager mViewPager;
	// ��5���ǵײ���ʾ��ǰ״̬��imageView
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private int currIndex = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whats_new);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);

		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);
		mPage3 = (ImageView) findViewById(R.id.page3);

		/*
		 * ������ÿһҳҪ��ʾ�Ĳ��֣����Ӧ����Ҫ���ص����������ʾ������ �Լ���Ҫ��ʾ����ҳ��
		 */
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.activity_what_first, null);
		View view2 = mLi.inflate(R.layout.activity_what_second, null);
		View view3 = mLi.inflate(R.layout.activity_what_third, null);
		View view4 = mLi.inflate(R.layout.activity_what_fourth, null);
		/*
		 * ���ｫÿһҳ��ʾ��view��ŵ�ArrayList������ ������ViewPager��������˳�����չʾ
		 */
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);


		// ���ViewPager�����������
		 PagerAdapter mPagerAdapter = new PagerAdapter() {
				
				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					return arg0 == arg1;
				}
				
				@Override
				public int getCount() {
					return views.size();
				}

				@Override
				public void destroyItem(View container, int position, Object object) {
					((ViewPager)container).removeView(views.get(position));
				}
				
				
				
				@Override
				public Object instantiateItem(View container, int position) {
					((ViewPager)container).addView(views.get(position));
					return views.get(position);
				}
			};
			
			mViewPager.setAdapter(mPagerAdapter);
	    }    
	    

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:				
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				Animation animation = AnimationUtils.loadAnimation(WhatsNewActivity.this, R.anim.alpha);
				mPage0.setAnimation(animation);
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				Intent intent = new Intent();
				intent.setClass(WhatsNewActivity.this,LoginActivity.class);
				startActivity(intent);
				break;
			}
			currIndex = arg0;
			Animation animation = AnimationUtils.loadAnimation(WhatsNewActivity.this, R.anim.alpha);
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			//mPage4.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}
/*    public void startbutton(View v) {  
      	Intent intent = new Intent();
		intent.setClass(WhatsNewActivity.this,LoginActivity.class);
		startActivity(intent);
		this.finish();
      }*/  
    
}