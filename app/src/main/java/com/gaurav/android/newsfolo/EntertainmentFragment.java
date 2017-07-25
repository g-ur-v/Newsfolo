package com.gaurav.android.newsfolo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URL;

import static com.gaurav.android.newsfolo.QueryUtils.createUrl;
import static com.gaurav.android.newsfolo.QueryUtils.jsonTester;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EntertainmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntertainmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntertainmentFragment extends Fragment {
    URL url = createUrl("http://www.newsfolo.com/wp-json/wp/v2/posts/");
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = (TextView) getActivity().findViewById(R.id.textView);
        try{

            new HeadlineLoader(getActivity(), Uri.parse("http://www.whizzygeeks.com/a.json").toString());
            if (jsonTester == null)
                textView.setText(R.string.no_headlines);
            else
                textView.setText(jsonTester);
        } catch (Exception e){
            e.printStackTrace();
            textView.setText("Exception Occured");
        }
    }
    public static EntertainmentFragment newInstance(String param1, String param2) {
        EntertainmentFragment fragment = new EntertainmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
