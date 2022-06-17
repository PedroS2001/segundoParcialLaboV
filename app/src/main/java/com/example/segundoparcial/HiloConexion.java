package com.example.segundoparcial;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HiloConexion extends Thread {

    Handler colaMensajes;
    private Integer tipo;
    private String url;

    public HiloConexion(Handler handler, String url, int tipo) {
        this.colaMensajes = handler;
        this.url = url;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        ConexionHttp con = new ConexionHttp();
        byte[] respuesta = con.obtenerInformacion(url);

        if(this.tipo == 1)  //TEXTO
        {
            String respuestaString = new String(respuesta);

            Message message = new Message();
            message.arg1 = 1;
            message.obj = respuestaString;
            colaMensajes.sendMessage(message);
        }
        else if(this.tipo == 3) //IMAGEN
        {
        }


    }


}
