package com.qdegrees.qaudittool.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;

public class PlanOfActionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvOverAll,tvBehaviourWise;

    LinearLayout linearLayoutOverAllPOA,linearLayoutBehaviourWisePOA,
            linearLayoutPlanOfAction,linearLayoutRaiseRebuttal;
    CheckBox checkBox1,checkBox2,checkBox23,checkBox13;
    TextInputLayout remarkLayout1,remarkLayout2,remarkLayout23,remarkLayout13;
    ProgressBar progressBar85,ProgressBar855,ProgressBar8553,progressBar8533;
    String sCheckValue;
    Button btnSave1,btnSave11;
    CardView cvRecordAudio1,cvRecordAudio11;

    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_of_action);

        mActivity = this;

        sCheckValue = getIntent().getStringExtra("valueCheck");

        btnSave1 = findViewById(R.id.btnSave1);
        cvRecordAudio1 = findViewById(R.id.cvRecordAudio1);

        btnSave11 = findViewById(R.id.btnSave11);
        cvRecordAudio11 = findViewById(R.id.cvRecordAudio11);

        linearLayoutOverAllPOA = findViewById(R.id.linearLayoutOverAllPOA);

        linearLayoutPlanOfAction = findViewById(R.id.linearLayoutPlanOfAction);
        linearLayoutRaiseRebuttal = findViewById(R.id.linearLayoutRaiseRebuttal);

        linearLayoutBehaviourWisePOA = findViewById(R.id.linearLayoutBehaviourWisePOA);

        tvOverAll = findViewById(R.id.tvOverAll);
        tvOverAll.setOnClickListener(this::onClick);

        tvBehaviourWise = findViewById(R.id.tvBehaviourWise);
        tvBehaviourWise.setOnClickListener(this::onClick);

        progressBar85 = findViewById(R.id.progressBar85);
        progressBar85.setProgress(85);

        ProgressBar855 = findViewById(R.id.ProgressBar855);
        ProgressBar855.setProgress(85);

        checkBox1 = findViewById(R.id.checkBox1);
        remarkLayout1 = findViewById(R.id.remarkLayout1);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox1.isChecked()) {
                    remarkLayout1.setVisibility(View.VISIBLE);
                }else {
                    remarkLayout1.setVisibility(View.GONE);
                }
            }
        });

        remarkLayout2 = findViewById(R.id.remarkLayout2);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox2.isChecked()) {
                    remarkLayout2.setVisibility(View.VISIBLE);
                }else {
                    remarkLayout2.setVisibility(View.GONE);
                }
            }
        });

        if (sCheckValue.equals("POA")) {
            setTitle("Plan Of Action");
            linearLayoutPlanOfAction.setVisibility(View.VISIBLE);
            linearLayoutRaiseRebuttal.setVisibility(View.GONE);
        }else {
            setTitle("Raise Rebuttal");
            linearLayoutPlanOfAction.setVisibility(View.GONE);
            linearLayoutRaiseRebuttal.setVisibility(View.VISIBLE);
        }

        ProgressBar8553 = findViewById(R.id.ProgressBar8553);
        ProgressBar8553.setProgress(85);
        remarkLayout23 = findViewById(R.id.remarkLayout23);
        checkBox23 = findViewById(R.id.checkBox23);
        checkBox23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox23.isChecked()) {
                    remarkLayout23.setVisibility(View.VISIBLE);
                    btnSave11.setVisibility(View.VISIBLE);
                    cvRecordAudio11.setVisibility(View.VISIBLE);
                }else {
                    remarkLayout23.setVisibility(View.GONE);
                    btnSave11.setVisibility(View.GONE);
                    cvRecordAudio11.setVisibility(View.GONE);
                }
            }
        });


        remarkLayout13 = findViewById(R.id.remarkLayout13);
        progressBar8533 = findViewById(R.id.progressBar8533);
        progressBar8533.setProgress(85);

        checkBox13 = findViewById(R.id.checkBox13);
        checkBox13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox13.isChecked()) {
                    remarkLayout13.setVisibility(View.VISIBLE);
                    btnSave1.setVisibility(View.VISIBLE);
                    cvRecordAudio1.setVisibility(View.VISIBLE);
                }else {
                    remarkLayout13.setVisibility(View.GONE);
                    btnSave1.setVisibility(View.GONE);
                    cvRecordAudio1.setVisibility(View.GONE);
                }
            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        if (view == tvOverAll) {
            linearLayoutOverAllPOA.setVisibility(View.VISIBLE);
            linearLayoutBehaviourWisePOA.setVisibility(View.GONE);


            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tvOverAll.setTextColor(getColor(R.color.grecus));
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view) );

                tvBehaviourWise.setTextColor(Color.BLACK);
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));

            } else {
                tvOverAll.setTextColor(getColor(R.color.grecus));
                tvOverAll.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view));

                tvBehaviourWise.setTextColor(Color.BLACK);
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));
            }



        }

        if (view == tvBehaviourWise) {

            linearLayoutOverAllPOA.setVisibility(View.GONE);
            linearLayoutBehaviourWisePOA.setVisibility(View.VISIBLE);

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tvBehaviourWise.setTextColor(getColor(R.color.grecus));
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view) );

                tvOverAll.setTextColor(Color.BLACK);
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));

            } else {
                tvBehaviourWise.setTextColor(getColor(R.color.grecus));
                tvBehaviourWise.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view));

                tvOverAll.setTextColor(Color.BLACK);
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));
            }
        }

    }
}