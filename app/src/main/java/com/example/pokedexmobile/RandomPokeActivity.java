package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;
import com.example.pokedexmobile.BroadcastReceiver.HeadsetPlugReciever;

public class RandomPokeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button generate;
    private HeadsetPlugReciever hdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_poke);
        hdr = new HeadsetPlugReciever();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.generatedPokeFrag, new FragmentDetails());
        ft.commitAllowingStateLoss();
        androidx.appcompat.widget.Toolbar tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        generate = findViewById(R.id.getRdmPokeBtn);
        generate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int rdm = (int) (Math.random() * 1025);
        String poke_id = String.valueOf(rdm);
        TextView tv = findViewById(R.id.tv_poke);
        ImageView img = findViewById(R.id.img_poke);

        //Request creation
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + poke_id;
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(hdr, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(hdr);
    }
}