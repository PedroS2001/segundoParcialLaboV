package com.example.segundoparcial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogText extends DialogFragment {

    int numero;
    String nombre;
    public DialogText(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(super.getContext());

        if(numero==1)
        {
            builder.setTitle("Usuario encontrado");
            builder.setMessage("El rol del usuario es "+ this.nombre );
        }
        else {
            builder.setTitle("Usuario no encontrado");
            builder.setMessage("El usuario " +this.nombre+" No esta dentro de la lista" );
        }

        builder.setPositiveButton("Cerrar", null);


        return builder.create();
    }

}
