package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AudioFilterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnApplyFilter;
    Activity mActivity;
    ProgressDialog progressDialog;
    AutoCompleteTextView atvLocation,atvProcessType,atvLOB,atvDisposition,atvcampaign_name,atvBrandName,
            atvCallType,atvCallSubType;
    String[] saLocation,saProcessName,saProcessID,saLobName,saDispositionName,saCampaign_name,saBrandName,
            saCallType,saCallSubType;

    RadioButton radioBtnAllCall,radioBtnGoodCall,radioBtnBadCall;


    String sFilterLocation = "",sFilterLob = "",sFilterProcessID,sFilterCallType = "",sFilterCallSubType = "",
            sFilterCampaignName = "",sFilterDisposition = "",sFilterCircle = "",sFilterBrandName = "",sFilterCallStatus  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_filter);

        getSupportActionBar().hide();
        mActivity = this;

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);



        atvProcessType = findViewById(R.id.atvProcessType);

        atvLocation = findViewById(R.id.atvLocation);

        atvLOB = findViewById(R.id.atvLOB);

        atvDisposition = findViewById(R.id.atvDisposition);

        atvcampaign_name = findViewById(R.id.atvcampaign_name);

        atvBrandName = findViewById(R.id.atvBrandName);

        atvCallType = findViewById(R.id.atvCallType);

        atvCallSubType = findViewById(R.id.atvCallSubType);


        btnApplyFilter = findViewById(R.id.btnApplyFilter);
        btnApplyFilter.setOnClickListener(this::onClick);

        radioBtnAllCall = findViewById(R.id.radioBtnAllCall);
        radioBtnGoodCall = findViewById(R.id.radioBtnGoodCall);
        radioBtnBadCall = findViewById(R.id.radioBtnBadCall);

        getFilterOption();

    }

    @Override
    public void onClick(View v) {

        if (v == btnApplyFilter) {

            String sProcessName = atvProcessType.getText().toString();
            sFilterLocation = atvLocation.getText().toString();
            sFilterLob = atvLOB.getText().toString();
            sFilterCallType = atvCallType.getText().toString();
            sFilterCallSubType = atvCallSubType.getText().toString();
            sFilterCampaignName = atvcampaign_name.getText().toString();
            sFilterDisposition = atvDisposition.getText().toString();
            sFilterBrandName = atvBrandName.getText().toString();


            if (sProcessName.isEmpty()) {
                Toast.makeText(mActivity, "Please Select Process Name", Toast.LENGTH_SHORT).show();
            }else {
                if (radioBtnBadCall.isChecked()) {
                    sFilterCallStatus = "";
                }else if (radioBtnBadCall.isChecked()) {
                    sFilterCallStatus = "1";
                }else if (radioBtnBadCall.isChecked()) {
                    sFilterCallStatus = "2";
                }

                for (int g=0; g < saProcessID.length; g++) {
                    if (sProcessName.equals(saProcessName[g])) {
                        sFilterProcessID = saProcessID[g];
                    }
                }

                Intent intent = new Intent(mActivity, PlayAudioFileActivity.class);
                intent.putExtra("sFilterCallStatus",sFilterCallStatus);
                intent.putExtra("sFilterProcessID",sFilterProcessID);
                intent.putExtra("sFilterLocation",sFilterLocation);
                intent.putExtra("sFilterLob",sFilterLob);
                intent.putExtra("sFilterCallType",sFilterCallType);
                intent.putExtra("sFilterCallSubType",sFilterCallSubType);
                intent.putExtra("sFilterCampaignName",sFilterCampaignName);
                intent.putExtra("sFilterDisposition",sFilterDisposition);
                intent.putExtra("sFilterBrandName",sFilterBrandName);
                intent.putExtra("sFilterCircle",sFilterCircle);
                startActivity(intent);
            }
        }
    }






    private void getFilterOption() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.FILTER_LIST,
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


                                JSONObject dataJsonDataObject = jsonObject.getJSONObject("data");


                                JSONArray locationJsonArray = dataJsonDataObject.getJSONArray("location");

                                saLocation = new String[locationJsonArray.length()];

                                for (int i=0; i < locationJsonArray.length(); i++) {
                                    JSONObject locationData = locationJsonArray.getJSONObject(i);

                                    saLocation[i] = locationData.getString("location");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saLocation);
                                    atvLocation.setAdapter(adapterLocation);

                                }

                                JSONArray processJsonArray = dataJsonDataObject.getJSONArray("process");

                                saProcessName = new String[processJsonArray.length()];
                                saProcessID = new String[processJsonArray.length()];

                                for (int i=0; i < processJsonArray.length(); i++) {
                                    JSONObject processData = processJsonArray.getJSONObject(i);

                                    saProcessName[i] = processData.getString("name");
                                    saProcessID[i] = processData.getString("id");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saProcessName);
                                    atvProcessType.setAdapter(adapterLocation);

                                }

                                JSONArray lobJsonArray = dataJsonDataObject.getJSONArray("lob");
                                saLobName = new String[lobJsonArray.length()];
                                for (int i=0; i < lobJsonArray.length(); i++) {
                                    JSONObject lobData = lobJsonArray.getJSONObject(i);

                                    saLobName[i] = lobData.getString("lob");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saLobName);
                                    atvLOB.setAdapter(adapterLocation);

                                }

                                JSONArray dispositionJsonArray = dataJsonDataObject.getJSONArray("disposition");
                                saDispositionName = new String[dispositionJsonArray.length()];
                                for (int i=0; i < dispositionJsonArray.length(); i++) {
                                    JSONObject dispositionData = dispositionJsonArray.getJSONObject(i);

                                    saDispositionName[i] = dispositionData.getString("disposition");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saDispositionName);
                                    atvDisposition.setAdapter(adapterLocation);

                                }

                                JSONArray campaign_nameJsonArray = dataJsonDataObject.getJSONArray("campaign_name");
                                saCampaign_name = new String[campaign_nameJsonArray.length()];
                                for (int i=0; i < campaign_nameJsonArray.length(); i++) {
                                    JSONObject campaign_nameData = campaign_nameJsonArray.getJSONObject(i);

                                    saCampaign_name[i] = campaign_nameData.getString("campaign_name");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saCampaign_name);
                                    atvcampaign_name.setAdapter(adapterLocation);

                                }


                                JSONArray brandNameJsonArray = dataJsonDataObject.getJSONArray("brand_name");
                                saBrandName = new String[brandNameJsonArray.length()];
                                for (int i=0; i < brandNameJsonArray.length(); i++) {
                                    JSONObject brandNameData = brandNameJsonArray.getJSONObject(i);

                                    saBrandName[i] = brandNameData.getString("brand_name");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saBrandName);
                                    atvBrandName.setAdapter(adapterLocation);

                                }


                                JSONArray CallTypeJsonArray = dataJsonDataObject.getJSONArray("call_type");
                                saCallType = new String[CallTypeJsonArray.length()];
                                for (int i=0; i < CallTypeJsonArray.length(); i++) {
                                    JSONObject callTypeData = CallTypeJsonArray.getJSONObject(i);

                                    saCallType[i] = callTypeData.getString("call_type");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saCallType);
                                    atvCallType.setAdapter(adapterLocation);

                                }


                                JSONArray CallSubTypeJsonArray = dataJsonDataObject.getJSONArray("call_sub_type");
                                saCallSubType = new String[CallSubTypeJsonArray.length()];
                                for (int i=0; i < CallSubTypeJsonArray.length(); i++) {
                                    JSONObject callSubTypeData = CallSubTypeJsonArray.getJSONObject(i);

                                    saCallSubType[i] = callSubTypeData.getString("call_sub_type");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saCallSubType);
                                    atvCallSubType.setAdapter(adapterLocation);

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
                //params.put("client_id", "1");

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);

    }


}