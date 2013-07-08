package edu.jxsd.x3510.fragments;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.jxsd.x3510.activity.LeftAndRightActivity;
import edu.jxsd.x3510.activity.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/*public class Map extends Fragment{
	private GoogleMap mMap = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//setUpMapIfNeeded();
		map = (MapView) getView().findViewById(R.id.map02);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.activity_map, null);
	}
	
	 private void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map.
	        if (mMap == null) {
	            // Try to obtain the map from the SupportMapFragment.
	            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map02))
	                    .getMap();
	            // Check if we were successful in obtaining the map.
	            if (mMap != null) {
	                setUpMap();
	            }
	        }
	    }

	    private void setUpMap() {
	        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
	    }
}
*/
/*public class Map extends Fragment {
	private View mMapViewContainer;
	private MapView mMapView;
 
	public static Map newInstance() {
		Map fragment = new Map();
 
		Bundle args = new Bundle();
		// add any necessary args here
		fragment.setArguments( args );
 
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView( inflater, container, savedInstanceState );
 
		// We can't grab the MapView onCreate() since Fragment#onCreate() is 
		// called before Activity#onCreate() (where the MapView is created).
		// We also can't do it in Fragment#onActivityCreated() since its called 
		// after Fragment#onCreateView().  So, we grab it every time here.
		//
		// Yes, its ugly that this fragment has to know that it lives inside
		// a MainActivity.
		LeftAndRightActivity mainActivity =  (LeftAndRightActivity) getActivity();
		mMapViewContainer = mainActivity.mMapViewContainer;
		mMapView = mainActivity.mMapView;
		return mMapView;
	}
 
	// your other fragment code
}*/
@SuppressLint("NewApi")
public class Map extends Fragment {
	
	MapView m;
	private GoogleMap mMap;
	
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		// inflat and return the layout
		View v = inflater.inflate(R.layout.activity_map_02, container, false);
		m = (MapView) v.findViewById(R.id.map);
		
	/*	mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map02)).getMap();*/

		m.onCreate(savedInstanceState);	
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		m.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		m.onDestroy();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		m.onLowMemory();
	}
}