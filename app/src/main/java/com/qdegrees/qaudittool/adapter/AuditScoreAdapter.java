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


public class AuditScoreAdapter extends RecyclerView.Adapter<AuditScoreAdapter.MenuViewHolder> {

    Context context;
    String[] saParameterName;

    public AuditScoreAdapter(Context context, String[] saParameterName) {
        this.context = context;
        this.saParameterName = saParameterName;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.audit_score_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sParameterName = saParameterName[position];


        viewHolder.tvBehaviourName.setText(sParameterName);


    }


    @Override
    public int getItemCount() {
        return saParameterName.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvBehaviourName;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvBehaviourName = itemView.findViewById(R.id.tvBehaviourName);


        }
    }
}