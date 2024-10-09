package com.example.tccmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    Context context;

    private List<com.example.tccmobile.Noticia> listaImage;
    ArrayList<String> arrayList;
    OnItemClickListener onItemClickListener;


    public ImageAdapter(List<Noticia> listaImage) {
        this.context = context;
        this.arrayList = arrayList;
        this.listaImage = listaImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_foto_noticia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        com.example.tccmobile.Noticia noticia = listaImage.get(position);

        byte[] imageBytes = noticia.getFoto();


        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder);
        }

        Glide.with(context).load(arrayList.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.imageView, arrayList.get(position)));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }


    public interface  OnItemClickListener {
        void onClick(ImageView imageView, String url);
    }

    @Override
    public int getItemCount() {
        return listaImage.size();
    }

    public void setListaImage(List<Noticia> novaListaImage) {
        this.listaImage = novaListaImage;
        notifyDataSetChanged();
    }

}
