package com.example.qualtopgroup.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qualtopgroup.sample.network.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Alberto Carrillo on 11/09/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final List<Notification> notifications;
    private final Calendar today;
    private Context context;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.notifications = notifications;
        this.context = context;
        today = Calendar.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.tvTime.setText(calculateDaysDiff(notification));

        holder.tvTitle.setText(notification.getMessage());
        holder.btnAction.setText((notification.getType().equals("Connect")
                ? context.getString(R.string.connect)
                : context.getString(R.string.view_details)));
        //drawContactAvatar(holder,notification.getAvatar());
    }

    private String calculateDaysDiff(Notification notification) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = formatter.parse(notification.getCreatedAt());//catch exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar notificationDate = Calendar.getInstance();
        notificationDate.setTime(d);
        long diff = today.getTimeInMillis() - notificationDate.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);
        if (days == 0) return "hoy";
        if (days == 1) return "1 día";
        else return days + " días";
    }

    @Override
    public int getItemCount() {
        return (notifications != null && !notifications.isEmpty()) ? notifications.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfile;
        private Button btnAction;
        private TextView tvTitle;
        private TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.in_iv_profile);
            tvTime = (TextView) itemView.findViewById(R.id.in_tv_time);
            tvTitle = (TextView) itemView.findViewById(R.id.in_tv_title);
            btnAction = (Button) itemView.findViewById(R.id.in_btn_action);
        }
    }
}
