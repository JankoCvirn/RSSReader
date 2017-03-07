package com.ursa_studio.rssreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class FeedsListFragment extends Fragment implements AdapterView.OnItemClickListener {

  private static final String TAG = "FEEDS";
  private List<Feed> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  public FeedAdapter feedAdapter;

  @Override public void onStart (){
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop (){
    EventBus.getDefault().unregister(this);
    super.onStop();
  }
  @Override public void onAttach (Context context){
    super.onAttach(context);

    feedList = new Select().from(Feed.class).execute();
  }

  @Nullable @Override
  public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_feeds, null);

    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerContacts);
    feedAdapter = new FeedAdapter(feedList, getContext());

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    recyclerView.setAdapter(feedAdapter);

    return view;
  }

  @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
  }

  private void reloadData (){
    feedList.clear();
    feedList = new Select().from(Feed.class).execute();
    feedAdapter = new FeedAdapter(feedList, getContext());
    recyclerView.setAdapter(feedAdapter);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent (FeedEvent event){
    reloadData();
    recyclerView.getAdapter().notifyDataSetChanged();
  }

  @Override public void onItemClick (AdapterView<?> adapterView, View view, int position, long l){

    Feed feed=feedAdapter.getItem(position);

  }
}
