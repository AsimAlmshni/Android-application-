package com.najah.edu.DB_city_position;

/**
 * Created by karam odeh on 4/27/2017.
 */

public class SQL_Stet_City {
    protected static final String [] COLUMNS_MAP = {
            //call COLUMNS from table
            Table_City.ENTER_COLUMN.NAME_CIYT_AR ,
            Table_City.ENTER_COLUMN.NAME_CIYT_EN,
            Table_City.ENTER_COLUMN.POS_X ,
            Table_City.ENTER_COLUMN.POS_Y
    };
    //create table to add task in database
    protected static final String SQL_CREATE = "CREATE TABLE " + Table_City.ENTER_COLUMN.TABLE_CITY+ " (" +
            Table_City.ENTER_COLUMN.NAME_CIYT_AR + Table_City.TEXT + Table_City.COMMA +
            Table_City.ENTER_COLUMN.NAME_CIYT_EN + Table_City.TEXT + " PRIMARY KEY," +
            Table_City.ENTER_COLUMN.POS_X + Table_City.TEXT + Table_City.COMMA +
            Table_City.ENTER_COLUMN.POS_Y + Table_City.TEXT  + " );";
    //delete table to add task from database
    protected static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + Table_City.ENTER_COLUMN.TABLE_CITY;
}
