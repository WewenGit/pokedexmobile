package com.example.pokedexmobile.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.pokedexmobile.MainActivity;
import com.example.pokedexmobile.R;

public class AirplaneBroadcastReceiver extends BroadcastReceiver {

    private MainActivity activity;
    @Override
    public void onReceive(Context context, Intent intent) throws UnsupportedOperationException{
        boolean activated = intent.getBooleanExtra("state", false);
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            //Log.d("ONRECEIVE","ouaip");
            activity = (MainActivity) context;
            activity.runOnUiThread(() -> {
                if (activated) {
                    activity.setContentView(R.layout.activity_main_airplane);
                } else {
                    //TODO remettre l'ancien état
                    activity.setContentView(R.layout.activity_main2);
                }
            });
        }
    }
}
