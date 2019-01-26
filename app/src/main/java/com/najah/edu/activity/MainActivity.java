package com.najah.edu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.najah.edu.DB_city_position.DB_helper_city;
import com.najah.edu.DataBaseToMap.DB_helper_map;
import com.najah.edu.rxloginsignup.R;
import com.najah.edu.DatABasE.DB_helper;
import com.najah.edu.fragment.LoginFragment;
import com.najah.edu.fragment.SignUpFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    //    For internet connection and snack Bar
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    private boolean internetConnected = true;
    public static String get_e = "" ;
    private Locale locale ;
    public static String what_languge = "ar";
    private DB_helper bb ;
    private DB_helper_map map_helper;
    private DB_helper_city db_helper_city;

    private static final String KEY = "calculatorHistory";

    @BindView(R.id.viewpager_login_signup)
    ViewPager viewpager_login_signup;
    @BindView(R.id.tablayout_login_signup)
    TabLayout tablayout_login_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.app_name));
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY)) {
            what_languge=savedInstanceState.getString(KEY);
            setLocal(what_languge);
        }


        map_helper = new DB_helper_map(this);
        insert_server_nablus();
        db_helper_city = new DB_helper_city(this);
        insert_city();
        bb = new DB_helper(this);
        setViewPager();
    }
    /**
     * Set Viewpager with two fragments
     */
    private void setViewPager() {
        viewpager_login_signup.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),this));
        tablayout_login_signup.setupWithViewPager(viewpager_login_signup);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                setLocal("en");
                Toast.makeText(this,  R.string.str_language , Toast.LENGTH_LONG).show();
                recreate();
                what_languge = "en";
                break;
            case R.id.action_shareAr:
                setLocal("ar");
                Toast.makeText(this, R.string.str_language , Toast.LENGTH_LONG).show();
                recreate();
                what_languge = "ar";
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setLocal(String lang) {
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    /**
     * Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }
    /**
     * Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status, false);
        }
    };
    private void setSnackbarMessage(String status, boolean showBar) {
        int internetStatus ;
        String internetStatus1 = "" ;
        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {
            internetStatus = R.string.WiFiConnected;
            internetStatus1="Internet Connected";
        } else {
            internetStatus = R.string.WiFiNotConnected;
            internetStatus1="Lost Internet Connection";
        }
        snackbar = Snackbar
                .make(coordinatorLayout, internetStatus, Snackbar.LENGTH_LONG)
                .setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (internetStatus1.equalsIgnoreCase("Lost Internet Connection")) {
            if (internetConnected) {
                snackbar.show();
                internetConnected = false;
            }
        } else {
            if (!internetConnected) {
                internetConnected = true;
                snackbar.show();
            }
        }
    }
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
    // button sign in
    public void onClickHandler(View view) {
        String get_email = ((EditText) findViewById(R.id.edt_email1)).getText().toString();
        String get_pass = ((EditText) findViewById(R.id.edt_password1)).getText().toString();
        //Toast.makeText(getBaseContext(), get_email + "     " + get_pass, Toast.LENGTH_LONG).show();
        get_e = get_email;
       if (bb.check_email_and_pass(get_email  , get_pass)) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(getBaseContext(), getString(R.string.str_login_error), Toast.LENGTH_LONG).show();
        }
    }
    /**
     *  Pager adapter to set fragment in view pager
     */
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private int PAGE_COUNT=2;
        private String tabTitles[] = new String[]{getString(R.string.str_login), getString(R.string.str_signup)};
        private Context mContext;
        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LoginFragment.getInstant();
                case 1:
                    return SignUpFragment.getInstant();
                default:
                    return LoginFragment.getInstant();
            }
        }
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("what_languge", what_languge);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            what_languge = savedInstanceState.getString("what_languge");
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newC) {
        super.onConfigurationChanged(newC);
        if (newC.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setLocal(what_languge);
            recreate();
        } else if (newC.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recreate();
            setLocal(what_languge);
        }
    }
    private void insert_server_nablus () {
        map_helper.insert_to_table_map("شرطة نابلس","100100","شارع فيصل","Nablus","Police","32.221981","35.263499","نابلس");

        map_helper.insert_to_table_map("المستشفى  العربي","333333","شارع شويترة، نابلس","Nablus","Ambulance","32.222798","35.254644","نابلس");
        map_helper.insert_to_table_map("مستشفى رفيديا","444444","ش. الرئيسي,بالقرب من مدارس طلائع الامل رفيديا","Nablus","Ambulance","32.225143","35.241402","نابلس");
        map_helper.insert_to_table_map("Najah University Hospital","555555","نابلس","Nablus","Ambulance","32.239170","35.245933","نابلس");

        map_helper.insert_to_table_map("أطفائية بلدية نابلس","6666666","نابلس الجبل الشمالي","Nablus","Firefigter","32.222542","35.264977","نابلس");
    }
    private void insert_city(){
        db_helper_city.insert_city_to_table("نابلس","Nablus","32.224744","35.262099");
        db_helper_city.insert_city_to_table("رام الله","Ramallah","31.903777","35.203416");
        db_helper_city.insert_city_to_table("جنين","Jenin","32.464563","35.293717");
        db_helper_city.insert_city_to_table("اريحا","Jericho","31.861118","35.461759");
        db_helper_city.insert_city_to_table("قلقيلية","Qalqilya","32.196030","34.981513");
        db_helper_city.insert_city_to_table("الخليل","Hebron","31.532807","35.099847");
        db_helper_city.insert_city_to_table("سلفيت","Salfit","32.085339","35.180843");
        db_helper_city.insert_city_to_table("طولكرم","Toulkarem","32.319172","35.024423");
        db_helper_city.insert_city_to_table("بيت لحم","Bethlehem","31.705629","35.202400");
        db_helper_city.insert_city_to_table("القدس","Jerusalem","31.767407","35.215531");
        db_helper_city.insert_city_to_table("طوباس","Tubas","32.321240","35.369932");
    }
}
