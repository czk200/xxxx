package com.example.ostatniaszansa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
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

public class MatchDetailActivity extends AppCompatActivity {
TextView mTeam1tv,mTeam2tv,mScoreTv,mDatetv;
    private String url= "https://api.myjson.com/bins/1bpft8";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Match Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        String team1= intent.getStringExtra("team1");
        String team2= intent.getStringExtra("team2");
        String team1score= intent.getStringExtra("team1s");
        String team2score= intent.getStringExtra("team2s");
        String matchstatus = intent.getStringExtra("status");
        String matchid= intent.getStringExtra("match_id");

        wczytwaniewydarzen(matchid);

        mTeam1tv = findViewById(R.id.team1tv);
        mTeam2tv=findViewById(R.id.team2tv);
        mScoreTv= findViewById(R.id.score);
        mDatetv = findViewById(R.id.matchstatus);

        mTeam2tv.setText(team2);
        mTeam1tv.setText(team1);
        mScoreTv.setText(team1+" "+team1score+" : "+team2score+" "+team2);
        mDatetv.setText(matchstatus);





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

private void wyswietlanie(String [] test){
    TextView textView = findViewById(R.id.gospodarze1);
    String wydarzenia="";

    for(int i=0;i<test.length;i++) {
        String [] tablicawydarzen = test[i].split(",");

        for(int j=0;j<3;j++){
            wydarzenia+=tablicawydarzen[j]+"\n";

        }
        wydarzenia+="\n";

    }
    textView.setText(wydarzenia);




}


    private void wczytwaniewydarzen(final String matchid) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");


                    for (int i=0; i<jsonArray.length(); i++){
                            JSONObject mecz1 = jsonArray.getJSONObject(i);
                            String maczid = mecz1.getString("match_id");
                            if(maczid.equals(matchid)){
                                JSONArray recs = mecz1.getJSONArray("goalscorer");
                        try{
                                String [] gospodarze = new String[recs.length()];
                            for(int j=0;j<recs.length();j++) {

                                JSONObject wynik = recs.getJSONObject(j);
                                String czas = wynik.getString("time");
                                String kto1 = wynik.getString("home_scorer");
                                String kto2 = wynik.getString("away_scorer");
                                String score = wynik.getString("score");
                                if (kto1.length() == 0) {
                                    gospodarze[j] = score + "," + czas + "'" + "," + kto2;

                                } else {
                                    gospodarze[j] = score + "," + czas + "'" + "," + kto1;
                                }
                            }wyswietlanie(gospodarze);






                        }catch (Exception e){

                        }}
                    }




                }catch(Exception e){ Toast.makeText(MatchDetailActivity.this,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchDetailActivity.this,"error"+error.toString(),Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}

