package com.dam.proyectodamdaw.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

public class MainActivity extends BaseActivity implements CallInterface, View.OnClickListener {
    Root root;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        executeCall(this);
    }

    @Override
    public void doInBackground() {
        root = Connector.getConector().get(Root.class,"forecast?lat=39.5870774&lon=-0.5416143&appid=4d56a19050d401d7ba6b982145243362&lang=es&units=metric");
    }

    @Override
    public void doInUI() {
        hideProgress();
        if(root != null){
            recyclerView = findViewById(R.id.recicled);

            MyReciclerViewAdapter adaptador = new MyReciclerViewAdapter(this,root);
            adaptador.setOnClickListener(this);
            recyclerView.setAdapter(adaptador);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public void onClick(View view){

    }
}