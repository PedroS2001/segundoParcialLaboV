package com.example.segundoparcial;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioModel {
    private String username;
    private String rol;
    private boolean admin;
    private Integer id;

    public UsuarioModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String toJSON(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("nombre", this.username);
            jsonObject.put("rol", this.rol);
            jsonObject.put("admin", this.admin);

            return jsonObject.toString();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }


}
