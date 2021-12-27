package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;

public class AuditFormActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivExpandView,ivMinimizeView;
    LinearLayout linearLayoutParameterMini,linearLayoutParameterExpand,linearLayoutParameterFields;

    ImageView ivShowMoreView,ivShowLessView;
    LinearLayout linearLayoutLessView,linearLayoutMoreView,linearDetailsAuditIDField;

    AutoCompleteTextView atvObservation,atvObservation1;
    ImageView ivAllTheBest,ivBadLuck,ivAllTheBest1,ivBadLuck1;
    Activity mActivity;

    TextInputLayout textInputLayoutFailType,textInputLayoutFailReason,
                    textInputLayoutFailType1,textInputLayoutFailReason1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_form);

        setTitle("Audit Form");
        mActivity = this;


        ivShowLessView = findViewById(R.id.ivShowLessView);
        ivShowLessView.setOnClickListener(this::onClick);

        ivShowMoreView = findViewById(R.id.ivShowMoreView);
        ivShowMoreView.setOnClickListener(this::onClick);


        linearLayoutLessView = findViewById(R.id.linearLayoutLessView);
        linearLayoutMoreView = findViewById(R.id.linearLayoutMoreView);
        linearDetailsAuditIDField = findViewById(R.id.linearDetailsAuditIDField);


        ivAllTheBest = findViewById(R.id.ivAllTheBest);
        ivBadLuck = findViewById(R.id.ivBadLuck);

        ivAllTheBest1 = findViewById(R.id.ivAllTheBest1);
        ivBadLuck1 = findViewById(R.id.ivBadLuck1);

        textInputLayoutFailType = findViewById(R.id.textInputLayoutFailType);
        textInputLayoutFailReason = findViewById(R.id.textInputLayoutFailReason);

        textInputLayoutFailType1 = findViewById(R.id.textInputLayoutFailType1);
        textInputLayoutFailReason1 = findViewById(R.id.textInputLayoutFailReason1);

        atvObservation = findViewById(R.id.atvObservation);
        String[] saObservation = {"Pass","Fail","N/A"};
        ArrayAdapter adapterObservation = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saObservation);
        atvObservation.setAdapter(adapterObservation);
        atvObservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ivAllTheBest.setVisibility(View.VISIBLE);
                    ivBadLuck.setVisibility(View.GONE);

                    textInputLayoutFailType.setVisibility(View.GONE);
                    textInputLayoutFailReason.setVisibility(View.GONE);
                }else if (i == 1) {
                    ivAllTheBest.setVisibility(View.GONE);
                    ivBadLuck.setVisibility(View.VISIBLE);

                    textInputLayoutFailType.setVisibility(View.VISIBLE);
                    textInputLayoutFailReason.setVisibility(View.VISIBLE);
                }else {
                    ivAllTheBest.setVisibility(View.GONE);
                    ivBadLuck.setVisibility(View.GONE);

                    textInputLayoutFailType.setVisibility(View.GONE);
                    textInputLayoutFailReason.setVisibility(View.GONE);
                }
            }
        });



        atvObservation1 = findViewById(R.id.atvObservation1);
        String[] saObservation1 = {"Pass","Fail","N/A"};
        ArrayAdapter adapterObservation1 = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saObservation1);
        atvObservation1.setAdapter(adapterObservation1);
        atvObservation1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ivAllTheBest1.setVisibility(View.VISIBLE);
                    ivBadLuck1.setVisibility(View.GONE);

                    textInputLayoutFailType1.setVisibility(View.GONE);
                    textInputLayoutFailReason1.setVisibility(View.GONE);
                }else if (i == 1) {
                    ivAllTheBest1.setVisibility(View.GONE);
                    ivBadLuck1.setVisibility(View.VISIBLE);

                    textInputLayoutFailType1.setVisibility(View.VISIBLE);
                    textInputLayoutFailReason1.setVisibility(View.VISIBLE);
                }else {
                    ivAllTheBest1.setVisibility(View.GONE);
                    ivBadLuck1.setVisibility(View.GONE);

                    textInputLayoutFailType1.setVisibility(View.GONE);
                    textInputLayoutFailReason1.setVisibility(View.GONE);
                }
            }
        });








        ivExpandView = findViewById(R.id.ivExpandView);
        ivExpandView.setOnClickListener(this::onClick);

        ivMinimizeView = findViewById(R.id.ivMinimizeView);
        ivMinimizeView.setOnClickListener(this::onClick);

        linearLayoutParameterExpand = findViewById(R.id.linearLayoutParameterExpand);
        linearLayoutParameterMini = findViewById(R.id.linearLayoutParameterMini);
        linearLayoutParameterFields = findViewById(R.id.linearLayoutParameterFields);

    }

    @Override
    public void onClick(View view) {

        if (view == ivShowLessView) {
            linearDetailsAuditIDField.setVisibility(View.GONE);
            linearLayoutLessView.setVisibility(View.GONE);
            linearLayoutMoreView.setVisibility(View.VISIBLE);

        }


        if (view == ivShowMoreView) {
            linearDetailsAuditIDField.setVisibility(View.VISIBLE);
            linearLayoutLessView.setVisibility(View.VISIBLE);
            linearLayoutMoreView.setVisibility(View.GONE);
        }


        if (view == ivExpandView) {

            linearLayoutParameterExpand.setVisibility(View.GONE);

            linearLayoutParameterMini.setVisibility(View.VISIBLE);
            linearLayoutParameterFields.setVisibility(View.VISIBLE);

        }
        if (view == ivMinimizeView) {
            linearLayoutParameterExpand.setVisibility(View.VISIBLE);

            linearLayoutParameterMini.setVisibility(View.GONE);
            linearLayoutParameterFields.setVisibility(View.GONE);
        }
    }
}