package com.example.pokedexmobile.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class AirplaneReciever extends BroadcastReceiver {

    private Button b;

    public AirplaneReciever(Button b) {
        this.b = b;
    }

    @Override
    public void onReceive(Context context, Intent intent) throws UnsupportedOperationException {
        boolean isAirplaneModeOn = intent.getBooleanExtra("state",
                false);
        if (isAirplaneModeOn) {
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }
}