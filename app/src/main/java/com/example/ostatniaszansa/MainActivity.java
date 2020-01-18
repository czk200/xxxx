package com.example.ostatniaszansa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String url= "https://api.myjson.com/bins/1bpft8";
    LocalDateTime localDate = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    String data = dtf.format(localDate);
    String tablica[] = new String[40];
    String Spotkania[]=new String[8];
    private RecyclerView.Adapter mAdapter;
    private List<Model> modelList;
    int dzien =-27;
    int runda=60;
    int miejsce =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();
        Button buttoncofnij = findViewById(R.id.button_cofnij);
        Button buttondodaj = findViewById(R.id.button_dodaj);

        kKolejka();




        buttondodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllItems();
                miejsce++;
                kKolejka();
            }
        });
        buttoncofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllItems();
                miejsce--;
                kKolejka();




            }
        });





    }

    private void loadUrlData(String [] args) {
       final int miejscewtabeli=(najblizej(Spotkania));
        TextView textView = findViewById(R.id.x);
        int a = miejscewtabeli+miejsce;
        textView.setText(Integer.toString(a));

       if((miejscewtabeli+miejsce)<0){
           miejsce++;
       }
        if((miejscewtabeli+miejsce)>=Spotkania.length-1){
            miejsce--;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


               try {

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject mecz = jsonArray.getJSONObject(i);
                        try{
                            String status = mecz.getString("match_date");
                            {
                            if(status.equals(Spotkania[miejscewtabeli+miejsce])) {
                                String id = mecz.getString("match_id");
                                String team1 = mecz.getString("match_hometeam_name");
                                String team2 = mecz.getString("match_awayteam_name");
                                String team1score = mecz.getString("match_hometeam_score");
                                String team2score = mecz.getString("match_awayteam_score");
                                String data = mecz.getString("match_date");
                                String goal = mecz.getString("goalscorer");
                                String matchstatus = mecz.getString("match_status");
                                if(matchstatus.equals("Finished")){
                                    matchstatus="Match Finished";
                                }else{
                                    matchstatus="Match not started";
                                }

                                Model model = new Model(id, team1, team2,data,team1score,team2score,matchstatus,goal);
                                modelList.add(model);
                            }}
                        }catch (Exception e){

                        }
                    }

                    mAdapter = new MyAdapter(modelList,getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);

                }catch(Exception e){ Toast.makeText(MainActivity.this,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"error"+error.toString(),Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void removeAllItems() {
        modelList.clear();
        mAdapter.notifyDataSetChanged();
    }

    private void kKolejka(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(".....");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject mecz = jsonArray.getJSONObject(i);
                        try{
                            String data = mecz.getString("match_date");
                            tablica[i]=data;
                        }catch (Exception e){

                        }
                    }
                    LinkedHashSet<String> duplikat=new LinkedHashSet<String>(Arrays.asList(tablica));
                    String [] newArray = duplikat.toArray(new String[duplikat.size()]);
                    Spotkania = newArray.clone();

                    loadUrlData(Spotkania);

                }catch(Exception e){ Toast.makeText(MainActivity.this,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"error"+error.toString(),Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private int najblizej(String [] test){
        {



            do {
                for (int i = 0; i < test.length - 1; i++)
                {String dzisiaj = (localDate.plusDays(dzien)).format(dtf);
                    if (test[i].equals(dzisiaj)) {
                        runda=i;
                    }
                }dzien-=1;
            }while(runda==60);
        }



        return runda;
                                        }






    }


















