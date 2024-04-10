package com.example.pokedexmobile.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;

import com.example.pokedexmobile.FlashlightOnFragment;
import com.example.pokedexmobile.FragmentDetails;
import com.example.pokedexmobile.MainActivity;
import com.example.pokedexmobile.R;
import com.example.pokedexmobile.RandomPokeActivity;

public class HeadsetPlugReciever extends BroadcastReceiver {

    private RandomPokeActivity rdpact;
    @Override
    public void onReceive(Context context, Intent intent) throws UnsupportedOperationException {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        boolean activated = intent.getBooleanExtra("state", false);
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            rdpact = (RandomPokeActivity) context;
            rdpact.runOnUiThread(() -> {
                if (activated) {
                    FragmentTransaction ft = rdpact.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.generatedPokeFrag, new FlashlightOnFragment());
                    ft.commitAllowingStateLoss();
                    //TODO remettre l'ancien état
                } else {

                    FragmentTransaction ft = rdpact.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.generatedPokeFrag, new FragmentDetails());
                    ft.commitAllowingStateLoss();
                }
            });
        }
    }
}