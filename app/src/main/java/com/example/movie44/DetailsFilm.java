package com.example.movie44;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsFilm extends AppCompatActivity {

    TextView titre;
    TextView  annee;
    ImageView poster;


    TextView  description;
    String titre_film = "";
    String description_film ="";
    String annee_film = "";
    String id_film = "";
    String poster_film_path = "";
    String url_img = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_4);

        titre= findViewById(R.id.titre);
        annee = findViewById(R.id.annee);
        description = findViewById(R.id.description);
        poster = findViewById(R.id.poster);


        Intent intent2 = getIntent();

        // Récupération des paramètres renseignés dans l'activité précédente (Results)
        if (intent2 != null){
            // Le code pour récupérer les extras ira ici
            if (intent2.hasExtra("nom")){ // vérifie qu'une valeur est associée à la clé “nom”
                 titre_film = intent2.getStringExtra("nom"); // on récupère la valeur associée à la clé
            }
            if (intent2.hasExtra("description")){ // vérifie qu'une valeur est associée à la clé “nom”
                description_film = intent2.getStringExtra("description"); // on récupère la valeur associée à la clé
            }

            if(intent2.hasExtra("realase")){
                annee_film = intent2.getStringExtra("realase");
            }

            if(intent2.hasExtra("id")){
                id_film = intent2.getStringExtra("realase");
            }

            if(intent2.hasExtra("poster")){
                poster_film_path = intent2.getStringExtra("poster");
            }
        }

        titre.setText(titre_film);
        description.setText(description_film);
        annee.setText(annee_film);

        Picasso.get().load(url_img + poster_film_path).into(poster);

    }

}
