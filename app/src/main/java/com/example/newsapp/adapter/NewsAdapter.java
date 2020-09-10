package com.example.newsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.data.model.NewsData;
import com.example.newsapp.utils.HelperMethod;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.core.content.ContextCompat.startActivity;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<NewsData> newsDataList;

    public NewsAdapter(Context context, Activity activity, List<NewsData> newsDataList) {
        this.context = context;
        this.activity = activity;
        this.newsDataList = newsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.textTitle.setText(newsDataList.get(position).getTitle());
        holder.textAuthName.setText(String.format(context.getString(R.string.by),newsDataList.get(position).getAuthName()));
        holder.textDate.setText(HelperMethod.formatDate(newsDataList.get(position).getDate()));
        holder.textNewsCategory.setText(newsDataList.get(position).getNewsCategory());
    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri newsUri = Uri.parse(newsDataList.get(position).getUrl());
                startActivity(context, new Intent(Intent.ACTION_VIEW, newsUri), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_auth_name)
        TextView textAuthName;
        @BindView(R.id.text_date)
        TextView textDate;
        @BindView(R.id.text_news_category)
        TextView textNewsCategory;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
