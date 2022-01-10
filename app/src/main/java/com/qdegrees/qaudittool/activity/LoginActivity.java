package com.qdegrees.qaudittool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qdegrees.qaudittool.MainMenuActivity;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Activity mActivity;

    EditText edtEmail,edtPassword;
    Button btnLogin;
    String sEmail,sPassword,sSaveUserRole;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        mActivity = this;

        /*sSaveUserRole = SharedPrefManager.getInstance(mActivity).getUserRole();

        if (sSaveUserRole.equals("partner-admin")) {
            startActivity(new Intent(mActivity, FeedbackActivity.class));
            finish();
        }else if (sSaveUserRole.equals("qa")) {
            startActivity(new Intent(mActivity, MainMenuActivity.class));
            finish();
        }else {
            startActivity(new Intent(mActivity, AudioFilterActivity.class));
            finish();
        }*/


        /*if (SharedPrefManager.getInstance(mActivity).isLoggedIn()) {
            if (SharedPrefManager.getInstance(mActivity).getUserEmail().equals("sb@qdegrees.com")) {
                startActivity(new Intent(mActivity, FeedbackActivity.class));
                finish();
            }else if (SharedPrefManager.getInstance(mActivity).getUserEmail().equals("Digvijay.singh@qdegrees.com")) {
                startActivity(new Intent(mActivity, MainMenuActivity.class));
                finish();
            }else {
                startActivity(new Intent(mActivity, AudioFilterActivity.class));
                finish();
            }
        }*/

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        /*findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MainMenuActivity.class));
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {

            sEmail = edtEmail.getText().toString();
            sPassword = edtPassword.getText().toString();

            if (sEmail.isEmpty()) {
                Toast.makeText(mActivity, "Please Fill Email", Toast.LENGTH_SHORT).show();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
                Toast.makeText(mActivity, "Please Fill Valid Email Address", Toast.LENGTH_SHORT).show();
            }else if (sPassword.isEmpty()) {
                Toast.makeText(mActivity, "Please Fill Password", Toast.LENGTH_SHORT).show();
            }/*else {

                if(sEmail.equals("Digvijay.singh@qdegrees.com")) {

                    String sName = "Digvijay Singh";
                    String sAuthKey = "authKey";
                    String sEmail = "Digvijay.singh@qdegrees.com";
                    String sUserId = "1621";

                    //SharedPrefManager.getInstance(mActivity).userLogin(sUserId,sName,sEmail,sAuthKey);

                    startActivity(new Intent(mActivity, MainMenuActivity.class));
                    finish();
                }*/else {
                    userLogin();
            }

        }
    }

    private void userLogin() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.LOGIN_USER,
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

                            if (sStatus!=1) {
                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mActivity, sMsg, Toast.LENGTH_SHORT).show();

                                String sName = jsonObject.getString("name");
                                String sAuthKey = jsonObject.getString("auth_key");
                                String sEmail = jsonObject.getString("email");
                                String sUserId = jsonObject.getString("user_id");
                                String sUserRole = jsonObject.getString("role");

                                SharedPrefManager.getInstance(mActivity).userLogin(sUserId,sName,sEmail,sAuthKey,sUserRole);

                                if (sUserRole.equals("partner-admin")) {
                                    startActivity(new Intent(mActivity, FeedbackActivity.class));
                                    finish();
                                }else if (sUserRole.equals("qa")) {
                                    startActivity(new Intent(mActivity, MainMenuActivity.class));
                                    finish();
                                }else {
                                    startActivity(new Intent(mActivity, AudioFilterActivity.class));
                                    finish();
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
                params.put("email", sEmail);
                params.put("password", sPassword);

                /*if (sDeviceStatus.equals("NOT WORKING")) {
                    params.put("status", "WORKING");
                }else {
                    params.put("device_status", "NOT WORKING");
                }*/


                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


}