package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.adapter.AuditScoreAdapter;
import com.qdegrees.qaudittool.adapter.SubParameterAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuditFormActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivExpandView,ivMinimizeView;
    LinearLayout linearLayoutParameterMini,linearLayoutParameterExpand,linearLayoutParameterFields;

    ImageView ivShowMoreView,ivShowLessView;
    LinearLayout linearLayoutLessView,linearLayoutMoreView,linearDetailsAuditIDField;

    ImageView ivShowMoreViewAuditBasic,ivShowLessViewAuditBasic;
    LinearLayout linearLayoutLessViewAuditBasic,linearLayoutMoreViewAuditBasic,linearLayoutAuditBasicDetails;



    ImageView ivShowMoreViewOverall,ivShowLessViewOverall;
    LinearLayout linearLayoutLessViewOverAll,linearLayoutMoreViewOvelAll,linearLayoutOverAllDetails;


    ImageView ivShowLessViewRCAType,ivShowMoreViewRCAType;
    LinearLayout linearLayoutLessViewRCAType,linearLayoutMoreViewRCAType,linearLayoutRCATypeDetails;


    ImageView ivShowMoreViewAuditQRC,ivShowLessViewAuditQRC;
    LinearLayout linearLayoutLessViewAuditQRC,linearLayoutMoreViewAuditQRC,linearLayoutAuditQRCDetails;

    ImageView ivShowMoreViewAuditCall,ivShowLessViewAuditCall;
    LinearLayout linearLayoutLessViewAuditCall,linearLayoutMoreViewAuditCall,linearLayoutAuditCallDetails;


    ImageView ivShowMoreViewAuditScore,ivShowLessViewAuditScore;
    LinearLayout linearLayoutLessViewAuditScore,linearLayoutMoreViewAuditScore,linearLayoutAuditScore;



    AutoCompleteTextView atvObservation,atvObservation1,atvParameter;
    ImageView ivAllTheBest,ivBadLuck,ivAllTheBest1,ivBadLuck1;
    Activity mActivity;

    TextInputLayout textInputLayoutFailType,textInputLayoutFailReason;
    TextInputEditText textInputEditTextClientName,textInputEditTextQAName,textInputEditTextAuditDate,
            textInputEditTextSheetVersion;

    String sQMSheetId,sUserId;
    String[] saParameterName,saSubParameter,saParameterId,saParameterIdSubSection;
    ProgressDialog progressDialog;

    RecyclerView recycleViewSubParameter,recycleViewAuditScore;

    ArrayList<String> allCollectedSubParameter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_form);

        setTitle("Audit Sheet");
        mActivity = this;

        sQMSheetId = getIntent().getStringExtra("sQMSheetId");
        sUserId = SharedPrefManager.getInstance(mActivity).getUserId();
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


        recycleViewSubParameter = findViewById(R.id.recycleViewSubParameter);
        recycleViewSubParameter.setHasFixedSize(true);
        recycleViewSubParameter.setLayoutManager(new LinearLayoutManager(mActivity));


        recycleViewAuditScore = findViewById(R.id.recycleViewAuditScore);
        recycleViewAuditScore.setHasFixedSize(true);
        recycleViewAuditScore.setLayoutManager(new LinearLayoutManager(mActivity));


        textInputEditTextClientName = findViewById(R.id.textInputEditTextClientName);
        textInputEditTextAuditDate = findViewById(R.id.textInputEditTextAuditDate);
        textInputEditTextSheetVersion = findViewById(R.id.textInputEditTextSheetVersion);
        textInputEditTextQAName = findViewById(R.id.textInputEditTextQAName);
        textInputEditTextQAName.setText(SharedPrefManager.getInstance(mActivity).getUserName());


        atvParameter = findViewById(R.id.atvParameter);


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


        ivShowLessViewOverall = findViewById(R.id.ivShowLessViewOverall);
        ivShowLessViewOverall.setOnClickListener(this::onClick);

        ivShowMoreViewOverall = findViewById(R.id.ivShowMoreViewOverall);
        ivShowMoreViewOverall.setOnClickListener(this::onClick);

        linearLayoutLessViewOverAll = findViewById(R.id.linearLayoutLessViewOverAll);
        linearLayoutMoreViewOvelAll = findViewById(R.id.linearLayoutMoreViewOvelAll);
        linearLayoutOverAllDetails = findViewById(R.id.linearLayoutOverAllDetails);

        ivShowLessViewRCAType = findViewById(R.id.ivShowLessViewRCAType);
        ivShowLessViewRCAType.setOnClickListener(this::onClick);

        ivShowMoreViewRCAType = findViewById(R.id.ivShowMoreViewRCAType);
        ivShowMoreViewRCAType.setOnClickListener(this::onClick);

        linearLayoutLessViewRCAType = findViewById(R.id.linearLayoutLessViewRCAType);
        linearLayoutMoreViewRCAType = findViewById(R.id.linearLayoutMoreViewRCAType);
        linearLayoutRCATypeDetails = findViewById(R.id.linearLayoutRCATypeDetails);







        ivShowMoreViewAuditScore = findViewById(R.id.ivShowMoreViewAuditScore);
        ivShowMoreViewAuditScore.setOnClickListener(this::onClick);

        ivShowLessViewAuditScore = findViewById(R.id.ivShowLessViewAuditScore);
        ivShowLessViewAuditScore.setOnClickListener(this::onClick);

        linearLayoutMoreViewAuditScore = findViewById(R.id.linearLayoutMoreViewAuditScore);
        linearLayoutLessViewAuditScore = findViewById(R.id.linearLayoutLessViewAuditScore);
        linearLayoutAuditScore = findViewById(R.id.linearLayoutAuditScore);









        ivShowLessView = findViewById(R.id.ivShowLessView);
        ivShowLessView.setOnClickListener(this::onClick);

        ivShowMoreView = findViewById(R.id.ivShowMoreView);
        ivShowMoreView.setOnClickListener(this::onClick);


        linearLayoutLessView = findViewById(R.id.linearLayoutLessView);
        linearLayoutMoreView = findViewById(R.id.linearLayoutMoreView);
        linearDetailsAuditIDField = findViewById(R.id.linearDetailsAuditIDField);


        ivAllTheBest = findViewById(R.id.ivAllTheBest);
        ivBadLuck = findViewById(R.id.ivBadLuck);
