package com.gaurav.android.newsfolo;

import java.io.Serializable;

class Headline implements Serializable{
    private int mId;
    private String mTitle;
    private String mTime;
    private String mLink;
    private String mContent;
    private String mAuthorName;
    private String mImageUrl;

    Headline(){

    }

    Headline(int id, String title, String link, String AuthorName, String imageUrl, String content, String time){
        mId = id;
        mTitle = title;
        mLink = link;
        mAuthorName = AuthorName;
        mImageUrl = imageUrl;
        mContent = content;
        mTime = time;
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

    void setId(int mId){
        this.mId = mId;
    }

    void setTitle(String title){ this.mTitle = title;}

    void setTime(String time){
        this.mTime = time;
    }

    void setLink(String mLink){
        this.mLink = mLink;
    }

    void setContent(String mContent){
        this.mContent = mContent;
    }

    void setAuthorName(String mAuthorName){
        this.mAuthorName = mAuthorName;
    }

    void setImageUrl(String mImageUrl){
        this.mImageUrl = mImageUrl;
    }
}
