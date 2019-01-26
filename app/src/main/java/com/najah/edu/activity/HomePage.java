package com.najah.edu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.najah.edu.DatABasE.DB_helper;
import com.najah.edu.fileuser.edit_info;
import com.najah.edu.rxloginsignup.R;

import static com.najah.edu.rxloginsignup.R.drawable.map;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener   {

    ImageButton police;
    ImageButton ambulance;
    ImageButton firefighter;

    private String email_is_login = MainActivity.get_e;
    private TextView show_name ;
    private TextView show_email ;
    private DB_helper cc;

    public static String city_center;
    public static String type_center;

    private GoogleApiClient client;

    Spinner CountrySp ;
    ArrayAdapter<CharSequence> adapter;
    int pos ;
    public static String GPS="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_home_page));
//        final Button camera = (Button)findViewById(R.id.nav_camera);
        police =(ImageButton)findViewById(R.id.imageButton);
        police.setOnClickListener(this);
        ambulance =(ImageButton)findViewById(R.id.imageButton2);
        ambulance.setOnClickListener(this);
        firefighter =(ImageButton)findViewById(R.id.imageButton3);
        firefighter.setOnClickListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        cc = new DB_helper(this);
        cc.get_info(email_is_login);
        Toast.makeText(getBaseContext(), getString(R.string.str_massge1) + " " + DB_helper.get_fname  ,Toast.LENGTH_SHORT).show();
        CountrySp = (Spinner) findViewById(R.id.select_spinner);
        adapter = ArrayAdapter.createFromResource
                (this, R.array.Country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CountrySp.setAdapter(adapter);
        CountrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
//                store string in GPS
                GPS = (String) parent.getItemAtPosition(position).toString();
                if (MainActivity.what_languge == "en")
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) +  " selected" , Toast.LENGTH_SHORT).show();
                else if (MainActivity.what_languge == "ar")
                    Toast.makeText(getBaseContext(), "اخترت "  + parent.getItemAtPosition(position)  , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        View headerView = navigationView.getHeaderView(0);
        show_name = (TextView) headerView.findViewById(R.id.textView2);
        show_name.setText( DB_helper.get_fname +" "+ DB_helper.get_lname );
        show_email = (TextView) headerView.findViewById(R.id.textView);
        show_email.setText( DB_helper.get_email  );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // button camera  to open camera
        if (id == R.id.nav_camera) {
            Intent cameraIntent = new Intent();
            //"android.media.action.IMAGE_CAPTURE"
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent , 0);
        }
        // view information user account
        else if (id == R.id.account_view) {
            cc.get_info(email_is_login);
            show_information_user();
        }
        // edit information account
        else if (id == R.id.edit_account) {
            Intent intent = new Intent(HomePage.this,edit_info.class);
            startActivity(intent);
        }
        // log out from account
        else if (id == R.id.nav_manage) {
            log_out_the_account();
        }
        else if (id == R.id.nav_share) {
        }
        else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if ( id == R.id.imageButton ){
            open_map("Police");
        }
        if ( id == R.id.imageButton2 ){
            open_map("Ambulance" );
        }
        if ( id == R.id.imageButton3 ){
            open_map("Firefigter");
        }
    }
    private void log_out_the_account(){
        AlertDialog.Builder mko = new AlertDialog.Builder(this);
        mko.setTitle(R.string.str_title);
        mko.setMessage(R.string.str_massge).setCancelable(false).setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                HomePage.this.finish();
            }
        }).setNegativeButton(R.string.str_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = mko.create();
        alertDialog.show();
    }
    private  void show_information_user (){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(   getString(R.string.firstName) +  " : " +    DB_helper.get_fname    +"\n"
                    + getString(R.string.lastName)  +  " : " +   DB_helper.get_lname    +"\n"
                    + getString(R.string.str_phone) +  " : " +  DB_helper.get_phone  + "\n"
                    + getString(R.string.str_email) +  " : " +  DB_helper.get_email );
            builder.setTitle(R.string.str_title1);
            builder.setPositiveButton(R.string.str_ok, null);
            builder.setCancelable(true);
            builder.create().show();
    }
    private void open_map (String type){
        city_center = GPS;
        type_center = type ;
        Intent intent = new Intent(HomePage.this,map_page.class);
        startActivity(intent);
    }
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("map_page Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}