package com.example.omerfdemir.msku_aps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by omerfdemir on 15.05.2017.
 */

public class Names extends AppCompatActivity {
    String departmentname;
    String JSON_URL;
    String JSON_URL2;
    String[] names;
    ArrayList<String> names_array;
    ArrayList<String> mails_array;
    String[] mails;
    String [] photos;
    List data;
    ListView listView;
    InstructorCustomAdapter instructorCustomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names);


        Intent intent = getIntent();
        departmentname = intent.getStringExtra("department_name");
        Log.d("Department name",departmentname);
        setTitle(departmentname);
        data = new ArrayList<>();

        instructorCustomAdapter = new InstructorCustomAdapter(Names.this,data);
        listView = (ListView) findViewById(R.id.lv_instructor);
        getAllDepartments();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Names.this,InstructorPage.class);
                i.putExtra("name",names_array.get(position).replace("[","").replace("]","").replace("\"",""));
                startActivity(i);
            }
        });

    }
    private void getAllDepartments(){
        String department_search = getString(R.string.department_search);
        JSON_URL = department_search + "select?fq=department:"+"\""+departmentname.replace(" ","%20").replace("\"","%22").replace("ü","%C3%BC").replace("ğ","%C4%9F").replace("İ","%C4%B0").replace("ş","%C5%9F")+"\""+"&indent=on&q=*.*&rows=5000&wt=json";
        //JSON_URL2 = "http://192.168.1.105:8983/solr/engineering/select?fq=department:%22Bilgisayar%20M%C3%BChendisli%C4%9Fi%22&indent=on&q=*:*&rows=500&wt=json";
        //JSON_URL =  "http://192.168.1.105:8983//solr/engineering/select?fq=department:\"Bilgisayar%20Mühendisliği\"&indent=on&q=*:*&rows=500&wt=json";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,JSON_URL,null,
                new Response.Listener<JSONObject>()  {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        try {
                            names = new String[response.getJSONObject("response").getJSONArray("docs").length()];
                            mails = new String[response.getJSONObject("response").getJSONArray("docs").length()];
                            photos = new String[response.getJSONObject("response").getJSONArray("docs").length()];
                            names_array = new ArrayList<>(response.getJSONObject("response").getJSONArray("docs").length());
                            mails_array = new ArrayList<>(response.getJSONObject("response").getJSONArray("docs").length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response.toString());
                        try {
                            Log.d("Length", String.valueOf(response.getJSONObject("response").getJSONArray("docs").length()));
                            Log.d("Lengthhh", String.valueOf(response.getJSONObject("response").getJSONArray("docs").getJSONObject(0).getString("name")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.getJSONObject("response").getJSONArray("docs").length(); i++ ) {

                                JSONObject jsonObject = response.getJSONObject("response").getJSONArray("docs").getJSONObject(i);
                                if (names_array.contains(jsonObject.getString("name"))){
                                    continue;
                                }
                                else{
                                    names_array.add(jsonObject.getString("name"));
                                    mails_array.add(jsonObject.getString("email"));
                                    data.add(new Instructor(jsonObject.getString("name"),"http://servis.mu.edu.tr/personelservis/Resim.aspx?mail="+jsonObject.getString("email").replace("[","").replace("]","").replace("\"","")));
                                    Log.d("M",jsonObject.getString("email").replace("["," ").replace("]"," ").replace("\""," "));
                                    Log.d("M2","http://servis.mu.edu.tr/personelservis/Resim.aspx?mail="+jsonObject.getString("email").replace("[","").replace("]","").replace("\"",""));

                                }
                                Log.d("Array", String.valueOf(names_array));
                                Log.d("Mails",String.valueOf(mails_array));
                                listView.setAdapter(instructorCustomAdapter);




                              //  department_names [i/2] = jsonArray.getString(i);
                              //  Log.d("Departments", department_names[i/2]);
                              //  Log.d("Type",department_names[0]);


                            /*adapter = new ArrayAdapter<String>(Companies.this, R.layout.list_item, li_tv, company_names);
                            lv.setAdapter(adapter);*/
                                //list.setAdapter(companiesCustomAdapter);




                            }



                          //  adapter = new ArrayAdapter<String>(Departments.this,R.layout.list_item,R.id.li_tv,department_names);
                          //  lv.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Names.this,
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
        AppController.getInstance().addToRequestQueue(req,"UTF-8");

    }
}
