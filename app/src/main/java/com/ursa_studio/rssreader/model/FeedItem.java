package com.ursa_studio.rssreader.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * RssReader
 * com.ursa_studio.rssreader.model
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class FeedItem implements Parcelable {

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

  public FeedItem (){
  }

  @Override public int describeContents (){
    return 0;
  }
  @Override public void writeToParcel (Parcel dest, int flags){
    dest.writeString(this.title);
    dest.writeString(this.link);
    dest.writeString(this.description);
    dest.writeString(this.punDate);
    dest.writeString(this.imageUrl);
  }
  protected FeedItem (Parcel in){
    this.title = in.readString();
    this.link = in.readString();
    this.description = in.readString();
    this.punDate = in.readString();
    this.imageUrl = in.readString();
  }
  public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {
    @Override public FeedItem createFromParcel (Parcel source){
      return new FeedItem(source);
    }
    @Override public FeedItem[] newArray (int size){
      return new FeedItem[size];
    }
  };
}
