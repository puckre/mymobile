package kr.ac.kumoh.ce.puckre;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends NMapActivity {
	public static final String API_KEY="7874177ec842a7d1032f08c9d11c5389";
	TextView text01;
	Button button01;
	NMapView mapview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mapview = new NMapView(this);
		mapview.setApiKey(API_KEY);
		
		text01 = (TextView) findViewById(R.id.text01);
		mapview = (NMapView) findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true, null);
		
		button01 = (Button) findViewById(R.id.button01);
		button01.setOnClickListener(new OnClickListener(){
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getMyLocation(); 
			}
			
		});
		
	}
	
	private void getMyLocation(){
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		MyLocationListener listener = new MyLocationListener();
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, listener);
		
		appendText("현재 위치를 요청했습니다.");
	}
	
	private void appendText(String msg) {
		// TODO Auto-generated method stub
		text01.append(msg + "\n");
	}
	
	class MyLocationListener implements LocationListener{

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			
			appendText("현재 위치 : " + latitude + "," + longitude);
			
			showMyLocation(latitude, longitude);
		}

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@SuppressLint("UseValueOf")
	private void showMyLocation(double latitude, double longitude){ 
		NMapViewerResourceProvider mMapViewerResourceProvider = null;
		NMapOverlayManager mOverlayManager;
		
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		mOverlayManager = new NMapOverlayManager(this, mapview, mMapViewerResourceProvider); 
				
		NGeoPoint myPoint = new NGeoPoint(longitude, latitude);
		
		int markerId = NMapPOIflagType.PIN;
		
		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
		poiData.beginPOIdata(1);
		poiData.addPOIitem(myPoint, "현재 위치", markerId, 0);
		poiData.endPOIdata();
		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		poiDataOverlay.showAllPOIdata(0); 
		
		NMapController controller = mapview.getMapController();
		controller.animateTo(myPoint);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
