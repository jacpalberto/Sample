package com.example.qualtopgroup.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualtopgroup.sample.network.BaseClient;
import com.example.qualtopgroup.sample.network.GetNotificationsResponse;
import com.example.qualtopgroup.sample.network.Notification;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Alberto Carrillo on 11/09/17.
 */

public class ActivityMulFabs extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvNotifications;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private NotificationAdapter adapter;
    private List<Notification> notifications;
    private TextView tvNotFound;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, b_fab_close, b_fab_open;

    public static Intent newIntent(Context context) {
        return new Intent(context, ActivityMulFabs.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul_fabs);
        linkUI();
        init();
        initRvs();
    }

    private void initRvs() {
        rvNotifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.startAnimation(b_fab_close);
                    fab.hide();
                    if (isFabOpen) {
                        fab1.startAnimation(fab_close);
                        fab2.startAnimation(fab_close);
                        fab3.startAnimation(fab_close);
                    }

                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.startAnimation(b_fab_open);
                    fab.show();
                    if (isFabOpen) {
                        fab1.startAnimation(fab_open);
                        fab2.startAnimation(fab_open);
                        fab3.startAnimation(fab_open);
                    }
                }
            }
        });
    }

    private void init() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        b_fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.b_fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        b_fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.b_fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_back);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this::fetchNotifications);
    }

    private void linkUI() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        rvNotifications = (RecyclerView) findViewById(R.id.an_rv_notifications);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.an_swipe);
        progressBar = (ProgressBar) findViewById(R.id.an_progress_bar);
        tvNotFound = (TextView) findViewById(R.id.an_tv_not_found);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                animateflocationBtn();
                break;
            case R.id.fab1:
                animateflocationBtn();
                fetchNotifications();
                break;
            case R.id.fab2:
                startActivity(ActivityMaps.newIntent(this));
                break;
            case R.id.fab3:
                Toast.makeText(this, "fab3 button", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void fetchNotifications() {
        showProgress();
        Observable<GetNotificationsResponse> observable = BaseClient.provideApiService().getNotifications();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(GetNotificationsResponse::getNotifications)
                .flatMap(Observable::fromIterable)
                .filter(notification -> notification.getType().equals("Task"))
                .toList()
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(List<Notification> notifications) {
        this.notifications = notifications;
        setupRvNotifications();
        dismissProgress();
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        Log.d("Error", throwable.toString());
        dismissProgress();
    }

       /* private int compareTo(Notification notification, Notification notification1) {
        if (notification.getCreatedAt().length() > notification1.getCreatedAt().length())
            return 0;
        else return 1;
    }*/


    public void animateflocationBtn() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    private void setupRvNotifications() {
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotifications.setLayoutManager(llManager);
        adapter = new NotificationAdapter(this, notifications);
        rvNotifications.setAdapter(adapter);
        rvNotifications.setHasFixedSize(true);
        tvNotFound.setVisibility(notifications.isEmpty() ? View.VISIBLE : View.GONE);
    }

    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    public void showProgress() {
        if (!swipeRefresh.isRefreshing()) progressBar.setVisibility(View.VISIBLE);
    }
}
