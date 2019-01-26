package com.najah.edu.DB_city_position;

import android.provider.BaseColumns;

/**
 * Created by karam odeh on 4/27/2017.
 */

public class Table_City {
    public Table_City(){}
    protected static final String TEXT = " TEXT ";//type is Text
    protected static final String COMMA = " , ";//type is COMMA

    public static abstract class ENTER_COLUMN implements BaseColumns {
        public static final String TABLE_CITY = "TABLECITYS";
        public static final String NAME_CIYT_EN = "NAMECIYTEN";
        public static final String NAME_CIYT_AR = "NAMECIYTAR";
        public static final String POS_X = "POSX";
        public static final String POS_Y = "POSY";
    }
}
