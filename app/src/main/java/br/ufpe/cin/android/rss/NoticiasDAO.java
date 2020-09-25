package br.ufpe.cin.android.rss;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.prof.rssparser.Article;

import java.util.List;


@Dao
public interface NoticiasDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirNoticia(List<Article> noticia);

    @Update
    void atualizarNoticia(Noticia noticia);

    @Delete
    void removerNoticia(Noticia noticia);

    @Query("SELECT * FROM noticias")
    List<Article> getNoticias();

}
