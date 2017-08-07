package com.gaurav.android.newsfolo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.Serializable;

/**
 * Created by gaurav on 6/8/17.
 */

public class DetailedHeadlineActivity extends AppCompatActivity implements Serializable {
    public DetailedHeadlineActivity(){
        //Necessary Empty Constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_news);
        try{
            Intent intent = getIntent();

            Headline mCurrentHeadline =  (Headline) intent.getSerializableExtra("currentHeadline");

            assert mCurrentHeadline != null;

            ImageView contentImage = (ImageView) findViewById(R.id.content_image);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(mCurrentHeadline.getImageUrl(), contentImage, new ImageLoadingListener(){
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

            String mAuthorName = mCurrentHeadline.getAuthorName();
            TextView authorName = (TextView) findViewById(R.id.author_name_content);
            authorName.setText(mAuthorName);

            String mDateTime = mCurrentHeadline.getTime();
            TextView dateTime = (TextView) findViewById(R.id.time_content);
            dateTime.setText(mDateTime);

            String mContent = mCurrentHeadline.getContent();
            TextView contentTextView = (TextView) findViewById(R.id.news_content);
            contentTextView.setText(mContent);
        } catch (NullPointerException e){
            Toast.makeText(this, "NullPointerExceptionOccurred", Toast.LENGTH_LONG).show();
        }

    }
}
