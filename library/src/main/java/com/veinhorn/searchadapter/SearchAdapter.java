package com.veinhorn.searchadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by veinhorn on 7.3.15.
 */
public abstract class SearchAdapter<T> extends BaseAdapter implements Filterable {
    private static final String TAG = "Filter Adapter";

    private Method method;
    private boolean ignoreCase;

    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> container;
    protected List<T> filteredContainer;

    public SearchAdapter(List<T> container, Context context) {
        this.container = container;
        filteredContainer = new ArrayList<>(container);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        ignoreCase = false;
    }

    public SearchAdapter<T> registerFilter(Class clazz, String fieldName) {
        String methodName = "get" + capitalize(fieldName);
        try {
            method = clazz.getMethod(methodName);
        } catch(NoSuchMethodException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return this;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void filter(String filterStr) {
        if(method != null) getFilter().filter(filterStr);
    }

    public void filter(CharSequence filterStr) {
        if(method != null) getFilter().filter(filterStr);
    }

    protected boolean search(String str1, CharSequence str2) {
        if(ignoreCase) return str1.toLowerCase().contains(str2.toString().toLowerCase());
        return str1.contains(str2);
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public SearchAdapter<T> setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<T> filtered = new ArrayList<>();
                try {
                    for(int i = 0; i < container.size(); i++) {
                        int t1 = 0;
                        T object = container.get(i);
                        String value = (String)method.invoke(object);
                        if(search(value, constraint)) filtered.add(object);
                    }
                    filterResults.count = filtered.size();
                    filterResults.values = filtered;
                    return filterResults;
                } catch(InvocationTargetException e) {
                    Log.e(TAG, e.getMessage(), e);
                } catch(IllegalAccessException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredContainer = (List<T>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredContainer.size();
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return filteredContainer.get(position);
    }
}