package com.example.qualtopgroup.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alberto Carrillo on 04/09/17.
 */

public class AdapterImg extends RecyclerView.Adapter<AdapterImg.RecyclerHolder> {
    private ArrayList<String> content;
    private Context context;

    public interface Callbacks {
        public void onButtonClicked(String titleKey);
    }

    private Callbacks mCallbacks;

    public AdapterImg(Context context, Callbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
        content = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            content.add(String.valueOf(i));
        }
        content.add(String.valueOf(4));
        content.add(String.valueOf(7));
        content.add(String.valueOf(1));
        content.add(String.valueOf(3));
        content.add(String.valueOf(6));
        content.add(String.valueOf(5));
        content.add(String.valueOf(4));
        content.add(String.valueOf(7));
        content.add(String.valueOf(3));
        content.add(String.valueOf(7));
        content.add(String.valueOf(1));
        content.add(String.valueOf(3));
        content.add(String.valueOf(5));
        content.add(String.valueOf(1));
        content.add(String.valueOf(4));
        content.add(String.valueOf(7));
        content.add(String.valueOf(3));
        this.context = context;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.binder(content.get(position), position);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView img;
        CardView cord;

        RecyclerHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            cord = (CardView) itemView.findViewById(R.id.coord);
        }

        void binder(String num, int position) {
            {
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                cord.setCardBackgroundColor(color);
                img.setOnClickListener(view -> {
                    if (mCallbacks != null) {
                        mCallbacks.onButtonClicked(String.valueOf(position));
                    }
                });
                img = (ImageView) itemView.findViewById(R.id.img);
                if (img != null)
                    switch (num) {
                        case "1":
                            img.setImageResource(R.drawable.fr1);
                            break;
                        case "2":
                            img.setImageResource(R.drawable.fr2);
                            break;
                        case "3":
                            img.setImageResource(R.drawable.fr3);
                            break;
                        case "4":
                            img.setImageResource(R.drawable.fr4);
                            break;
                        case "5":
                            img.setImageResource(R.drawable.fr5);
                            break;
                        case "6":
                            img.setImageResource(R.drawable.fr6);
                            break;
                        case "7":
                            img.setImageResource(R.drawable.fr7);
                            break;
                    }
            }
        }
    }
}