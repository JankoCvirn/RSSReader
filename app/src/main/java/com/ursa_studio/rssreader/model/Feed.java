package com.ursa_studio.rssreader.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * RssReader
 * com.ursa_studio.rssreader.model
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

@Table(name = "Feed")
public class Feed extends Model implements Parcelable {

  @Column (name = "feedname")
  private String feedName;
  @Column(name = "feedUrl")
  private String feedUrl;

  public String getFeedName (){
    return feedName;
  }
  public void setFeedName (String feedName){
    this.feedName = feedName;
  }
  public String getFeedUrl (){
    return feedUrl;
  }
  public void setFeedUrl (String feedUrl){
    this.feedUrl = feedUrl;
  }

  @Override public int describeContents (){
    return 0;
  }
  @Override public void writeToParcel (Parcel dest, int flags){
    dest.writeString(this.feedName);
    dest.writeString(this.feedUrl);
  }
  public Feed (){
  }
  protected Feed (Parcel in){
    this.feedName = in.readString();
    this.feedUrl = in.readString();
  }
  public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
    @Override public Feed createFromParcel (Parcel source){
      return new Feed(source);
    }
    @Override public Feed[] newArray (int size){
      return new Feed[size];
    }
  };
}
