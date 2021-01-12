package com.ilddang.job.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ilddang.job.R;
import com.ilddang.job.data.NoticeListItemData;
import com.ilddang.job.util.Constants;
import com.ilddang.job.util.GpsTracker;
import com.ilddang.job.widget.NoticeItemAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainNoticeListActivity extends BaseActivity  {
    RecyclerView mNoticeListView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private GpsTracker gpsTracker;
    private LinearLayout mMoreOptionLayout;
    private LinearLayout mProfileLayout;
    private ImageView mProfileImage;
    private TextView mProfileHello;
    private RelativeLayout mAlphaLayout;
    private ToggleButton mMyInfoButton;
    private View mActionBar;
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notice_list_activity);
        String session = getIntent().getStringExtra("login_session");
        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);

        mWebView.loadUrl("http://117.52.20.138:3000/work?sessionid=" + session);

//               createSpinnerActionBar();
//        mAlphaLayout = findViewById(R.id.alpha_layout);
//        mNoticeListView = findViewById(R.id.notice_list_recycler_view);
//
//        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mNoticeListView.setLayoutManager(manager);
//
//        //TODO: get data from server
//        NoticeListItemData data
//                = new NoticeListItemData("영진인테리어", "공사 끝난 건물 필름 시공자 4명급구",
//                2, "장항2동 2km", "일 8만원", "10.01 오전 03:00 ~ 당일 오후 18:30", Constants.CurrentStatus.RECRUITING);
//
//        NoticeListItemData data2
//                = new NoticeListItemData("장인타일", "화장실 타일 시공자 4명급구",
//                2, "장항2동 2km", "총 12만원", "10.01 오전 03:00 ~ 10.05 오후 18:30", Constants.CurrentStatus.CLOSED);
//
//        NoticeListItemData data3
//                = new NoticeListItemData("하늘건설", "공사 끝난건물 필름 시공자 4명 급구",
//                2, "장항2동 2km", "총 12만원", "10.01 오전 03:00 ~ 10.05 오후 18:30", Constants.CurrentStatus.SCHEDULED);
//        ArrayList<NoticeListItemData> list = new ArrayList<>();
//        list.add(data);
//        list.add(data2);
//        list.add(data3);
//
//        NoticeItemAdapter adapter = new NoticeItemAdapter(this, list, true);
//        mNoticeListView.setAdapter(adapter);
//
//        if (!checkLocationServicesStatus()) {
//
//            showDialogForLocationServiceSetting();
//        }else {
//
//            checkRunTimePermission();
//        }
//
//        gpsTracker = new GpsTracker(MainNoticeListActivity.this);
//
//        double latitude = gpsTracker.getLatitude();
//        double longitude = gpsTracker.getLongitude();
//
//        String address = getCurrentAddress(latitude, longitude);
//
//        TextView adressText = findViewById(R.id.address);
//        if (address.contains("대한민국")) {
//            adressText.setText(address.replace("대한민국", ""));
//        }
//
//        mMyInfoButton = findViewById(R.id.my_info_button);
//        mMyInfoButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                if (checked) {
//                    mAlphaLayout.setVisibility(View.VISIBLE);
//                    mMoreOptionLayout.setVisibility(View.VISIBLE);
//                    mProfileLayout.setVisibility(View.VISIBLE);
//                    Glide.with(MainNoticeListActivity.this)
//                            .load(R.drawable.default_profile_image)
//                            .circleCrop()
//                            .into(mProfileImage);
//                    //TODO: get profile name from server
//                    mProfileHello.setText(getResources().getString(R.string.profile_hello, "슈퍼맨"));
//                } else {
//                    setTurnOffAlpha();
//                }
//            }
//        });
//        mMoreOptionLayout = findViewById(R.id.more_option_layout);
//        mProfileLayout = findViewById(R.id.profile_layout);
//        mProfileImage = findViewById(R.id.profile_image);
//        mProfileHello = findViewById(R.id.profile_hello);
    }

    private void createSpinnerActionBar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mActionBar = inflater.inflate(R.layout.custom_actionbar, null);
        TextView titleView = mActionBar.findViewById(R.id.action_bar_title);
        titleView.setVisibility(View.GONE);
        ImageButton backButton = mActionBar.findViewById(R.id.action_bar_back_button);
        backButton.setVisibility(View.GONE);

        Spinner spinner = mActionBar.findViewById(R.id.action_bar_spinner);
        spinner.setVisibility(View.VISIBLE);
        String[] alignArray = {"최신순", "거리순"};

        ArrayAdapter<String> alignAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item_text, alignArray
        );
        alignAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(alignAdapter);

        getSupportActionBar().setCustomView(mActionBar);

        Toolbar parent = (Toolbar) mActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    private void setTurnOffAlpha() {
        mAlphaLayout.setVisibility(View.GONE);
        mMoreOptionLayout.setVisibility(View.GONE);
        mProfileLayout.setVisibility(View.GONE);
    }

//    public void onButtonClick(View view) {
//        mMyInfoButton.performClick();
//        if (view.getId() == R.id.my_ilddang) {
//            Intent intent = new Intent(this, MyProfileActivity.class);
//            startActivity(intent);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grandResults) {
//
//        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//            boolean check_result = true;
//
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//
//            if (!check_result) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
//                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
//
//                    Toast.makeText(MainNoticeListActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
//                    finish();
//                } else {
//                    Toast.makeText(MainNoticeListActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        }
//    }

    void checkRunTimePermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainNoticeListActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainNoticeListActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainNoticeListActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MainNoticeListActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainNoticeListActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(MainNoticeListActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainNoticeListActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
