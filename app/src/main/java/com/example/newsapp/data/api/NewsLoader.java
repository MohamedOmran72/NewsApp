package com.example.newsapp.data.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.newsapp.data.model.NewsData;
import com.example.newsapp.utils.QueryUtils;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<NewsData> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of news.
        List<NewsData> newsData = QueryUtils.fetchNewsData(mUrl);
        return newsData;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
