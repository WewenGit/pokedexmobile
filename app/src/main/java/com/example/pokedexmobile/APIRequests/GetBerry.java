package com.example.pokedexmobile.APIRequests;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

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
    public static String[] callBerry(Looper loop, String requete) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(loop);
        String[] berryInfos = new String[0]; 
        executor.execute(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        return berryInfos;
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
