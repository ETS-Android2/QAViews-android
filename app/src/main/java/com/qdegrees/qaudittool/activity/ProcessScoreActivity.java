package com.qdegrees.qaudittool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.adapter.ParameterWiseScoreAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProcessScoreActivity extends AppCompatActivity implements View.OnClickListener{


    ProgressBar progress_bar,progress_bar2,progress_bar1,progressBarRed,progress_bar5,progress_bar15,
                progress_bar151,progress_bar115;

    RelativeLayout relativeLayoutAlertScore,RelativeLayoutGreenScore,RelativeLayoutRedScore,
                    relativeLayoutAlerRedScore;
    // Change Activity on Scroll Down
    LinearLayout linearLayoutForDownWord;
    SwipeListener swipeListener;

    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6;
    TextView tvAllScore,tvWithoutFatalScoreParameter;

    Activity mActivity;
    boolean isPass = false;



    EditText edtRebuttalRemark1;
    TextView tvRaiseRebuttal1,tvPlanOfAction1,tvAcceptFeedback1;

    int clickRebuttal1 = 0;
    ProgressDialog progressDialog;

    RecyclerView recyclerViewParameterWiseScore;
    String sAuditId,sWithoutFatalScore;
    String[] saParameterName,saSubParameterName,saFailType,saFailReason,saRemark,saScoreWithFatalPer,saScore,saIsCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_score);

        setTitle("Score");
        mActivity = this;

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        sAuditId = getIntent().getStringExtra("sAuditId");
        sWithoutFatalScore = getIntent().getStringExtra("sWithoutFatalScore");

        edtRebuttalRemark1 = findViewById(R.id.edtRebuttalRemark1);

        tvRaiseRebuttal1 = findViewById(R.id.tvRaiseRebuttal1);
        tvRaiseRebuttal1.setOnClickListener(this);

        tvPlanOfAction1 = findViewById(R.id.tvPlanOfAction1);
        tvPlanOfAction1.setOnClickListener(this);

        tvAcceptFeedback1 = findViewById(R.id.tvAcceptFeedback1);
        tvAcceptFeedback1.setOnClickListener(this);


        // Change activity on scroll Down
        linearLayoutForDownWord = findViewById(R.id.linearLayoutForDownWord);
        swipeListener = new SwipeListener(linearLayoutForDownWord);

        progress_bar2 = findViewById(R.id.progress_bar2);
        float valueInint = Float.valueOf(sWithoutFatalScore);
        progress_bar2.setProgress((int) valueInint);


        tvWithoutFatalScoreParameter = findViewById(R.id.tvWithoutFatalScoreParameter);
        tvWithoutFatalScoreParameter.setText(sWithoutFatalScore + "%");

        progressBarRed = findViewById(R.id.progressBarRed);
        progressBarRed.setProgress(20);


        recyclerViewParameterWiseScore = findViewById(R.id.recyclerViewParameterWiseScore);
        recyclerViewParameterWiseScore.setHasFixedSize(true);
        recyclerViewParameterWiseScore.setLayoutManager(new LinearLayoutManager(this));


        auditScoreParameterWise();

    }

    // Change Activity on Scroll Down
    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;

        SwipeListener(View view) {
            int threshold = 100;
            int velocity_threshold = 100;


            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();

                            try {

                                if (Math.abs(xDiff) < Math.abs(yDiff)) {
                                    if (Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold) {

                                        if (yDiff > 0) {
                                            onBackPressed();
                                            //tvSwipeGesture.setText("Swiped Down");
                                        }else {
                                            //tvSwipeGesture.setText("Swiped Up");
                                            //startActivity(new Intent(mActivity, ProcessScoreActivity.class));
                                        }
                                        return true;
                                    }
                                }

                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;

                        }
                    };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if (v == tvAllScore) {
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.VISIBLE);
            linearLayout4.setVisibility(View.VISIBLE);
            linearLayout5.setVisibility(View.VISIBLE);
            linearLayout6.setVisibility(View.VISIBLE);
        }

        if (v == RelativeLayoutRedScore) {
            linearLayout3.setVisibility(View.VISIBLE);

            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
            linearLayout4.setVisibility(View.GONE);
            linearLayout5.setVisibility(View.GONE);
            linearLayout6.setVisibility(View.GONE);
        }

        if (v == RelativeLayoutGreenScore) {
            linearLayout3.setVisibility(View.GONE);

            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout4.setVisibility(View.VISIBLE);
            linearLayout5.setVisibility(View.VISIBLE);
            linearLayout6.setVisibility(View.VISIBLE);
        }

        if (v == relativeLayoutAlertScore) {

            isPass = true;

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
            View mView = LayoutInflater.from(mActivity).inflate(R.layout.score_alert_view, null);
            mBuilder.setCancelable(false);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            TextView tvTitleBehaviour = mView.findViewById(R.id.tvTitleBehaviour);
            tvTitleBehaviour.setText("CUSTOMER SERVICE ESSENTIALS");

            TextView tvTitleParameter = mView.findViewById(R.id.tvTitleParameter);
            tvTitleParameter.setText("Initial warm and Farewell");

            TextView tvPassFail = mView.findViewById(R.id.tvPassFail);
            TextView tvPassFailScore = mView.findViewById(R.id.tvPassFailScore);

            LinearLayout linearLaoutFalMap = mView.findViewById(R.id.linearLaoutFalMap);
            LinearLayout linearFialMapData = mView.findViewById(R.id.linearFialMapData);
            TextInputLayout remarkLayout = mView.findViewById(R.id.remarkLayout);

            if (isPass) {
                linearLaoutFalMap.setVisibility(View.GONE);
                linearFialMapData.setVisibility(View.GONE);
                remarkLayout.setVisibility(View.GONE);

                tvPassFail.setText("Pass");
                tvPassFail.setTextColor(Color.parseColor("#4CAF50"));

                tvPassFailScore.setText("5");
            }


            // for Close Alert Dailog
            TextView tvCloseScoreView = mView.findViewById(R.id.tvCloseScoreView);
            tvCloseScoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            dialog.setCancelable(true);
        }



        if (v == relativeLayoutAlerRedScore) {
            isPass = false;

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
            View mView = LayoutInflater.from(mActivity).inflate(R.layout.score_alert_view, null);
            mBuilder.setCancelable(false);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            TextView tvTitleBehaviour = mView.findViewById(R.id.tvTitleBehaviour);
            tvTitleBehaviour.setText("CALL HANDLING SKILLS");

            TextView tvTitleParameter = mView.findViewById(R.id.tvTitleParameter);
            tvTitleParameter.setText("Hold Procedures & Conference Protocol");

            TextView tvPassFail = mView.findViewById(R.id.tvPassFail);
            TextView tvPassFailScore = mView.findViewById(R.id.tvPassFailScore);

            LinearLayout linearLaoutFalMap = mView.findViewById(R.id.linearLaoutFalMap);
            LinearLayout linearFialMapData = mView.findViewById(R.id.linearFialMapData);
            TextInputLayout remarkLayout = mView.findViewById(R.id.remarkLayout);

            if (!isPass) {
                linearLaoutFalMap.setVisibility(View.VISIBLE);
                linearFialMapData.setVisibility(View.VISIBLE);
                remarkLayout.setVisibility(View.VISIBLE);

                tvPassFail.setText("Fail");
                tvPassFail.setTextColor(Color.parseColor("#F44336"));

                tvPassFailScore.setText("0");
            }


            // for Close Alert Dailog
            TextView tvCloseScoreView = mView.findViewById(R.id.tvCloseScoreView);
            tvCloseScoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            dialog.setCancelable(true);
        }


        if (v == tvPlanOfAction1) {
            Intent intent = new Intent(mActivity, PlanOfActionActivity.class);
            intent.putExtra("valueCheck","POA");
            startActivity(intent);
        }

        if (v == tvRaiseRebuttal1){
            Intent intent = new Intent(mActivity, PlanOfActionActivity.class);
            intent.putExtra("valueCheck","Rebuttal");
            startActivity(intent);
        }

        if (v == tvAcceptFeedback1) {

            edtRebuttalRemark1.setVisibility(View.GONE);

            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(mActivity, DashboardRebuttalActivity.class));
                }
            },3000);
        }

    }


    private void auditScoreParameterWise() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.PARAMETER_WISE_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response",response);
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);

                            String sMsg = jsonObject.getString("message");
                            int  sStatus = jsonObject.getInt("status");

                            Log.e("serverMessage",sMsg);

                            if (sStatus != 200) {

                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();
                            }else {


                                JSONArray array1 = jsonObject.getJSONArray("data");

                                if (array1.length() == 0) {
                                    startActivity(new Intent(mActivity, DashboardRebuttalActivity.class));
                                }else {

                                    saParameterName = new String[array1.length()];
                                    saSubParameterName = new String[array1.length()];
                                    saFailType = new String[array1.length()];
                                    saFailReason = new String[array1.length()];
                                    saRemark = new String[array1.length()];
                                    saScoreWithFatalPer = new String[array1.length()];
                                    saScore = new String[array1.length()];
                                    saIsCritical = new String[array1.length()];


                                    for (int i=0; i<array1.length(); i++) {
                                        JSONObject data = array1.getJSONObject(i);

                                        saParameterName[i] = data.getString("parameter");
                                        saSubParameterName[i] = data.getString("sub_parameter");
                                        saFailType[i] = data.getString("reason_type_name");
                                        saFailReason[i] = data.getString("reason_name");
                                        saFailType[i] = data.getString("remarks");
                                        saScoreWithFatalPer[i] = data.getString("with_fatal_score_per");
                                        saScore[i] = data.getString("score");
                                        saIsCritical[i] = data.getString("is_critical");

                                    }

                                    recyclerViewParameterWiseScore.setAdapter(new ParameterWiseScoreAdapter(
                                            mActivity,saParameterName,saSubParameterName,saFailType,saFailReason,saRemark,
                                            saScoreWithFatalPer,saScore,saIsCritical
                                    ));

                                   // Toast.makeText(mActivity, saParameterName[0], Toast.LENGTH_SHORT).show();


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mActivity, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("audit_id", sAuditId);

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(mActivity, DashboardRebuttalActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}