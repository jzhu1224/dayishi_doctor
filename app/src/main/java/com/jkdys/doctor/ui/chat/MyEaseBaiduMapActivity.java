package com.jkdys.doctor.ui.chat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyEaseBaiduMapActivity extends BaseAppCompatActivity {

    private final static String TAG = "map";
    static MapView mMapView = null;
    LocationClient mLocClient;
    public MyEaseBaiduMapActivity.MyLocationListenner myListener = new MyLocationListenner();
    @BindView(R.id.btn_send)
    Button sendButton;
    static BDLocation lastLocation = null;
    public static MyEaseBaiduMapActivity instance = null;
    ProgressDialog progressDialog;
    private BaiduMap mBaiduMap;


    public class BaiduSDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            String st1 = getResources().getString(R.string.Network_error);
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

                String st2 = getResources().getString(R.string.please_check);
                Toast.makeText(instance, st2, Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(instance, st1, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private MyEaseBaiduMapActivity.BaiduSDKReceiver mBaiduReceiver;

    private void showMap(double latitude, double longtitude, String address) {
        sendButton.setVisibility(View.GONE);
        LatLng llA = new LatLng(latitude, longtitude);
        CoordinateConverter converter= new CoordinateConverter();
        converter.coord(llA);
        converter.from(CoordinateConverter.CoordType.COMMON);
        LatLng convertLatLng = converter.convert();
        OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location))
                .zIndex(4).draggable(true);
        mBaiduMap.addOverlay(ooA);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
        mBaiduMap.animateMapStatus(u);
    }

    private void showMapWithLocationClient() {
        String str1 = getResources().getString(R.string.Making_sure_your_location);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str1);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("map", "cancel retrieve location");
                finish();
            }
        });

        progressDialog.show();

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// open gps
        // option.setCoorType("bd09ll");
        // Johnson change to use gcj02 coordination. chinese national standard
        // so need to conver to bd09 everytime when draw on baidu map
        option.setCoorType("gcj02");
        option.setScanSpan(30000);
        option.setAddrType("all");
        mLocClient.setLocOption(option);
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
        super.onPause();
        lastLocation = null;
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
        super.onResume();
    }

    @Override
    protected int getLayout() {
        if (!inited) {
            inited = true;
            SDKInitializer.initialize(getApplicationContext());
        }

        return R.layout.activity_my_ease_baidu_map;
    }

    @Override
    protected void onDestroy() {
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.onDestroy();
        unregisterReceiver(mBaiduReceiver);
        super.onDestroy();
    }
    private void initMapView() {
        mMapView.setLongClickable(true);
    }

    /**
     * format new location to string and show on screen
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            Log.d("map", "On location change received:" + location);
            Log.d("map", "addr:" + location.getAddrStr());
            sendButton.setEnabled(true);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            if (lastLocation != null) {
                if (lastLocation.getLatitude() == location.getLatitude() && lastLocation.getLongitude() == location.getLongitude()) {
                    Log.d("map", "same location, skip refresh");
                    // mMapView.refresh(); //need this refresh?
                    return;
                }
            }
            lastLocation = location;
            mBaiduMap.clear();
            LatLng llA = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            CoordinateConverter converter= new CoordinateConverter();
            converter.coord(llA);
            converter.from(CoordinateConverter.CoordType.COMMON);
            LatLng convertLatLng = converter.convert();
            OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location))
                    .zIndex(4).draggable(true);
            mBaiduMap.addOverlay(ooA);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
            mBaiduMap.animateMapStatus(u);
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    public void sendLocation() {
        Intent intent = this.getIntent();
        intent.putExtra("latitude", lastLocation.getLatitude());
        intent.putExtra("longitude", lastLocation.getLongitude());
        intent.putExtra("address", lastLocation.getAddrStr());
        this.setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        initView();
    }

    boolean inited = false;

    public void initView() {
        instance = this;

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        toolbar.setTitle("当前位置");

        mMapView = (MapView) findViewById(R.id.bmapView);
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        initMapView();
        if (latitude == 0) {
            mMapView = new MapView(this, new BaiduMapOptions());
            mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                    mCurrentMode, true, null));
            showMapWithLocationClient();
        } else {
            double longtitude = intent.getDoubleExtra("longitude", 0);
            String address = intent.getStringExtra("address");
            LatLng p = new LatLng(latitude, longtitude);
            mMapView = new MapView(this,
                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                            .target(p).build()));
            showMap(latitude, longtitude, address);
        }
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mBaiduReceiver = new MyEaseBaiduMapActivity.BaiduSDKReceiver();
        registerReceiver(mBaiduReceiver, iFilter);
    }

    @OnClick(R.id.btn_send)
    void onsendLocationClick() {
        sendLocation();
    }

}
