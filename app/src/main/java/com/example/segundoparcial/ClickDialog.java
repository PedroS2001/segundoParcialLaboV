package com.example.segundoparcial;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONObject;

import java.io.Writer;

public class ClickDialog implements DialogInterface.OnClickListener {

    Activity a;
    View v;
    public ClickDialog(Activity a, View v) {
        this.a = a;
        this.v = v;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i== DialogInterface.BUTTON_POSITIVE)
        {
            EditText tv = this.v.findViewById(R.id.etUsername);
            String username = tv.getText().toString();

            if( username.equals(""))
            {
                //ESTA VACIO NO DEJO SEGUIR
                Log.d("FIIII", "POSITIVO: "+tv.getText().toString());
            }
            else
            {
                Spinner spn = this.v.findViewById(R.id.spinner);

                String rol = spn.getSelectedItem().toString();
                Log.d("PEDRO", "onClick: "+ rol);

                ToggleButton tbb = this.v.findViewById(R.id.toggleAdmin);
                boolean esAdmin = tbb.isChecked();

                UsuarioModel u = new UsuarioModel();

                u.setUsername(username);
                u.setAdmin(esAdmin);
                u.setRol(rol);
                u.setId( maxId()+1 );

                String usuarioJson = u.toJSON();

                Log.d("PEPEPE", "onClick: "+usuarioJson);


                MainActivity.listaUsuarios.add(u);


                Handler handler = new Handler((Handler.Callback) a);

                Message message = new Message();
                message.arg1 = 3;
                message.obj = usuarioJson;
                handler.sendMessage(message);

            }

        }else if(i== DialogInterface.BUTTON_NEUTRAL)
        {
            Log.d("CLICK EN", "CERRAR: "+i);
        }
    }


    public int maxId()
    {
        int maxId=0;
        for (UsuarioModel u: MainActivity.listaUsuarios)
        {
            if (u.getId() > maxId)
            {
                maxId = u.getId();
            }
        }
        return  maxId;
    }



}
