package com.example.pokedexmobile.APIRequests;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

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

/** Class GetDetailledDescription
 * Uses a request, a TextView, an ImageView to get and set pokemon details from the API
 * Decodes the JSON information, sets the result on textview and get the sprite via GetSprite Class.
 */
public class GetDetailledDescription {

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

        private static String[] decodeJSON(String resultatJSON) {
            String resultat = "Name : ";
            String id;
            try {
                JSONObject jso = new JSONObject(resultatJSON);
                resultat += jso.getString("name");
                id = jso.getString("id");
                resultat += "\n- ID : ";
                resultat += jso.getString("id");
                resultat += "\n- Types : ";
                JSONArray ja_types = jso.getJSONArray("types");
                for (int i = 0; i<ja_types.length(); i++) {
                    JSONObject i_type = ja_types.getJSONObject(i);
                    JSONObject typei = i_type.getJSONObject("type");
                    resultat += typei.getString("name") + " ";
                }
                String[] res = new String[2];
                res[0] = resultat;
                res[1] = id;
                return res;
            } catch (JSONException e) {
                return new String[]{"Erreur parsing du résultat JSON, le Pokémon entré n'existe probablement pas."};
            }
        }


        private static void display(String resultatJSON, TextView txt, ImageView img) {
            String[] res = decodeJSON(resultatJSON);
            txt.setText(res[0]);
            Looper l = Looper.getMainLooper();
            String sprite_request = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + res[1] + ".png";
            GetSprite.call(sprite_request, l, img);
        }

        /**
         * Execute la requête http passée en paramètre
         *
         * @param requete (String), requête à exécuter
         * @param looper  (Looper), looper du thread principal utilisant la méthode
         *                (obtenu avec Looper.getMainLooper())
         * @param txt     (TextView), TextView sur lequel affiché le résultat de la requête
         */
        public static void call(String requete, Looper looper, TextView txt, ImageView img) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(looper);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String data = getDataFromHTTP(requete);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            display(data, txt, img);
                        }
                    });
                }
            });
        }
    }

