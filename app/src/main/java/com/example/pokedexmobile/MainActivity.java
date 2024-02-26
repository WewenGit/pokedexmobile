package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout mainLayout = findViewById(R.id.main_layout);

        for (int i = 0; i <10 ; i++) {
            //definition of a layout and two components
            RelativeLayout pokemonRL = new RelativeLayout(this);
            TextView pokemon_name = new TextView(this);
            Button pokemon_sprite = new Button(this);

            //set content values
            pokemon_name.setText(R.string.nom_pokemon);
            pokemon_sprite.setBackgroundResource(R.drawable.tranchodon);
            //set content ids and ad them
            pokemon_name.setId(View.generateViewId());
            pokemonRL.addView(pokemon_name);
            pokemonRL.addView(pokemon_sprite);

            //position parameters
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.END_OF, pokemon_name.getId());

            pokemon_sprite.setLayoutParams(lp);

            pokemonRL.addView(mainLayout);

        }
        setContentView(mainLayout);
    }
}