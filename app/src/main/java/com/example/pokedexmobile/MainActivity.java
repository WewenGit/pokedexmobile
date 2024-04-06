package com.example.pokedexmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }

    //Mettre la liste dans ListFragment et gérer comment elle fonctionne ici là, juste là.

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
            case R.id.searchPoke:
                Intent i = new Intent(this, DetailsActivity.class);
                startActivity(i);
                return true;
        }
        return true;
    }
}