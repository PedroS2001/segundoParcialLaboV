package com.example.segundoparcial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

public class ConfigDialog extends DialogFragment  {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(super.getContext());

        builder.setTitle("Crear Usuario");
        //builder.setMessage("un mensaje");

        LayoutInflater layoutInflater= LayoutInflater.from(super.getContext());
        View v = layoutInflater.inflate(R.layout.dialog_config, null);

        ClickDialog click = new ClickDialog(this.getActivity(), v);

        builder.setNeutralButton("Cerrar", click);
        builder.setPositiveButton("Guardar", click);

        builder.setView(v);



        return builder.create();
    }

}
