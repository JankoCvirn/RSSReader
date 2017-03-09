package com.ursa_studio.rssreader.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * RssReader
 * com.ursa_studio.rssreader.network
 * Created by janko on 07.03.17..
 * Description:
 * Usage:
 */

public class HttpClient {

  private static AsyncHttpClient client = new AsyncHttpClient();

  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    client.setTimeout(300000);
    client.get(url, params, responseHandler);
  }



}
