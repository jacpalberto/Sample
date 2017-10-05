package com.example.qualtopgroup.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alberto Carrillo on 04/09/17.
 */

public class Activity2 extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, Activity2.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        init();
    }

    private void init() {
        findViewById(R.id.btn_enter).setOnClickListener
                (v1 -> startActivity(ActivityMenu.newIntent(this)));
        findViewById(R.id.btn_fb).setOnClickListener
                (v1 -> startActivity(ActivityFabs.newIntent(this)));

    }
}
