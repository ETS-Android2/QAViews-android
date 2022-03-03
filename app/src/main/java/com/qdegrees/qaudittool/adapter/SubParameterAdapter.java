package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.activity.ProcessScoreActivity;


public class SubParameterAdapter extends RecyclerView.Adapter<SubParameterAdapter.MenuViewHolder> {

    Context context;
    String[] saSubParameterName;

    public SubParameterAdapter(Context context, String[] saSubParameterName) {
        this.context = context;
        this.saSubParameterName = saSubParameterName;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sub_parameter_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        String sSubParameterName = saSubParameterName[position];



        viewHolder.tvSubParameterName1.setText(sSubParameterName);



    }


    @Override
    public int getItemCount() {
        return saSubParameterName.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubParameterName1,tvProcessNameServerNew,tvWithoutFatalScoreNew,tvAgentFeedbackEmailNew,
                tvViewMore;
        ProgressBar progressBarAddNew;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvSubParameterName1 = itemView.findViewById(R.id.tvSubParameterName1);


        }
    }
}