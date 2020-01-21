package com.example.aplicacionconsum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionconsum.adapters.ProductosAdapter;
import com.example.aplicacionconsum.models.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    private ArrayList<Producto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Productos");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewProductos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        lista = new ArrayList<>();
        final ProductosAdapter adapter = new ProductosAdapter(this, R.layout.producto_card, lista);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProductos = database.getReference("productos");

        refProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Producto>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Producto>>(){};
                ArrayList<Producto> listaAux = dataSnapshot.getValue(genericTypeIndicator);
                lista.addAll(listaAux);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        Toast.makeText(ProductosActivity.this, String.valueOf(lista.size()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.cerrarSesionMiMenu:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            case R.id.miCarroMiMenu:
                startActivity(new Intent(this, MiCarroActivity.class));
        }
        return true;
    }
}
