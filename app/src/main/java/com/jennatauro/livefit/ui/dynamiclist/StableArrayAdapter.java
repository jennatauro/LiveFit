package com.jennatauro.livefit.ui.dynamiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class StableArrayAdapter extends ArrayAdapter<Exercise> {

    final int INVALID_ID = -1;

    HashMap<Exercise, Integer> mIdMap = new HashMap<Exercise, Integer>();
    List<Exercise> objects = new ArrayList<Exercise>();

    public StableArrayAdapter(Context context, int resource, List<Exercise> objects) {
        super(context, resource, objects);
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_exercise, null);
        }

        Exercise workout = getItem(position);

        setRowView(position, convertView);


        return convertView;
    }

    private void setRowView(final int position, final View convertView) {
        Exercise item = getItem(position);
        ((TextView) convertView.findViewById(R.id.exercise_name)).setText(item.getTitle());
    }

    @Override
    public Exercise getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Exercise item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
