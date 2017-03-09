package com.ursa_studio.rssreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.activeandroid.query.Select;
import com.ursa_studio.rssreader.R;
import com.ursa_studio.rssreader.adapter.FeedAdapter;
import com.ursa_studio.rssreader.model.Feed;
import com.ursa_studio.rssreader.model.FeedEvent;
import com.ursa_studio.rssreader.utils.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * RssReader
 * com.ursa_studio.rssreader.fragments
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class FeedsListFragment extends Fragment
    implements AdapterView.OnItemClickListener, View.OnClickListener {

  private static final String TAG = "FEEDS";
  public FeedAdapter feedAdapter;
  private List<Feed> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  @Override public void onAttach (Context context){
    super.onAttach(context);

    feedList = new Select().from(Feed.class).execute();
  }
  @Nullable @Override
  public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_feeds, null);

    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFeeds);
    feedAdapter = new FeedAdapter(feedList, this, getContext());

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    recyclerView.setAdapter(feedAdapter);

    view.findViewById(R.id.fab).setOnClickListener(this);

    return view;
  }
  @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
  }
  @Override public void onStart (){
    super.onStart();
    EventBus.getDefault().register(this);
  }
  @Override public void onStop (){
    EventBus.getDefault().unregister(this);
    super.onStop();
  }
  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent (FeedEvent event){
    reloadData();
    recyclerView.getAdapter().notifyDataSetChanged();
  }
  private void reloadData (){
    feedList.clear();
    feedList = new Select().from(Feed.class).execute();
    feedAdapter = new FeedAdapter(feedList, this, getContext());
    recyclerView.setAdapter(feedAdapter);
  }
  @Override public void onItemClick (AdapterView<?> adapterView, View view, int position, long l){

    Log.d(TAG, "Clicked ========");
    Feed feed = feedAdapter.getItem(position);
    Bundle bundle = new Bundle();
    bundle.putString("url", feed.getFeedUrl());

    FeedsItemListFragment fragment = new FeedsItemListFragment();
    fragment.setArguments(bundle);

    getFragmentManager().beginTransaction()
        .replace(R.id.flContent, fragment, "FEED_ITEMS")
        .addToBackStack("tag2")
        .commit();
  }
  @Override public void onClick (View view){
    switch(view.getId()){

      case R.id.fab:

        showDialogFragment();
        break;
    }
  }

  private void showDialogFragment (){

    AddFeedDialogFragment addFeedDialogFragment = AddFeedDialogFragment.newInstance("New RSS");
    addFeedDialogFragment.show(getActivity().getSupportFragmentManager(), "fragment_edit_name");
  }
}
