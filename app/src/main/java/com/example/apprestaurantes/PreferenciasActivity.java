package com.example.apprestaurantes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PreferenciasActivity extends AppCompatActivity {

    private RadioGroup radioGroupStars;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);



        //Configuración de preferencias
        preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        radioGroupStars = findViewById(R.id.radioGroupStars);

        //Establecer el filtro guardado o "Sin Filtro"
        int savedStars = preferences.getInt("selectedStars", 0);
        if (savedStars == 0) {
            ((RadioButton) findViewById(R.id.radio_sinFiltro)).setChecked(true);
        } else {
            ((RadioButton) radioGroupStars.getChildAt(savedStars)).setChecked(true);
        }

        //Guardar la preferencia seleccionada
        radioGroupStars.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedStars = checkedId == R.id.radio_sinFiltro ? 0 : group.indexOfChild(findViewById(checkedId));
            preferences.edit().putInt("selectedStars", selectedStars).apply();
            Toast.makeText(this, "Filtro guardado", Toast.LENGTH_SHORT).show();
        });

    }
    public void atras (View view){

        finish();
    }
}
