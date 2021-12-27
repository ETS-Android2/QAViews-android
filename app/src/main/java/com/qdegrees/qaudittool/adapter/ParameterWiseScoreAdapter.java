package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.activity.PlayAudioFileActivity;

import pl.droidsonroids.gif.GifImageView;


public class ParameterWiseScoreAdapter extends RecyclerView.Adapter<ParameterWiseScoreAdapter.MenuViewHolder> {

    Context context;
    String[] saParameterName,saSubParameterName,saFailType,saFailReason,saRemark,saScoreWithFatalPer,saScore,
            saIsCritical;

    public ParameterWiseScoreAdapter(Context context, String[] saParameterName, String[] saSubParameterName,
                                     String[] saFailType, String[] saFailReason, String[] saRemark,
                                     String[] saScoreWithFatalPer, String[] saScore, String[] saIsCritical) {
        this.context = context;
        this.saParameterName = saParameterName;
        this.saSubParameterName = saSubParameterName;
        this.saFailType = saFailType;
        this.saFailReason = saFailReason;
        this.saRemark = saRemark;
        this.saScoreWithFatalPer = saScoreWithFatalPer;
        this.saScore = saScore;
        this.saIsCritical = saIsCritical;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.parameter_score_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sParameterName = saParameterName[position];
        final String sSubParameterName = saSubParameterName[position];
        final String sFailType = saFailType[position];
        final String sFailReason = saFailReason[position];
        final String sRemark = saRemark[position];
        final String sScoreWithFatalPer = saScoreWithFatalPer[position];
        final String sScore = saScore[position];
        final String sIsCritical = saIsCritical[position];

        float valueInint = Float.valueOf(sScoreWithFatalPer);

        if (sIsCritical.equals("0")) {
            viewHolder.relativeLayoutAlertScoreGreen.setVisibility(View.VISIBLE);
            viewHolder.relativeLayoutAlertScoreRed.setVisibility(View.GONE);

            viewHolder.tvParameterScorePer.setText(sScoreWithFatalPer + "%");

            viewHolder.progress_bar.setProgress((int) valueInint);

        }else {
            viewHolder.relativeLayoutAlertScoreGreen.setVisibility(View.GONE);
            viewHolder.relativeLayoutAlertScoreRed.setVisibility(View.VISIBLE);


            viewHolder.tvParameterScorePerRed.setText(sScoreWithFatalPer + "%");

            viewHolder.progress_barRed.setProgress((int) valueInint);
        }


        viewHolder.tvParameterName.setText(sParameterName);

        viewHolder.tvSubParameterName.setText(sSubParameterName);

        viewHolder.tvParameterScore.setText(sScore);


    }


    @Override
    public int getItemCount() {
        return saParameterName.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubParameterName,tvParameterName,tvParameterScore,tvParameterScorePer,
                tvParameterScorePerRed;
        ProgressBar progress_bar,progress_barRed;
        RelativeLayout relativeLayoutAlertScoreRed,relativeLayoutAlertScoreGreen;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvSubParameterName = itemView.findViewById(R.id.tvSubParameterName);
            tvParameterName = itemView.findViewById(R.id.tvParameterName);
            tvParameterScore = itemView.findViewById(R.id.tvParameterScore);
            tvParameterScorePer = itemView.findViewById(R.id.tvParameterScorePer);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            relativeLayoutAlertScoreRed = itemView.findViewById(R.id.relativeLayoutAlertScoreRed);
            relativeLayoutAlertScoreGreen = itemView.findViewById(R.id.relativeLayoutAlertScoreGreen);
            progress_barRed = itemView.findViewById(R.id.progress_barRed);

            tvParameterScorePerRed = itemView.findViewById(R.id.tvParameterScorePerRed);

        }
    }
}