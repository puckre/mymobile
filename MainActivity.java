package kr.ac.kumoh.ce.puckre;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends NMapActivity implements LocationListener{
	public static final String API_KEY="7874177ec842a7d1032f08c9d11c5389";
	NMapView mapview;
	
	// 위도와 경도 초기화
	double latPoint = 0; 
	double lngPoint = 0; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 지도 객체 생성과 API_KEY 설정
		mapview = new NMapView(this);
		mapview.setApiKey(API_KEY);
		
		// 지도를 보여주기 위한 부분을 찾고 지도 상에 Zoom 버튼을 생성한다.
		mapview = (NMapView) findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true, null);

		// GPS 장치를 이용하기 위해 LocationManager를 사용한다. 
		// requestLocationUpdates의 파라메타 값으로 GPS or Network 장치를 설정하고 위치 갱신 시간과 갱신 받기위한 거리, Listener를 설정한다.
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, (LocationListener) this);
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		// 위도 경도 좌표를 getLatitude, getLongitude를 사용하여 받아온다.
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();	
		
		// 받아온 위도 경도 좌표를 showMyLocation 함수를 호출하여 지도상에 표시한다.
		showMyLocation(latitude, longitude);
	}
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	// 받아온 위도 경도 좌표를 지도상에 보여주는 함수
	private void showMyLocation(double latitude, double longitude){ 
		NMapViewerResourceProvider mMapViewerResourceProvider = null;
		NMapOverlayManager mOverlayManager;
		
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		mOverlayManager = new NMapOverlayManager(this, mapview, mMapViewerResourceProvider); 
				
		NGeoPoint myPoint = new NGeoPoint(longitude, latitude);
		
		// 위치 좌표에 오버레이 아이템을 표시하여 지도상에 보여주는 부분
		
		// 오버레이 아이템 상에 있는 이미지 PIN을 이용하는 부분
		int markerId = NMapPOIflagType.PIN; 
		
		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
		poiData.beginPOIdata(1);
		
		// 좌표 정보와 오버레이 아이템 정보, 텍스트 정보를 설정한다.
		poiData.addPOIitem(myPoint, "현재 위치", markerId, 0);
		poiData.endPOIdata();
		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		poiDataOverlay.showAllPOIdata(0); 
		
		// 지도 상에 위치 좌표와 오버레이 아이템 모두를 보여준다. (최종적으로 지도상에 표시하고 보여주는 부분)
		NMapController controller = mapview.getMapController();
		controller.animateTo(myPoint);
	}
}
