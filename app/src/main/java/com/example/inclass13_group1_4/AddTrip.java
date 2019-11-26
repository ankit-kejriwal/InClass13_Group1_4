package com.example.inclass13_group1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddTrip extends AppCompatActivity {

    Button buttonsearch;
    Button buttonaddtrip;
    EditText editTextcity;
    EditText editTextname;
    ListView listViewcity;
    City city;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        buttonsearch = findViewById(R.id.buttonSearch);
        buttonaddtrip = findViewById(R.id.buttonaddtrip);
        editTextcity = findViewById(R.id.editTextCity);
        editTextname = findViewById(R.id.editTexttripName);
        listViewcity = findViewById(R.id.listViewCity);
        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input ="input="+editTextcity.getText().toString();

                String apikey = "key="+ "";
                String types = "types=(cities)";
                String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?"+ input+"&" +types+"&"+apikey;
                new GetNewsAsync().execute(url);
            }
        });

        buttonaddtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name  = editTextname.getText().toString();
                Intent returnIntent = getIntent();
                returnIntent.putExtra("city",city);
                returnIntent.putExtra("name",name);
                setResult(AddTrip.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetNewsAsync extends AsyncTask<String ,Void, ArrayList<City>> {

        @Override
        protected ArrayList<City> doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<City> result = new ArrayList<City>();
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String json = stringBuilder.toString();
                    JSONObject root = new JSONObject(json);
//                    JSONObject rootMessage  = root.getJSONObject("predictions");
                    JSONArray predictions = root.getJSONArray("predictions");
                    for(int i=0;i<predictions.length();i++){
                        JSONObject cityJSON = predictions.getJSONObject(i);
                        City city = new City();
                        city.id = cityJSON.getString("id");
                        city.place_id = cityJSON.getString("place_id");
                        city.description = cityJSON.getString("description");
                        result.add(city);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddTrip.this);
            progressDialog.setMessage("Loading");
            progressDialog.setProgress(0);
            progressDialog.setMax(20);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<City> cities) {
            Log.d("demo",cities.toString());
            progressDialog.dismiss();
            showListView(cities);
        }
    }

    public void showListView(final ArrayList<City> cities){
        final String[] cityList = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++){
            cityList[i] = cities.get(i).description;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cityList);
        listViewcity.setAdapter(adapter);

        listViewcity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                city = cities.get(i);
                editTextcity.setText(city.getDescription());
            }
        });
    }
}
