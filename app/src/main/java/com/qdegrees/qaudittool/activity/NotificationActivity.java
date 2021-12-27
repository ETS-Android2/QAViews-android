package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.qdegrees.qaudittool.R;

public class NotificationActivity extends AppCompatActivity {

    TextView tvTextLabelFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setTitle("Notification");

        tvTextLabelFeedback = findViewById(R.id.tvTextLabelFeedback);

        //tvTextLabelFeedback.setTypeface(null, Typeface.BOLD);

        SpannableString content = new SpannableString(tvTextLabelFeedback.getText().toString());
        content.setSpan(new StyleSpan(Typeface.BOLD), 19, 37, 0);
        tvTextLabelFeedback.setText(content);



    }
}