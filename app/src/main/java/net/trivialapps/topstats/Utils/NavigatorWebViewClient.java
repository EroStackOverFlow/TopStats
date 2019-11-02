package net.trivialapps.topstats.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.trivialapps.topstats.BuildConfig;
import net.trivialapps.topstats.NavigatorActivity;



public class NavigatorWebViewClient extends WebViewClient {


    public NavigatorWebViewClient() { }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      NavigatorActivity.editText.setText(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    // When page loading
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(BuildConfig.DEBUG)
        Log.i("page"," commence a se charger avec pour url :"+" "+url );
    }

    // When page load finish.
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        NavigatorActivity.progressbar.setVisibility(View.GONE);
        if(BuildConfig.DEBUG)
        Log.i("page"," jai termine mon chargement avec l url: "+url );
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        if(BuildConfig.DEBUG)
        Log.i("page"," se charge avec pour url :"+" "+url );
    }

}
