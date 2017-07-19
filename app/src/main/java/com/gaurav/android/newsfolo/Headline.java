package com.gaurav.android.newsfolo;

class Headline {
    private int mId;
    private String mTitle;
    private String mTime;
    private String mLink;
    private String mContent;
    private String mAuthorName;

    Headline(int id, String title, String link, String AuthorName){
        mId = id;
        mTitle = title;
        mLink = link;
        mAuthorName = AuthorName;
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
}
