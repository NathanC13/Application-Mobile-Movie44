package com.example.movie44;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import  android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.util.ArrayList;

// Senconde activité permettant de rechercher des films en fonction de certains critères
public class Search extends AppCompatActivity {

    private Button rechercherbtn;
    private TextView contenu;
    private ListView install;
    Spinner genre;
    private TextView nombre;
    CheckBox pop;
    Boolean is_cheked;

    private ArrayAdapter<String> aa;
    private ArrayAdapter<JsonElement> listeDesJsonElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_2);

        pop = findViewById(R.id.pop);
        genre = findViewById(R.id.spinner2);
        contenu = findViewById(R.id.editTextTextMultiLine3);
        nombre = findViewById(R.id.editTextNumberDecimal3);


        aa = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1);
        listeDesJsonElements = new ArrayAdapter<JsonElement>(Search.this, android.R.layout.simple_list_item_1);

        // Remplissage du spinner avec les genres des films de l'API
        pop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    genre.setEnabled(false);
                    contenu.setEnabled(false);
                    is_cheked = true;
                }else{
                    genre.setEnabled(true);
                    contenu.setEnabled(true);
                    nombre.setEnabled(true);
                    is_cheked = false;

                }
            }
        });




        // Récuperation des genres
        Ion.with(getApplicationContext()).load("https://api.themoviedb.org/3/genre/movie/list?api_key=9ff2e7d040d16512b0607bf63215f567").asJsonObject().setCallback(new FutureCallback<JsonObject>() {

            @Override
            public void onCompleted(Exception e, JsonObject result) {
                for (JsonElement elem : result.getAsJsonArray("genres")){
                    aa.add(String.valueOf(elem.getAsJsonObject().get("name")).replaceAll("\"",""));
                    listeDesJsonElements.add(elem);
                }
            }
        });
        genre.setAdapter(aa);
    }

    // Fonction permettant de se diriger vers l'activité Results une fois les paramètres de sélection rentrés avec un clic sur le bouton
    public void onClick2(View v){

        String val2 = contenu.getText().toString();
        String val3 = nombre.getText().toString();
        Intent intent = new Intent(Search.this, Results.class);
        intent.putExtra("2", val2);
        intent.putExtra("3",val3);
        intent.putExtra("check_pop",is_cheked);
        startActivity(intent);
    }

}
