package com.qdegrees.qaudittool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qdegrees.qaudittool.R;

public class DashboardRebuttalActivity extends AppCompatActivity {

    CardView cvProcess1;

    Activity mActivity;
    ProgressBar progressBarMyTeamScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_rebuttal);

        setTitle("Dashboard");
        mActivity = this;

        /*cvProcess1 = findViewById(R.id.cvProcess1);
        cvProcess1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardRebuttalActivity.this, ProcessScoreActivity.class));
            }
        });*/

        progressBarMyTeamScore = findViewById(R.id.progressBarMyTeamScore);
        progressBarMyTeamScore.setProgress(100);


        //showAlertDailogNotification();

    }

    private void showAlertDailogNotification() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
        View mView = LayoutInflater.from(mActivity).inflate(R.layout.notification_alert_view, null);
        mBuilder.setCancelable(false);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        Button btnCheckScore = mView.findViewById(R.id.btnCheckNow);
        btnCheckScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDailog();
                dialog.dismiss();
            }
        });

        TextView tvRemindLater = mView.findViewById(R.id.tvRemindLater);
        tvRemindLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        // for Close Alert Dailog
        TextView tvCloseNotificationView = mView.findViewById(R.id.tvCloseNotificationView);
        tvCloseNotificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void showAlertDailog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
        View mView = LayoutInflater.from(mActivity).inflate(R.layout.feedback_alert_view, null);
        mBuilder.setCancelable(false);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        Button btnCheckScore = mView.findViewById(R.id.btnCheckScore);
        btnCheckScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ProcessScoreActivity.class));
            }
        });

        ProgressBar progressBarRed = mView.findViewById(R.id.progressBarRed);
        progressBarRed.setProgress(20);

        ProgressBar progress_bar2 = mView.findViewById(R.id.progress_bar2);
        progress_bar2.setProgress(75);

        ProgressBar progress_bar150 = mView.findViewById(R.id.progress_bar150);
        progress_bar150.setProgress(50);

        TextView tvSendFeedback = mView.findViewById(R.id.tvSendFeedback);
        tvSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SendFeedbackActivity.class));
            }
        });

        // for Close Alert Dailog
        TextView tvCloseFeedbackView = mView.findViewById(R.id.tvCloseFeedbackView);
        tvCloseFeedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(mActivity, "Click Logout", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_notification:
                startActivity(new Intent(mActivity, NotificationActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}