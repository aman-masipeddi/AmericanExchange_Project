package com.dji.americanexchange_project.views;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// Interface is created and implemented in the MainActivity.
public interface IView {
    void addMarkers(GoogleMap googleMap, List<LatLng> locationsList);
    void toastMessage(String message);

}

