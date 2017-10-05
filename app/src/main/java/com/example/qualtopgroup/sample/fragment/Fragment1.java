package com.example.qualtopgroup.sample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qualtopgroup.sample.R;

/**
 * Created by Alberto Carrillo on 04/09/17.
 */

public class Fragment1 extends Fragment {

    private OnFragmentInteractionListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vRoot = inflater.inflate(R.layout.fragment1, container, false);
        TextView tvName = (TextView) vRoot.findViewById(R.id.tv_name);
        EditText etName = (EditText) vRoot.findViewById(R.id.et_name);
        vRoot.findViewById(R.id.btn_click).setOnClickListener
                (v1 -> tvName.setText(etName.getText().toString()));
        return vRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }
}