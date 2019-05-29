package com.imageex.imageexapp;

public class EventActionImage {
    public static final int DELETE_IMAGE = 1;
    public static final int UPLOAD_IMAGE = 2;
    public static final int TAKE_IMAGE = 3;

    private int flag;

    public EventActionImage(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
