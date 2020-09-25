package br.ufpe.cin.android.rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //TextView rssfeed = null;
    public String RSS_FEED;

    SharedPreferences prefs;

    ListView conteudoRSS;
    List<Article> noticias;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conteudoRSS = findViewById(R.id.conteudoRSS);

        //Configurações do recyclerView
        //Cria a View -> RecyclerView
        recyclerView = new RecyclerView(this);
        //Define o tamanho fixo independente do conteúdo que irá receber
        recyclerView.setHasFixedSize(true);
        //Define o formato linear ao layout e vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Adiciona uma linha para separar cada item da lista
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        //prefs pra pegar o link do feed, inicialmente vai pegar o que tiver em feed_padrao que ta salvo no xml string em resources
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        RSS_FEED = prefs.getString(PreferenciasActivity.rssfeed, getString(R.string.feed_padrao));

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Service pra baixar o rss (ISSO TA FUNCIONANDO, SÓ VER NO LOG tag NoticiasNoService))
        Intent i = new Intent(this, RssDownloadService.class);
        i.setData(Uri.parse(RSS_FEED));
        RssDownloadService.enqueueWork(this, i);


        //Recuperar a lista que foi baixada acima?
        NoticiasDB db = NoticiasDB.getInstance(getApplicationContext());
        NoticiasDAO dao = db.obterDAO();
        List<Article> noticias = dao.getNoticias();
        //Preciso da lista de artigos noticias pra inserir no adapter abaixo

        //Adapta o recyclerView de acordo com o RssAdapter (que recebe a lista de noticias)
        recyclerView.setAdapter(new RssAdapter(getApplicationContext(),noticias));
//      //Define o content view com a recyclerView pronta
        setContentView(recyclerView);

    }

    //ao voltar pra main activity, a string RSS_FEED recebe o novo link fornecido pelo usuário em configurações
    protected void onRestart() {
        super.onRestart();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        RSS_FEED = prefs.getString(PreferenciasActivity.rssfeed, getString(R.string.feed_padrao));

    }


    //Botão de configuração na action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //process your onClick here
            startActivity(new Intent(this, PreferenciasActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String getRssFeed(String feed) throws IOException {
        InputStream in = null;
        String rssFeed = "";
        try {
            URL url = new URL(feed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            byte[] response = out.toByteArray();
            rssFeed = new String(response, "UTF-8");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return rssFeed;
    }
}