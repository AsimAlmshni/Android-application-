package com.najah.edu.activity;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.najah.edu.DB_city_position.DB_helper_city;
import com.najah.edu.DataBaseToMap.DB_helper_map;
import com.najah.edu.rxloginsignup.R;

public class map_page extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DB_helper_map helper_map;
    private DB_helper_city db_helper_city;
    public static int count_row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db_helper_city = new DB_helper_city(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double position_x = 0;
        double position_y = 0;
        if (MainActivity.what_languge == "en"){
            if (db_helper_city.search_city_en(HomePage.city_center) == true){
                db_helper_city.get_info_en(HomePage.city_center);
                position_x = Double.parseDouble(db_helper_city.pos_x);
                position_y = Double.parseDouble(db_helper_city.pos_y);
            }
        }
        else if (MainActivity.what_languge == "ar"){
            if (db_helper_city.search_city_ar(HomePage.GPS)){
                db_helper_city.get_info_ar(HomePage.GPS);
                position_x = Double.parseDouble(db_helper_city.pos_x);
                position_y = Double.parseDouble(db_helper_city.pos_y);
            }
        }
        LatLng city = new LatLng(position_x, position_y);
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(city).title(HomePage.city_center)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(city));
        mMap.setMinZoomPreference(10);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        helper_map = new DB_helper_map(this);
        mMap = googleMap;
        count_row = helper_map.check_city_and_type(HomePage.city_center,HomePage.type_center);
        helper_map = new DB_helper_map(this);
        if (count_row > 0) {
            helper_map.get_position_x_and_y(HomePage.city_center,HomePage.type_center);
            try {
                int x = count_row - 1;
                while ( x >= 0){
                    double xx = Double.parseDouble(helper_map.array_pos_x[x].toString().trim());
                    double yy = Double.parseDouble(helper_map.array_pos_y[x].toString().trim());
                    LatLng center = new LatLng(xx, yy);
                    mMap.addMarker(new MarkerOptions().position(center)
                                                      .title(helper_map.array_name_center[x])
                                                      .snippet(helper_map.array_Title[x]));

                    x--;
                }
            }
            catch (Exception e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
    }



}
