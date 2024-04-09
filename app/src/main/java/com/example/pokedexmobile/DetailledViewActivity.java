package com.example.pokedexmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;

import org.w3c.dom.Text;

public class DetailledViewActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailled_view_activity);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        //gérage de l'intent
        Intent intent = getIntent();
        TextView tvId = findViewById(R.id.affichageId);

        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            int idPokemon = bundle.getInt("idChosenPokemon");
            //creation des views qui afficheront les elements
            TextView tvName = findViewById(R.id.affichageName);
            TextView tvType1 = findViewById(R.id.affichageType1);
            TextView tvType2 = findViewById(R.id.affichageType2);
            ImageView ivName = findViewById(R.id.affichageSprite);

            //TODO
            String poke_request = "https://pokeapi.co/api/v2/pokemon/" + idPokemon;
            Looper looper = Looper.getMainLooper();
            tvId.setText("N°"+idPokemon);
            GetDetailledDescription.call(poke_request, looper, tvName,tvType1,tvType2, ivName);

            //TODO add les éléments
        }
        else{
            tvId.setText("Erreur de récupération du Pokémon");
        }

    }

    //Gestion de la Toolbar
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
            case R.id.listPoke:
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                return true;
            case R.id.searchPoke:
                Intent i2 = new Intent(this, DetailsActivity.class);
                startActivity(i2);
                return true;
            case R.id.generatePoke:
                Intent i3 = new Intent(this, RandomPokeActivity.class);
                startActivity(i3);
                return true;
        }
        return true;
    }
}
