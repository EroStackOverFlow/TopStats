package net.trivialapps.topstats;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import net.trivialapps.topstats.Utils.MyWebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

   private WebView webView = null;

   public static ProgressBar progressbar = null;
   public static String url = null;


    public TopFragment() { }


    public static TopFragment newInstance(String ex_url) {

        url = ex_url;

        return (new TopFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        webView = view.findViewById(R.id.webview);
        progressbar = view.findViewById(R.id.progressbar);

        // Custom WebViewClient to handle event on WebView.
        webView.setWebViewClient(new MyWebViewClient());

        this.goUrl();

        return view;
    }

    private void goUrl()  {

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }


}
