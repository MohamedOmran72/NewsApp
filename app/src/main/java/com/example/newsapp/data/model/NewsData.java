package com.example.newsapp.data.model;

public class NewsData {
    private String mTitle;

    private String mAuthName;

    private String mDate;

    private String mNewsCategory;

    private String mUrl;

    public NewsData(String mTitle, String mAuthName, String mDate, String mNewsCategory, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthName = mAuthName;
        this.mDate = mDate;
        this.mNewsCategory = mNewsCategory;
        this.mUrl = mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getNewsCategory() {
        return mNewsCategory;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAuthName() {
        return mAuthName;
    }
}
