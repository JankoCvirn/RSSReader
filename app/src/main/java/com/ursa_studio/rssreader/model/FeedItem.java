package com.ursa_studio.rssreader.model;

/**
 * RssReader
 * com.ursa_studio.rssreader.model
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class FeedItem {

  private String title;
  private String link;
  private String description;
  private String punDate;
  private String imageUrl;

  public String getTitle (){
    return title;
  }
  public void setTitle (String title){
    this.title = title;
  }
  public String getLink (){
    return link;
  }
  public void setLink (String link){
    this.link = link;
  }
  public String getDescription (){
    return description;
  }
  public void setDescription (String description){
    this.description = description;
  }
}
