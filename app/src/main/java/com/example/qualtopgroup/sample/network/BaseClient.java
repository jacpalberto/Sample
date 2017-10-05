package com.example.qualtopgroup.sample.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alberto Carrillo on 11/09/17.
 */
public class BaseClient {

    private static String Token;

    private static OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImphY3BhbGJlcnRvQGdtYWlsLmNvbSIsInVzZXJTdGF0dXMiOnRydWUsInVzZXJIZWFkTGluZSI6IkFuZHJvaWQgRGV2ZWxvcGVyIiwidXNlclBlcnNvbmFsRW1haWwiOiJqYWNwYWxiZXJ0b0Bob3RtYWlsLmNvbSIsInVzZXJXb3JrRW1haWwiOiJqYWNwYWxiZXJ0b0BtYWlsZmYuY29tIiwidXNlclNreXBlIjoiaGRqZGpkIiwidXNlckNvdW50cnkiOiJNw6l4aWNvIiwidXNlclBob25lIjoiMzMzMyIsInVzZXJGYWNlYm9vayI6bnVsbCwidXNlclR3aXR0ZXIiOm51bGwsInVzZXJHb29nbGUiOm51bGwsInVzZXJMaW5rZWRpbiI6bnVsbCwidXNlckZ1bGxOYW1lIjoiQWxiZXJ0byBDYXJyaWxsbyIsImlhdCI6MTUwNTE1NjAzNSwiZXhwIjoxNTA1MTU5NjM1NDM2fQ.0pJok-JY-kSNiLEoZDrlb_VYMA3R-tQ0kUKDU5LTmWg";

        if (Token != null) {
            return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(chain -> {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("token", Token);
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }).build();
        } else return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public static Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://ec2-34-202-194-185.compute-1.amazonaws.com:3000/api/")
                .client(provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Api provideApiService() {
        return retrofit().create(Api.class);
    }
}
