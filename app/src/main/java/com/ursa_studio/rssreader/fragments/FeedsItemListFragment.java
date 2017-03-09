package com.ursa_studio.rssreader.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.loopj.android.http.TextHttpResponseHandler;
import com.ursa_studio.rssreader.R;
import com.ursa_studio.rssreader.adapter.FeedListAdapter;
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
  private static final String KEY_IMAGE = "image";
  private static final String KEY_URL = "url";
  private static final String KEY_CHANNEL = "channel";
  private static final String KEY_TITLE_SEC = "title";
  public FeedListAdapter feedAdapter;
  private List<FeedItem> feedList = new ArrayList<>();
  private RecyclerView recyclerView;
  private ImageView imageView;
  private String url;
  private ProgressBar progressBar;
  private TextView rssTitle;

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
    recyclerView = (RecyclerView) view.findViewById(R.id.list);
    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    rssTitle = (TextView) view.findViewById(R.id.textViewTitle);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    feedAdapter = new FeedListAdapter(feedList, this, getContext());

    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    recyclerView.setAdapter(feedAdapter);

    return view;
  }

  @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);

    reloadData();
    if(url != null){

      pullRssFeed(url);
    }
  }
  private void reloadData (){

    feedAdapter = new FeedListAdapter(feedList, this, getContext());
    recyclerView.setAdapter(feedAdapter);
  }
  private void pullRssFeed (String url){

    HttpClient.get(url, null, new TextHttpResponseHandler() {

      @Override public void onStart (){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
      }
      @Override public void onFinish (){
        super.onFinish();
        progressBar.setVisibility(View.INVISIBLE);
      }
      @Override public void onFailure (int statusCode, Header[] headers, String responseString,
          Throwable throwable){
        Log.d(TAG, "Response NACK:" + responseString);
        Toast.makeText(getContext(), getString(R.string.error_loading), Toast.LENGTH_SHORT).show();
      }
      @Override public void onSuccess (int statusCode, Header[] headers, String responseString){
        Log.d(TAG, "Response ACK:" + responseString);
        parseFeed(responseString);
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

    NodeList nl2 = document.getElementsByTagName(KEY_IMAGE);
    Element e2 = (Element) nl2.item(0);

    String url = xmlParser.getValue(e2, KEY_URL);

    NodeList nl3 = document.getElementsByTagName(KEY_CHANNEL);
    Element e3 = (Element) nl3.item(0);

    String title = xmlParser.getValue(e3, KEY_TITLE_SEC);

    rssTitle.setText(title);

    reloadData();

    Glide.with(getContext())
        .load(url)
        .asBitmap()
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.broken_link)
        .into(imageView);
  }
  @Override public void onItemClick (AdapterView<?> adapterView, View view, int position, long l){

    FeedItem feedItem = feedAdapter.getItem(position);
    Uri webpage = Uri.parse(feedItem.getLink());
    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
    if(intent.resolveActivity(getActivity().getPackageManager()) != null){
      startActivity(intent);
    }
  }
}
