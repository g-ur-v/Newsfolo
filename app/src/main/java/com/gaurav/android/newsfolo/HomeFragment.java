package com.gaurav.android.newsfolo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment
        implements LoaderCallbacks<List<Headline>> {
    private static final String REQUEST_URL = "https://www.newsfolo.com/wp-json/wp/v2/posts";
    private static final int LOADER_ID = 1;

    private HomeHeadlineAdapter mAdapter;
    private TextView mEmptyStateTextView;

    private Context context ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        uriBuilder.appendQueryParameter("filter[category_name]","Editor's Picks");
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
        if (context instanceof OnFragmentInteractionListener) {

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    interface OnFragmentInteractionListener {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        ListView headlineListView = (ListView) getActivity().findViewById(R.id.list);
        headlineListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView = (TextView) getActivity().findViewById(R.id.empty_view);
        mAdapter = new HomeHeadlineAdapter(context, new ArrayList<Headline>());
        headlineListView.setAdapter(mAdapter);
        headlineListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View views, int position, long l) {
                Headline currentHeadline = mAdapter.getItem(position);
                assert currentHeadline != null;
                Uri headlineUri = Uri.parse(currentHeadline.getLink());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, headlineUri);
                startActivity(websiteIntent);
            }
        });
        try {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, null, this);
            } else {
                View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                mEmptyStateTextView.setVisibility(View.GONE);
            }
        /*
            if (isNetworkPresent(getActivity())) {
                View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                mEmptyStateTextView.setVisibility(View.GONE);
            } else {
                android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, null, this);
            }*/
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*public static boolean isNetworkPresent(Context context) {
        boolean isNetworkAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        try {

            if (cm != null) {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null) {
                    isNetworkAvailable = netInfo.isConnectedOrConnecting();
                }
            }
        } catch (Exception ex) {
            Log.e("Network Avail Error", ex.getMessage());
        }
        try{
            //check for wifi also
            if(!isNetworkAvailable){
                WifiManager connect = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                assert cm != null;
                NetworkInfo.State wifi = cm.getNetworkInfo(1).getState();
                isNetworkAvailable = connect.isWifiEnabled()
                        && wifi.toString().equalsIgnoreCase("CONNECTED");

            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isNetworkAvailable;
    }*/
}
