package com.najah.edu.DB_city_position;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karam odeh on 4/27/2017.
 */

public class DB_helper_city extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Citypostion.db";
    public static String pos_x ;
    public static String pos_y;
    Context context ;
    public DB_helper_city(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Stet_City.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_Stet_City.SQL_DELETE);
        onCreate(db);
    }

    public void insert_city_to_table (String t1 , String t2 , String t3 , String t4 ){
        ContentValues val = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        //save in database
        val.put(Table_City.ENTER_COLUMN.NAME_CIYT_AR, t1);
        val.put(Table_City.ENTER_COLUMN.NAME_CIYT_EN, t2);
        val.put(Table_City.ENTER_COLUMN.POS_X, t3);
        val.put(Table_City.ENTER_COLUMN.POS_Y, t4);
        db.insert(Table_City.ENTER_COLUMN.TABLE_CITY,null,val);
        val.clear();
    }

    public boolean search_city_en (String city){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Table_City.ENTER_COLUMN.NAME_CIYT_EN };
        String selection = Table_City.ENTER_COLUMN.NAME_CIYT_EN + " = ?" ;
        String[] selectionArgs = {city};
        Cursor cursor = db.query(Table_City.ENTER_COLUMN.TABLE_CITY, columns , selection , selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean search_city_ar (String city){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Table_City.ENTER_COLUMN.NAME_CIYT_EN };
        String selection = Table_City.ENTER_COLUMN.NAME_CIYT_AR + " = ?" ;
        String[] selectionArgs = {city};
        Cursor cursor = db.query(Table_City.ENTER_COLUMN.TABLE_CITY, columns , selection , selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public void get_info_en (String city) {

        SQLiteDatabase db = this.getReadableDatabase();
        String columns [] = {Table_City.ENTER_COLUMN.POS_X,Table_City.ENTER_COLUMN.POS_Y};
        String selection = Table_City.ENTER_COLUMN.NAME_CIYT_EN + " = ?";
        String[] selectionArgs = {city};
        Cursor cursor = db.query(Table_City.ENTER_COLUMN.TABLE_CITY, columns , selection , selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            pos_x = cursor.getString(cursor.getColumnIndex(Table_City.ENTER_COLUMN.POS_X)).toString().trim();
            pos_y = cursor.getString(cursor.getColumnIndex(Table_City.ENTER_COLUMN.POS_Y)).toString().trim();
        }
    }

    public void get_info_ar (String city) {

        SQLiteDatabase db = this.getReadableDatabase();
        String columns [] = {Table_City.ENTER_COLUMN.POS_X,Table_City.ENTER_COLUMN.POS_Y};
        String selection = Table_City.ENTER_COLUMN.NAME_CIYT_AR + " = ?";
        String[] selectionArgs = {city};
        Cursor cursor = db.query(Table_City.ENTER_COLUMN.TABLE_CITY, columns , selection , selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            pos_x = cursor.getString(cursor.getColumnIndex(Table_City.ENTER_COLUMN.POS_X)).toString().trim();
            pos_y = cursor.getString(cursor.getColumnIndex(Table_City.ENTER_COLUMN.POS_Y)).toString().trim();
        }
    }

}
