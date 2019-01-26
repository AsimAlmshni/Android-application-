package com.najah.edu.DataBaseToMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.najah.edu.DatABasE.StatmentSql;
import com.najah.edu.DatABasE.Table;
import com.najah.edu.activity.MainActivity;
import com.najah.edu.activity.map_page;

/**
 * Created by karam odeh on 4/27/2017.
 */

public class DB_helper_map extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CENTERS.db";
    public static String array_pos_x[] ;
    public static String array_pos_y[] ;
    public static String array_Title[] ;
    public static String array_name_center[] ;
    public static String array_Phone[] ;
    Context context ;
    public DB_helper_map(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context ;
        array_pos_x = new String[map_page.count_row];
        array_pos_y = new String[map_page.count_row];
        array_Title = new String[map_page.count_row];
        array_name_center = new String[map_page.count_row];
        array_Phone = new String[map_page.count_row];
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tabels
        db.execSQL(stat_sql_map.SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(stat_sql_map.SQL_DELETE);
        onCreate(db);
    }
    // insert to database
    public void insert_to_table_map (String t1 , String t2 , String t3 , String t4 , String t5 , String t6 , String t7 , String t8 ){
        ContentValues val = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        //save in database
        val.put(Table_Centers.ENTER_COL.NAME_CETER, t1);
        val.put(Table_Centers.ENTER_COL.PHONE_CENTER, t2);
        val.put(Table_Centers.ENTER_COL.TITLE_CENTER, t3);
        val.put(Table_Centers.ENTER_COL.CITY_CENTER, t4);
        val.put(Table_Centers.ENTER_COL.TYPE_CENTER, t5);
        val.put(Table_Centers.ENTER_COL.POS_X, t6);
        val.put(Table_Centers.ENTER_COL.POS_Y, t7);
        val.put(Table_Centers.ENTER_COL.CITY_CENTER_AR, t8);
        db.insert(Table_Centers.ENTER_COL.TABLE_MAP_CENTERS,null,val);
        val.clear();

    }

    // if id exist or not
    public boolean check_id(String id) {
        // array of columns to fetch
        String[] columns = {Table_Centers.ENTER_COL.PHONE_CENTER};
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Table_Centers.ENTER_COL.PHONE_CENTER + " = ?" ;
        // selection arguments
        String[] selectionArgs = {id};
        // query user table with conditions
        Cursor cursor = db.query(Table_Centers.ENTER_COL.TABLE_MAP_CENTERS,columns, selection,selectionArgs,null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    // find center in this city and type srever
    public int check_city_and_type(String city, String type) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Table_Centers.ENTER_COL.PHONE_CENTER };
        String selection = "";
        if (MainActivity.what_languge == "en")
            selection = Table_Centers.ENTER_COL.CITY_CENTER + " = ?" + " AND " + Table_Centers.ENTER_COL.TYPE_CENTER + " = ?";
        else if (MainActivity.what_languge == "ar")
            selection = Table_Centers.ENTER_COL.CITY_CENTER_AR + " = ?" + " AND " + Table_Centers.ENTER_COL.TYPE_CENTER + " = ?";
        String[] selectionArgs = {city, type};
        Cursor cursor = db.query(Table_Centers.ENTER_COL.TABLE_MAP_CENTERS, columns , selection , selectionArgs, null, null, null);        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return cursorCount;
        }
        return 0;
    }
    // take data from table ()
    public void get_position_x_and_y (String city, String type) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String columns[] = {Table_Centers.ENTER_COL.POS_X, Table_Centers.ENTER_COL.POS_Y , Table_Centers.ENTER_COL.PHONE_CENTER , Table_Centers.ENTER_COL.TITLE_CENTER , Table_Centers.ENTER_COL.NAME_CETER};
            String selection = "";
            if (MainActivity.what_languge == "en")
                selection = Table_Centers.ENTER_COL.CITY_CENTER + " = ?" + " AND " + Table_Centers.ENTER_COL.TYPE_CENTER + " = ?";
            else if (MainActivity.what_languge == "ar")
                selection = Table_Centers.ENTER_COL.CITY_CENTER_AR + " = ?" + " AND " + Table_Centers.ENTER_COL.TYPE_CENTER + " = ?";
            String[] selectionArgs = {city, type};
            Cursor cursor = db.query(Table_Centers.ENTER_COL.TABLE_MAP_CENTERS, columns, selection, selectionArgs, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int i = 0;
                    do {
                        array_pos_x[i] = cursor.getString(cursor.getColumnIndex(Table_Centers.ENTER_COL.POS_X)).toString().trim();
                        array_pos_y[i] = cursor.getString(cursor.getColumnIndex(Table_Centers.ENTER_COL.POS_Y)).toString().trim();
                        array_Phone[i] = cursor.getString(cursor.getColumnIndex(Table_Centers.ENTER_COL.PHONE_CENTER)).toString().trim();
                        array_Title[i] = cursor.getString(cursor.getColumnIndex(Table_Centers.ENTER_COL.TITLE_CENTER)).toString().trim();
                        array_name_center[i] = cursor.getString(cursor.getColumnIndex(Table_Centers.ENTER_COL.NAME_CETER)).toString().trim();
                        i++;
                    } while (cursor.moveToNext() || i < map_page.count_row);
                }
            }
            cursor.close();
            db.close();
        }
        catch (Exception e) {
            String TAG = map_page.class.getSimpleName();
            Log.d(TAG,e.getMessage()+"");
        }
    }
}
