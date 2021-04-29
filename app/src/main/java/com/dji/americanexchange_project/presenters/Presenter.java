package com.dji.americanexchange_project.presenters;


// Importing required libraries.
import android.database.Cursor;

import com.dji.americanexchange_project.models.IModel;
import com.dji.americanexchange_project.models.Model;
import com.dji.americanexchange_project.views.IView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

// Creating a presenter class.
public class Presenter implements IPresenter{

    IView iView; // Declaring the IView object.
    private IModel iModel; // Declaring the IModel object.


    // Presenter constructor.
    public Presenter(IView iView) {
        this.iView = iView;
        iModel = new Model(iView);
    }

    //This function is used to insert data to the SQLite database.
    public void addData(LatLng loc){

        boolean insertData = iModel.addDatatoDB(loc);

        if (insertData) {
            iView.toastMessage("Location Added");
        } else {
            iView.toastMessage("Something went wrong");
        }
    }

    // This function is used to get the list of locations.
    @Override
    public List<LatLng> getLocationsList() {
        List<LatLng> locations = new ArrayList<LatLng>();

        Cursor data = iModel.getDatafromDB();

        while(data.moveToNext()){
            locations.add(new LatLng(data.getDouble(1),data.getDouble(2)));
        }

        return locations;
    }


    // When the add location button from the side menu is clicked, this function is called.
    @Override
    public Boolean addLocationClicked( Boolean value) {

        return true;
    }

    // This functions calls the getLocationList() function and then calls the addMarkers() function from the IView.
    @Override
    public void addMarkers(GoogleMap googleMap) {

        List<LatLng> locations;
        locations = getLocationsList();

        iView.addMarkers(googleMap, locations);
    }

}


