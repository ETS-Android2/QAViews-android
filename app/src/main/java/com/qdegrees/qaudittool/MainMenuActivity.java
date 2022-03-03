package com.qdegrees.qaudittool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.qdegrees.qaudittool.activity.AudioFilterActivity;
import com.qdegrees.qaudittool.activity.AuditFormActivity;
import com.qdegrees.qaudittool.activity.CallListeningActivity;
import com.qdegrees.qaudittool.activity.FeedbackActivity;
import com.qdegrees.qaudittool.activity.LoginActivity;
import com.qdegrees.qaudittool.activity.PlayAudioFileActivity;
import com.qdegrees.qaudittool.activity.SubmittedAuditListActivity;
import com.qdegrees.qaudittool.adapter.AudioFileListAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    Activity mActivity;

    ImageView ivHome,ivAudit,ivMenu;
    TextView tvHighlight1,tvHighlight2,tvHighlight3,tvFataScoreServer,tvRebuttalScoreServer,
            tvCoverageScoreServer,tvUserName,tvUserEmail,tvSubmittedList,tvRebuttalRaiseList;

    AutoCompleteTextView atvSelectProcess,atvSelectDateRange,atvSelectAgentName;
    LinearLayout linearLayoutHomeView,linearLayoutAuditForm,linearLayoutMenu;
    Button btnSearchAuditForm;

    String sUserId,sQMSheetId,sActivityValue = "No";
    ProgressDialog progressDialog;
    String[] saProcessTitle,saProcessTitleId,saProcessName,saProcessNameId,
                saAgentName,saAgentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mActivity = this;
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        sUserId = SharedPrefManager.getInstance(mActivity).getUserId();

        setTitle("Dashboard");

        /*proguBar50 = findViewById(R.id.proguBar50);
        proguBar50.setProgress(50);*/

        tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(SharedPrefManager.getInstance(mActivity).getUserName());

        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(SharedPrefManager.getInstance(mActivity).getUserEmail());

        tvRebuttalRaiseList = findViewById(R.id.tvRebuttalRaiseList);
        tvRebuttalRaiseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SubmittedAuditListActivity.class);
                intent.putExtra("sActivityValue","rebuttal");
                startActivity(intent);
            }
        });

        tvSubmittedList = findViewById(R.id.tvSubmittedList);
        tvSubmittedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity, SubmittedAuditListActivity.class);
                intent.putExtra("sActivityValue","submit");
                startActivity(intent);
            }
        });

        tvFataScoreServer = findViewById(R.id.tvFataScoreServer);
        tvRebuttalScoreServer = findViewById(R.id.tvRebuttalScoreServer);
        tvCoverageScoreServer = findViewById(R.id.tvCoverageScoreServer);

        ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(this::onClick);

        ivAudit = findViewById(R.id.ivAudit);
        ivAudit.setOnClickListener(this::onClick);

        ivMenu = findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this::onClick);

        tvHighlight1 = findViewById(R.id.tvHighlight1);
        tvHighlight2 = findViewById(R.id.tvHighlight2);
        tvHighlight3 = findViewById(R.id.tvHighlight3);

        atvSelectProcess = findViewById(R.id.atvSelectProcess);


        atvSelectDateRange = findViewById(R.id.atvSelectDateRange);
        String[] saDateRange = {"Today","Yesterday","Last 7 Days","Last 30 Days","This Month","Last Month","Custom Range"};
        ArrayAdapter adapterDateRange = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saDateRange);
        atvSelectDateRange.setAdapter(adapterDateRange);


        atvSelectAgentName = findViewById(R.id.atvSelectAgentName);

        btnSearchAuditForm = findViewById(R.id.btnSearchAuditForm);
        btnSearchAuditForm.setOnClickListener(this::onClick);

        linearLayoutHomeView = findViewById(R.id.linearLayoutHomeView);
        linearLayoutAuditForm = findViewById(R.id.linearLayoutAuditForm);
        linearLayoutMenu = findViewById(R.id.linearLayoutMenu);


        auditorDashboardData();
        gettingListOfProcessName();
        //agentNameData();

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

                AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
                alert.setMessage("Are you sure?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                SharedPrefManager.getInstance(mActivity).logout();
                                finish();

                            }
                        }).setNegativeButton("Cancel", null);

                AlertDialog alert1 = alert.create();
                alert1.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        if (v == ivHome) {
            setTitle("Dashboard");

            linearLayoutHomeView.setVisibility(View.VISIBLE);
            linearLayoutAuditForm.setVisibility(View.GONE);
            linearLayoutMenu.setVisibility(View.GONE);

            tvHighlight1.setVisibility(View.VISIBLE);
            tvHighlight2.setVisibility(View.INVISIBLE);
            tvHighlight3.setVisibility(View.INVISIBLE);
        }

        if (v == ivAudit) {
            setTitle("New Audit");

            linearLayoutHomeView.setVisibility(View.GONE);
            linearLayoutAuditForm.setVisibility(View.VISIBLE);
            linearLayoutMenu.setVisibility(View.GONE);

            tvHighlight1.setVisibility(View.INVISIBLE);
            tvHighlight2.setVisibility(View.VISIBLE);
            tvHighlight3.setVisibility(View.INVISIBLE);
        }

        if (v == ivMenu) {
            setTitle("Menu");

            linearLayoutHomeView.setVisibility(View.GONE);
            linearLayoutAuditForm.setVisibility(View.GONE);
            linearLayoutMenu.setVisibility(View.VISIBLE);

            tvHighlight1.setVisibility(View.INVISIBLE);
            tvHighlight2.setVisibility(View.INVISIBLE);
            tvHighlight3.setVisibility(View.VISIBLE);

            //Toast.makeText(mActivity, "Click Menu", Toast.LENGTH_SHORT).show();
        }

        if (v == btnSearchAuditForm) {
            String sProcessName = atvSelectProcess.getText().toString();
            String sAgentName = atvSelectAgentName.getText().toString();

            if (sProcessName.isEmpty()) {
                Toast.makeText(mActivity, "Please Select Process", Toast.LENGTH_SHORT).show();
            }else if (sAgentName.isEmpty()) {
                Toast.makeText(mActivity, "Please Select Agent Name", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(mActivity, AuditFormActivity.class);
                intent.putExtra("sQMSheetId", sQMSheetId);
                startActivity(intent);
            }

        }

    }

    private void auditorDashboardData() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.AUDITOR_DASHBOARD,
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

                                JSONObject dataJsonDataObject = jsonObject.getJSONObject("data");

                                String sRebuttalCount = dataJsonDataObject.getString("rebuttal_count");
                                String sFatalCount = dataJsonDataObject.getString("fatal_count");
                                String sAuditCount = dataJsonDataObject.getString("audit_count");

                                tvFataScoreServer.setText(sFatalCount);
                                tvRebuttalScoreServer.setText(sRebuttalCount);
                                tvCoverageScoreServer.setText(sAuditCount);

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
                params.put("auditor_id", "354");
                params.put("start_date", "2021-11-01");
                params.put("end_date", "2021-11-24");
                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


    private void gettingListOfProcessName() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.AUDITOR_DASHBOARD_PROCESS_NAME,
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

                            if (sStatus != 0) {
                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();

                                JSONArray array1 = jsonObject.getJSONArray("data");

                                saProcessNameId = new String[array1.length()];
                                saProcessName = new String[array1.length()];
                                saProcessTitleId = new String[array1.length()];
                                saProcessTitle = new String[array1.length()];

                                for (int i=0; i < array1.length(); i++) {
                                    JSONObject data = array1.getJSONObject(i);

                                    saProcessNameId[i] = data.getString("id");
                                    saProcessName[i] = data.getString("name");

                                    JSONObject innerObject = data.getJSONObject("process");

                                    saProcessTitle[i] = data.getString("name") + "-"
                                            + innerObject.getString("name");


                                    ArrayAdapter adapterAgentName = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saProcessTitle);
                                    atvSelectProcess.setAdapter(adapterAgentName);
                                    atvSelectProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                            for (int y=0; y < saProcessNameId.length; y++) {
                                                if (saProcessName[i].equals(saProcessName[y])) {

                                                    sQMSheetId = saProcessNameId[y];

                                                    agentNameData();
                                                }
                                            }

                                            //Toast.makeText(mActivity, saProcessName[i], Toast.LENGTH_SHORT).show();
                                        }
                                    });



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

                params.put("user_id", sUserId);


                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


    private void agentNameData() {

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

                                JSONArray partnerArray = dataObject.getJSONArray("partners_list");

                                saAgentId = new String[partnerArray.length()];
                                saAgentName = new String[partnerArray.length()];

                                for (int i=0; i < partnerArray.length(); i++) {
                                    JSONObject data = partnerArray.getJSONObject(i);

                                    saAgentId[i] = data.getString("id");
                                    saAgentName[i] = data.getString("name");

                                    ArrayAdapter adapterAgentName = new ArrayAdapter(mActivity,
                                            R.layout.support_simple_spinner_dropdown_item,saAgentName);
                                    atvSelectAgentName.setAdapter(adapterAgentName);

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
                params.put("qmsheet_id", sQMSheetId);
                params.put("user_id", sUserId);
                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }

}