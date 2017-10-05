package com.example.qualtopgroup.sample.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alberto Carrillo on 11/09/17.
 */

public class Notification {
    @SerializedName("notificationId")
    @Expose
    private Integer notificationId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("receiverId")
    @Expose
    private Integer receiverId;
    @SerializedName("emiterId")
    @Expose
    private Integer emiterId;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getEmiterId() {
        return emiterId;
    }

    public void setEmiterId(Integer emiterId) {
        this.emiterId = emiterId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
