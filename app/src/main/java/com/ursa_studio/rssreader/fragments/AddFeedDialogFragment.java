package com.ursa_studio.rssreader.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.ursa_studio.rssreader.R;
import com.ursa_studio.rssreader.model.Feed;
import com.ursa_studio.rssreader.model.FeedEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;

/**
 * RssReader
 * com.ursa_studio.rssreader.fragments
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class AddFeedDialogFragment extends DialogFragment {

  private EditText etFeedName;
  private EditText etFeedUrl;
  private Button btnCancel;
  private Button btnAdd;

  public AddFeedDialogFragment (){
  }

  public static AddFeedDialogFragment newInstance(String title) {
    AddFeedDialogFragment frag = new AddFeedDialogFragment();
    Bundle args = new Bundle();
    args.putString("title", title);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_dialog_add_feed, container);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    etFeedName = (EditText)view.findViewById(R.id.etFeedName);
    etFeedUrl = (EditText)view.findViewById(R.id.etFeedUrl);
    btnAdd = (Button) view.findViewById(R.id.btnOk);
    btnCancel = (Button) view.findViewById(R.id.btnCancel);


    String title = getArguments().getString("title", "Enter Name");
    getDialog().setTitle(title);

    etFeedName.requestFocus();
    getDialog().getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick (View view){

        close();
      }
    });

    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick (View view){

        if (isValidUrl(etFeedUrl.getEditableText().toString())&&isName(etFeedName.getEditableText().toString())){

          saveRssFeed();
          close();
        }

      }
    });
  }

  private boolean isValidUrl(String url) {
    Pattern p = Patterns.WEB_URL;
    Matcher m = p.matcher(url.toLowerCase());
    return m.matches();
  }

  private boolean isName(String name) {

    if (name.length()>0) return true;
    else {
      return false;
    }

  }

  private void saveRssFeed(){

    Feed feed=new Feed();
    feed.setFeedName(etFeedName.getEditableText().toString());
    feed.setFeedUrl(etFeedUrl.getEditableText().toString());
    feed.save();
    EventBus.getDefault().post(new FeedEvent("saved"));


  }

  private void close(){

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.remove(this);
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    ft.commit();


  }




}
