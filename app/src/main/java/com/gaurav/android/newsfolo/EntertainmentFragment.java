package com.gaurav.android.newsfolo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class EntertainmentFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Headline>>, Serializable {
    private static final Integer LOADER_ID = 1;

    private HomeHeadlineAdapter mAdapter;
    private TextView mEmptyStateTextView;

    private Context context ;

    public EntertainmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public Loader<List<Headline>> onCreateLoader(int id, Bundle args) {
        Log.d("in", "onCreateLoader");
        Uri baseUri = Uri.parse(Const.EntertainmentUrlXml);
        return new HeadlineLoader(context, baseUri.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Headline>> loader, List<Headline> headlines) {
        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_headlines);
        mAdapter.clear();
        if (headlines!= null && !headlines.isEmpty()){
            mAdapter.addAll(headlines);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Headline>> loader) {
        mAdapter.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new HeadlineLoader(context,Const.EntertainmentUrlXml);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
