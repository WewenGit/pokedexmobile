package com.example.pokedexmobile;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetDetailledDescription;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private static int page;
    private static int lastFrame;

    private static int lastPokemonId;

    private static int buttonId;
    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance() {

        return new ListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ScrollView sv = v.findViewById(R.id.listFragment);
        LinearLayout ll = new LinearLayout(getContext());
        ll.setBackgroundColor(Color.parseColor("#212121"));
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams wholeListParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(wholeListParam);

        //TODO un seul boutton
        Button loadMoreButton=new Button(getContext());
        if (page==0) {
            lastPokemonId=0;
            int uniqueId = View.generateViewId();
            buttonId=uniqueId;
            loadMoreButton.setId(uniqueId);
            loadMoreButton.setText(R.string.load_more);
            loadMoreButton.setOnClickListener(this);
        }


        //def des params pour le linear layout

        if (lastPokemonId==1000){
            for (int i = 1001; i <= 1025; i++) {
                setAPokemonInLayout(i, ll);
                //Can't load more
                loadMoreButton = getActivity().findViewById(buttonId);
                loadMoreButton.setText(R.string.can_t_load_anymore_pokemon);
                loadMoreButton.setEnabled(false);
            }
        }
        else{
            for (int i = lastPokemonId+1; i <= lastPokemonId+200; i++) {
                setAPokemonInLayout(i,ll);
                if (getContext() != null) {
                    FrameLayout fl = new FrameLayout(getContext());
                    int uniqueId = View.generateViewId();
                    fl.setId(uniqueId);
                    lastFrame = uniqueId;
                    ll.addView(fl);
                }
                else{
                    loadMoreButton.setText(R.string.impossible_to_load_more);
                }
            }
            lastPokemonId+=200;
        }

        if (page==0){ll.addView(loadMoreButton);}

        sv.addView(ll);
        return v;
    }

    private void setAPokemonInLayout(int i, LinearLayout ll){
        LinearLayout.LayoutParams onePokemonLayoutParam = new LinearLayout.LayoutParams(
                1050,
                200
        );
        onePokemonLayoutParam.setMargins(0, 30, 0, 0);
        onePokemonLayoutParam.gravity = Gravity.CENTER;

        LinearLayout textPlusImg = new LinearLayout(getContext());
        textPlusImg.setOrientation(LinearLayout.HORIZONTAL);
        textPlusImg.setLayoutParams(onePokemonLayoutParam);
        GradientDrawable roundBorders = new GradientDrawable();
        roundBorders.setCornerRadius(30);
        textPlusImg.setBackground(roundBorders);

        TextView testView = new TextView(getContext());
        ImageView iv = new ImageView(getContext());
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + i;
        Looper looper = Looper.getMainLooper();
        GetDetailledDescription.call(poke_request, looper, testView, iv, textPlusImg);
        textPlusImg.addView(testView);
        textPlusImg.addView(iv);
        ll.addView(textPlusImg);
    }


    @Override
    public void onClick(View v) {
        page+=1;
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(lastFrame, newInstance());
            ft.commit();
        }
    }
}