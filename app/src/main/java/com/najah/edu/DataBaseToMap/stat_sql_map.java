package com.najah.edu.DataBaseToMap;


/**
 * Created by karam odeh on 4/27/2017.
 */

public class stat_sql_map {
    protected static final String [] COLUMNS_MAP = {
            //call COLUMNS from table
            Table_Centers.ENTER_COL.NAME_CETER ,
            Table_Centers.ENTER_COL.PHONE_CENTER,
            Table_Centers.ENTER_COL.TITLE_CENTER ,
            Table_Centers.ENTER_COL.CITY_CENTER,
            Table_Centers.ENTER_COL.TYPE_CENTER ,
            Table_Centers.ENTER_COL.POS_X,
            Table_Centers.ENTER_COL.POS_Y,
            Table_Centers.ENTER_COL.CITY_CENTER_AR
    };
    //create table to add task in database
    protected static final String SQL_CREATE = "CREATE TABLE " + Table_Centers.ENTER_COL.TABLE_MAP_CENTERS + " (" +
            Table_Centers.ENTER_COL.NAME_CETER + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.PHONE_CENTER + Table_Centers.TEXT + " PRIMARY KEY," +
            Table_Centers.ENTER_COL.TITLE_CENTER + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.CITY_CENTER + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.TYPE_CENTER + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.POS_X + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.POS_Y + Table_Centers.TEXT + Table_Centers.COMMA +
            Table_Centers.ENTER_COL.CITY_CENTER_AR + Table_Centers.TEXT + " );";
    //delete table to add task from database
    protected static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + Table_Centers.ENTER_COL.TABLE_MAP_CENTERS;
}
