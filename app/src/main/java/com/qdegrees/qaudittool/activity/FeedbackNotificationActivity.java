package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.adapter.NotificationListAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackNotificationActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Activity mActivity;
    String sEmail;

    // For Server Data
    String[] saAuditId,saAgentFeedback,saAgentFeedbackRecording,saAgentFeedbackEmail,saCritical,
            saCallId,saProcessName,saCusName,saCusNumber,saWithoutFatalScore;

    RecyclerView recycleVewNotificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_notification);

        mActivity = this;

        setTitle("Feedback Notification");

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Data...");

        sEmail = SharedPrefManager.getInstance(mActivity).getUserEmail();


        recycleVewNotificationList = findViewById(R.id.recycleVewNotificationList);
        recycleVewNotificationList.setLayoutManager(new LinearLayoutManager(mActivity));
        recycleVewNotificationList.setHasFixedSize(true);

        allNotificationAuditList();
    }


    private void allNotificationAuditList() {

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

                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();

                                JSONArray array1 = jsonObject.getJSONArray("data");

                                if (array1.length() == 0) {
                                    startActivity(new Intent(mActivity, DashboardRebuttalActivity.class));
                                }else {

                                    saAuditId = new String[array1.length()];
                                    saAgentFeedback = new String[array1.length()];
                                    saAgentFeedbackRecording = new String[array1.length()];
                                    saAgentFeedbackEmail = new String[array1.length()];
                                    saCritical = new String[array1.length()];

                                    saCallId = new String[array1.length()];
                                    saProcessName = new String[array1.length()];
                                    saCusName = new String[array1.length()];
                                    saCusNumber = new String[array1.length()];
                                    saWithoutFatalScore = new String[array1.length()];

                                    for (int i=0; i<array1.length(); i++) {
                                        JSONObject data = array1.getJSONObject(i);

                                        saAuditId[i] = data.getString("audit_id");
                                        saAgentFeedback[i] = data.getString("agent_feedback");
                                        saAgentFeedbackRecording[i] = data.getString("feedback_to_agent_recording");
                                        saAgentFeedbackEmail[i] = data.getString("auditor_email");
                                        saCritical[i] = data.getString("is_critical");

                                        saCallId[i] = data.getString("call_id");
                                        saProcessName[i] = data.getString("process_name");
                                        saCusName[i] = data.getString("customer_name");
                                        saCusNumber[i] = data.getString("phone_number");
                                        saWithoutFatalScore[i] = data.getString("without_fatal");

                                        recycleVewNotificationList.setAdapter(new NotificationListAdapter(
                                                mActivity,saAuditId,saCallId,saProcessName,saWithoutFatalScore,saAgentFeedbackEmail
                                        ));

                                        /*tvAgentFeedbackEmail.setText("From " + saAgentFeedbackEmail[position]);
                                        tvAgentFeedbackText.setText(saAgentFeedback[position]);

                                        tvCallIDServer.setText(saCallId[position]);
                                        tvProcessNameServer.setText(saProcessName[position]);
                                        tvCusNameServer.setText(saCusName[position]);
                                        tvCusNumberServer.setText(saCusNumber[position]);

                                        float valueInint = Float.valueOf(saWithoutFatalScore[position]);

                                        progress_barFatalScore.setProgress((int) valueInint);
                                        tvWithoutFatalScore.setText(saWithoutFatalScore[position] + "%");*/

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
                params.put("email", sEmail);

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }

}