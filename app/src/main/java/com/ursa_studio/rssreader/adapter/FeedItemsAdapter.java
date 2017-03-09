package com.ursa_studio.rssreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.ursa_studio.rssreader.R;
import com.ursa_studio.rssreader.model.Feed;
import com.ursa_studio.rssreader.model.FeedItem;
import java.util.List;

public class FeedItemsAdapter extends RecyclerView.Adapter<FeedItemsAdapter.ViewHolder> {

  private static final String TAG = "AdapterItem";
  private List<FeedItem> feedList;
  private Context context;
  private AdapterView.OnItemClickListener onItemClickListener;

  public FeedItemsAdapter (List<FeedItem> feedList, Context context){
    this.feedList = feedList;
    this.context = context;
    this.onItemClickListener = onItemClickListener;
  }



  //TODO click
  @Override public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_row, parent, false);

    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder (ViewHolder holder, int position){

    FeedItem feedItem = feedList.get(position);

    holder.feedName.setText(feedItem.getTitle());
    holder.feedUrl.setText(feedItem.getDescription());
  }

  @Override public int getItemCount (){
    return feedList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView feedName, feedUrl;

    public ViewHolder (View view){

      super(view);

      feedName = (TextView) view.findViewById(R.id.textFeedName);
      feedUrl = (TextView) view.findViewById(R.id.textFeedUrl);
    }
  }

  public FeedItem getItem(int position){

    return feedList.get(position);
  }


}
