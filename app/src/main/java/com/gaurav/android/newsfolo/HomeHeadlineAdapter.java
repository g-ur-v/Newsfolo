package com.gaurav.android.newsfolo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by rawal's on 13-Jul-17.
 */

public class HomeHeadlineAdapter extends ArrayAdapter<Headline> {
    public HomeHeadlineAdapter(Context context, List<Headline> headlines){
        super(context, 0, headlines);
    }
    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, parent, false);
        }
        Headline currentHeadline = getItem(position);

        ImageView HeadlineImage = (ImageView) listItemView.findViewById(R.id.headline_image);
        HeadlineImage.setImageResource(R.drawable.ic_launcher);

        String HeadlineTitle = currentHeadline.getTitle();
        TextView HeadlineTitleView = (TextView) listItemView.findViewById(R.id.headline_title);
        HeadlineTitleView.setText(HeadlineTitle);

        String AuthorName = currentHeadline.getAuthorName();
        TextView AuthorNameView = (TextView) listItemView.findViewById(R.id.author_name);
        AuthorNameView.setText(AuthorName);

        int id = currentHeadline.getId();
        String link = currentHeadline.getLink();

        return listItemView;
    }
}
