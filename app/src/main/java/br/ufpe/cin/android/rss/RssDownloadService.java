package br.ufpe.cin.android.rss;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.List;

public class RssDownloadService extends JobIntentService {

    private static final int JOB_ID = 123;

    List<Article> noticias;

    public static void enqueueWork(Context context, Intent intent){
        enqueueWork(context, RssDownloadService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        //Codigo aqui dentro roda em uma thread de background
        //Ex: conexao de rede, acessar banco de dados, operações complexas (long running)
        //ao terminar de executar este método, o Service se encerra


        //////Aqui baixa o xml do site atraves da biblioteca rssparser
        String rss_feed = intent.getDataString();
        Parser p = new Parser.Builder().build();
        p.onFinish(
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(Channel channel) {
                        noticias = channel.getArticles();
                        //Log.d("NoticiasNoService",noticias.toString());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("RSS_APP",e.getMessage());
                    }
                }
        );
        p.execute(rss_feed);


        /////aqui salva o que baixou no banco de dados
        NoticiasDB noticiasDB = NoticiasDB.getInstance(this);
        noticiasDB.noticiasDAO().inserirNoticia(noticias);
        //Log.d....



    }
}