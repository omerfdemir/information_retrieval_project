package com.example.omerfdemir.msku_aps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private String JSON_URL;
    private String TAG;
    TextView tv;
    String [] department_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.helloworld);
        searchQuery("enis");

    }
    private void searchQuery(String search){
        String search_query_solr = getString(R.string.search_query_solr);
        JSON_URL = search_query_solr +"&q=*" + search +"*&wt=json";
        //JSON_URL = "http://192.168.1.102:8983/solr/engineering/select?indent=on&q=*ali*&wt=json";
        JsonObjectRequest req = new JsonObjectRequest(JSON_URL,null,
                new Response.Listener<JSONObject>()  {
                    @Override
                    public void onResponse(JSONObject facet_fields) {
                        Log.d("Response",facet_fields.toString());


                        try {
                            // Parsing json array response
                            // loop through each json object
                            Log.d("Response",facet_fields.toString());
                            JSONObject jsonObject = facet_fields.getJSONObject("department");
                            String departmentnames = jsonObject.getString("department");


                            /*adapter = new ArrayAdapter<String>(Companies.this, R.layout.list_item, li_tv, company_names);
                            lv.setAdapter(adapter);*/
                            //list.setAdapter(companiesCustomAdapter);
                            tv.setText(department_names[0]);





                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Hata",error.getMessage());

                    }
                }){
        };
        AppController.getInstance().addToRequestQueue(req);
    }
}

