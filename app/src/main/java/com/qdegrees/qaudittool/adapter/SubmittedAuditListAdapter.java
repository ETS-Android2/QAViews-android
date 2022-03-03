package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qdegrees.qaudittool.R;


public class SubmittedAuditListAdapter extends RecyclerView.Adapter<SubmittedAuditListAdapter.MenuViewHolder> {

    Context context;
    String[] saAuditDate,saAgentName,saWithFatalScore,saWithoutFatalScore;

    public SubmittedAuditListAdapter(Context context, String[] saAuditDate, String[] saAgentName,
                                     String[] saWithFatalScore, String[] saWithoutFatalScore) {
        this.context = context;
        this.saAuditDate = saAuditDate;
        this.saAgentName = saAgentName;
        this.saWithFatalScore = saWithFatalScore;
        this.saWithoutFatalScore = saWithoutFatalScore;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.submitted_audit_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sAuditDate = saAuditDate[position];
        final String sAgentName = saAgentName[position];
        final String sWithoutFatalScore = saWithoutFatalScore[position];
        final String sScoreWithFatalPer = saWithFatalScore[position];

        float valueInintWithFatalScore = Float.valueOf(sScoreWithFatalPer);
        viewHolder.tvWithFatalScoreSubmitted.setText(sScoreWithFatalPer + "%");
        viewHolder.progressBarWithFatalScoreSubmitted.setProgress((int) valueInintWithFatalScore);



        float valueInintWithoutFatalScore = Float.valueOf(sWithoutFatalScore);
        viewHolder.tvWithoutFatalScoreSubmitted.setText(sWithoutFatalScore + "%");
        viewHolder.progressBarWithoutFatalScoreSubmitted.setProgress((int) valueInintWithoutFatalScore);




        viewHolder.tvAgentNameSubmitted.setText(sAgentName);
        viewHolder.tvAuditDateSubmitted.setText(sAuditDate);



    }


    @Override
    public int getItemCount() {
        return saAuditDate.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuditDateSubmitted,tvAgentNameSubmitted,
                tvWithFatalScoreSubmitted,tvWithoutFatalScoreSubmitted;

        ProgressBar progressBarWithFatalScoreSubmitted,progressBarWithoutFatalScoreSubmitted;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvAuditDateSubmitted = itemView.findViewById(R.id.tvAuditDateSubmitted);
            tvAgentNameSubmitted = itemView.findViewById(R.id.tvAgentNameSubmitted);


            progressBarWithFatalScoreSubmitted = itemView.findViewById(R.id.progressBarWithFatalScoreSubmitted);
            tvWithFatalScoreSubmitted = itemView.findViewById(R.id.tvWithFatalScoreSubmitted);



            progressBarWithoutFatalScoreSubmitted = itemView.findViewById(R.id.progressBarWithoutFatalScoreSubmitted);
            tvWithoutFatalScoreSubmitted = itemView.findViewById(R.id.tvWithoutFatalScoreSubmitted);


        }
    }
}