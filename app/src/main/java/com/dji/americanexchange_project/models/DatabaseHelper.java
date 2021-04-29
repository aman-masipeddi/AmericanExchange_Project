package com.dji.americanexchange_project.models;

// Importing required Libraries.
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Creating a DatabaseHelper class which will assist with creating a database.
public class DatabaseHelper extends SQLiteOpenHelper {

    // Creating required variable.
    static final String TABLE_NAME = "locations_table";
    static final String COL0 = "ID";
    static final String COL1 = "Lat";
    static final String COL2 = "Lng";
    private static final String DB_NAME = "locations.db";


    // Constructor for the DatabaseHelper class.
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // This function/method creates the database and required table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL1 + " REAL,"
                + COL2 + " REAL" +
                ");";
        db.execSQL(createTable);
    }

    // This function/method is used when there are any changes or upgrades done to the database. Like adding new tables or new columns in the tables.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
