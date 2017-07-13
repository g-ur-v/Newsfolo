package com.gaurav.android.newsfolo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by rawal's on 13-Jul-17.
 */

public class HeadlineLoader extends AsyncTaskLoader<List<Headline>>{
    private String mUrl;
    public HeadlineLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Headline> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<Headline> headlines = QueryUtils.fetchEarthquakeData(mUrl);
        return headlines;
    }
}
