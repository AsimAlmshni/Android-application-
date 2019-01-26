package com.najah.edu.DataBaseToMap;

import android.provider.BaseColumns;

/**
 * Created by karam odeh on 4/26/2017.
 */

public class Table_Centers {
    public Table_Centers(){}
    protected static final String TEXT = " TEXT ";//type is Text
    protected static final String INT = " INTEGER ";//type is INTEGER
    protected static final String COMMA = " , ";//type is COMMA

    public static abstract class ENTER_COL implements BaseColumns {
        public static final String TABLE_MAP_CENTERS = "MAPCENTERS";
        public static final String NAME_CETER = "NAMECETER";
        public static final String PHONE_CENTER = "PHONECENTER";
        public static final String TITLE_CENTER = "TITLECENTER";
        public static final String CITY_CENTER = "CITYCENTER";
        public static final String TYPE_CENTER = "TYPECENTER";
        public static final String POS_X = "POSX";
        public static final String POS_Y = "POSY";
        public static final String CITY_CENTER_AR  = "CITYCENTERAR";
    }
}
