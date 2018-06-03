package com.jordanhuus.www.newsudacityproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jordanhuus on 6/3/2018.
 */

public class CategoriesAdapter extends ArrayAdapter<Category>{
    public CategoriesAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        // Ensure recycled view is inflated
        if(listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item, parent, false);
        }

        // Retrieve Category object
        Category category = getItem(position);

        // Set category icon
        ImageView categoryIcon = listItem.findViewById(R.id.category_icon);
        categoryIcon.setImageResource(category.getCategoryIconResourceId());

        // Set category title
        TextView categoryTitle = listItem.findViewById(R.id.category_list_item_title);
        categoryTitle.setText(category.getCategoryTitle());

        return listItem;
    }


}
