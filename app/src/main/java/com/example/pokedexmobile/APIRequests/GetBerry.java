package com.example.pokedexmobile.APIRequests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedexmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GetBerry {
    private static String[] berryData;

    public static String[] callBerry(Looper loop, String requete) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(loop);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("BerrydleRQ", requete);
                String data = getDataFromHTTP(requete);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        berryData = decodeJSON(data);
                    }
                });
            }
        });
        return berryData;
    }

    public static String[] guessBerry(Looper loop, String requete, LinearLayout sv, Activity activity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(loop);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("BerrydleRQ", requete);
                String data = getDataFromHTTP(requete);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        display(data, sv, activity);
                    }
                });
            }
        });
        return berryData;
    }

    @SuppressLint("SetTextI18n")
    public static void display(String jsonStr, LinearLayout linearLayout, Activity activity) {
        String[] res = decodeJSON(jsonStr);
        Log.e("BerryResolver","DON'T USE THAT IS'T CHEATING AND IM GOING TO PLEURER :( (Log uniquement à but de débug, y'a 64 baies, j'ai autre chose à faire que de toute les tester \n" + Arrays.toString(res) + "\n " + Arrays.toString(berryData));
        Button bt = activity.findViewById(R.id.bdleguess);
        if (res[0].equals(berryData[0])) {
            bt.setText("WELL PLAYED");
            bt.setBackgroundColor(Color.parseColor("#999900"));
            bt.setEnabled(false);
            Toast t = new Toast(activity);
            t.setText("BRAVO BIEN JOUE AHAHAHAHAHAHA");
        }
        int idx = 0;
        Log.e("BerryGuessDisplay","Entered Display method " + Arrays.toString(res));

        if (!res[0].equals("Erreur parsing du résultat JSON.")) {
            Log.e("BerryGuessDisplay","Res not null");
            TableLayout tableLayout = new TableLayout(activity);
            tableLayout.setPadding(0, 30 ,0, 0);
            Log.e("BerryGuessDisplay","Table is created");
                TextView entete = new TextView(activity);
                entete.setText(res[0].toUpperCase());
                entete.setBackgroundColor(Color.parseColor("#000099"));
                tableLayout.addView(entete,0);
                for (int i=1; i<res.length; i++) {
                        Log.e("BerryGuessDisplay","Exploring res...");
                        if (res[i] != null) {
                            Log.e("BerryGuessDisplay","Res not null");
                            TextView textView = new TextView(activity);
                            textView.setPadding(0, 10, 0, 0);
                            textView.setText(res[i]);
                            if (res[i].equals(berryData[i])) {
                                Log.e("BerryGuessDisplay","Res good");
                                textView.setBackgroundColor(Color.parseColor("#009900"));
                            } else {
                                Log.e("BerryGuessDisplay","Res bad");
                                textView.setBackgroundColor(Color.parseColor("#990000"));
                            }
                            tableLayout.addView(textView);
                        }
                }
            Log.e("BerryGuessDisplay","Row in tab");

            linearLayout.addView(tableLayout, 0);
            Log.e("BerryGuessDisplay","Should be displayed in tab ???");

        } else {
            TextView tv = new TextView(activity);
            tv.setText(Arrays.toString(res));
            linearLayout.addView(tv);
        }
    }

    public static String[] decodeJSON(String resultatJSON) {
        String[] res = new String[9];
        try {
            JSONObject jso = new JSONObject(resultatJSON);
            JSONObject firmness = jso.getJSONObject("firmness");
            res[3] = "Firmness : \n" + firmness.getString("name");
            res[1] = "Growth time : \n" + jso.getString("growth_time");
            res[2] = "Max Harvest : \n" + jso.getString("max_harvest");
            res[4] = "Size : \n" + jso.getString("size");
            res[5] = "Smoothness :\n" + jso.getString("smoothness");
            res[6] = "Soil dryness : \n" + jso.getString("soil_dryness");
            res[0] = "Name : \n" + jso.getString("name"); //Berry name
            JSONObject type = jso.getJSONObject("natural_gift_type");
            res[7] = "Natural gift type : \n" + type.getString("name");
            return res;
        } catch (JSONException e) {
            return new String[]{"Erreur parsing du résultat JSON,"};
        }
    }

    public static String getDataFromHTTP(String param) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connexion;
        try {
            URL url = new URL(param);
            connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("GET");
            InputStream inputStream = connexion.getInputStream();
            InputStreamReader inputStreamReader = new
                    InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String ligne;
            while ((ligne = bf.readLine()) != null) {
                result.append(ligne);
            }
            inputStream.close();
            bf.close();
            connexion.disconnect();
        }
        // Gestion des erreurs de l'exécution de la requête
        catch (MalformedURLException m) {
            result = new StringBuilder("Mauvais URL");
        }
        catch (IOException io) {
            result = new StringBuilder("Pas de connexion internet");
        }
        catch (Exception e) {
            result = new StringBuilder("Erreur ");
        }
        return result.toString();
    }
}
