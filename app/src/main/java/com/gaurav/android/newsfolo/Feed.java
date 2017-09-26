package com.gaurav.android.newsfolo;

/**
 * Created by hp on 7/19/2017.
 */

public class Feed {
    private String text;
    private int id;
    private String title, item, links, image_list;

    public String getText(){
        return text;
    }

    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }

    public String getItem(){
        return item;
    }

    public String getLinks() { return links; }

    public String getImage_list(){ return image_list; }

    public void setText(String text){
        this.text = text;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setItem(String item){
        this.item = item;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setLinks(String links) {this.links = links; }

    public void setImage_list(String image_list) { this.image_list = image_list; }

    @Override
    public String toString() {
        return "Title:"+title;
    }
}
