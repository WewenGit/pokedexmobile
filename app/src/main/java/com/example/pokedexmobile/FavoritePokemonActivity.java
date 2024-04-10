package com.example.pokedexmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class FavoritePokemonActivity extends AppCompatActivity {

    private final FavoritePokemonDB favDBHelper=  new FavoritePokemonDB(this);
    private ArrayList<Integer> getAllFavoritePokemon;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_layout);

        Bundle extras=new Bundle();
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            extras = intent.getExtras();
        }
        extras.putBoolean("isFavList",true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(extras);
        ft.replace(R.id.framelist,listFragment);
        ft.commit();
        androidx.appcompat.widget.Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }

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
                break;
            case R.id.searchPoke:
                Intent i2 = new Intent(this, DetailsActivity.class);
                startActivity(i2);
                break;
            case R.id.generatePoke:
                Intent i3 = new Intent(this, RandomPokeActivity.class);
                startActivity(i3);
                break;
            case R.id.favorites:
                Bundle args = new Bundle();
                args.putSerializable("favDBHelper", favDBHelper);
                args.putIntegerArrayList("favList", MainActivity.getAllFavoritePokemon());
                Intent i4 = new Intent(this, FavoritePokemonActivity.class);
                i4.putExtras(args);
                startActivity(i4);
                break;
            case R.id.berrydleGame:
                Intent i5 = new Intent(this, BerrydleActivity.class);
                startActivity(i5);
                break;
        }
        return true;
    }
}
