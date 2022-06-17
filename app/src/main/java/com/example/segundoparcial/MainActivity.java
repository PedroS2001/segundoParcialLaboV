package com.example.segundoparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener {


    static List<UsuarioModel> listaUsuarios;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.tv = findViewById(R.id.tvPrincipal);


        SharedPreferences sp = super.getSharedPreferences("config", Context.MODE_PRIVATE);
        String usuarios = sp.getString("usuarios", "");

        if(usuarios.isEmpty())
        {
            Handler handler = new Handler(this);
            HiloConexion hilo = new HiloConexion(handler, "http://192.168.1.220:3001/usuarios", 1 );
            hilo.start();
            Log.d("CARGA DE DATOS", "YENDO A BUSCARLOS A LA API");
        }else{
            this.tv.setText(usuarios);
            Log.d("sad", "onCreate: pedor");
            MainActivity.listaUsuarios = new ArrayList<>();
            this.fillLista(usuarios);
        }

        Log.d("LISTAJSON", "onCreate: " + this.listaToJson());

        ActionBar acbar = super.getSupportActionBar();
        acbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        MenuItem searchItem = menu.findItem(R.id.campoBuscar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home)
        {
            this.finish();   //cierra el activity
        }
        else if(item.getItemId() == R.id.mnAgregar)
        {
            Log.d("LISTAJSON", "onOptionsItemSelected: " + this.listaToJson() );
            //GENERO EL DIALOG
            ConfigDialog dialog = new ConfigDialog();
            dialog.show(getSupportFragmentManager(),"dialogo");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        String respuesta = message.obj.toString();
        Log.d("MESSAGE", "handleMessage: " + respuesta  );

        if(message.arg1 == 1)
        {
            //tv.setText(respuesta);
            try {
                JSONArray respuestaArray = new JSONArray(respuesta);
                Log.d("--------ARRAY", ""+ respuestaArray);

                for(int i=0; i<respuestaArray.length(); i++)
                {
                    JSONObject jsObj = respuestaArray.getJSONObject(i);
                    UsuarioModel usuario = new UsuarioModel();

                    usuario.setId( Integer.parseInt(jsObj.getString("id")) );
                    usuario.setUsername(jsObj.getString("username"));
                    usuario.setRol(jsObj.getString("rol"));
                    usuario.setAdmin( Boolean.getBoolean( jsObj.getString("admin") ) );


                    listaUsuarios.add( usuario );
                    Log.d("----", "Se anadio" + usuario.getUsername());
                }

                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("usuarios", respuesta);

                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(message.arg1 == 3)     //es imagen
        {
            Log.d("LELEGO EL 3", "handleMessage: " + respuesta);
            Log.d("ADS", "handleMessage: "+ listaToJson() );


            SharedPreferences sp = super.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("usuarios", listaToJson() );

            editor.commit();

            this.actualizarTextView();


        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d("activity", "cambio texto:" + s);
        UsuarioModel user = new UsuarioModel();
        user.setUsername("");
        for(UsuarioModel u: MainActivity.listaUsuarios)
        {
            if(u.getUsername().equals(s))
            {
                user = u;
            }
        }

        if(user.getUsername().equals(""))
        {
            DialogText dialog = new DialogText(2, s);
            dialog.show(getSupportFragmentManager(),"dialogo");
        }
        else {
            DialogText dialog = new DialogText(1, user.getRol());
            dialog.show(getSupportFragmentManager(),"dialogo");

        }


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("activity", "Hago una busqueda con:" + s);
        return false;
    }



    public void fillLista(String usuariosString) {
        JSONArray respuestaArray = null;
        try {
            respuestaArray = new JSONArray(usuariosString);

            Log.d("--------ARRAY", ""+ respuestaArray);

            for(int i=0; i<respuestaArray.length(); i++)
            {
                JSONObject jsObj = respuestaArray.getJSONObject(i);
                UsuarioModel usuario = new UsuarioModel();

                usuario.setId( Integer.parseInt(jsObj.getString("id")) );
                usuario.setUsername(jsObj.getString("username"));
                usuario.setRol(jsObj.getString("rol"));
                usuario.setAdmin( Boolean.getBoolean( jsObj.getString("admin") ) );

                MainActivity.listaUsuarios.add( usuario );
                Log.d("----", "Se anadio " + usuario.getUsername());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String listaToJson()
    {
        String cadenaJson = "";
        for(UsuarioModel u: MainActivity.listaUsuarios)
        {
            cadenaJson+= u.toJSON();
            //cadenaJson.concat(u.toJSON());
            cadenaJson += ",";
            //cadenaJson.concat(",");
        }

        return cadenaJson;
    }

    public void actualizarTextView()
    {
        SharedPreferences sp = super.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String usuarios = sp.getString("usuarios", "");

        this.tv.setText(usuarios);
    }

}

/* Consultar el sharedPreference si tiene cargado un array de json
si no tiene le consulto a la api. recordar permiso de internet
Realizar un menu para ver en la toolbar un btn de tipo searchView y uno de AgregarPersona
En el btn de agregar genera un dialog como el del pdf
Agregarlo al array del SharedPreference,





 */