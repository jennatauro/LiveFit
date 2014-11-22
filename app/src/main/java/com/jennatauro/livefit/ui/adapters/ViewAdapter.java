package com.jennatauro.livefit.ui.adapters;

import java.util.List;

/**
 * Created by jennatauro on 2014-11-22.
 */
public interface ViewAdapter<T> {

    /*
     * Replaces entire adapter with passed in list
     */
    public void replace(List<T> objects);

    /*
     * Add range of data, starting at positon
     */
    public void addData(List<T> object, int position);

    /*
     * Remove data at position
     */
    public void removeData(int position);
}
