package com.gaurav.android.newsfolo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by hp on 9/26/2017.
 */

public class RssFeedData extends Fragment {

    ListView listNews;
    String feedLink;
    String tagname;
    public ArrayList<Feed> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        feedLink = Const.HomeUrlXml;
        listNews = (ListView)view.findViewById(R.id.list);
        new LoadFeed().execute(feedLink);
    }

    class LoadFeed extends AsyncTask<String, Void, ArrayList<Feed>> {

        ProgressDialog progressDialog;

        public LoadFeed() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected ArrayList<Feed> doInBackground(String... params) {
            ArrayList<Feed> feeds = new ArrayList<>();
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            try{
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                parser = factory.newPullParser();
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet method = new HttpGet(new URI(params[0]));
                HttpResponse res = client.execute(method);
                InputStream is = res.getEntity().getContent();
                parser.setInput(new InputStreamReader(is));
                int eventType = parser.getEventType();
                Feed feed = new Feed();
                String text = "";
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase("item")) {
                                feed = new Feed();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("item")) {
                                feeds.add(feed);
                            }else if (tagname.equalsIgnoreCase("title")){
                                feed.setTitle(text);
                            }else if (tagname.equalsIgnoreCase("link")) {
                                feed.setLinks(text);
                            }else if(tagname.equalsIgnoreCase("thumbnail")){
                                feed.setImage_list(parser.getAttributeValue(null,"url"));
                            }
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return feeds;
        }

        @Override
        protected void onPostExecute(ArrayList<Feed> feeds) {
            super.onPostExecute(feeds);
            progressDialog.dismiss();
            try {
                //ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, feeds);
                //lv.setAdapter(adapter);
                listNews.setAdapter(new SetAdapter(getActivity(), feeds));
                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Uri uri = Uri.parse(items.get(position).getLinks());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }catch (Exception e){
                Toast.makeText(getActivity(), "Couldn't load latest news", Toast.LENGTH_SHORT).show();
            }
        }

        public class SetAdapter extends BaseAdapter {

            public SetAdapter(Context context, ArrayList<Feed> feeds){
                items = feeds;
            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflate = getActivity().getLayoutInflater();
                convertView = inflate.inflate(R.layout.list_item_view, null);
                //Toast.makeText(MainActivity.this, items.get(position).getImage_list(), Toast.LENGTH_SHORT).show();
                ImageView image = (ImageView)convertView.findViewById(R.id.news_image);
                TextView title = (TextView)convertView.findViewById(R.id.news_text);
                title.setText(items.get(position).getTitle());
                return convertView;
            }
        }

    }
}
