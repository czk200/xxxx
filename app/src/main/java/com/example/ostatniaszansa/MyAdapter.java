package com.example.ostatniaszansa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Model> modelList;
    private Context context;

    public MyAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model model = modelList.get(position);
        holder.team1tv.setText(model.getTeam1());
        holder.team2tv.setText(model.getTeam2());
        holder.datetv.setText(model.getDate());
        holder.matchstatus.setText(model.getMatchstatus());




        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchId=model.getId();
                String status = model.getMatchstatus();
                String t1=model.getTeam1();
                String t2=model.getTeam2();
                String t1score =model.getteam1score();
                String t2score =model.getteam2score();
                String goal = model.getGoal();


                if(status.equals("Match Finished")) {
                    Intent intent = new Intent(context, MatchDetailActivity.class);
                    intent.putExtra("match_id", matchId);
                    intent.putExtra("status", status);
                    intent.putExtra("team1", t1);
                    intent.putExtra("team2", t2);
                    intent.putExtra("team1s", t1score);
                    intent.putExtra("team2s", t2score);
                    intent.putExtra("team2s", t2score);
                    intent.putExtra("goalscore", goal);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }





            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView team1tv, team2tv,datetv,matchstatus;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team1tv= itemView.findViewById(R.id.team1tv);
            team2tv =itemView.findViewById(R.id.team2tv);
            datetv = itemView.findViewById(R.id.datetv);
            matchstatus = itemView.findViewById(R.id.matchstatus);

            cardView= itemView.findViewById(R.id.cardView);

        }
    }
}
