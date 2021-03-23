package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class descripcion extends AppCompatActivity {

    TextView des,titulo,score,year;
    ImageView iv;
    public static String [] ID = new String[5];
    String  b,a;
    Button back;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion);
        des= findViewById(R.id.des);
        score= findViewById(R.id.score);
        titulo= findViewById(R.id.titulo);
        year= findViewById(R.id.year);
        back= findViewById(R.id.back);
        iv =findViewById(R.id.im);
        putdes();

        back.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }

    private void putdes() {
        ArrayList<String> lista = (ArrayList<String>) getIntent().getStringArrayListExtra("lista");
        des.setText(lista.get(0));
        titulo.setText(lista.get(4));
        year.setText(lista.get(3));
        score.setText("Puntaje: "+lista.get(2));
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+lista.get(1)).into(iv);

    }

}

