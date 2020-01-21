package com.example.aplicacionconsum.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionconsum.R;
import com.example.aplicacionconsum.models.Producto;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoHolder>{

    private Context context;
    private int resource;
    private ArrayList<Producto> lista;

    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductoHolder(LayoutInflater.from(context).inflate(resource, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder productoHolder, int i) {
        Producto p = lista.get(i);

        productoHolder.tvNombre.setText(p.getNombre());
        productoHolder.tvPrecio.setText(NumberFormat.getCurrencyInstance().format(p.getPrecio()));

        Picasso.get()
                .load(p.getImagen())
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.ic_launcher_background)
                .into(productoHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public ProductosAdapter(Context context, int resource, ArrayList<Producto> lista) {
        this.context = context;
        this.resource = resource;
        this.lista = lista;
    }

    public class ProductoHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvNombre, tvPrecio;

        public ProductoHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagenIvProducto);
            tvNombre = itemView.findViewById(R.id.nombreTvProducto);
            tvPrecio = itemView.findViewById(R.id.precioTvProducto);

        }
    }
}
