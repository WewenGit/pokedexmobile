package com.example.pokedexmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class BerrydleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bdleNG, bdleAnsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berrydle);
        bdleNG = findViewById(R.id.bdleNewGame);
        bdleAnsw = findViewById(R.id.bdleguess);
        bdleNG.setOnClickListener(this);
        bdleAnsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}