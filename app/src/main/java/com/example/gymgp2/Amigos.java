package com.example.gymgp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Amigos extends AppCompatActivity {

    ImageView btnatras5,btnanadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        casteo();

        btnatras5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Menu.class);
                startActivity(intencion);
            }
        });

        btnanadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),AnadirAmigos.class);
                startActivity(intencion);
            }
        });

    }

    public void casteo(){
        btnatras5 = (ImageView) findViewById(R.id.btnatras5);
        btnanadir = (ImageView) findViewById(R.id.btnanadir);
    }
}