/**
 *  Program : GPS 위치 정보 구하기
 *  Author  : gxgsung
 *  Date    : 2010.12.31
 *  HomeUrl : gxgsung.blog.me
 */

package com.android.location1;

import java.io.*;
import java.util.*;
import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.location.*;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;


public class LocationDemo extends Activity implements LocationListener {
	
	private LocationManager locManager;
	Geocoder geoCoder;
	private Location myLocation = null;
	double latPoint = 0;
	double lngPoint = 0;
	float  speed = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		// LocationListener의 핸들을 얻음
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS로 부터 위치정보를 업데이트 요청, 1초마다 5km 이동시
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        
        // 기지국으로 부터 위치정보를 업데이트 요청
        //locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);
        
        // 주소를 확인하기 위한 Geocoder KOREA 와 KOREAN 둘다 가능
        geoCoder = new Geocoder(this, Locale.KOREAN); 

        final Button gpsButton = (Button) findViewById(R.id.gpsButton);
		gpsButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				GetLocations();
				Log.d("location", "button pressed");
			}
		});

    }
    
	public void GetLocations() {
		// 텍스트뷰를 찾음
		TextView latText = (TextView) findViewById(R.id.tvLatitude);
		TextView lngText = (TextView) findViewById(R.id.tvLongitude);
		TextView speedText = (TextView) findViewById(R.id.tvSpeed);
		TextView jusoText = (TextView) findViewById(R.id.tvAddress);
		StringBuffer juso = new StringBuffer();

		if (myLocation != null) {
			latPoint = myLocation.getLatitude();
			lngPoint = myLocation.getLongitude();
			speed = (float)(myLocation.getSpeed() * 3.6);

			try {
				// 위도,경도를 이용하여 현재 위치의 주소를 가져온다. 
				List<Address> addresses;
				addresses = geoCoder.getFromLocation(latPoint, lngPoint, 1);
				for(Address addr: addresses){
					int index = addr.getMaxAddressLineIndex();
					for(int i=0;i<=index;i++){
						juso.append(addr.getAddressLine(i));
						juso.append(" ");
					}
					juso.append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		latText.setText(String.valueOf(latPoint));
		lngText.setText(String.valueOf(lngPoint));
		speedText.setText(String.valueOf(speed));
		jusoText.setText(String.valueOf(juso));
	}

	public void onLocationChanged(Location location) {
		Log.d("location", "location changed");
		myLocation = location;
	}

	public void onProviderDisabled(String s) {

	}

	public void onProviderEnabled(String s) {

	}

	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	
		menu.add(0,1,0,"프로그램 소개");
		menu.add(0,2,0,"종료");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
    		new AlertDialog.Builder(this)
    		.setTitle("프로그램 소개")
    		.setMessage("안드로이드 학습을 위하여 만든 어플입니다.\n" +
    				"소스코드가 공개되어 있으며 자유롭게 사용하실수 있습니다.\n" +
    				"http://gxgsung.blog.me")
    		.setIcon(R.drawable.icon)
    		.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			})
    		.show();
			return true;
		case 2:
			finish();
			System.exit(0);
			return true;			
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			System.exit(0);
		case KeyEvent.KEYCODE_HOME:
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}
		return super.onKeyDown(keyCode, event);
	}

	
}


