package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.activity.ProcessScoreActivity;


public class PlanOfActionAdapter extends RecyclerView.Adapter<PlanOfActionAdapter.MenuViewHolder> {

    Context context;
    String[] saParameterName,saSubParameterName,saScoreWithFatalPer,saScore,
            saParameterId,saSubParameterId;;
    String sAuditId;

    public PlanOfActionAdapter(Context context, String[] saParameterName, String[] saSubParameterName,
                               String[] saScoreWithFatalPer, String[] saScore,
                               String sAuditId,
                               String[] saParameterId, String[] saSubParameterId) {
        this.context = context;
        this.saParameterName = saParameterName;
        this.saSubParameterName = saSubParameterName;
        this.saScoreWithFatalPer = saScoreWithFatalPer;
        this.saScore = saScore;
        this.sAuditId = sAuditId;

        this.saParameterId = saParameterId;
        this.saSubParameterId = saSubParameterId;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_of_action_parameter_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sParameterName = saParameterName[position];
        final String sSubParameter = saSubParameterName[position];
        final String sScore = saScore[position];

        float valueInint = Float.valueOf(saScoreWithFatalPer[position]);
        viewHolder.progressBarNew85.setProgress((int) valueInint);
        viewHolder.tvScoreParameterNew.setText(saScoreWithFatalPer[position] + "%");


        viewHolder.tvParameterNameNew.setText(sParameterName);
        viewHolder.tvParameterNamePOA.setText(sSubParameter);

        viewHolder.tvScoreParameter.setText(sScore);


        viewHolder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (viewHolder.checkBox1.isChecked()) {
                    viewHolder.remarkLayoutNew1.setVisibility(View.VISIBLE);
                    viewHolder.btnSavePlanOfAction.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.remarkLayoutNew1.setVisibility(View.GONE);
                    viewHolder.btnSavePlanOfAction.setVisibility(View.GONE);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return saParameterName.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvParameterNameNew,tvParameterNamePOA,tvScoreParameterNew,tvScoreParameter;
        ProgressBar progressBarNew85;
        TextInputLayout remarkLayoutNew1;
        CheckBox checkBox1;
        Button btnSavePlanOfAction;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvParameterNameNew = itemView.findViewById(R.id.tvParameterNameNew);
            tvParameterNamePOA = itemView.findViewById(R.id.tvParameterNamePOA);
            progressBarNew85 = itemView.findViewById(R.id.progressBarNew85);
            tvScoreParameterNew = itemView.findViewById(R.id.tvScoreParameterNew);

            tvScoreParameter = itemView.findViewById(R.id.tvScoreParameter);

            remarkLayoutNew1 = itemView.findViewById(R.id.remarkLayoutNew1);
            checkBox1 = itemView.findViewById(R.id.checkBoxNew1);

            btnSavePlanOfAction = itemView.findViewById(R.id.btnSavePlanOfAction);

        }
    }
}