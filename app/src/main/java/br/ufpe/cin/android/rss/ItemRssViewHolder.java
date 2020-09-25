package br.ufpe.cin.android.rss;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ItemRssViewHolder extends RecyclerView.ViewHolder {
    TextView titulo = null;
    TextView data;
    ImageView imagem;
    TextView link = null;
    LinearLayout parentLayout;


    public ItemRssViewHolder(View v) {
        super(v);
        this.titulo = v.findViewById(R.id.titulo);
        this.data = v.findViewById(R.id.dataPublicacao);
        this.imagem = v.findViewById((R.id.imagem));
        parentLayout = v.findViewById(R.id.parent_layout);

    }
}
