package com.gaurav.android.newsfolo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class SportsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Headline>>  {
    private static final String REQUEST_URL = "http://test.newsfolo.com/wp-json/wp/v2/posts";
    private static final Integer LOADER_ID = 1;

    private HomeHeadlineAdapter mAdapter;
    /*private TextView mEmptyStateTextView;*/

    private Context context ;

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public Loader<List<Headline>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("filter[category_name]","Sports");
        return new HeadlineLoader(context, baseUri.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Headline>> loader, List<Headline> headlines) {
        /*View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_headlines);*/
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
        new HeadlineLoader(context,REQUEST_URL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        ListView headlineListView = (ListView) getActivity().findViewById(R.id.list);
        /*headlineListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView = (TextView) getActivity().findViewById(R.id.empty_view);*/
        mAdapter = new HomeHeadlineAdapter(context, new ArrayList<Headline>());
        headlineListView.setAdapter(mAdapter);
        headlineListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View views, int position, long l) {
                Headline currentHeadline = mAdapter.getItem(position);
                assert currentHeadline != null;
                Intent intent = new Intent(getActivity(), DetailedHeadlineActivity.class);
                intent.putExtra("currentHeadline", currentHeadline);
                startActivity(intent);
            }
        });
        try {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


            if (networkInfo == null || !networkInfo.isConnected()) {
                Toast.makeText(context,"Network Not Available",Toast.LENGTH_LONG).show();
            } else {
                /*View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                mEmptyStateTextView.setVisibility(View.GONE);*/
                Toast.makeText(context,"Network Available",Toast.LENGTH_LONG).show();
                android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, null, this);
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Exception in checking connectivity",Toast.LENGTH_LONG).show();
        }
    }
}
