package com.gaurav.android.newsfolo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class HomeHeadlineAdapter extends ArrayAdapter<Headline> {

    HomeHeadlineAdapter(Context context, List<Headline> headlines){
        super(context, 0, headlines);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView , @NonNull ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, parent, false);
        }
        try {
            Headline currentHeadline = getItem(position);

            ImageView HeadlineImage = (ImageView) listItemView.findViewById(R.id.headline_image);
            HeadlineImage.setImageResource(R.drawable.ic_launcher);

            assert currentHeadline != null;
            String HeadlineTitle = currentHeadline.getTitle();
            TextView HeadlineTitleView = (TextView) listItemView.findViewById(R.id.headline_title);
            HeadlineTitleView.setText(HeadlineTitle);

            String AuthorName = currentHeadline.getAuthorName();
            TextView AuthorNameView = (TextView) listItemView.findViewById(R.id.author_name);
            AuthorNameView.setText(AuthorName);

            int id = currentHeadline.getId();
            String link = currentHeadline.getLink();
        } catch(Exception e){
            e.printStackTrace();
        }
        return listItemView;
    }
}
