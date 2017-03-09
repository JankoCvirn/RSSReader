package com.ursa_studio.rssreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.ursa_studio.rssreader.fragments.FeedsListFragment;

public class MainActivity extends AppCompatActivity {

  private String TAG = "MAIN";
  protected FragmentManager fragmentManager;

  @Override protected void onCreate (Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    //noinspection ConstantConditions
    getSupportActionBar().setTitle(getString(R.string.app_name));

    showFeedsFragment();
  }

  private void showFeedsFragment (){

    Fragment fragment = new FeedsListFragment();
    fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .replace(R.id.flContent, fragment, "FEED_LIST")
        .addToBackStack("tag")
        .commit();
  }

  @Override public void onBackPressed (){

    int count = getFragmentManager().getBackStackEntryCount();

    if(count == 0){
      super.onBackPressed();
    } else{

      getFragmentManager().popBackStack();
    }
  }
}
