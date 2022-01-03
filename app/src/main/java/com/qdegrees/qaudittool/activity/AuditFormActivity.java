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

    ImageView ivShowMoreViewAuditBasic,ivShowLessViewAuditBasic;
    LinearLayout linearLayoutLessViewAuditBasic,linearLayoutMoreViewAuditBasic,linearLayoutAuditBasicDetails;

    ImageView ivShowMoreViewAuditQRC,ivShowLessViewAuditQRC;
    LinearLayout linearLayoutLessViewAuditQRC,linearLayoutMoreViewAuditQRC,linearLayoutAuditQRCDetails;

    ImageView ivShowMoreViewAuditCall,ivShowLessViewAuditCall;
    LinearLayout linearLayoutLessViewAuditCall,linearLayoutMoreViewAuditCall,linearLayoutAuditCallDetails;



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




        ivShowLessViewAuditCall = findViewById(R.id.ivShowLessViewAuditCall);
        ivShowLessViewAuditCall.setOnClickListener(this::onClick);

        ivShowMoreViewAuditCall = findViewById(R.id.ivShowMoreViewAuditCall);
        ivShowMoreViewAuditCall.setOnClickListener(this::onClick);

        linearLayoutLessViewAuditCall = findViewById(R.id.linearLayoutLessViewAuditCall);
        linearLayoutMoreViewAuditCall = findViewById(R.id.linearLayoutMoreViewAuditCall);
        linearLayoutAuditQRCDetails = findViewById(R.id.linearLayoutAuditQRCDetails);

        ivShowLessViewAuditQRC = findViewById(R.id.ivShowLessViewAuditQRC);
        ivShowLessViewAuditQRC.setOnClickListener(this::onClick);

        ivShowMoreViewAuditQRC = findViewById(R.id.ivShowMoreViewAuditQRC);
        ivShowMoreViewAuditQRC.setOnClickListener(this::onClick);

        linearLayoutLessViewAuditQRC = findViewById(R.id.linearLayoutLessViewAuditQRC);
        linearLayoutMoreViewAuditQRC = findViewById(R.id.linearLayoutMoreViewAuditQRC);
        linearLayoutAuditCallDetails = findViewById(R.id.linearLayoutAuditCallDetails);


        ivShowLessViewAuditBasic = findViewById(R.id.ivShowLessViewAuditBasic);
        ivShowLessViewAuditBasic.setOnClickListener(this::onClick);

        ivShowMoreViewAuditBasic = findViewById(R.id.ivShowMoreViewAuditBasic);
        ivShowMoreViewAuditBasic.setOnClickListener(this::onClick);


        linearLayoutLessViewAuditBasic = findViewById(R.id.linearLayoutLessViewAuditBasic);
        linearLayoutMoreViewAuditBasic = findViewById(R.id.linearLayoutMoreViewAuditBasic);
        linearLayoutAuditBasicDetails = findViewById(R.id.linearLayoutAuditBasicDetails);


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

        if (view == ivShowLessViewAuditCall) {
            linearLayoutAuditCallDetails.setVisibility(View.GONE);
            linearLayoutLessViewAuditCall.setVisibility(View.GONE);
            linearLayoutMoreViewAuditCall.setVisibility(View.VISIBLE);

        }

        if (view == ivShowMoreViewAuditCall) {
            linearLayoutAuditCallDetails.setVisibility(View.VISIBLE);
            linearLayoutLessViewAuditCall.setVisibility(View.VISIBLE);
            linearLayoutMoreViewAuditCall.setVisibility(View.GONE);
        }

        if (view == ivShowLessViewAuditQRC) {
            linearLayoutAuditQRCDetails.setVisibility(View.GONE);
            linearLayoutLessViewAuditQRC.setVisibility(View.GONE);
            linearLayoutMoreViewAuditQRC.setVisibility(View.VISIBLE);

        }

        if (view == ivShowMoreViewAuditQRC) {
            linearLayoutAuditQRCDetails.setVisibility(View.VISIBLE);
            linearLayoutLessViewAuditQRC.setVisibility(View.VISIBLE);
            linearLayoutMoreViewAuditQRC.setVisibility(View.GONE);
        }

        if (view == ivShowLessViewAuditBasic) {
            linearLayoutAuditBasicDetails.setVisibility(View.GONE);
            linearLayoutLessViewAuditBasic.setVisibility(View.GONE);
            linearLayoutMoreViewAuditBasic.setVisibility(View.VISIBLE);

        }


        if (view == ivShowMoreViewAuditBasic) {
            linearLayoutAuditBasicDetails.setVisibility(View.VISIBLE);
            linearLayoutLessViewAuditBasic.setVisibility(View.VISIBLE);
            linearLayoutMoreViewAuditBasic.setVisibility(View.GONE);
        }

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