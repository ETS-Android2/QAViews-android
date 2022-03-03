package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.adapter.NotificationListAdapter;
import com.qdegrees.qaudittool.adapter.SubmittedAuditListAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmittedAuditListActivity extends AppCompatActivity {

    RecyclerView recycleViewSubmittedAuditList;

    Activity mActivity;

    String[] saAuditDate,saAgentName,saWithFatalScore,saWithoutFatalScore;
    ProgressDialog progressDialog;
    String sUserId,sActivityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_audit_list);

        sActivityValue = getIntent().getStringExtra("sActivityValue");

        mActivity = this;
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        sUserId = SharedPrefManager.getInstance(mActivity).getUserId();


        recycleViewSubmittedAuditList = findViewById(R.id.recycleViewSubmittedAuditList);
        recycleViewSubmittedAuditList.setHasFixedSize(true);
        recycleViewSubmittedAuditList.setLayoutManager(new LinearLayoutManager(mActivity));


        if (sActivityValue.equals("submit")) {
            setTitle("Submited Audit List");
            allSubmittedAuditList();
        }else if (sActivityValue.equals("rebuttal")) {
            setTitle("Rebuttal Raise List");
        }
    }




    private void allSubmittedAuditList() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.AUDITOR_SUBMITTED_AUDIT_LIST
                + "?user_id=" + sUserId ,
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

                            if (sStatus != 1) {

                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();

                                JSONArray array1 = jsonObject.getJSONArray("data");

                                if (array1.length() == 0) {
                                    Toast.makeText(mActivity, "No Data Found", Toast.LENGTH_SHORT).show();
                                }else {

                                    saAuditDate = new String[array1.length()];
                                    saAgentName = new String[array1.length()];
                                    saWithFatalScore = new String[array1.length()];
                                    saWithoutFatalScore = new String[array1.length()];

                                    for (int i=0; i<array1.length(); i++) {
                                        JSONObject data = array1.getJSONObject(i);

                                        saAuditDate[i] = data.getString("audit_date");
                                        //saAgentName[i] = "Digvijay Singh";
                                        saWithFatalScore[i] = data.getString("with_fatal_score_per");
                                        saWithoutFatalScore[i] = data.getString("overall_score");

                                        JSONObject rawData = data.getJSONObject("raw_data");

                                        saAgentName[i] = rawData.getString("agent_name");

                                        recycleViewSubmittedAuditList.setAdapter(new SubmittedAuditListAdapter(
                                                mActivity,saAuditDate,saAgentName,saWithFatalScore,saWithoutFatalScore
                                        ));

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
                //params.put("email", sEmail);

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }



}