package com.dji.americanexchange_project.presenters;

import android.content.Context;

// Creating a INotificationPresenter interface which will be implemented in NotificationPresenter class.
public interface INotificationPresenter {

    void createNotification(Context context, String message);
    void getMessageFromAPI();
}
