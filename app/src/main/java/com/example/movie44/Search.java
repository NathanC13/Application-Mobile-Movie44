package com.example.movie44;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import  android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private Button rechercherbtn;
    private TextView contenu;
    private ListView install;

    private ArrayAdapter<String> aa;
    private ArrayAdapter<JsonElement> listeDesJsonElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_2);

        /*
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter ad = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
         */

        rechercherbtn = findViewById(R.id.button2);
        contenu = findViewById(R.id.editTextTextMultiLine3);
        install = findViewById(R.id.installations);

        aa = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1);
        listeDesJsonElements = new ArrayAdapter<JsonElement>(Search.this, android.R.layout.simple_list_item_1);

        rechercherbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                larecherche(view);
            }
        });

        install.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent versSecondaire = new Intent(Search.this,Results.class);
                versSecondaire.putExtra("valeurInstallation", aa.getItem(i));
                versSecondaire.putExtra("valeurInstallationAdresse", String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().getAsJsonObject("adresse").get("voie")).replaceAll("\"","") + " " + String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().getAsJsonObject("adresse").get("codePostal")).replaceAll("\"","") + " " + String.valueOf(listeDesJsonElements.getItem(i).getAsJsonObject().getAsJsonObject("adresse").get("commune")).replaceAll("\"",""));
                versSecondaire.putExtra("valeurInstallationListe", (Parcelable) listeDesJsonElements);
                startActivity(versSecondaire);
            }

        });
    }

    public void larecherche(View v){
        Ion.with(v.getContext()).load("https://nosql-workshop.herokuapp.com/api/installations/search?query="+contenu.getText()).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray response) {

                for (JsonElement elem: response
                ) {
                    aa.add(String.valueOf(elem.getAsJsonObject().get("nom")).replaceAll("\"",""));
                    listeDesJsonElements.add(elem);

                }
                install.setAdapter(aa);
            }
        });
    }


    /*
    public void onClick2(View v){

        Button b2 = findViewById(R.id.button2);
        Intent i = new Intent(Search.this, Results.class);
        startActivity(i);
    }
    */

}
