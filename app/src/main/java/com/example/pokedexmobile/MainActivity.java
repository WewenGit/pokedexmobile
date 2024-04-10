package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pokedexmobile.BroadcastReceiver.AirplaneBroadcastReceiver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AirplaneBroadcastReceiver airplaneReceiver;
    private static FavoritePokemonDB favDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isAirPlaneModeOn = Settings.System.getInt(this.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

        airplaneReceiver = new AirplaneBroadcastReceiver();
        favDBHelper = new FavoritePokemonDB(this);

        if (isAirPlaneModeOn){
            setContentView(R.layout.activity_main_airplane);
        }
        else{
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
                args.putIntegerArrayList("favList", getAllFavoritePokemon());
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
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneReceiver);
    }

    public static ArrayList<Integer> getAllFavoritePokemon() {
        ArrayList<Integer> favoritePokemonIds = new ArrayList<>();

        String selectQuery = "SELECT " + FavoritePokemonDB.KEY_ID + " FROM " + FavoritePokemonDB.TABLE_FAVORITES;

        SQLiteDatabase db = favDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(FavoritePokemonDB.KEY_ID));
                favoritePokemonIds.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoritePokemonIds;
    }


}