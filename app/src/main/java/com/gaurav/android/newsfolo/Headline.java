package com.gaurav.android.newsfolo;

class Headline {
    private int mId;
    private String mTitle;
    private String mTime;
    private String mLink;
    private String mContent;
    private String mAuthorName;
    private String mImageUrl;

    Headline(int id, String title, String link, String AuthorName, String imageUrl){
        mId = id;
        mTitle = title;
        mLink = link;
        mAuthorName = AuthorName;
        mImageUrl = imageUrl;
    }

    int getId(){
        return mId;
    }

    String getTitle(){
        return mTitle;
    }

    String getTime(){
        return mTime;
    }

    String getLink(){
        return mLink;
    }

    String getContent(){
        return mContent;
    }

    String getAuthorName(){
        return mAuthorName;
    }

    String getImageUrl(){
        return mImageUrl;
    }
}
