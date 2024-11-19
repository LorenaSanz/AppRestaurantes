package com.example.apprestaurantes;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestauranteAdapter adapter;
    private ArrayList<Restaurante> listaRestaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes);

        //Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaRestaurantes = new ArrayList<>();

        try {
            cargarRestaurantes();

            //Configurar el adaptador con el listener para eliminar
            adapter = new RestauranteAdapter(listaRestaurantes, this, position -> mostrarConfirmacionEliminar(position));
            recyclerView.setAdapter(adapter);

            if (listaRestaurantes.isEmpty()) {
                Toast.makeText(this, "No hay restaurantes que coincidan con el filtro seleccionado.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar restaurantes", Toast.LENGTH_SHORT).show();
            Log.e("RestaurantesActivity", "Error al cargar restaurantes", e);
        }
    }

    //Método llamado por el botón de volver
    public void atras(View view) {
        finish();
    }

    private void cargarRestaurantes() {
        listaRestaurantes.clear(); //Limpiar la lista antes de recargar

        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        int selectedStars = preferences.getInt("selectedStars", 0);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = null;

        try {
            if (selectedStars > 0) {
                cursor = db.rawQuery("SELECT * FROM restaurantes WHERE valoracion = ?", new String[]{String.valueOf(selectedStars)});
            } else {
                cursor = db.rawQuery("SELECT * FROM restaurantes", null);
            }

            while (cursor.moveToNext()) {
                Restaurante restaurante = new Restaurante(
                        cursor.getInt(0),    //id
                        cursor.getString(1), //nombre
                        cursor.getString(2), //dirección
                        cursor.getString(3), //teléfono
                        cursor.getString(4), //comentarios
                        cursor.getInt(5)     //valoracion
                );
                listaRestaurantes.add(restaurante);
            }

            Log.d("CargarRestaurantes", "Restaurantes cargados: " + listaRestaurantes.size());
        } catch (Exception e) {
            Toast.makeText(this, "Error en la consulta de la base de datos", Toast.LENGTH_SHORT).show();
            Log.e("RestaurantesActivity", "Error en la consulta SQL", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    //Método para eliminar restaurantes de la base de datos
    public void eliminarRestaurante(int position) {
        Restaurante restaurante = listaRestaurantes.get(position);

        //Inicializar la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        //Intentar eliminar el restaurante
        int rowsDeleted = db.delete("restaurantes", "id = ?", new String[]{String.valueOf(restaurante.getId())});

        db.close();

        //Mostrar resultado y actualizar la lista
        if (rowsDeleted == 1) {
            Toast.makeText(this, "Restaurante eliminado correctamente", Toast.LENGTH_SHORT).show();

            //Eliminar de la lista local y notificar al adaptador
            listaRestaurantes.remove(position);
            adapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(this, "No existe un restaurante con ese ID", Toast.LENGTH_SHORT).show();
        }

    }
    void mostrarConfirmacionEliminar(int position) {
        Restaurante restaurante = listaRestaurantes.get(position);

        //Crear el diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que deseas eliminar el restaurante \"" + restaurante.getNombre() + "\"?");

        builder.setPositiveButton("Eliminar", (dialog, which) -> eliminarRestaurante(position));
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        //Mostrar el diálogo
        builder.create().show();
    }

}

