package com.dji.americanexchange_project.presenters;

// Importing required libraries.
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dji.americanexchange_project.R;

import com.dji.americanexchange_project.views.IView;

import org.json.JSONException;
import org.json.JSONObject;


// Creating a NotificationPresenter class which implements INotificationPresenter interface.
public class NotificationPresenter implements INotificationPresenter{

    IView iView; // Creating an object.

    // Constructor fo the NotificationPresenter class.
    public NotificationPresenter(IView iView) {
        this.iView = iView;
       }

    // This function is called in the getMessagefromAPI() function and deals with building a notification.
    @Override
    public void createNotification(Context context, String message) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification_ID")
                .setSmallIcon(R.drawable.ic_add_location_black_24dp)
                .setContentTitle("Notification")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(01, builder.build());

    }

    // This function makes a call to the API and retrieves the information and then calls the createNotification() function.
    @Override
    public void getMessageFromAPI(){

        RequestQueue queue = Volley.newRequestQueue((Context) iView); // Queue is declared and initialized.
        String url ="https://run.mocky.io/v3/01024cf4-5557-4705-97af-d4e346b1b601"; // giving the API.



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            createNotification((Context) iView, response.getString("message"));// Sending the response message to createNotification.
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        createNotification((Context) iView, "error retrieving message"); // Sending error message to createNotification if retrieving the message from API is failed.

                    }
                });

        queue.add(jsonObjectRequest); // Adding the jsonObjectRequest to be executed.
    }
}
