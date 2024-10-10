package com.example.tccmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<Noticia> listaNoticias;
    private OnItemClickListener onItemClickListener;
    private boolean mostrarImagem;  // Variável para controlar a exibição da imagem

    public ImageAdapter(Context context, List<Noticia> listaNoticias, boolean mostrarImagem) {
        this.context = context;
        this.listaNoticias = listaNoticias;
        this.mostrarImagem = mostrarImagem;  // Inicializar controle
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_manchete_noticia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = listaNoticias.get(position);

        // Lógica para exibir ou ocultar a imagem
        if (mostrarImagem) {
            byte[] imageBytes = noticia.getFoto();
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setImageResource(R.drawable.placeholder);
            }
            holder.imageView.setVisibility(View.VISIBLE);  // Exibe a imagem
        } else {
            holder.imageView.setVisibility(View.GONE);  // Oculta a imagem
        }

        holder.mancheteTextView.setText(noticia.getManchete());

        // Configurar o clique do item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mancheteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem_noticia);
            mancheteTextView = itemView.findViewById(R.id.mancheteTextView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}