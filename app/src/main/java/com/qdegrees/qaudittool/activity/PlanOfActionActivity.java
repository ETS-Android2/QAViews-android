package com.qdegrees.qaudittool.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.qdegrees.qaudittool.adapter.PlanOfActionAdapter;
import com.qdegrees.qaudittool.adapter.RaiseRebuttalAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlanOfActionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvOverAll,tvBehaviourWise;

    LinearLayout linearLayoutOverAllPOA,linearLayoutBehaviourWisePOA,
            linearLayoutPlanOfAction,linearLayoutRaiseRebuttal;
    String sCheckValue,sAuditId;

    Activity mActivity;

    RecyclerView recycleViewPlanOfAction,recycleViewRebuttalRaise;
    ProgressDialog progressDialog;
    String[] saParameterName,saSubParameterName,saScoreWithFatalPer,saScore,
                saParameterId,saSubParameterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_of_action);

        mActivity = this;

        sCheckValue = getIntent().getStringExtra("valueCheck");
        sAuditId = getIntent().getStringExtra("sAuditId");

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        linearLayoutOverAllPOA = findViewById(R.id.linearLayoutOverAllPOA);

        linearLayoutPlanOfAction = findViewById(R.id.linearLayoutPlanOfAction);
        linearLayoutRaiseRebuttal = findViewById(R.id.linearLayoutRaiseRebuttal);

        linearLayoutBehaviourWisePOA = findViewById(R.id.linearLayoutBehaviourWisePOA);

        tvOverAll = findViewById(R.id.tvOverAll);
        tvOverAll.setOnClickListener(this::onClick);

        tvBehaviourWise = findViewById(R.id.tvBehaviourWise);
        tvBehaviourWise.setOnClickListener(this::onClick);

        if (sCheckValue.equals("POA")) {
            setTitle("Plan Of Action");
            linearLayoutPlanOfAction.setVisibility(View.VISIBLE);
            linearLayoutRaiseRebuttal.setVisibility(View.GONE);
        }else {
            setTitle("Raise Rebuttal");
            linearLayoutPlanOfAction.setVisibility(View.GONE);
            linearLayoutRaiseRebuttal.setVisibility(View.VISIBLE);
        }

        recycleViewPlanOfAction = findViewById(R.id.recycleViewPlanOfAction);
        recycleViewPlanOfAction.setHasFixedSize(true);
        recycleViewPlanOfAction.setLayoutManager(new LinearLayoutManager(mActivity));

        recycleViewRebuttalRaise = findViewById(R.id.recycleViewRebuttalRaise);
        recycleViewRebuttalRaise.setHasFixedSize(true);
        recycleViewRebuttalRaise.setLayoutManager(new LinearLayoutManager(mActivity));

        auditScoreParameterWise();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        if (view == tvOverAll) {
            linearLayoutOverAllPOA.setVisibility(View.VISIBLE);
            linearLayoutBehaviourWisePOA.setVisibility(View.GONE);


            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tvOverAll.setTextColor(getColor(R.color.grecus));
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view) );

                tvBehaviourWise.setTextColor(Color.BLACK);
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));

            } else {
                tvOverAll.setTextColor(getColor(R.color.grecus));
                tvOverAll.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view));

                tvBehaviourWise.setTextColor(Color.BLACK);
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));
            }



        }

        if (view == tvBehaviourWise) {

            linearLayoutOverAllPOA.setVisibility(View.GONE);
            linearLayoutBehaviourWisePOA.setVisibility(View.VISIBLE);

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tvBehaviourWise.setTextColor(getColor(R.color.grecus));
                tvBehaviourWise.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view) );

                tvOverAll.setTextColor(Color.BLACK);
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));

            } else {
                tvBehaviourWise.setTextColor(getColor(R.color.grecus));
                tvBehaviourWise.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view));

                tvOverAll.setTextColor(Color.BLACK);
                tvOverAll.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.background_corner_tab_view_not));
            }
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
                                    saScoreWithFatalPer = new String[array1.length()];
                                    saScore = new String[array1.length()];

                                    saParameterId = new String[array1.length()];
                                    saSubParameterId = new String[array1.length()];


                                    for (int i=0; i<array1.length(); i++) {
                                        JSONObject data = array1.getJSONObject(i);

                                        saParameterName[i] = data.getString("parameter");
                                        saSubParameterName[i] = data.getString("sub_parameter");
                                        saScoreWithFatalPer[i] = data.getString("with_fatal_score_per");
                                        saScore[i] = data.getString("score");

                                        saParameterId[i] = data.getString("paramter_id");
                                        saSubParameterId[i] = data.getString("subparameter_id");

                                    }

                                    recycleViewPlanOfAction.setAdapter(new PlanOfActionAdapter(
                                            mActivity, saParameterName, saSubParameterName,
                                            saScoreWithFatalPer, saScore, sAuditId,
                                            saParameterId,saSubParameterId
                                    ));

                                    recycleViewRebuttalRaise.setAdapter(new RaiseRebuttalAdapter(
                                            mActivity, saParameterName, saSubParameterName,
                                            saScoreWithFatalPer, saScore, sAuditId,
                                            saParameterId,saSubParameterId
                                    ));



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

}