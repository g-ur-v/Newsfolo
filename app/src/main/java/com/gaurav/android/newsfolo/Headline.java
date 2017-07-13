package com.gaurav.android.newsfolo;

/**
 * Created by rawal's on 12-Jul-17.
 */

public class Headline {
    private int mId;
    private String mTitle;
    private String mTime;
    private String mLink;
    private String mContent;
    private String mAuthorName;

    public Headline(int id, String title, String time, String link, String content){
        mId = id;
        mTitle = title;
        mTime = time;
        mLink = link;
        mContent = content;
    }
    public Headline(int id, String title, String link, String AuthorName){
        mId = id;
        mTitle = title;
        mLink = link;
        mAuthorName = AuthorName;
    }

    public int getId(){
        return mId;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getmTime(){
        return mTime;
    }

    public String getLink(){
        return mLink;
    }

    public String getContent(){
        return mContent;
    }

    public String getAuthorName(){
        return mAuthorName;
    }
}
