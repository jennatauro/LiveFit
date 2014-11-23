package com.jennatauro.livefit.ui.dynamiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.eventBus.events.EditExerciseEvent;
import com.jennatauro.livefit.eventBus.events.ExerciseDeletedEvent;
import com.jennatauro.livefit.eventBus.events.SeeExerciseEvent;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by jennatauro on 2014-11-23.
 */
public class StableArrayAdapter extends ArrayAdapter<Exercise> implements View.OnClickListener{

    @Inject
    Bus bus;

    final int INVALID_ID = -1;

    HashMap<Exercise, Integer> mIdMap = new HashMap<Exercise, Integer>();
    List<Exercise> objects = new ArrayList<Exercise>();

    public StableArrayAdapter(Context context, int resource, List<Exercise> objects) {
        super(context, resource, objects);
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);
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

        convertView.findViewById(R.id.delete_exercise).setOnClickListener(this);
        convertView.findViewById(R.id.edit_exercise).setOnClickListener(this);
        convertView.findViewById(R.id.see_exercise).setOnClickListener(this);

        convertView.findViewById(R.id.delete_exercise).setTag(position);
        convertView.findViewById(R.id.edit_exercise).setTag(position);
        convertView.findViewById(R.id.see_exercise).setTag(position);
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

    public void replace(ArrayList<Exercise> exercises) {
        this.objects = exercises;
        mIdMap.clear();
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case(R.id.delete_exercise): {
                Integer index = (Integer) v.getTag();
                Exercise exercise = objects.get(index);
                bus.post(new ExerciseDeletedEvent(exercise));
                break;
            }
            case(R.id.edit_exercise): {
                Integer index = (Integer) v.getTag();
                Exercise exercise = objects.get(index);
                bus.post(new EditExerciseEvent(exercise, index));
                break;
            }
            case(R.id.see_exercise): {
                Integer index = (Integer) v.getTag();
                Exercise exercise = objects.get(index);
                bus.post(new SeeExerciseEvent(exercise));
                break;
            }
        }
    }
}
