package com.example.gymgp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    ImageView btnamigos,btnatras2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        casteo();

        btnatras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Login.class);
                startActivity(intencion);
            }
        });

        btnamigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Amigos.class);
                startActivity(intencion);
            }
        });


    }

    public void casteo(){
        btnamigos = (ImageView) findViewById(R.id.btnamigos);
        btnatras2 = (ImageView) findViewById(R.id.btnatras2);
    }
}