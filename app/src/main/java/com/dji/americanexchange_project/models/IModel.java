package com.dji.americanexchange_project.models;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;

// Creating an interface IModel which is implemented by Model class.
public interface IModel {

    boolean addDatatoDB(LatLng item);
    Cursor getDatafromDB();
}

