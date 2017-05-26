package com.example.omerfdemir.msku_aps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by omerfdemir on 12.05.2017.
 */

public class Departments extends AppCompatActivity {
    private String JSON_URL;
    private String TAG;
    String [] department_names;
    ArrayAdapter<String> adapter;
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculties);
        lv = (ListView) findViewById(R.id.faculties);
        getAllDepartments();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Names.class);
                intent.putExtra("department_name",department_names[position]);
                startActivity(intent);
            }
        });
    }
    private void getAllDepartments(){
        String facet_field = getString(R.string.facet_field);
        JSON_URL = facet_field + "department&facet=on&indent=on&wt=json";
        //JSON_URL = "http://192.168.1.102:8983/solr/engineering/select?indent=on&q=*ali*&wt=json";
        JsonObjectRequest req = new JsonObjectRequest(JSON_URL,null,
                new Response.Listener<JSONObject>()  {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        try {
                            department_names = new String[response.getJSONObject("facet_counts").getJSONObject("facet_fields").getJSONArray("department").length()/2];
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response.toString());
                        try {
                            Log.d("Length", String.valueOf(response.getJSONObject("facet_counts").getJSONObject("facet_fields").getJSONArray("department").length()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.getJSONObject("facet_counts").getJSONObject("facet_fields").getJSONArray("department").length(); i=i+2 ) {

                                JSONArray jsonArray = response.getJSONObject("facet_counts").getJSONObject("facet_fields").getJSONArray("department");

                                department_names [i/2] = jsonArray.getString(i);
                                Log.d("Departments", department_names[i/2]);
                                Log.d("Type",department_names[0]);


                            /*adapter = new ArrayAdapter<String>(Companies.this, R.layout.list_item, li_tv, company_names);
                            lv.setAdapter(adapter);*/
                                //list.setAdapter(companiesCustomAdapter);




                            }



                            adapter = new ArrayAdapter<String>(Departments.this,R.layout.list_item,R.id.li_tv,department_names);
                            lv.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Departments.this,
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
