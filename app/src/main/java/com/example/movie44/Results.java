package com.example.movie44;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


// Troisième activité permettant l'affichage de la liste des films correspondant aux critères de sélection.
public class Results extends AppCompatActivity {

    private Button rechercherbtn;
    private TextView contenu;
    private ListView install;

    private ArrayAdapter<String> aa;
    private ArrayAdapter<JsonElement> listeDesJsonElements;

    String str = "";
    String str2 = "";
    Boolean pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_3);

        // Récupération des paramètres de sélection rentrés sur l'activité précédente
        Intent intent2 = getIntent();

        if (intent2 != null){
            // Le code pour récupérer les extras ira ici
            if (intent2.hasExtra("2")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str = intent2.getStringExtra("2"); // on récupère la valeur associée à la clé
            }
            if (intent2.hasExtra("3")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str2 = intent2.getStringExtra("3"); // on récupère la valeur associée à la clé
            }
            if(intent2.hasExtra("check_pop")){
                pop= intent2.getBooleanExtra("check_pop",false);
            }
        }

        rechercherbtn = findViewById(R.id.button3);
        install = findViewById(R.id.installations);

        aa = new ArrayAdapter<String>(Results.this, android.R.layout.simple_list_item_1);
        listeDesJsonElements = new ArrayAdapter<JsonElement>(Results.this, android.R.layout.simple_list_item_1);

        // Lors d'un clic sur le bouton : afficher la recherche, cela appelle les méthodes de recherche en fonction des critères
        rechercherbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(pop){
                    larechercheParPopularite(view);
                }else{
                    larecherche(view);
                }

            }
        });

        // Listener permettant de lors d'un clic sur un film de la liste, afficher les détails de celui-ci dans une nouvelle activité (DetailsFilm)
        install.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent versSecondaire = new Intent(Results.this,DetailsFilm.class);
                versSecondaire.putExtra("nom", aa.getItem(i));
                versSecondaire.putExtra("description", String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().get("overview")).replaceAll("\"",""));
                versSecondaire.putExtra("realase", String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().get("release_date")).replaceAll("\"",""));
                versSecondaire.putExtra("id",String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().get("id")).replaceAll("\"",""));
                versSecondaire.putExtra("poster",String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().get("poster_path")).replaceAll("\"",""));
                startActivity(versSecondaire);
            }

        });
    }

    //Première fonction de recherche permettant la recherche selon les critères de sélection
    public void larecherche(View v){

        // Récupération des paramètres de sélection rentrés sur l'activité précédente
        Intent intent2 = getIntent();

        if (intent2 != null){
            // Le code pour récupérer les extras ira ici
            if (intent2.hasExtra("2")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str = intent2.getStringExtra("2"); // on récupère la valeur associée à la clé
            }
            if (intent2.hasExtra("3")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str2 = intent2.getStringExtra("3"); // on récupère la valeur associée à la clé
            }
        }

        //
        if(str2.isEmpty()){

            // Cette fonction permet de parcourir le json de l'API selon le nom du film qu'à rentré l'utilisateur si le nombre de films souhaité n'est pas renseigné
            Ion.with(v.getContext()).load("https://api.themoviedb.org/3/search/movie?api_key=9ff2e7d040d16512b0607bf63215f567&query="+str+"&language=fr-FR").asJsonObject().setCallback(new FutureCallback<JsonObject>() {

                int i = 0;
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    for (JsonElement elem : result.getAsJsonArray("results")){
                            aa.add(String.valueOf(elem.getAsJsonObject().get("original_title")).replaceAll("\"", ""));
                            listeDesJsonElements.add(elem);
                    }
                    install.setAdapter(aa);
                }
            });
        }else if(!str2.isEmpty()){
            int resultat = Integer.parseInt(str2);

            // Cette fonction permet de parcourir le json de l'API selon le nom du film qu'à rentré l'utilisateur si le nombre de films souhaité n'est pas nul
            Ion.with(v.getContext()).load("https://api.themoviedb.org/3/search/movie?api_key=9ff2e7d040d16512b0607bf63215f567&query="+str+"&language=fr-FR").asJsonObject().setCallback(new FutureCallback<JsonObject>() {

                int i = 0;
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    for (JsonElement elem : result.getAsJsonArray("results")){
                        if(i<resultat){
                            aa.add(String.valueOf(elem.getAsJsonObject().get("original_title")).replaceAll("\"", ""));
                            listeDesJsonElements.add(elem);
                            i++;
                        }
                    }
                    install.setAdapter(aa);
                }
            });
        }


    }

    //Seconde fonction de recherche permettant d'afficher la liste des films les plus populaires
    public void larechercheParPopularite(View v){

        Intent intent2 = getIntent();

        if (intent2 != null){
            if (intent2.hasExtra("3")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str2 = intent2.getStringExtra("3"); // on récupère la valeur associée à la clé
            }
        }
        if(str2.isEmpty()){

            // Cette fonction permet de parcourir le json de l'API selon le nom du film qu'à rentré l'utilisateur si le nombre de films souhaité n'est pas renseigné

            Ion.with(v.getContext()).load("https://api.themoviedb.org/3/discover/movie?api_key=9ff2e7d040d16512b0607bf63215f567&language=fr-FR&sort_by=popularity.desc").asJsonObject().setCallback(new FutureCallback<JsonObject>() {

                int i = 0;
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    for (JsonElement elem : result.getAsJsonArray("results")){

                            aa.add(String.valueOf(elem.getAsJsonObject().get("original_title")).replaceAll("\"", ""));
                            listeDesJsonElements.add(elem);


                    }
                    install.setAdapter(aa);
                }
            });

        }else if(!str2.isEmpty()){
            int resultat = Integer.parseInt(str2);

            // Cette fonction permet de parcourir le json de l'API selon le nom du film qu'à rentré l'utilisateur si le nombre de films souhaité n'est pas nul

            Ion.with(v.getContext()).load("https://api.themoviedb.org/3/discover/movie?api_key=9ff2e7d040d16512b0607bf63215f567&language=fr-FR&sort_by=popularity.desc").asJsonObject().setCallback(new FutureCallback<JsonObject>() {

                int i = 0;
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    for (JsonElement elem : result.getAsJsonArray("results")){
                        if(i<resultat){
                            aa.add(String.valueOf(elem.getAsJsonObject().get("original_title")).replaceAll("\"", ""));
                            listeDesJsonElements.add(elem);
                            i++;
                        }
                    }
                    install.setAdapter(aa);
                }
            });
        }

    }
}
