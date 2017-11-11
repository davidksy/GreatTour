package com.example.a60047506.greattour;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by 60047506 on 2017-11-10.
 */

public class MyItem {

    private Drawable icon;
    private Bitmap bitmap;
    private String name;
    private String contents;

    public Bitmap getBitmp(){
        return bitmap;
    }

    public void setBit(Bitmap bm){
        this.bitmap = bm;
    }
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}

