package com.example.pokedexmobile;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ButtonAddFavorite {

    private final Button addFav;
    private final int idButton;
    private final FavoritePokemonDB favDBHelper;

    public ButtonAddFavorite(Context context, int idPokemon, FavoritePokemonDB favDBHelper, boolean alreadyIn){
        this.favDBHelper = favDBHelper;
        addFav = new Button(context);
        idButton = View.generateViewId();
        addFav.setId(idButton);
        if (!alreadyIn){
            addFav.setText(R.string.add_to_favorites);
            addFav.setOnClickListener(view -> addFavoritePokemon(idPokemon,  idButton));
        }
        else{
            addFav.setText(R.string.remove);
            addFav.setOnClickListener(view->remove(idPokemon, idButton));
        }
    }

    public Button getButton(){
        return this.addFav;
    }

    public void addFavoritePokemon(int id, int idButton) {
        favDBHelper.addFavoritePokemon(id);
        addFav.setText(R.string.remove);
        addFav.setOnClickListener(null);
        addFav.setOnClickListener(view->remove(id, idButton));
    }

    private void remove(int id, int idButton) {
        favDBHelper.removeFavoritePokemon(id);
        addFav.setText(R.string.add_to_favorites);
        addFav.setOnClickListener(null);
        addFav.setOnClickListener(view -> addFavoritePokemon(id,idButton));
    }
}
