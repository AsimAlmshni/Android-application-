package com.najah.edu.DatABasE;

/**
 * Created by islam on 4/22/2017.
 */

public class StatmentSql {
    protected static final String [] COLUMNS = {
            //call COLUMNS from table
            Table.ENTER.FIRST_NAME ,
            Table.ENTER.LAST_NAME,
            Table.ENTER.EMAIL ,
            Table.ENTER.PASSWORD ,
            Table.ENTER.PHONE
    };
    //create table to add task in database
    protected static final String SQL_CREATE = "CREATE TABLE " + Table.ENTER.TABLE_NAME + " (" +
            Table.ENTER.EMAIL + Table.TEXT + " PRIMARY KEY," +
            Table.ENTER.PASSWORD + Table.TEXT + Table.COMMA +
            Table.ENTER.FIRST_NAME + Table.TEXT + Table.COMMA +
            Table.ENTER.LAST_NAME + Table.TEXT + Table.COMMA +
            Table.ENTER.PHONE + Table.TEXT + " );";
    //delete table to add task from database
    protected static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + Table.ENTER.TABLE_NAME;
}
