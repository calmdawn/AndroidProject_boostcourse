package com.calmdawn.boostcoursemobileproject.model;

public class MovieGalleryItem {
    String url;
    int viewType;

    public MovieGalleryItem(String url, int viewType) {
        this.url = url;
        this.viewType = viewType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
