package com.example.apprestaurantes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //Método que abre la ventana de añadir nuevo restaurante
    public void nuevoRestaurante(View view) {
        Intent i = new Intent(this, NuevoRestauranteActivity.class);
        startActivity(i);
    }
    //Método que abre la ventana de ver Favpritos
    public void verFavoritos(View view){
            Intent i = new Intent(this, RestaurantesActivity.class);
            startActivity(i);

    }
    //Método que abre la ventana de filtrar por estrellas
    public void preferencias(View view){
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }

    //Método que abre la ventana de acerca de
    public void acercaDe(View view){
        Intent i = new Intent(this, AcercaActivity.class);
        startActivity(i);
    }
}

