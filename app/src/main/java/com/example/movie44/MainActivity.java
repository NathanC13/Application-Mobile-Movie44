package com.example.movie44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

// Première activité qui correspond à la page d'acceuil
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Lors d'un clic sur le bouton de la première activité, cela lance l'activité Search qui permet de rechercher des films
    public void onClick(View v){
        Button b1 = findViewById(R.id.button);
        Intent i = new Intent(MainActivity.this, Search.class);
        startActivity(i);
    }


}