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

        contenu = findViewById(R.id.editTextTextMultiLine3);
    }

    public void onClick2(View v){

        String val2 = contenu.getText().toString();
        Intent intent = new Intent(Search.this, Results.class);
        intent.putExtra("2", val2);
        startActivity(intent);
    }

}
