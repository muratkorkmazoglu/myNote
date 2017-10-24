package com.krkmz.mynote;

import android.media.Image;

/**
 * Created by muratkorkmazoglu on 24.10.2017.
 */

public class ListModel {
    public ListModel(String text, int image) {
        this.text = text;
        this.image = image;
    }

    private String text;
    private int image;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
