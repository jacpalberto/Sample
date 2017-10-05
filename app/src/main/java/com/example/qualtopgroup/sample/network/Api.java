package com.example.qualtopgroup.sample.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Alberto Carrillo on 11/09/17.
 */

public interface Api {
    @GET("users/notifications")
    Observable<GetNotificationsResponse> getNotifications();
}
