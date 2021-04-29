package com.dji.americanexchange_project.models;

// Importing required Libraries.
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dji.americanexchange_project.views.IView;
import com.google.android.gms.maps.model.LatLng;

import static android.content.ContentValues.TAG;
import static com.dji.americanexchange_project.models.DatabaseHelper.COL1;
import static com.dji.americanexchange_project.models.DatabaseHelper.COL2;
import static com.dji.americanexchange_project.models.DatabaseHelper.TABLE_NAME;

// Creating a Model class whihc implements the IModel interface.
public class Model implements IModel{

    private SQLiteDatabase database; // declaring a SQLiteDatabase object.

    // Constructor for the Model class.
    public Model(IView iView) {
        database = new DatabaseHelper((Context) iView).getWritableDatabase();// Initializing the Database object.
    }

    // This function/method deals with adding the user given location to the database. This is called in Presenter class function.
    @Override
    public boolean addDatatoDB(LatLng item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item.latitude);
        contentValues.put(COL2, item.longitude);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = database.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // This function/method deals with retrieving the user given locations List from the database. This is called in Presenter class function.
    @Override
    public Cursor getDatafromDB() {

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;

    }
}

