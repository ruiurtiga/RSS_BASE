package br.ufpe.cin.android.rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class PreferenciasActivity extends AppCompatActivity {
    public static String rssfeed = "uname";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);



        //Após criar o fragmento, use o código abaixo para exibir

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings,new PrefsFragment())
                .commit();

    }



}