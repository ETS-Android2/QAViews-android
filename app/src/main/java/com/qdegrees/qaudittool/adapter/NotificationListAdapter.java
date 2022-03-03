package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.activity.PlayAudioFileActivity;
import com.qdegrees.qaudittool.activity.ProcessScoreActivity;

import pl.droidsonroids.gif.GifImageView;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MenuViewHolder> {

    Context context;
    String[] saAuditId,saCallId,saProcessName,saWithoutFatalScore,saAgentFeedbackEmail;

    public NotificationListAdapter(Context context, String[] saAuditId, String[] saCallId,
                                   String[] saProcessName, String[] saWithoutFatalScore,
                                   String[] saAgentFeedbackEmail) {
        this.context = context;
        this.saAuditId = saAuditId;
        this.saCallId = saCallId;
        this.saProcessName = saProcessName;
        this.saWithoutFatalScore = saWithoutFatalScore;
        this.saAgentFeedbackEmail = saAgentFeedbackEmail;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sCallId = saCallId[position];
        final String sAuditId = saAuditId[position];
        final String sProcessName = saProcessName[position];
        final String sFeedbackFromEmail = saAgentFeedbackEmail[position];

        float valueInint = Float.valueOf(saWithoutFatalScore[position]);
        viewHolder.progressBarAddNew.setProgress((int) valueInint);
        viewHolder.tvWithoutFatalScoreNew.setText(saWithoutFatalScore[position] + "%");


        viewHolder.tvCallIDServerNew.setText(sCallId);
        viewHolder.tvAgentFeedbackEmailNew.setText("From " + sFeedbackFromEmail);
        viewHolder.tvProcessNameServerNew.setText(sProcessName);

        viewHolder.tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProcessScoreActivity.class);
                intent.putExtra("sAuditId",sAuditId);
                intent.putExtra("sWithoutFatalScore",saWithoutFatalScore[position]);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return saAuditId.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvCallIDServerNew,tvProcessNameServerNew,tvWithoutFatalScoreNew,tvAgentFeedbackEmailNew,
                tvViewMore;
        ProgressBar progressBarAddNew;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvCallIDServerNew = itemView.findViewById(R.id.tvCallIDServerNew);
            tvProcessNameServerNew = itemView.findViewById(R.id.tvProcessNameServerNew);
            progressBarAddNew = itemView.findViewById(R.id.progressBarAddNew);
            tvWithoutFatalScoreNew = itemView.findViewById(R.id.tvWithoutFatalScoreNew);

            tvAgentFeedbackEmailNew = itemView.findViewById(R.id.tvAgentFeedbackEmailNew);
            tvViewMore = itemView.findViewById(R.id.tvViewMore);
            //ivAddRemark = itemView.findViewById(R.id.ivAddRemark);*/

        }
    }
}