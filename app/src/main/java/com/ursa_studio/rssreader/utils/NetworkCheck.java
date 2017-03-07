package com.ursa_studio.rssreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

  private Context context;

  public NetworkCheck (Context context) {

    this.context=context;
  }

  public boolean isConnected () {

    try {
      final ConnectivityManager connectivityManager =
          ((ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE));
      NetworkInfo network = connectivityManager.getActiveNetworkInfo ();
      return network.isConnectedOrConnecting ();
    } catch (Exception e) {

      return false;
    }
  }
}
