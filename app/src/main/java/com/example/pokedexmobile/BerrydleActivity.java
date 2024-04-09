package com.example.pokedexmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.pokedexmobile.APIRequests.GetBerry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BerrydleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bdleNG, bdleAnsw;
    private String[] berryData, berryGuess; //0 : Firmness 1 : Growth Time 2 : Max Harvest
    // 4 : size 5 : smoothness 6 : soil_dryness 7 : Berry Name 8 : Natural gift type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berrydle);
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getBerryData();
        bdleNG = findViewById(R.id.bdleNewGame);
        bdleAnsw = findViewById(R.id.bdleguess);
        bdleNG.setOnClickListener(this);
        bdleAnsw.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.bdleguess)) {
            guess();
        } else if (v == findViewById(R.id.bdleNewGame)) {
            Intent i = new Intent(this, BerrydleActivity.class);
            startActivity(i);
        }
    }

    public void guess(){
        EditText edt = findViewById(R.id.bdleEdt);
        String berryGuess = edt.getText().toString();
        berryGuess = berryGuess.toLowerCase();
        ScrollView sv = findViewById(R.id.bdleSV);
        //Request creation
        String berryrq = "https://pokeapi.co/api/v2/berry/" + berryGuess;
        Looper looper = Looper.getMainLooper();

        String[] guess = GetBerry.guessBerry(looper, berryrq,sv);

    }

    public void getBerryData() {
        int rdm = (int) (Math.random() * 64);
        String berryid = String.valueOf(rdm);
        String berry_rq = "https://pokeapi.co/api/v2/berry/" + berryid;
        Looper looper = Looper.getMainLooper();

        berryData = GetBerry.callBerry(looper, berry_rq);

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
                return true;
            case R.id.searchPoke:
                Intent i2 = new Intent(this, DetailsActivity.class);
                startActivity(i2);
                return true;
            case R.id.generatePoke:
                Intent i3 = new Intent(this, RandomPokeActivity.class);
                startActivity(i3);
                return true;
            case R.id.berrydleGame:
                Intent i4 = new Intent(this, BerrydleActivity.class);
                startActivity(i4);
                return true;
        }
        return true;
    }


}