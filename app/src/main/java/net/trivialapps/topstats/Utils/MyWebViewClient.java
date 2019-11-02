package net.trivialapps.topstats.Utils;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;

import net.trivialapps.topstats.TopFragment;

public class MyWebViewClient extends WebViewClient {



    public MyWebViewClient(){ }


    // When page loading
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        TopFragment.progressbar.setVisibility(View.VISIBLE);
    }

    // When page load finish.
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        TopFragment.progressbar.setVisibility(View.GONE);
    }


}
