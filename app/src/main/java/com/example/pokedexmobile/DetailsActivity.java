package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button go;
    private GetDetailledDescription gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.pokeDetailsFrag, new FragmentDetails());
        ft.commit();
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        go = findViewById(R.id.btn_go);
        go.setOnClickListener(this);
    }

    //Gestion du bouton GO, l'affichage est géré par la requête API "GetDetailledDescription", à qui on
    //envoie le TextView et l'ImageView à éditer.

    // Refaire la gestion d'erreur en cas de saisie incorrecte.
    @Override
    public void onClick(View v) {
        //Data extraction
        EditText req_poke = findViewById(R.id.edt_poke_name);
        String poke_name = req_poke.getText().toString();
        poke_name = poke_name.toLowerCase();
        TextView tv = findViewById(R.id.tv_poke);
        ImageView img = findViewById(R.id.img_poke);

        //Request creation
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + poke_name;
        Looper looper = Looper.getMainLooper();

        //Start
        GetDetailledDescription.call(poke_request, looper, tv, img);
    }



    // Gestion de la Toolbar
    public boolean onCreateOptionsMenu(Menu m) {
        super.onCreateOptionsMenu(m);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, m);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.generatePoke:
                Intent i3 = new Intent(this, RandomPokeActivity.class);
                startActivity(i3);
                return true;
            case R.id.listPoke:
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                return true;
            case R.id.searchPoke:
                Intent i2 = new Intent(this, DetailsActivity.class);
                startActivity(i2);
                return true;
            case R.id.berrydleGame:
                Intent i4 = new Intent(this, BerrydleActivity.class);
                startActivity(i4);
                return true;
        }
        return true;
    }



}