package com.jordanhuus.www.newsudacityproject.Articles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jordanhuus.www.newsudacityproject.Articles.Model.News;
import com.jordanhuus.www.newsudacityproject.R;

import java.util.List;

/**
 * Created by jordanhuus on 5/18/2018.
 */

public class ArticlesAdapter extends ArrayAdapter<News> {

    public ArticlesAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<News> objects) {
        super(context, 0, textViewResourceId, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list_item = convertView;

        if(list_item==null){
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        // Retrieve News object
        News news = getItem(position);

        // Title
        TextView title = list_item.findViewById(R.id.article_title);
        title.setText(news.getTitle());

        // Publication Date
        TextView publicationDate = list_item.findViewById(R.id.publication_date);
        publicationDate.setText(news.getPublicationDate());

        return list_item;
    }
}
