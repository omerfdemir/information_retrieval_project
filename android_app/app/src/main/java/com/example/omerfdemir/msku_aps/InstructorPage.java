package com.example.omerfdemir.msku_aps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import github.chenupt.dragtoplayout.DragTopLayout;

/**
 * Created by omerfdemir on 16.05.2017.
 */

public class InstructorPage extends AppCompatActivity{
    ListView listView;
    String name,mail,photo,instructor_name;
    String JSON_URL,JSON_URL2;
    String[] publications;
    ArrayAdapter<String> adapter;
    ImageView imageView;
    TextView tvname,tvemail;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_page);

        listView = (ListView) findViewById(R.id.list_inst);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        getInstructor(name);
         imageView = (ImageView) findViewById(R.id.ip_iv);
         tvemail = (TextView) findViewById(R.id.email);
         tvname = (TextView) findViewById(R.id.name);




    }

    private void getInstructor(String name){
        String department_search = getString(R.string.department_search);
        JSON_URL = department_search + "select?fq=name:"+"\""+name.replace(" ","%20").replace("\"","%22").replace("ü","%C3%BC").replace("ğ","%C4%9F").replace("İ","%C4%B0").replace("ş","%C5%9F")+"\""+"&indent=on&q=*.*&rows=5000&wt=json";
        JSON_URL2 = department_search + "select?fq=name:"+"\""+URLEncoder.encode(name)+"\""+"&indent=on&q=*.*&rows=5000&wt=json";
        //JSON_URL =  "http://192.168.1.105:8983//solr/engineering/select?fq=department:\"Bilgisayar%20Mühendisliği\"&indent=on&q=*:*&rows=500&wt=json";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,JSON_URL2,null,
                new Response.Listener<JSONObject>()  {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        try {

                            publications = new String[response.getJSONObject("response").getJSONArray("docs").length()];


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



                                instructor_name = jsonObject.getString("name").replace("[","").replace("]","").replace("\"","");
                                mail = jsonObject.getString("email").replace("[","").replace("]","").replace("\"","");
                                publications[i] = jsonObject.getString("publications").replace("[","").replace("]","").replace("\"","");
                                Log.d("Pub",jsonObject.getString("publications"));
                                    photo = "http://servis.mu.edu.tr/personelservis/Resim.aspx?mail="+jsonObject.getString("email").replace("[","").replace("]","").replace("\"","");
                                    Log.d("M",jsonObject.getString("email").replace("["," ").replace("]"," ").replace("\""," "));
                                    Log.d("M2","http://servis.mu.edu.tr/personelservis/Resim.aspx?mail="+jsonObject.getString("email").replace("[","").replace("]","").replace("\"",""));





                                //  department_names [i/2] = jsonArray.getString(i);
                                //  Log.d("Departments", department_names[i/2]);
                                //  Log.d("Type",department_names[0]);


                            /*adapter = new ArrayAdapter<String>(Companies.this, R.layout.list_item, li_tv, company_names);
                            lv.setAdapter(adapter);*/
                                //list.setAdapter(companiesCustomAdapter);

                                adapter = new ArrayAdapter<String>(InstructorPage.this,R.layout.list_item,R.id.li_tv,publications);
                                listView.setAdapter(adapter);
                                Glide.with(InstructorPage.this).load(photo)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imageView);
                                tvname.setText(instructor_name);
                                tvemail.setText(mail);
                            }








                            //  adapter = new ArrayAdapter<String>(Departments.this,R.layout.list_item,R.id.li_tv,department_names);
                            //  lv.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(InstructorPage.this,
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


    public String correctString(String str){
        str.replace("["," ").replace("]"," ").replace("\""," ");
        return str;
    }
    public String correctUrl(String url){
        url.replace(" ","%20").replace("\"","%22").replace("ü","%C3%BC").replace("ğ","%C4%9F").replace("İ","%C4%B0").replace("ş","%C5%9F");
        return url;
    }

    }

