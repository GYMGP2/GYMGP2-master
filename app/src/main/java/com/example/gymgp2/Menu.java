package com.example.gymgp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    ImageView btnamigos,btnatras2,btnperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        casteo();

        btnatras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mSharedPrefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putString("usuario","");
                editor.putString("password","");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnamigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Amigos.class);
                startActivity(intencion);
            }
        });

       btnperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Actualizar.class);
                startActivity(intencion);
            }
        });


    }

    public void casteo(){
        btnamigos = (ImageView) findViewById(R.id.btnamigos);
        btnatras2 = (ImageView) findViewById(R.id.btnatras2);
        btnperfil = (ImageView) findViewById(R.id.btnperfil);
    }
}