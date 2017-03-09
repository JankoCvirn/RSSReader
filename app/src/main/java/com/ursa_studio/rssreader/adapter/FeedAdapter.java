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
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

  private static final String TAG = "Adapter";
  private List<Feed> feedList;
  private Context context;
  private AdapterView.OnItemClickListener onItemClickListener;

  public FeedAdapter (List<Feed> feedList, AdapterView.OnItemClickListener onItemClickListener,
      Context context){
    this.feedList = feedList;
    this.context = context;
    this.onItemClickListener = onItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_row, parent, false);

    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder (ViewHolder holder, int position){

    Feed feed = feedList.get(position);

    holder.feedName.setText(feed.getFeedName());
    holder.feedUrl.setText(feed.getFeedUrl());
  }

  @Override public int getItemCount (){
    return feedList.size();
  }
  public Feed getItem (int position){

    return feedList.get(position);
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView feedName, feedUrl;

    public ViewHolder (View view){

      super(view);

      view.setOnClickListener(this);
      feedName = (TextView) view.findViewById(R.id.textFeedName);
      feedUrl = (TextView) view.findViewById(R.id.textFeedUrl);
    }
    @Override public void onClick (View view){
      onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
    }
  }
}
