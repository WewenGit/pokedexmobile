package com.example.pokedexmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.pokedexmobile.BroadcastReceiver.AirplaneBroadcastReceiver;

import java.util.ArrayList;

public class FavoritePokemonActivity extends AppCompatActivity {

    private FavoritePokemonDB favBD;
    private ArrayList<Integer> favList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favBD = getArguments().getSerializable("favBDHelper");
        setContentView(R.layout.activity_main2);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListFragment listFragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable("favDBHelper", favDBHelper);
        args.putIntegerArrayList("favList", getAllFavoritePokemon());
        listFragment.setArguments(args);
        ft.replace(R.id.framelist,listFragment);
        ft.commit();
        androidx.appcompat.widget.Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }
}
