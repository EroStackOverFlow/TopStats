package net.trivialapps.topstats;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.trivialapps.topstats.Utils.CategoryAdapter;
import net.trivialapps.topstats.Utils.MyAdapter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , SharedPreferences.OnSharedPreferenceChangeListener , MyAdapter.OnItemClickedListener, CategoryAdapter.OnItemClickedListener {


    private Fragment top_fragment = null;
    private Fragment first_fragment = null;
    private Fragment about_fragment = null;
    private Fragment category_fragment = null;
    private Toolbar toolbar= null;
    private int mColor = 0;
    private int category = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAppBar();
        this.showAccueilFragment();
        this.configureSharePreference();
        if(BuildConfig.DEBUG)
        Log.i("couleur"," "+mColor);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(top_fragment != null){
                this.showFirstFragment(category);
        } else if(about_fragment != null || first_fragment != null){
                this.showAccueilFragment();
        }
            else if(category_fragment.isVisible()){
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        }else if(id == R.id.about) {this.showAboutFragment();
                 return true;
                 }
         else
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Uri internet_url = null;
        String  textMessage = null;
        if(TopFragment.url != null) {
            String url_sans_https = TopFragment.url.substring(7,TopFragment.url.length()-1);
            internet_url = Uri.parse("http:"+url_sans_https);
           textMessage = "you can visit this website  \n " +TopFragment.url+" \n with the Topp Stat App";
        }
        else
            Toast.makeText(this,getString(R.string.toast_open_url),Toast.LENGTH_LONG).show();


        switch (id){
            case R.id.accueil :
                this.showAccueilFragment();
                toolbar.setTitle("Top Stat");

                break;

            case R.id.navigator :
                Intent navigator = new Intent(MainActivity.this,NavigatorActivity.class);
                startActivity(navigator);

                break;

            case R.id.nav_open:
                if(TopFragment.url == null) break;
                Intent secondeActivite = new Intent(Intent.ACTION_VIEW,internet_url);
                try {
                    startActivity(secondeActivite);
                    // Et s'il n'y a pas d'activité qui puisse gérer ce type de fichier
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, getResources().getString(R.string.oups), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.nav_share:
                if(TopFragment.url == null) break;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, textMessage);
                i.setType("text/plain");


                try {
                    startActivity(i);
                    // Et s'il n'y a pas d'activité qui puisse gérer ce type de fichier
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, getResources().getString(R.string.oups), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureAppBar(){

        this.toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first_fragment == null)
                MainActivity.this.reloadTopFragment();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
  if(mColor != 0){
      this.toolbar.setBackgroundColor(mColor);
      fab.setBackgroundColor(mColor);
      TopFragment.progressbar.setBackgroundColor(mColor);
  }
    }

    private void showTopFragment(String url){
        this.top_fragment = TopFragment.newInstance(url);
        this.startTransactionFragment(this.top_fragment);
    }

    private void showFirstFragment(int category){
        this.top_fragment = null;
        this.first_fragment = FirstFragment.newInstance(category);
        this.startTransactionFragment(this.first_fragment);
    }

    private void reloadTopFragment(){
        if (this.top_fragment!= null){
            String url_reload = TopFragment.url;
            this.showTopFragment(url_reload);}
    }


    private void showAboutFragment(){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_frame_layout,  this.about_fragment = AboutFragment.newInstance()).commit();
            this.toolbar.setTitle("About - Top Stat");
    }

    private void showAccueilFragment(){
        this.top_fragment = null;
        this.about_fragment = null;
        this.first_fragment = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_frame_layout,  this.category_fragment = CategoryFragment.newInstance()).commit();
        this.toolbar.setTitle(getResources().getString(R.string.app_name));

    }

    private void startTransactionFragment(Fragment fragment){

        if (findViewById(R.id.activity_top_fragment) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_top_fragment, fragment).commit();
        }else
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_frame_layout, fragment).commit();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mColor =  sharedPreferences.getInt("AppColorPref",Color.RED);
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
    public void onItemClicked(int imgId) {


            switch (imgId){

                //listenner de categoryfragment
                case R.mipmap.category_anime_bd:
                    category = 0;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_cinema_serie:
                    category = 1;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_finance:
                    category = 2;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_it:
                    category = 3;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_jeux_videos:
                    category = 4;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_music:
                    category = 5;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_religion:
                    category = 6;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_sante:
                    category = 7;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_sport:
                    category = 8;
                    this.showFirstFragment(category);
                    break;
                case R.mipmap.category_universite:
                    category = 9;
                    this.showFirstFragment(category);
                    break;
                //listenner de topfragment
                case R.mipmap.entreprises:
                    this.showTopFragment(getResources().getString(R.string.url_entrerpises_les_plus_fortunee));
                    this.toolbar.setTitle(getResources().getString(R.string.Entreprise_les_plus_fortunees));
                    break;

                case R.mipmap.pib:
                    this.showTopFragment(getResources().getString(R.string.url_PIB));
                    this.toolbar.setTitle(getResources().getString(R.string.Classement_des_pays_par_PIB));
                    break;

                case R.mipmap.fifa:
                    this.showTopFragment(getResources().getString(R.string.url_FIFA_rank_list));
                    this.toolbar.setTitle(getResources().getString(R.string.classement_fifa));
                    break;

                case R.mipmap.millitaire:
                    this.showTopFragment(getResources().getString(R.string.url_puissances_millitaires));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_plus_grandes_puisssances_Millitaires));
                    break;

                case R.mipmap.plus_riche:
                    this.showTopFragment(getResources().getString(R.string.url_plus_riche_du_monde));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_plus_grosses_fortunes_de_la_planete));
                    break;
                case R.mipmap.univ:
                    this.showTopFragment(getResources().getString(R.string.url_classement_universite));
                    this.toolbar.setTitle(getResources().getString(R.string.classement_universite));
                    break;
                case R.mipmap.cine:
                    this.showTopFragment(getResources().getString(R.string.url_Liste_des_plus_gros_succès_du_box_office_mondial));
                    this.toolbar.setTitle(getResources().getString(R.string.Liste_des_plus_gros_succès_du_box_office_mondial));
                    break;
                case R.mipmap.serie:
                    this.showTopFragment(getResources().getString(R.string.url_les_meilleures_series_du_moment));
                    this.toolbar.setTitle(getResources().getString(R.string.les_meilleures_series_du_moment));
                    break;
                case R.mipmap.anime1:
                    this.showTopFragment(getResources().getString(R.string.url_Top_100_des_meilleurs_animes_japonais));
                    this.toolbar.setTitle(getResources().getString(R.string.Top_100_des_meilleurs_animes_japonais));
                    break;
                case R.mipmap.top_10_acteurs_payes:
                    this.showTopFragment(getResources().getString(R.string.url_top_10_des_acteurs_les_mieux_payes));
                    this.toolbar.setTitle(getResources().getString(R.string.top_10_des_acteurs_les_mieux_payes));
                    break;
                case R.mipmap.anime2:
                    this.showTopFragment(getResources().getString(R.string.url_Les_meilleurs_Mangas_de_l_Année_2019));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_meilleurs_Mangas_de_l_Année_2019));
                    break;
                case R.mipmap.anime3:
                    this.showTopFragment(getResources().getString(R.string.url_Les_meilleurs_Animes_de_l_Année_2019));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_meilleurs_Animes_de_l_Année_2019));
                    break;
                case R.mipmap.it1:
                    this.showTopFragment(getResources().getString(R.string.url_Les_meilleurs_entreprises_IT));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_meilleurs_entreprises_IT));
                    break;
                case R.mipmap.it2:
                    this.showTopFragment(getResources().getString(R.string.url_les_meilleures_universités_mondiales_en_technologie));
                    this.toolbar.setTitle(getResources().getString(R.string.les_meilleures_universités_mondiales_en_technologie));
                    break;
                case R.mipmap.game1:
                    this.showTopFragment(getResources().getString(R.string.url_Liste_des_jeux_vidéo_les_plus_vendus));
                    this.toolbar.setTitle(getResources().getString(R.string.Liste_des_jeux_vidéo_les_plus_vendus));
                    break;
                case R.mipmap.game2:
                    this.showTopFragment(getResources().getString(R.string.url_Top_25_des_jeux_massivement_multijoueurs));
                    this.toolbar.setTitle(getResources().getString(R.string.Top_25_des_jeux_massivement_multijoueurs));
                    break;
                case R.mipmap.game3:
                    this.showTopFragment(getResources().getString(R.string.url_Liste_des_consoles_de_jeux_vidéo_les_plus_vendues));
                    this.toolbar.setTitle(getResources().getString(R.string.Liste_des_consoles_de_jeux_vidéo_les_plus_vendues));
                    break;
                case R.mipmap.music1:
                    this.showTopFragment(getResources().getString(R.string.url_Classement_Forbes_Les_Musiciens_Les_Mieux_Payés_En_2018));
                    this.toolbar.setTitle(getResources().getString(R.string.Classement_Forbes_Les_Musiciens_Les_Mieux_Payés_En_2018));
                    break;
                case R.mipmap.music:
                    this.showTopFragment(getResources().getString(R.string.url_Liste_des_albums_musicaux_les_plus_vendus));
                    this.toolbar.setTitle(getResources().getString(R.string.Liste_des_albums_musicaux_les_plus_vendus));
                    break;
                case R.mipmap.religion1:
                    this.showTopFragment(getResources().getString(R.string.url_Les_religions_les_plus_populaires_au_monde));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_religions_les_plus_populaires_au_monde));
                    break;
                case R.mipmap.religion2:
                    this.showTopFragment(getResources().getString(R.string.url_Liste_des_papes));
                    this.toolbar.setTitle(getResources().getString(R.string.Liste_des_papes));
                    break;
                case R.mipmap.sport1:
                    this.showTopFragment(getResources().getString(R.string.url_sportifs_les_plus_riches));
                    this.toolbar.setTitle(getResources().getString(R.string.sportifs_les_plus_riches));
                    break;
                case R.mipmap.sante1:
                    this.showTopFragment(getResources().getString(R.string.url_classement_des_meilleurs_hôpitaux_du_monde));
                    this.toolbar.setTitle(getResources().getString(R.string.classement_des_meilleurs_hôpitaux_du_monde));
                    break;
                case R.mipmap.sante2:
                    this.showTopFragment(getResources().getString(R.string.url_Les_10_principales_causes_de_mortalité));
                    this.toolbar.setTitle(getResources().getString(R.string.Les_10_principales_causes_de_mortalité));
                    break;
                default:
                    break;

            }

    }
}
