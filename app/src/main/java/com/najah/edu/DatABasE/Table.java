package com.najah.edu.DatABasE;

import android.provider.BaseColumns;

/**
 * Created by islam on 4/22/2017.
 */

public final class Table {
    public Table(){}
    protected static final String TEXT = " TEXT ";//type is Text
    protected static final String INT = " INTEGER ";//type is INTEGER
    protected static final String COMMA = " , ";//type is COMMA

    public static abstract class ENTER implements BaseColumns {
        public static final String TABLE_NAME = "USERACOUNT";
        public static final String FIRST_NAME = "FIRSTNAME";
        public static final String LAST_NAME = "LASTNAME";
        public static final String EMAIL = "EMAIL";
        public static final String PASSWORD = "PASSWORD";
        public static final String PHONE = "PHONE";
    }

}
