package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdegrees.qaudittool.R;

public class CallListeningActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvFilter;
    ImageView ivPlayAudioFIle;

    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_listening);

        setTitle("Recording Calls");

        mActivity = this;

        tvFilter = findViewById(R.id.tvFilter);
        tvFilter.setOnClickListener(this);

        ivPlayAudioFIle = findViewById(R.id.ivPlayAudioFIle);
        ivPlayAudioFIle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == ivPlayAudioFIle) {
            startActivity(new Intent(mActivity, PlayAudioFileActivity.class));
        }

        if (v == tvFilter) {
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
            View mView = LayoutInflater.from(this).inflate(R.layout.filter_alert_view, null);
            mBuilder.setCancelable(false);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            AutoCompleteTextView atvSheetType = mView.findViewById(R.id.atvProcessType);
            String[] saSheetType = {"Select Sheet","Sheet 1","Sheet 2","Sheet 3"};
            ArrayAdapter adapterSheetType = new ArrayAdapter(this,
                    R.layout.support_simple_spinner_dropdown_item,saSheetType);
            atvSheetType.setAdapter(adapterSheetType);

            // for Close Alert Dailog
            TextView tvCloseFilterView = mView.findViewById(R.id.tvCloseFilterView);
            tvCloseFilterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            // For apply
            Button btnApplyFilter = mView.findViewById(R.id.btnApplyFilter);
            btnApplyFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*sAddress = edtAddress.getText().toString();
                    sArea = edtArea.getText().toString();
                    sCity = edtCity.getText().toString();
                    sNumber = edtNumber.getText().toString();
                    swhatsApp = edtWhatsApp.getText().toString();
                    sFacebook = edtFacebook.getText().toString();
                    sTwitter = edtTwitter.getText().toString();
                    sInstagram = edtInsta.getText().toString();
                    sAbout = edtAbout.getText().toString();
                    sService = edtService.getText().toString();*/


                    dialog.dismiss();

                }
            });

            dialog.show();
            dialog.setCancelable(true);
        }

    }
}