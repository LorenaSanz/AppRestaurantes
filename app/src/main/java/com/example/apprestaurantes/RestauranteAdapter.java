package com.example.apprestaurantes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder> {

    private ArrayList<Restaurante> listaRestaurantes;
    private Context context;
    private OnDeleteListener deleteListener;

    //Interfaz para eliminaciones desde el adaptador
    public interface OnDeleteListener {
        void onDelete(int position);
    }

    public RestauranteAdapter(ArrayList<Restaurante> listaRestaurantes, Context context, OnDeleteListener deleteListener) {
        this.listaRestaurantes = listaRestaurantes;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurante, parent, false);
        return new RestauranteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder holder, int position) {
        Restaurante restaurante = listaRestaurantes.get(position);
        holder.nombre.setText(restaurante.getNombre());
        holder.direccion.setText(restaurante.getDireccion());
        holder.telefono.setText(restaurante.getTelefono());
        holder.comentarios.setText(restaurante.getComentarios());
        holder.ratingBar.setRating(restaurante.getValoracion());

        //Evento de clic para el botón "Llamar", lo intenté meter en un método para llamarlo con un onClick desde el XML pero me daba error
        holder.btnLlamar.setOnClickListener(v -> {
            String telefono = restaurante.getTelefono();
            if (telefono != null && !telefono.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefono));
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Número de teléfono no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        //Evento de clic para el botón "Maps", lo intenté meter en un método para llamarlo con un onClick desde el XML pero me daba error
        holder.btnMaps.setOnClickListener(v -> {
            String direccion = restaurante.getDireccion();
            if (direccion != null && !direccion.isEmpty()) {
                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(direccion));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(mapIntent);
            } else {
                Toast.makeText(context, "Dirección no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (context instanceof RestaurantesActivity) {
                ((RestaurantesActivity) context).mostrarConfirmacionEliminar(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaRestaurantes.size();
    }

    public static class RestauranteViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, direccion, telefono, comentarios;
        RatingBar ratingBar;
        Button btnLlamar, btnMaps;
        ImageButton btnEliminar;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            direccion = itemView.findViewById(R.id.direccion);
            telefono = itemView.findViewById(R.id.telefono);
            comentarios = itemView.findViewById(R.id.comentarios);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnLlamar = itemView.findViewById(R.id.btn_llamar);
            btnMaps = itemView.findViewById(R.id.btn_maps);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
