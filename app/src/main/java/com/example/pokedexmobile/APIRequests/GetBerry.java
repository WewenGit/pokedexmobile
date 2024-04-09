package com.example.pokedexmobile.APIRequests;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static String[] guessBerry(Looper loop, String requete, ScrollView sv) {
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

    public static void display() {

    }

    public static String[] decodeJSON(String resultatJSON) {
        String[] res = new String[9];
        try {
            JSONObject jso = new JSONObject(resultatJSON);

            JSONObject firmness = jso.getJSONObject("firmness");
            res[0] = firmness.getString("name");
            Log.e("BerrydleDecodeJSON","firmness " +  res[0]);

            res[1] = jso.getString("growth_time");
            Log.e("BerrydleDecodeJSON","growth_time " +  res[1]);

            res[2] = jso.getString("max_harvest");
            Log.e("BerrydleDecodeJSON","max_harvest " +  res[2]);

            //res[3] = jso.getString("natural_gift_power");
            //Log.e("BerrydleDecodeJSON","natural_gift_power " +  res[3]);

            res[4] = jso.getString("size");
            Log.e("BerrydleDecodeJSON", "size " +  res[4]);

            res[5] = jso.getString("smoothness");
            Log.e("BerrydleDecodeJSON", "smoothness " +  res[5]);

            res[6] = jso.getString("soil_dryness");
            Log.e("BerrydleDecodeJSON", "soil_dryness " + res[6]);

            res[8] = jso.getString("name"); //Berry name

            JSONObject type = jso.getJSONObject("natural_gift_type");
            res[7] = type.getString("name");
            Log.e("BerrydleDecodeJSON", "natural_gift_type " + res[7]);
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
