package com.example.segundoparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/* Consultar el sharedPreference si tiene cargado un array de json
si no tiene le consulto a la api. recordar permiso de internet
Realizar un menu para ver en la toolbar un btn de tipo searchView y uno de AgregarPersona
En el btn de agregar genera un dialog como el del pdf
Agregarlo al array del SharedPreference,





 */