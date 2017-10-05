package com.example.qualtopgroup.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.qualtopgroup.sample.fragment.Fragment1;
import com.example.qualtopgroup.sample.fragment.Fragment2;
import com.example.qualtopgroup.sample.fragment.Fragment3;

/**
 * Created by Alberto Carrillo on 04/09/17.
 */

public class ActivityMenu extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener,
        Fragment2.OnFragmentInteractionListener, Fragment3.OnFragmentInteractionListener {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView mRecyclerView;

    public static Intent newIntent(Context context) {
        return new Intent(context, ActivityMenu.class);
    }

    Boolean isEven(int n) {
        return (n % 2 == 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_menu);
        //init();
        init2();
    }

    private void init2() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new Fragment1(), "fr1").commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            String tag = "";
            switch (item.getItemId()) {
                case R.id.action_favorites:
                    tag = "1";
                    //fragment = Fragment1.newInstance();
                    break;
                case R.id.action_schedules:
                    tag = "2";
                    fragment = Fragment2.newInstance();
                    break;
                case R.id.action_music:
                    tag = "3";
                    fragment = Fragment3.newInstance();
                    break;
            }
            if (fragment != null) {
                Fragment fragment2 = getSupportFragmentManager()
                        .findFragmentByTag(fragment.getTag());
                Log.d("Fragment",fragment.toString()+" ");

                if (fragment2 == null) {
                    // fragment must be added
                    Toast.makeText(this, "New in stack tag: " + tag + "frTag: " + fragment.getTag(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.container, fragment, tag).addToBackStack(null).commit();
                } else {
                    Toast.makeText(this, "Already in stack", Toast.LENGTH_SHORT).show();
                    // fragment already added
                }
            }
            return true;
        });

    }
}