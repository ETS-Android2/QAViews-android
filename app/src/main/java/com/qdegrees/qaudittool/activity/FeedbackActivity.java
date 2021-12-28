package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    SwipeListener swipeListener;
    SwipeListener1 swipeListener1;
    LinearLayout linearLayoutForSwipeView,linearLayoutForSwipeViewRight;
    Activity mActivity;
    ImageView gifShowSwipeUp,gifShowSwipeRight;

    ProgressBar progress_bar150New,progress_bar150Ne;

    EditText edtRebuttalRemark;
    TextView tvRaiseRebuttal,tvPlanOfAction,tvAcceptFeedback,tvAgentFeedbackEmail,tvAgentFeedbackText;
    ProgressDialog progressDialog;


    // For Server Data
    String[] saAuditId,saAgentFeedback,saAgentFeedbackRecording,saAgentFeedbackEmail,saCritical;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mActivity = this;
        setTitle("Feedback");
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        linearLayoutForSwipeView = findViewById(R.id.linearLayoutForSwipeView);
        linearLayoutForSwipeViewRight = findViewById(R.id.linearLayoutForSwipeViewRight);

        /*progress_bar150New = findViewById(R.id.progress_bar150New);
        progress_bar150New.setProgress((int) 85.55);

        progress_bar150Ne = findViewById(R.id.progress_bar150Ne);
        progress_bar150Ne.setProgress(85);*/


        swipeListener = new SwipeListener(linearLayoutForSwipeView);

        swipeListener1 = new SwipeListener1(linearLayoutForSwipeViewRight);

        edtRebuttalRemark = findViewById(R.id.edtRebuttalRemark);

        tvRaiseRebuttal = findViewById(R.id.tvRaiseRebuttal);
        tvRaiseRebuttal.setOnClickListener(this);

        tvPlanOfAction = findViewById(R.id.tvPlanOfAction);
        tvPlanOfAction.setOnClickListener(this);

        tvAcceptFeedback = findViewById(R.id.tvAcceptFeedback);
        tvAcceptFeedback.setOnClickListener(this);

        tvAgentFeedbackEmail = findViewById(R.id.tvAgentFeedbackEmail);
        tvAgentFeedbackText = findViewById(R.id.tvAgentFeedbackText);

        gifShowSwipeUp = findViewById(R.id.gifShowSwipeUp);
        Glide.with(mActivity).load(R.drawable.swipe_up).into(gifShowSwipeUp);

        gifShowSwipeRight = findViewById(R.id.gifShowSwipeRight);
        Glide.with(mActivity).load(R.drawable.right_swipe).into(gifShowSwipeRight);

        auditResponse();

    }

    @Override
    public void onClick(View view) {

        if (view == tvAcceptFeedback) {

            edtRebuttalRemark.setVisibility(View.GONE);

            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(mActivity, DashboardRebuttalActivity.class));
                }
            },3000);
        }

        if (view == tvPlanOfAction) {

            if (saCritical[position].equals("0")) {
                Toast.makeText(mActivity, "No Need Plan of Action", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(mActivity, PlanOfActionActivity.class);
                intent.putExtra("valueCheck","POA");
                startActivity(intent);
            }
        }

        if (view == tvRaiseRebuttal){


            if (saCritical[position].equals("0")) {
                Toast.makeText(mActivity, "No Need Raise Rebuttal", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(mActivity, PlanOfActionActivity.class);
                intent.putExtra("valueCheck","Rebuttal");
                startActivity(intent);
            }

        }

    }


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
                                            //tvSwipeGesture.setText("Swiped Down");
                                        }else {
                                            //tvSwipeGesture.setText("Swiped Up");
                                            Intent intent = new Intent(mActivity, ProcessScoreActivity.class);
                                            intent.putExtra("sAuditId",saAuditId[position]);
                                            startActivity(intent);
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

    private class SwipeListener1 implements View.OnTouchListener {

        GestureDetector gestureDetector;

        SwipeListener1(View view) {
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

                                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                                    if (Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold) {

                                        if (xDiff > 0) {


                                            position--;

                                            if (position > -1) {
                                                tvAgentFeedbackEmail.setText("From " + saAgentFeedbackEmail[position]);
                                                tvAgentFeedbackText.setText(saAgentFeedback[position]);
                                            }else {
                                                Toast.makeText(mActivity, "This is First Notification", Toast.LENGTH_SHORT).show();
                                            }

                                            //Toast.makeText(mActivity, "Swiped Right", Toast.LENGTH_SHORT).show();
                                        }else {

                                            position++;

                                            if (position < saAgentFeedback.length+1) {
                                                tvAgentFeedbackEmail.setText("From " + saAgentFeedbackEmail[position]);
                                                tvAgentFeedbackText.setText(saAgentFeedback[position]);
                                            }else {
                                                Toast.makeText(mActivity, "This is last Notification", Toast.LENGTH_SHORT).show();
                                            }

                                            //Toast.makeText(mActivity, "Swiped Left", Toast.LENGTH_SHORT).show();
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


    private void auditResponse() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.FEEDBACK_NOTIFICATION,
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

                                    position = 0;

                                    saAuditId = new String[array1.length()];
                                    saAgentFeedback = new String[array1.length()];
                                    saAgentFeedbackRecording = new String[array1.length()];
                                    saAgentFeedbackEmail = new String[array1.length()];
                                    saCritical = new String[array1.length()];

                                    for (int i=0; i<array1.length(); i++) {
                                        JSONObject data = array1.getJSONObject(i);

                                        saAuditId[i] = data.getString("audit_id");
                                        saAgentFeedback[i] = data.getString("agent_feedback");
                                        saAgentFeedbackRecording[i] = data.getString("feedback_to_agent_recording");
                                        saAgentFeedbackEmail[i] = data.getString("auditor_email");
                                        saCritical[i] = data.getString("is_critical");

                                        tvAgentFeedbackEmail.setText("From " + saAgentFeedbackEmail[position]);
                                        tvAgentFeedbackText.setText(saAgentFeedback[position]);

                                    }


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(mActivity, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("email", "imaculada@mamamoney.co.za");

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


}