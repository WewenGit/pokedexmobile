package com.example.pokedexmobile.APIRequests;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Arrays;
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
            StringBuilder resultat = new StringBuilder("Name : ");
            String id;
            String nom;
            String type1=null;
            String type2=null;
            try {
                JSONObject jso = new JSONObject(resultatJSON);
                nom=jso.getString("name");
                resultat.append(nom);
                id = jso.getString("id");
                resultat.append("\n- ID : ");
                resultat.append(id);
                resultat.append("\n- Types : ");
                JSONArray ja_types = jso.getJSONArray("types");
                for (int i = 0; i<ja_types.length(); i++) {
                    JSONObject i_type = ja_types.getJSONObject(i);
                    JSONObject typei = i_type.getJSONObject("type");
                    if (i==0){
                        type1=typei.getString("name");
                    }
                    if (i==1){
                        type2=typei.getString("name");
                    }
                    resultat.append(typei.getString("name")).append(" ");
                }
                String[] res = new String[5];
                res[0] = resultat.toString();
                res[1] = id;
                res[2]=nom;
                res[3]=type1;
                res[4]=type2;
                return res;
            } catch (JSONException e) {
                return new String[]{"Erreur parsing du résultat JSON, le Pokémon entré n'existe probablement pas."};
            }
        }


        private static void display(String resultatJSON, TextView txt, ImageView img) {
            String[] res = decodeJSON(resultatJSON);
            displayBasic(txt,img,res);
        }

        private static void display(String resultatJSON, TextView txt, ImageView img, LinearLayout ll) {
            String[] res = decodeJSON(resultatJSON);
            displayBasic(txt,img,res);
            setColor(ll,res[3]);

        }

        private static void displayBasic(TextView txt, ImageView img, String[] res){
            txt.setText(res[0]);
            Log.e("PokeGetDetails", "returned" + Arrays.toString(res));

            if (!res[0].equals("Erreur parsing du résultat JSON, le Pokémon entré n'existe probablement pas.")) {
                Looper l = Looper.getMainLooper();
                String sprite_request = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + res[1] + ".png";
                GetSprite.call(sprite_request, l, img);
            }
        }

        private static void setColor(LinearLayout ll, String type){
        switch(type) {
            case "normal":
                ll.setBackgroundColor(Color.parseColor("#A8A77A"));
                break;
            case "fire":
                ll.setBackgroundColor(Color.parseColor("#EE8130"));
                break;
            case "water":
                ll.setBackgroundColor(Color.parseColor("#6390F0"));
                break;
            case "electric":
                ll.setBackgroundColor(Color.parseColor("#F7D02C"));
                break;
            case "grass":
                ll.setBackgroundColor(Color.parseColor("#7AC74C"));
                break;
            case "ice":
                ll.setBackgroundColor(Color.parseColor("#96D9D6"));
                break;
            case "fighting":
                ll.setBackgroundColor(Color.parseColor("#C22E28"));
                break;
            case "poison":
                ll.setBackgroundColor(Color.parseColor("#A33EA1"));
                break;
            case "ground":
                ll.setBackgroundColor(Color.parseColor("#E2BF65"));
                break;
            case "flying":
                ll.setBackgroundColor(Color.parseColor("#A98FF3"));
                break;
            case "psychic":
                ll.setBackgroundColor(Color.parseColor("#F95587"));
                break;
            case "bug":
                ll.setBackgroundColor(Color.parseColor("#A6B91A"));
                break;
            case "rock":
                ll.setBackgroundColor(Color.parseColor("#B6A136"));
                break;
            case "ghost":
                ll.setBackgroundColor(Color.parseColor("#735797"));
                break;
            case "dragon":
                ll.setBackgroundColor(Color.parseColor("#6F35FC"));
                break;
            case "dark":
                ll.setBackgroundColor(Color.parseColor("#705746"));
                break;
            case "steel":
                ll.setBackgroundColor(Color.parseColor("#B7B7CE"));
                break;
            case "fairy":
                ll.setBackgroundColor(Color.parseColor("#D685AD"));
                break;
            default:
                break;
        }
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

        public static void call(String requete, Looper looper, TextView txt, ImageView img, LinearLayout ll) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(looper);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String data = getDataFromHTTP(requete);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            display(data, txt, img, ll);
                        }
                    });
                }
            });
        }
    }

