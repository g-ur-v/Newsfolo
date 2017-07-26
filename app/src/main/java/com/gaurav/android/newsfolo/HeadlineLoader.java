package com.gaurav.android.newsfolo;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class HeadlineLoader extends AsyncTaskLoader<List<Headline>>{
    private String mUrl;
    HeadlineLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Headline> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        return QueryUtils.fetchHeadlines(mUrl);
    }
}
