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
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.loopj.android.http.TextHttpResponseHandler;
import com.ursa_studio.rssreader.MainActivity;
import com.ursa_studio.rssreader.R;
import com.ursa_studio.rssreader.adapter.FeedItemsAdapter;
import com.ursa_studio.rssreader.model.FeedItem;
import com.ursa_studio.rssreader.network.HttpClient;
import com.ursa_studio.rssreader.parser.XMLParser;
import com.ursa_studio.rssreader.utils.DividerItemDecoration;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * RssReader
 * com.ursa_studio.rssreader.fragments
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class FeedsItemListFragment extends Fragment implements AdapterView.OnItemClickListener {

  private static final String TAG = "FEEDS";
  private static final String KEY_ITEM = "item";
  private static final String KEY_TITLE = "title";
  private static final String KEY_DESCRIPTION = "description";
  private static final String KEY_LINK = "link";
  private List<FeedItem> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  public FeedItemsAdapter feedAdapter;
  private ImageView imageView;
  private String url;

  @Override public void onAttach (Context context){
    super.onAttach(context);
  }

  @Nullable @Override
  public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState){

    Bundle bundle = this.getArguments();

    if(bundle != null){
      url = bundle.getString("url");
    }

    View view = inflater.inflate(R.layout.fragment_feed, null);
    imageView = (ImageView) view.findViewById(R.id.imageView);
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerContacts);


    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    ((MainActivity) getActivity()).isFloatingButtonVisible(false);

    return view;
  }

  @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);

    if(url != null){

      pullRssFeed(url);
    }
  }

  @Override public void onItemClick (AdapterView<?> adapterView, View view, int position, long l){

  }

  private void pullRssFeed (String url){

    HttpClient.get(url, null, new TextHttpResponseHandler() {

      @Override public void onStart (){
        super.onStart();
      }
      @Override public void onFailure (int statusCode, Header[] headers, String responseString,
          Throwable throwable){
        Log.d(TAG, "Response NACK:" + responseString);
      }
      @Override public void onSuccess (int statusCode, Header[] headers, String responseString){
        Log.d(TAG, "Response ACK:" + responseString);
        parseFeed(responseString);
      }

      @Override public void onFinish (){
        super.onFinish();
      }
    });
  }

  private void parseFeed (String response){

    XMLParser xmlParser = new XMLParser();
    Document document = xmlParser.getDomElement(response);

    NodeList nl = document.getElementsByTagName(KEY_ITEM);
    for(int i = 0; i < nl.getLength(); i++){

      Element e = (Element) nl.item(i);
      FeedItem feedItem = new FeedItem();
      feedItem.setTitle(xmlParser.getValue(e, KEY_TITLE));

      feedItem.setDescription(xmlParser.getValue(e, KEY_DESCRIPTION));
      feedItem.setLink(xmlParser.getValue(e, KEY_LINK));

      feedList.add(feedItem);
    }
    feedAdapter = new FeedItemsAdapter(feedList, getContext());
    recyclerView.setAdapter(feedAdapter);
    recyclerView.getAdapter().notifyDataSetChanged();

    NodeList nl2 = document.getElementsByTagName("image");
    Element e2 = (Element) nl2.item(0);

    String url = xmlParser.getValue(e2, "url");


    Glide.with(getContext())
        .load(url)
        .asGif()
        .placeholder(R.drawable.rss_logo_icon_png_312)
        .error(R.drawable.broken_link)
        .into(imageView);
  }
}
