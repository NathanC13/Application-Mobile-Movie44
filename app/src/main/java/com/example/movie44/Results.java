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

    public void larecherche(View v){
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

        if(str2.isEmpty()){

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

    public void larechercheParPopularite(View v){

        Intent intent2 = getIntent();

        if (intent2 != null){
            if (intent2.hasExtra("3")){ // vérifie qu'une valeur est associée à la clé “edittext”
                str2 = intent2.getStringExtra("3"); // on récupère la valeur associée à la clé
            }
        }
        if(str2.isEmpty()){

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
