package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.util.LinkedList;

public class SpinnerAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private final int ACT_CIUDAD = 1234;
    Ciudad selecionada;
    Spinner miSpinner;
    ImageView ima;
    Button but;
    ArrayAdapter<Ciudad> adapter;
    Button add;
    LinkedList misCiu = new LinkedList<Ciudad>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        miSpinner = (Spinner) findViewById(R.id.spinner);
        ima = findViewById(R.id.ciudadFoto);
        but = findViewById(R.id.selecPais);
        but.setOnClickListener(this);
        add = findViewById(R.id.addCiudad);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearCiudad.class);
                startActivityForResult(intent, ACT_CIUDAD);
            }
        });

        misCiu.add(new Ciudad("Budapest",47.4811281f,18.9902214f,"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/budapest-danubio-parlamento-1552491234.jpg"));
        misCiu.add(new Ciudad("Valencia",39.4077643f,-0.4315509f,"https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Ciutat_de_les_Arts_i_les_Ci%C3%A8ncies.jpg/1280px-Ciutat_de_les_Arts_i_les_Ci%C3%A8ncies.jpg"));


        adapter = new ArrayAdapter<Ciudad>(this, android.R.layout.simple_spinner_item, misCiu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACT_CIUDAD){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Se cancelo, se puso linda, la amiga llamaba, porque su novio le enagañaba", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_OK){
                String cityname = data.getExtras().getString("cityname");
                String citylon = data.getExtras().getString("citylon");
                String citylan = data.getExtras().getString("citylat");
                String cityima = data.getExtras().getString("cityima");
                Ciudad ciudad = new Ciudad(cityname, Float.parseFloat(citylon), Float.parseFloat(citylan), cityima);
                misCiu.add(ciudad);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.configuracion:
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}