package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button go;
    private GetDetailledDescription gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        go = findViewById(R.id.btn_go);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Data extraction
        EditText req_poke = findViewById(R.id.edt_poke_name);
        String poke_name = req_poke.getText().toString();
        TextView tv = findViewById(R.id.tv_poke);
        ImageView img = findViewById(R.id.img_poke);

        //Request creation
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + poke_name;
        Looper looper = Looper.getMainLooper();

        //Start
        GetDetailledDescription.call(poke_request, looper, tv, img);
    }



}