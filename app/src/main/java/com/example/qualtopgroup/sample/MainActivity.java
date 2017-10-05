package com.example.qualtopgroup.sample;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxBus = RxBus.getInstance();
        rxBus.getRxBus()
                .subscribe
                (a -> Toast.makeText(this, a.getA(), Toast.LENGTH_SHORT).show());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxBus.getRxBus().unsubscribeOn(Schedulers.io());
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterExample());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(Activity2.newIntent(this)));
    }

    private void snack(String s) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
