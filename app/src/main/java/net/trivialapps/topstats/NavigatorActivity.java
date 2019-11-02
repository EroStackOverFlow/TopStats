package net.trivialapps.topstats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import net.trivialapps.topstats.Utils.NavigatorWebViewClient;

import java.util.Objects;

public class NavigatorActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String url = "http://google.com";
    private WebView webView;
    public static EditText editText;
    private Button button;
    public static ProgressBar progressbar = null;
    private int mColor = 0;
    private Toolbar toolbar= null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        this.configureToolBar();
        this.configureSharePreference();


        editText = findViewById(R.id.EditText);
        webView = findViewById(R.id.WebViewNavigator);
        button = findViewById(R.id.Button);
        progressbar = findViewById(R.id.progressBarNavigator);

        webView.setWebViewClient(new NavigatorWebViewClient());

        if (getIntent().getDataString()!=null){
            //ouveture par une activite externe ou par le mainactivity
            if (!getIntent().getDataString().toString().equals("")) {
                //Log.i("valeur de lintent", getIntent().getDataString().toString());
                goUrl2();
            }
            else
                // ouverture par defaut
                this.goUrl3();
        }
        else
            // ouverture par defaut
            this.goUrl3();

       this.initLoadUrl();



    }


    private void configureToolBar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Browser");
    }

    private void initLoadUrl(){

            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavigatorActivity.this.goUrl();
                            if(BuildConfig.DEBUG)
                            Log.i("page"," jai envoye  l url: "+url );
                        }
                    }
            );
            editText.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // Ne réagir qu'à l'appui sur une touche (et pas au relâchement)
                    if(event.getAction() == 0)
                        // S'il s'agit d'un appui sur la touche « entrée »
                        if(keyCode == 66)
                            NavigatorActivity.this.goUrl();
                    return true;
                }

            });

    }


    //ouverture lors du click sur la touche "go"
    private void goUrl()  {

        if (editText.getText() == null)
                return;

        String entete = "https://";
        String test_url = editText.getText().toString();
        if((test_url.substring(0,8).equals(entete) ) || (test_url.substring(0,7).equals("http://") )){
            if(BuildConfig.DEBUG)
            Log.i("page"," lentete vaut: "+test_url.substring(0,6) );
            url= test_url;
        }

        else{
            entete = String.format("%s%s", entete, editText.getText().toString());
            editText.setText(entete);
            url = entete;
        }

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private void goUrl2(){
        url =  getIntent().getDataString();
        editText.setText(Objects.requireNonNull(url).trim());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url.trim());
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mColor =  sharedPreferences.getInt("AppColorPref", Color.RED);
        this.toolbar.setBackgroundColor(mColor);
    }

    private void configureSharePreference(){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // On indique que l'acitivt� est � l'�coute des changements de pr�f�rence
        mPrefs.registerOnSharedPreferenceChangeListener(this);
        // On r�cup�re la couleur voulue par l'utilisateur, par d�faut il s'agira du rouge
        mColor = mPrefs.getInt("AppColorPref", Color.RED);
        this.toolbar.setBackgroundColor(mColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, AppPreference.class);
            startActivity(i);
            return true;
        }else if(id == R.id.reload) {
            this.goUrl();
        return true;
    }

        else
            return super.onOptionsItemSelected(item);
    }

    private void goUrl3(){
        url = "https://www.google.com";
        editText.setText(Objects.requireNonNull(url).trim());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url.trim());
    }

}
