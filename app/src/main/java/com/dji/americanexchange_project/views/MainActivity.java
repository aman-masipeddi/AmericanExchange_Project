package com.dji.americanexchange_project.views;

//Importing the required packages
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dji.americanexchange_project.R;
import com.dji.americanexchange_project.presenters.INotificationPresenter;
import com.dji.americanexchange_project.presenters.IPresenter;
import com.dji.americanexchange_project.presenters.NotificationPresenter;
import com.dji.americanexchange_project.presenters.Presenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, IView {

//    Declaring required variables.
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private IPresenter iPresenter;
    private INotificationPresenter iNotifcationPresenter;

    Boolean addLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Home"); //Setting title for this activity.


        createNotificationChannel(); // Calling the function to create a notification channel.

        iNotifcationPresenter = new NotificationPresenter(this); // Initializing the object.

        iNotifcationPresenter.getMessageFromAPI(); // calling the function/method from the class.

        iPresenter = new Presenter(this); // Initializing the object.

        // Creating a map Fragment.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDrawerLayout = findViewById(R.id.drawerLayout); // Initializing the drawer layout for the navigation view.
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close); // initializing toggle bar.

        mDrawerLayout.addDrawerListener(mToggle); // adding the toggle bar to the drawerlayout.
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNavigationView = findViewById(R.id.navigation_view); // Initializing the navigation view.

        // setting a item click listener for the items in menu of navigation view.
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.add_location: // code for what happens when we click add location button.
                       addLocation =  iPresenter.addLocationClicked(addLocation); // Sending the result of the addLocationClicked to the variable addLocation.
                       toastMessage("You may Add your location now"); // Sending a toast.
                }
                return false;
            }
        });
    }

    // This function is used to call the navigation view.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//     This function is used to display the respective data of the map.
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        iPresenter.addMarkers(googleMap); // Calling the addMarkers function from the presenter object.
    }

    // addMarker function. This deals with adding marker to the map
    @Override
    public void addMarkers(final GoogleMap googleMap, List<LatLng> locationsList) {

        List<LatLng> constLocationList = new ArrayList<>();
        constLocationList.add(new LatLng(60,-60));
        constLocationList.add(new LatLng(41,-74));

        for(LatLng loc: constLocationList){
            googleMap.addMarker(new MarkerOptions().position(loc));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }


        for(LatLng loc: locationsList){
            googleMap.addMarker(new MarkerOptions().position(loc));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

        // This deals with what happens when map is interacted.
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(addLocation != false){
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);

                    googleMap.addMarker(markerOptions);

                    iPresenter.addData(latLng);

                    addLocation = false;

                }
                else {
                    toastMessage("Please enable add location in menu");
                }
            }
        });

    }

    // toastMessage function is used to create toasts.
    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    // This function is used to create a notification channel and is called in the onCreate method/function.
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "Notification Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification_ID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
