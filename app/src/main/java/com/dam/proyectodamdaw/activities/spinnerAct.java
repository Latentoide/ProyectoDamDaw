package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

public class spinnerAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Ciudad selecionada;
    Spinner miSpinner;
    ImageView ima;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        miSpinner = (Spinner) findViewById(R.id.spinner);
        ima = findViewById(R.id.ciudadFoto);
        but = findViewById(R.id.selecPais);
        but.setOnClickListener(this);

        Ciudad[] misCiu = {
                new Ciudad("Budapest",47.4811281f,18.9902214f,"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/budapest-danubio-parlamento-1552491234.jpg"),
                new Ciudad("Valencia",39.4077643f,-0.4315509f,"https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Ciutat_de_les_Arts_i_les_Ci%C3%A8ncies.jpg/1280px-Ciutat_de_les_Arts_i_les_Ci%C3%A8ncies.jpg")
        };

        ArrayAdapter<Ciudad> adapter = new ArrayAdapter<Ciudad>(this, android.R.layout.simple_spinner_item, misCiu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        miSpinner.setAdapter(adapter);
        miSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selecionada = (Ciudad) adapterView.getSelectedItem();
        ImageDownloader.downloadImage(selecionada.getImg(), ima);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(selecionada != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("select", selecionada);

            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("select", selecionada);
        outState.putSerializable("numero", miSpinner.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selecionada = (Ciudad) savedInstanceState.getSerializable("select");
        miSpinner.setSelection((int)savedInstanceState.getSerializable("numero"));
    }
}