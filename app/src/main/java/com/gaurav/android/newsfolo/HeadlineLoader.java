package com.gaurav.android.newsfolo;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

class HeadlineLoader extends AsyncTaskLoader<List<Headline>>{
    private String mUrl;
    private List<Headline> mData;
    /*private FileObserver mFileObserver;*/

    HeadlineLoader(Context context, String url){
        super(context);
        mUrl = url;
        Log.d("URl",url);
    }

    @Override
    protected void onStartLoading() {
        if (mData != null){
            deliverResult(mData);
        } else{
            forceLoad();
        }
/*
        if (mFileObserver == null){
            String path = new File(getContext().getCacheDir(), "download.json").getPath();
            mFileObserver= new FileObserver(path){
                @Override
                public void onEvent(int event, String path) {
                    //Notify the loader to reload the data
                    onContentChanged();
                    *//*If the loader is started, this will kick off loadInBackground()
                    *immediately. Otherwise, the fact that something changed will be cached
                    *and can be later retrieved via takeContentChanged()*//*
                }
            };
            mFileObserver.startWatching();
        }
        if (takeContentChanged()|| mData == null){
            forceLoad();
        }*/
    }

    @Override
    public List<Headline> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        return QueryUtils.fetchHeadlines(mUrl, getContext());
    }

    @Override
    public void deliverResult(List<Headline> data) {
        mData = data;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {/*
        if (mFileObserver!=null){
            mFileObserver.stopWatching();
            mFileObserver = null;
        }*/
    }
}
