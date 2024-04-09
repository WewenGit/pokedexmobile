package com.example.pokedexmobile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment{

    private Button loadMoreButton;
    private int lastPokemonId;

    private LinearLayout ll;
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //définition de la liste de 200
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ScrollView sv = v.findViewById(R.id.listFragment);
        ll = new LinearLayout(getContext());
        ll.setBackgroundColor(Color.parseColor("#212121"));
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams wholeListParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(wholeListParam);

        //creation du button load more
        loadMoreButton=new Button(getContext());
        //si on est à la première génération de la list on définit les attributes suivants
        lastPokemonId=0;
        int buttonId = 1051054080;
        loadMoreButton.setId(buttonId);
        loadMoreButton.setText(R.string.load_more);
        loadMoreButton.setOnClickListener(this::clickLoadMore);


        //def des params pour le linear layout

        if (lastPokemonId==1000){
            for (int i = 1001; i <= 1025; i++) {
                setAPokemonInLayout(i);
            }
            //Can't load more
            loadMoreButton = v.findViewById(buttonId);
            loadMoreButton.setText(R.string.can_t_load_anymore_pokemon);
            loadMoreButton.setEnabled(false);
        }
        else{
            for (int i = lastPokemonId+1; i <= lastPokemonId+100; i++) {
                setAPokemonInLayout(i);
            }
            lastPokemonId+=100;
        }
        ll.addView(loadMoreButton);

        sv.addView(ll);
        return v;
    }

    private void setAPokemonInLayout(int i){
        //definition parameters
        LinearLayout.LayoutParams onePokemonLayoutParam = new LinearLayout.LayoutParams(
                1050,
                200
        );
        onePokemonLayoutParam.setMargins(0, 30, 0, 0);
        onePokemonLayoutParam.gravity = Gravity.CENTER; //center la boite, créer les 2 margins +/-

        LinearLayout textPlusImg = new LinearLayout(getContext());
        textPlusImg.setId(i); //on lui donne l'id du pokémon
        textPlusImg.setOrientation(LinearLayout.HORIZONTAL);
        textPlusImg.setLayoutParams(onePokemonLayoutParam);
        GradientDrawable roundBorders = new GradientDrawable();
        roundBorders.setCornerRadius(30);
        textPlusImg.setBackground(roundBorders);
        textPlusImg.setOnClickListener(view -> goDetail(i));

        TextView infoView = new TextView(getContext());
        ImageView iv = new ImageView(getContext());
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + i;
        Looper looper = Looper.getMainLooper();
        GetDetailledDescription.call(poke_request, looper, infoView, iv, textPlusImg);
        textPlusImg.addView(infoView);
        textPlusImg.addView(iv);
        ll.addView(textPlusImg);
    }


    //si on clique sur load more
    public void clickLoadMore(View v){

        ll.removeView(loadMoreButton);
        for (int i = lastPokemonId + 1; i <= lastPokemonId + 100; i++) {
            setAPokemonInLayout(i);
        }
        lastPokemonId += 100;
        ll.addView(loadMoreButton);
        ll.invalidate();
    }

    public void goDetail(int idPokemon){
        Intent goDetailIntent = new Intent(getActivity(),DetailledViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idChosenPokemon",idPokemon);
        goDetailIntent.putExtras(bundle);
        startActivity(goDetailIntent);
    }
}