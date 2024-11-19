package com.example.apprestaurantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, "restaurantesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creación de la tabla restaurantes
        db.execSQL("CREATE TABLE restaurantes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "direccion TEXT, " +
                "telefono TEXT, " +
                "comentarios TEXT, " +
                "valoracion INTEGER)"); //Son las estrellas
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina la tabla si ya existe y la vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS restaurantes");
        onCreate(db);
    }
}
