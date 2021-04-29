package com.dji.americanexchange_project.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// Creating a IPresenter interface which will be implemented in Presenter class.
public interface IPresenter {
    void addMarkers(GoogleMap googleMap);
    void addData(LatLng loc);
    List<LatLng> getLocationsList();
    Boolean addLocationClicked( Boolean value);
}
