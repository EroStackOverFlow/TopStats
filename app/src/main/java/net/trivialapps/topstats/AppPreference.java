package net.trivialapps.topstats;

import android.os.Bundle;


public class AppPreference extends AppCompatPreferenceActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

}

