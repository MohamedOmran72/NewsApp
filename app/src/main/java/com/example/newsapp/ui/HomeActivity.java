package com.example.newsapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.data.api.NewsLoader;
import com.example.newsapp.data.model.NewsData;
import com.example.newsapp.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsData>> {

    private static final String THE_GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?api-key=50c12479-8e4f-4d01-8bd3-c1f984aa20b0";
    private static final int NEWS_LOADER_ID = 1;
    private static ArrayList<NewsData> newsArrayList = new ArrayList<>();
    private final String ACTIVITY_TAG = "Activity Main";
    private LoaderManager loaderManager;

    @BindView(R.id.recycler_home)
    RecyclerView recyclerHome;
    @BindView(R.id.progress_indicator)
    ProgressBar progressIndicator;
    @BindView(R.id.text_empty_view)
    TextView textEmptyView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        newsAdapter = new NewsAdapter(this, this, newsArrayList);
        recyclerHome.setAdapter(newsAdapter);

        if (isConnected) {
            Log.d(ACTIVITY_TAG, "device connected to network");
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getSupportLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            textEmptyView.setText(R.string.no_internet_connection);
        }

    }

    @NonNull
    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, @Nullable Bundle args) {
        //return new NewsLoader(this, THE_GUARDIAN_REQUEST_URL);
        Log.d(ACTIVITY_TAG, " onCreateLoader");


        NewsLoader loader = new NewsLoader(this, QueryUtils.uriBuilder());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                loader.loadInBackground();
//            }
//        }).start();
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsData>> loader, List<NewsData> newsData) {
        //after finishing the background thread work, hide the progress bar
        Log.d(ACTIVITY_TAG, " onLoadFinished");

        //clear the adapter from the previous data
        //mAdapter.clear();

        //if there is a valid list of {@link News} then add them to the adapter's data set
        //this will trigger the listView to update

        progressIndicator.setVisibility(View.GONE);
        if (newsData == null || newsData.isEmpty()) {
            textEmptyView.setText(R.string.no_news);
            Log.d(ACTIVITY_TAG, " Data is empty or null");
        } else {
            newsArrayList.addAll(newsData);
            newsAdapter.notifyDataSetChanged();
            Log.d(ACTIVITY_TAG, " " + newsData.toString());
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsData>> Loader) {
        Log.d(ACTIVITY_TAG, " onLoadFinished");

        newsAdapter.onDetachedFromRecyclerView(recyclerHome);
    }
}