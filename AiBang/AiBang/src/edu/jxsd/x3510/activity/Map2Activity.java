package edu.jxsd.x3510.activity;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import edu.jxsd.x3510.http.HttpUtility;
import edu.jxsd.x3510.view.MyListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * This shows how to create a simple activity with a map and a marker on the
 * map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is
 * not installed/enabled/updated on a user's device.
 */
public class Map2Activity extends FragmentActivity implements
		OnItemSelectedListener {
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;
	private List<HelpMessage> data = null;
	// public static final String PATH =
	// "http://192.168.1.103:8080/AiBangServer/getAllHelpMessage.action";
	public static String PATH;
	private static String TAG = "MAPACTIVITY";
	private HttpUtility hu = new HttpUtility();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	private UiSettings mUiSettings;
	private String school;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PATH = this.getResources().getString(R.string.ip)
				+ "AiBangServer/getAllHelpMessage.action";
		
		setContentView(R.layout.activity_map2);
		school = getIntent().getStringExtra(LeftAndRightActivity.EXTRA_MESSAGE);

		Spinner spinner = (Spinner) findViewById(R.id.layers_spinner2);
		spinner.getBackground().setAlpha(200);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.layers_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		setUpMapIfNeeded();

		HandlerHTTP handler = new HandlerHTTP();
		Log.e(TAG, school);
		new Map2Activity().getResponseThread(handler, PATH, school);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		if (school.equals("江西师范大学")) {
			setPolyLineOption();
		} else if (school.equals("南昌大学")) {
			setPolyLineOption2();
		}

		// setPolygon();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.frag_map2)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// setUpMap();
			}
		}
	}

	private void setUpMap() {
		for (int i = 0; i < data.size(); i++) {
			mMap.addMarker(new MarkerOptions().position(
					new LatLng(data.get(i).getX(), data.get(i).getY())).title(
					data.get(i).getHelpContent()));
		}

		mMap.setMyLocationEnabled(true);

		mUiSettings = mMap.getUiSettings();
	}

	private void setPolyLineOption() {
		PolylineOptions rectOptions = new PolylineOptions()
				.add(new LatLng(28.6851, 116.0237))
				.add(new LatLng(28.6747, 116.0249))
				// North of the previous point, but at the same longitude
				.add(new LatLng(28.6731, 116.0385))
				// Same latitude, and 30km to the west
				.add(new LatLng(28.6840, 116.0378))
				.add(new LatLng(28.6851, 116.0237)); // Same longitude, and 16km
														// to the south
		// Closes the polyline.

		Polyline polyline = mMap.addPolyline(rectOptions);
	}

	private void setPolyLineOption2() {
		PolylineOptions rectOptions = new PolylineOptions()
				.add(new LatLng(28.68487, 115.93659))
				.add(new LatLng(28.68479, 115.94027))
				// North of the previous point, but at the same longitude
				.add(new LatLng(28.68190, 115.94036))
				// Same latitude, and 30km to the west
				.add(new LatLng(28.68190, 115.9354))
				.add(new LatLng(28.68487, 115.93659)); // Same longitude, and
														// 16km
														// to the south
		// Closes the polyline.

		Polyline polyline = mMap.addPolyline(rectOptions);
	}

	private void setPolygon() {
		PolygonOptions rectOptions = new PolygonOptions()
				.add(new LatLng(28.6851, 116.0237))
				.add(new LatLng(28.6747, 116.0249))
				// North of the previous point, but at the same longitude
				.add(new LatLng(28.6731, 116.0385))
				// Same latitude, and 30km to the west
				.add(new LatLng(28.6840, 116.0378))
				.add(new LatLng(28.6851, 116.0237));
		Polygon polygon = mMap.addPolygon(rectOptions);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		setLayer((String) parent.getItemAtPosition(position));
	}

	private boolean checkReady() {
		if (mMap == null) {
			Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void setLayer(String layerName) {
		if (!checkReady()) {
			return;
		}
		if (layerName.equals(getString(R.string.normal))) {
			mMap.setMapType(MAP_TYPE_NORMAL);
		} else if (layerName.equals(getString(R.string.hybrid))) {
			mMap.setMapType(MAP_TYPE_HYBRID);
		} else {
			Log.i("LDA", "Error setting layer with name " + layerName);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	private void getResponseThread(final HandlerHTTP handlerHTTP,
			final String PATH, final String school) {
		new Thread(new Runnable() {
			public void run() {
				String resultHTTP = null;
				HashMap<String, String> params = new HashMap<String, String>();
				Log.e(TAG, school);
				params.put("school", school);
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
		MyListView listView;

		public HandlerHTTP(MyListView listView) {
			this.listView = listView;
		}

		public HandlerHTTP() {

		}

		public void initData(JSONArray jsonArray) throws JSONException {
			data = new LinkedList<HelpMessage>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				HelpMessage hp = new HelpMessage();
				hp.setUserName(jsonObject.getString("userName"));
				hp.setHelpContent(jsonObject.getString("helpContent"));
				hp.setX(Float.valueOf(jsonObject.getString("x")));
				hp.setY(Float.valueOf(jsonObject.getString("y")));
				data.add(hp);
			}
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.obj == null) {
				Toast.makeText(Map2Activity.this.getApplicationContext(),
						"connect failed", Toast.LENGTH_SHORT).show();
			} else {
				String json = msg.obj.toString();
				Log.e(TAG, json);
				try {
					JSONArray jsonArray = new JSONArray(json);
					try {
						if (data == null) {
							initData(jsonArray);
							setUpMap();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
