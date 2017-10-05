package com.example.qualtopgroup.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alberto Carrillo on 05/09/17.
 */

public class ActivityFabs extends AppCompatActivity implements AdapterImg.Callbacks {
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;

    public static Intent newIntent(Context context) {
        return new Intent(context, ActivityFabs.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabs);
        init();
        doSomeWork();
        doSomeWork2();
    }

    private void init() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(v -> startActivity(ActivityMulFabs.newIntent(this)));
        recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        recyclerView.setAdapter(new AdapterImg(this, this));
        recyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            View v = recyclerView.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(600)
                                    .setStartDelay(i * 100)
                                    .start();
                        }

                        return true;
                    }
                });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFab.getVisibility() == View.VISIBLE) {
                    mFab.hide();
                } else if (dy < 0 && mFab.getVisibility() != View.VISIBLE) {
                    mFab.show();
                }
            }
        });
    }

    private void doSomeWork() {
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Integer::valueOf)
                .sorted()
                .filter(integer -> integer % 2 == 0)
                .subscribe(s -> Log.d("f", s + ""));
    }

    private void doSomeWork2() {

        getObservableInt()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .flatMapObservable((Function<List<Integer>, ObservableSource<?>>) Observable::fromIterable)
                .map(String::valueOf)
                .subscribe(l -> Log.d("List", l));
    }

    private List<Integer> parseIntToList(Integer integer) {
        List<Integer> lista = new ArrayList<>();
        lista.add(integer);
        lista.add(integer * 2);
        return lista;
    }

    private Observable<Integer> getObservableInt() {
        return Observable.just(1, 10, 2, 5, 3, 4, 8, 6, 7, 9);
    }

    private Observable<String> getObservable() {
        return Observable.just("100", "1000", "10001", "5", "102", "122", "3", "6", "1232");
    }

    @Override
    public void onButtonClicked(String titleKey) {
        Toast.makeText(this, titleKey, Toast.LENGTH_SHORT).show();
    }
}
