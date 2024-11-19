package com.example.apprestaurantes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NuevoRestauranteActivity extends AppCompatActivity {

    private EditText etNombre, etDireccion, etTelefono, etComentarios;
    private RatingBar ratingBar;
    long result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_restaurante);

        //Inicializar los campos de entrada
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        etComentarios = findViewById(R.id.etComentarios);
        ratingBar = findViewById(R.id.ratingBar);
    }

    //Método llamado por el botón de volver
    public void atras(View view) {
        finish(); //Cierra la actividad y regresa a la anterior
    }

    //Método para guardar el restaurante en la base de datos
    public void guardarRestaurante(View view) {
        //Obtener los valores de los campos de entrada
        String nombre = etNombre.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String comentarios = etComentarios.getText().toString().trim();
        int valoracion = (int) ratingBar.getRating(); //Obtener la valoración como número entero

        //Verificar que los campos obligatorios no estén vacíos
        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || valoracion == 0) {
            Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validar que el teléfono sea válido
        if (!telefono.matches("^[679]\\d{8}$")) {
            Toast.makeText(this, "Nº de teléfono no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        //Guardar en la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("direccion", direccion);
        values.put("telefono", telefono);
        values.put("comentarios", comentarios); //Comentarios es opcional
        values.put("valoracion", valoracion);

        result = db.insert("restaurantes", null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Restaurante guardado", Toast.LENGTH_SHORT).show();
            //Limpiar los campos después de guardar
            etNombre.setText("");
            etDireccion.setText("");
            etTelefono.setText("");
            etComentarios.setText("");
            ratingBar.setRating(0);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}
