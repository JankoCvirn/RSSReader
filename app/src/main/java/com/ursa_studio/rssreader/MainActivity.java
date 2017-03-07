package com.ursa_studio.rssreader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.ursa_studio.rssreader.fragments.AddFeedDialogFragment;
import com.ursa_studio.rssreader.fragments.FeedsListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private String TAG = "MAIN";
  protected FragmentManager fragmentManager;

  @Override protected void onCreate (Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    //noinspection ConstantConditions
    getSupportActionBar().setTitle(getString(R.string.app_name));

    findViewById(R.id.fab).setOnClickListener(this);
    showFeedsFragment();
  }

  private void showFeedsFragment (){

    Fragment fragment = new FeedsListFragment();
    fragmentManager = getSupportFragmentManager ();
    fragmentManager.beginTransaction ()
        .replace (R.id.flContent, fragment)
        .addToBackStack ("tag")
        .commit ();


  }

  private void showDialogFragment (){

    FragmentManager fm = getSupportFragmentManager();
    AddFeedDialogFragment addFeedDialogFragment = AddFeedDialogFragment.newInstance("New RSS");
    addFeedDialogFragment.show(fm, "fragment_edit_name");
  }




  @Override public void onClick (View view){

    switch(view.getId()){

      case R.id.fab:

        showDialogFragment();
        break;
    }
  }

  @Override public void onBackPressed (){
    super.onBackPressed();
  }

  @Override protected void onResume () {
    super.onRestart ();
    showFeedsFragment();

  }
}
