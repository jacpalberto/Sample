package com.example.qualtopgroup.sample.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alberto Carrillo on 11/09/17.
 */

public class GetNotificationsResponse {

    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
