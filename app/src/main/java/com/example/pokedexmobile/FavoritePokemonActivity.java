package com.example.pokedexmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class FavoritePokemonActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        assert savedInstanceState != null;
        savedInstanceState.putBoolean("isFavList",true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(savedInstanceState);
        ft.replace(R.id.framelist,listFragment);
        ft.commit();
        androidx.appcompat.widget.Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }
}
