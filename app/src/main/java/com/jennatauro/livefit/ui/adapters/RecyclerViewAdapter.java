package com.jennatauro.livefit.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennatauro on 2014-11-22.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter <RecyclerViewAdapter.RecyclerViewBaseHolder> implements ViewAdapter<T>, LiveFitItemClickAdapter {

    protected List<T> items;
    protected AdapterView.OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(){
        items = new ArrayList<T>();
    }

    public void replace(List<T> objects){
        items = objects;
        notifyDataSetChanged();
    }

    public void addData(List<T> objects, int position){
        throw new UnsupportedOperationException();
    }

    public void removeData(int position){
        throw new UnsupportedOperationException();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void onItemClick(RecyclerViewBaseHolder viewHolder){
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getPosition(), viewHolder.getItemId());
        }
    }

    public T getItem(int position){ return items.get(position); }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class RecyclerViewBaseHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        RecyclerViewAdapter mAdapter;

        public RecyclerViewBaseHolder(View view, RecyclerViewAdapter adapter) {
            super(view);
            mAdapter = adapter;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemClick(this);
        }
    }
}
