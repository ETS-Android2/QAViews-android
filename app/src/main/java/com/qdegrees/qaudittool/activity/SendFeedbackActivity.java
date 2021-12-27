package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.qdegrees.qaudittool.R;

public class SendFeedbackActivity extends AppCompatActivity {

    Activity mActivity;

    AutoCompleteTextView atvSelectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        setTitle("Feedback");
        mActivity = this;


        atvSelectType = findViewById(R.id.atvSelectType);
        String[] saValue = {"Accepted","Rejected","Acknowledgement","Working","Work Later","Bad Requrest"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saValue);
        atvSelectType.setAdapter(adapter);

    }
}