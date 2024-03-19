package com.example.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedexmobile.APIRequests.GetPokemon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button go;
    private GetPokemon gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        go = findViewById(R.id.btn_go);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Data extraction
        EditText req_poke = findViewById(R.id.edt_poke_name);
        String poke_name = req_poke.getText().toString();
        TextView tv = findViewById(R.id.tv_poke);
        ImageView img = findViewById(R.id.img_poke);

        //Request creation
        String poke_request = "https://pokeapi.co/api/v2/pokemon/" + poke_name;
        Looper looper = Looper.getMainLooper();

        //Start
        GetPokemon.call(poke_request, looper, tv, img);
    }

   /* public void call(String req, Looper looper, TextView tv, ImageView img) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        exec.execute(new Runnable() {
            @Override
            public void run() {
                String result = getHTTPContent(param);
                try {
                    JSONObject jsonres = new JSONObject(result);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String resp = decode(jsonres);
                                display(resp);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public String decode(JSONObject jsoninfos) throws JSONException {
        String response = "";
        response = response + "City : " + jsoninfos.getString("name") + "\n";
        JSONObject main = jsoninfos.getJSONObject("main");
        response = response + "Temperature :" + main.getString("temp") + "\n";
        JSONArray ja = jsoninfos.getJSONArray("weather");
        for (int i = 0; i<ja.length(); i++) {
            JSONObject w = ja.getJSONObject(i);
            response = response + "Weather : " + w.getString("main") + "\n";
        }
        return response;
    }

    public void display(String toShow) {
        TextView tv = findViewById(R.id.tv_map);
        tv.setText(toShow);
    }

    public String getHTTPContent(String param) {
        StringBuilder res = new StringBuilder();
        HttpURLConnection con = null;
        String link = "https://api.openweathermap.org/data/2.5/weather?q=" + param + ",fr&units=metric&lang=fr&ap\n" +
                "pid=ea3fd47b79737d51d2e5684dcd45ed17";
        try {
            URL url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream inpS = con.getInputStream();
            InputStreamReader inpSR = new InputStreamReader(inpS);
            BufferedReader bffr = new BufferedReader(inpSR);
            String line = "";
            while ((line = bffr.readLine()) != null) {
                res.append(line);
            }
            inpS.close();
            bffr.close();
            con.disconnect();
        } catch (Exception e) {
            res = new StringBuilder("Erreur" + e);
        }
        return res.toString();
    }*/

}