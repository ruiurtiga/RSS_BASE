package br.ufpe.cin.android.rss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RssAdapter extends RecyclerView.Adapter<ItemRssViewHolder>{

    List<Article> noticias;
    private Context mContext;


    RssAdapter(Context context, List<Article> noticias) {
        this.noticias = noticias;
        mContext = context;
    }



    @NonNull
    @Override
    public ItemRssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemRssViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRssViewHolder holder, int position) {
        Article n = noticias.get(position);
        //Aqui é feita a ligação entre os objetos da classe Article e os objetos da classe ItemRssViewHolder
        //Titulo
        holder.titulo.setText(n.getTitle());
        //Data da publicação
        holder.data.setText(n.getPubDate());
        //Imagem, através da biblioteca Picasso
        //Recebe o endereço da imagem do rss
        Picasso.get().load(n.getImage()).into(holder.imagem);


        //Aqui o setOnClickListener para ao clicar num objeto da lista realizar uma ação
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent implicito para abrir o browser com o link da noticia
                Intent i = new Intent();
                //addFlags é para que o startActivity inicie aqui
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(n.getLink());
                i.setData(uri);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                mContext.startActivity(i);
            }
        });


        /////////////////////////////////QUESTAO 5///////////////n sei se ta certo////////////
//        new Thread(() -> {
//            String link = n.getLink();
//            String titulo = n.getTitle();
//            String descricao = n.getDescription();
//            List<String> categorias = n.getCategories();
//            String imagem = n.getImage();
//            String data = n.getPubDate();
//
//            NoticiasDB noticiasDB = NoticiasDB.getInstance(mContext);
//            Noticia noticia = new Noticia(link, titulo, descricao, data, imagem);
//            noticiasDB.noticiasDAO().InserirNoticia(noticia);
//            Log.d("NoticiasNoAdapter", noticias.toString());
//        }
//        ).start();
        ////////////////////////////////////////////////////////////////////////////////////

    }


    //Total de elementos da coleção
    @Override
    public int getItemCount() {
        return noticias.size();
    }
}