/*
        ivAllTheBest1 = findViewById(R.id.ivAllTheBest1);
        ivBadLuck1 = findViewById(R.id.ivBadLuck1);*/

        textInputLayoutFailType = findViewById(R.id.textInputLayoutFailType);
        textInputLayoutFailReason = findViewById(R.id.textInputLayoutFailReason);
/*
        textInputLayoutFailType1 = findViewById(R.id.textInputLayoutFailType1);
        textInputLayoutFailReason1 = findViewById(R.id.textInputLayoutFailReason1);*/

        /*atvObservation = findViewById(R.id.atvObservation);
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
        });*/



        /*atvObservation1 = findViewById(R.id.atvObservation1);
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
        });*/



        ivExpandView = findViewById(R.id.ivExpandView);
        ivExpandView.setOnClickListener(this::onClick);

        ivMinimizeView = findViewById(R.id.ivMinimizeView);
        ivMinimizeView.setOnClickListener(this::onClick);

        linearLayoutParameterExpand = findViewById(R.id.linearLayoutParameterExpand);
        linearLayoutParameterMini = findViewById(R.id.linearLayoutParameterMini);
        linearLayoutParameterFields = findViewById(R.id.linearLayoutParameterFields);

        auditSheetPreFillData();

    }

    @Override
    public void onClick(View view) {

        if (view == ivShowMoreViewAuditScore) {
            linearLayoutAuditScore.setVisibility(View.VISIBLE);
            linearLayoutLessViewAuditScore.setVisibility(View.VISIBLE);
            linearLayoutMoreViewAuditScore.setVisibility(View.GONE);

        }

        if (view == ivShowLessViewAuditScore) {

            linearLayoutAuditScore.setVisibility(View.GONE);
            linearLayoutLessViewAuditScore.setVisibility(View.GONE);
            linearLayoutMoreViewAuditScore.setVisibility(View.VISIBLE);

        }

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




        if (view == ivShowLessViewOverall) {
            linearLayoutOverAllDetails.setVisibility(View.GONE);
            linearLayoutLessViewOverAll.setVisibility(View.GONE);
            linearLayoutMoreViewOvelAll.setVisibility(View.VISIBLE);

        }

        if (view == ivShowMoreViewOverall) {
            linearLayoutOverAllDetails.setVisibility(View.VISIBLE);
            linearLayoutLessViewOverAll.setVisibility(View.VISIBLE);
            linearLayoutMoreViewOvelAll.setVisibility(View.GONE);
        }



        if (view == ivShowLessViewRCAType) {
            linearLayoutRCATypeDetails.setVisibility(View.GONE);
            linearLayoutLessViewRCAType.setVisibility(View.GONE);
            linearLayoutMoreViewRCAType.setVisibility(View.VISIBLE);

        }

        if (view == ivShowMoreViewRCAType) {
            linearLayoutRCATypeDetails.setVisibility(View.VISIBLE);
            linearLayoutLessViewRCAType.setVisibility(View.VISIBLE);
            linearLayoutMoreViewRCAType.setVisibility(View.GONE);
        }

    }


    private void auditSheetPreFillData() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.AUDITOR_AGENT_NAME,
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

                            if (sStatus!=0) {
                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();

                                JSONObject dataObject = jsonObject.getJSONObject("data");

                                JSONObject sheetObject = dataObject.getJSONObject("sheet_details");

                                JSONObject clientObject = sheetObject.getJSONObject("client");

                                String sClientName = clientObject.getString("name");
                                textInputEditTextClientName.setText(sClientName);

                                String sAuditTimeStamp = dataObject.getString("audit_timestamp");
                                textInputEditTextAuditDate.setText(sAuditTimeStamp);

                                String sSheetVersion = sheetObject.getString("version");
                                textInputEditTextSheetVersion.setText(sSheetVersion);



                                JSONArray parameterArray = sheetObject.getJSONArray("parameter");

                                saParameterId = new String[parameterArray.length()];
                                saParameterName = new String[parameterArray.length()];

                                for (int l=0; l < parameterArray.length(); l++) {
                                    JSONObject data = parameterArray.getJSONObject(l);

                                    saParameterId[l] = data.getString("id");
                                    saParameterName[l] = data.getString("parameter");

                                    ArrayAdapter adapterParameter = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saParameterName);
                                    atvParameter.setAdapter(adapterParameter);


                                    /*JSONArray subParameterArray = data.getJSONArray("qm_sheet_sub_parameter");

                                    saSubParameter = new String[subParameterArray.length()];
                                    saParameterIdSubSection = new String[subParameterArray.length()];

                                    for (int v=0; v < subParameterArray.length(); v++) {
                                        JSONObject data1 = subParameterArray.getJSONObject(v);

                                        saSubParameter[v] = data1.getString("sub_parameter");
                                        saParameterIdSubSection[v] = data1.getString("qm_sheet_parameter_id");

                                        //Toast.makeText(mActivity, saSubParameter[v], Toast.LENGTH_SHORT).show();


                                    }*/

                                }

                                atvParameter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        JSONArray parameterArray = null;
                                        try {
                                            parameterArray = sheetObject.getJSONArray("parameter");
                                            saParameterId = new String[parameterArray.length()];
                                            saParameterName = new String[parameterArray.length()];

                                            JSONObject data = parameterArray.getJSONObject(i);
                                            JSONArray subParameterArray = data.getJSONArray("qm_sheet_sub_parameter");
                                            saSubParameter = new String[subParameterArray.length()];
                                            saParameterIdSubSection = new String[subParameterArray.length()];

                                            for (int v=0; v < subParameterArray.length(); v++) {
                                                JSONObject data1 = subParameterArray.getJSONObject(v);

                                                saSubParameter[v] = data1.getString("sub_parameter");
                                                saParameterIdSubSection[v] = data1.getString("qm_sheet_parameter_id");

                                                recycleViewSubParameter.setAdapter(new SubParameterAdapter(
                                                        mActivity,saSubParameter));

                                                //Toast.makeText(mActivity, saSubParameter[v], Toast.LENGTH_SHORT).show();


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });


                                /*JSONArray partnerArray = dataObject.getJSONArray("partners_list");

                                saAgentId = new String[partnerArray.length()];
                                saAgentName = new String[partnerArray.length()];

                                for (int i=0; i < partnerArray.length(); i++) {
                                    JSONObject data = partnerArray.getJSONObject(i);

                                    saAgentId[i] = data.getString("id");
                                    saAgentName[i] = data.getString("name");

                                    ArrayAdapter adapterAgentName = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saAgentName);
                                    atvSelectAgentName.setAdapter(adapterAgentName);

                                }*/

                                recycleViewAuditScore.setAdapter(new AuditScoreAdapter(mActivity,saParameterName));


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
                params.put("qmsheet_id", sQMSheetId);
                params.put("user_id", sUserId);
                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }



}