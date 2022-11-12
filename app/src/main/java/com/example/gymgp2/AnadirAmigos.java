package com.example.gymgp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AnadirAmigos extends AppCompatActivity {

    ImageView btnatras6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_amigos);

        casteo();

        btnatras6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Amigos.class);
                startActivity(intencion);
            }
        });


    }

    public void casteo(){
        btnatras6 = (ImageView) findViewById(R.id.btnatras6);
    }
}