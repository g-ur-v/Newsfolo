package com.gaurav.android.newsfolo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

class HomeHeadlineAdapter extends ArrayAdapter<Headline> {
    private ImageLoader imageLoader;

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

            assert currentHeadline != null;

            ImageView HeadlineImage = (ImageView) listItemView.findViewById(R.id.headline_image);
            /*URL url = currentHeadline.getImageUrl();
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            HeadlineImage.setImageBitmap(bmp);*/
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
            imageLoader.displayImage(currentHeadline.getImageUrl(), HeadlineImage, new ImageLoadingListener(){
                /**
                 * Is called when image loading task was started
                 *
                 * @param imageUri Loading image URI
                 * @param view     View for image
                 */
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                /**
                 * Is called when an error was occurred during image loading
                 *
                 * @param imageUri   Loading image URI
                 * @param view       View for image. Can be <b>null</b>.
                 * @param failReason {@linkplain FailReason The reason} why image
                 */
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                /**
                 * Is called when image is loaded successfully (and displayed in View if one was specified)
                 *
                 * @param imageUri    Loaded image URI
                 * @param view        View for image. Can be <b>null</b>.
                 * @param loadedImage Bitmap of loaded and decoded image
                 */
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                /**
                 * Is called when image loading task was cancelled because View for image was reused in newer task
                 *
                 * @param imageUri Loading image URI
                 * @param view     View for image. Can be <b>null</b>.
                 */
                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

            String HeadlineTitle = currentHeadline.getTitle();
            TextView HeadlineTitleView = (TextView) listItemView.findViewById(R.id.headline_title);
            HeadlineTitleView.setText(HeadlineTitle);

            String AuthorName = currentHeadline.getAuthorName();
            TextView AuthorNameView = (TextView) listItemView.findViewById(R.id.author_name);
            AuthorNameView.setText(AuthorName);

        } catch(Exception e){
            e.printStackTrace();
        }
        return listItemView;
    }
}
