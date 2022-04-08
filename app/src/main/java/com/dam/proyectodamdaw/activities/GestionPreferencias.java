package com.dam.proyectodamdaw.activities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class GestionPreferencias {
    private SharedPreferences sharedPreferences;
    private static GestionPreferencias instancia;

    private GestionPreferencias(){

    }

    public String getUnidad(Context context){
        inicializa(context);
        return sharedPreferences.getString("unidades", "standard");
    }

    private void inicializa(Context context){
        if(sharedPreferences == null){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static GestionPreferencias getInstance(){
        if(instancia == null){
            instancia = new GestionPreferencias();
        }
        return instancia;
    }
}
