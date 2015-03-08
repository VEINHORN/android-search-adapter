package com.veinhorn.example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.veinhorn.searchadapter.SearchAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by veinhorn on 8.3.15.
 */
public class MyAdapter extends SearchAdapter<Movie> {
    class ViewHolder {
        @InjectView(R.id.serial_title) TextView title;
        @InjectView(R.id.serial_original_title) TextView enTitle;
        @InjectView(R.id.serial_poster) ImageView poster;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public MyAdapter(List<Movie> movies, Context context) {
        super(movies, context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(filteredContainer.get(position).getTitle());
        viewHolder.enTitle.setText(filteredContainer.get(position).getEnTitle());
        viewHolder.poster.setImageDrawable(context.getResources().getDrawable(filteredContainer.get(position).getPoster()));
        return convertView;
    }
}