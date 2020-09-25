package br.ufpe.cin.android.rss;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "noticias")
public class Noticia {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "link")
    String link;
    @ColumnInfo(name = "titulo")
    String titulo;
    @ColumnInfo(name = "descricao")
    String descricao;
//    @ColumnInfo(name = "categorias")
//    List<String> categorias;
    @ColumnInfo(name = "datas")
    String data;

    public Noticia(@NonNull String link, String titulo, String descricao,  String data) {
        this.link = link;
        this.titulo = titulo;
        this.descricao = descricao;
        //this.categorias = categorias;
        this.data = data;
    }

    public String getLink() {return link;}
    public String getTitulo() {return titulo;}
    public String getDescricao(){return descricao;}
    //public List<String> getCategorias() {return categorias;}
    public String getData(){return data;}

}
