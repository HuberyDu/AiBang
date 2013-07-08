package edu.jxsd.x3510.activity;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import edu.jxsd.x3510.bean.HelpMessage;
import edu.jxsd.x3510.utlis.PictureUtlis;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class HelpContentActivity extends FragmentActivity implements OnItemSelectedListener,OnTouchListener ,OnGestureListener {
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private HelpMessage hp;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_content);     
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_help_content_ll_touch);
        hp = (HelpMessage) getIntent().getSerializableExtra(LeftAndRightActivity.EXTRA_MESSAGE);
        setUpMapIfNeeded();        
      
        gestureDetector=new GestureDetector(this, this);
        ll.setOnTouchListener(this);
		ll.setLongClickable(true); 
        initView();
        
    }

    public void initView(){
    	ImageView iv = (ImageView)findViewById(R.id.activity_help_content_head_iv_portrait);	
    	Drawable drawable = getResources().getDrawable(
				hp.getPhotoPotrait());
		setHeadPortrait(iv, drawable);
		
		TextView tv = (TextView) findViewById(R.id.activity_help_content_tv_user_name);
		tv.setText(hp.getUserName());
		
		tv = (TextView) findViewById(R.id.activity_help_content_tv_help_content);
		tv.setText(hp.getHelpContent());
		
		tv = (TextView) findViewById(R.id.activity_help_content_time);
		tv.setText(hp.getHelpTime());
		
		tv = (TextView) findViewById(R.id.activity_help_content_tv_countmsg);
		tv.setText(hp.getComment());
		
		
    }
    
    public void setHeadPortrait(ImageView iv, Drawable drawable) {

		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();

		BitmapDrawable bbb = new BitmapDrawable(PictureUtlis.toRoundCorner(
				bitmap, 10));
		iv.setImageDrawable(bbb);
	}
    
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setPolyLineOption();
    }

 
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_help_content_fragment_map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(hp.getX(),hp.getY())).title(hp.getHelpContent()).visible(true));       
        mMap.setMyLocationEnabled(true);
        mUiSettings = mMap.getUiSettings();
    }
    
    private void setPolyLineOption(){
    	PolylineOptions rectOptions = new PolylineOptions()
        .add(new LatLng(28.6851, 116.0237))
        .add(new LatLng(28.6747, 116.0249))  // North of the previous point, but at the same longitude
        .add(new LatLng(28.6731,116.0385)) // Same latitude, and 30km to the west
        .add(new LatLng(28.6840,116.0378)) 
        .add(new LatLng(28.6851,116.0237)); // Same longitude, and 16km to the south
        // Closes the polyline.
    	
    	Polyline polyline = mMap.addPolyline(rectOptions);
    }
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// 参数解释：  
				// e1：第1个ACTION_DOWN MotionEvent   
				// e2：最后一个ACTION_MOVE MotionEvent   
				// velocityX：X轴上的移动速度，像素/秒   
				// velocityY：Y轴上的移动速度，像素/秒   
				// 触发条件 ：   
				// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒 
				float moveX = (e2.getX() - e1.getX());
				if(moveX > 400 && Math.abs(velocityX) > 100){
					this.finish();
				}else{
					
				}
				System.out.println("x1 "+e1.getX());
				System.out.println("x2 "+e2.getX());
				System.out.println("moveX "+moveX);
				System.out.println("velocityX "+velocityX);
				return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return  gestureDetector.onTouchEvent(event);
	}
    
}
