package com.qdegrees.qaudittool.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.activity.DashboardRebuttalActivity;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RaiseRebuttalAdapter extends RecyclerView.Adapter<RaiseRebuttalAdapter.MenuViewHolder> {

    Context context;
    String[] saParameterName,saSubParameterName,saScoreWithFatalPer,saScore,
            saParameterId,saSubParameterId;
    String sAuditId,sUserId,sRebuttalRemark;

    public RaiseRebuttalAdapter(Context context, String[] saParameterName, String[] saSubParameterName,
                                String[] saScoreWithFatalPer, String[] saScore, String sAuditId,
                                String[] saParameterId, String[] saSubParameterId) {
        this.context = context;
        this.saParameterName = saParameterName;
        this.saSubParameterName = saSubParameterName;
        this.saScoreWithFatalPer = saScoreWithFatalPer;
        this.saScore = saScore;
        this.sAuditId = sAuditId;

        this.saParameterId = saParameterId;
        this.saSubParameterId = saSubParameterId;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raise_rebuttal_parameter_view, null);
        return new MenuViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MenuViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        final String sParameterName = saParameterName[position];
        final String sSubParameter = saSubParameterName[position];
        final String sScore = saScore[position];

        float valueInint = Float.valueOf(saScoreWithFatalPer[position]);
        viewHolder.progressBarNew851.setProgress((int) valueInint);
        viewHolder.tvScoreParameterNew1.setText(saScoreWithFatalPer[position] + "%");


        viewHolder.tvParameterNameNew1.setText(sParameterName);
        viewHolder.tvParameterNamePOA1.setText(sSubParameter);

        viewHolder.tvScoreParameter1.setText(sScore);


        viewHolder.checkBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (viewHolder.checkBox11.isChecked()) {
                    viewHolder.remarkLayoutNew11.setVisibility(View.VISIBLE);
                    viewHolder.btnSaveRaiseRebuttal.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.remarkLayoutNew11.setVisibility(View.GONE);
                    viewHolder.btnSaveRaiseRebuttal.setVisibility(View.GONE);
                }
            }
        });

        viewHolder.btnSaveRaiseRebuttal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sRebuttalRemark = viewHolder.edtRebuttalRemarkRaise.getText().toString();

                if (sRebuttalRemark.isEmpty()) {
                    Toast.makeText(context, "Please Fill Rebuttal Remark", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(context, "Fine", Toast.LENGTH_SHORT).show();
                    actionRaiseRebuttal(saSubParameterId[position],saParameterName[position]);
                }

                //actionRaiseRebuttal();
            }
        });


    }


    @Override
    public int getItemCount() {
        return saParameterName.length;
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvParameterNameNew1,tvParameterNamePOA1,tvScoreParameterNew1,tvScoreParameter1;
        ProgressBar progressBarNew851;
        TextInputLayout remarkLayoutNew11;
        TextInputEditText edtRebuttalRemarkRaise;
        CheckBox checkBox11;
        Button btnSaveRaiseRebuttal;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvParameterNameNew1 = itemView.findViewById(R.id.tvParameterNameNew1);
            tvParameterNamePOA1 = itemView.findViewById(R.id.tvParameterNamePOA1);
            progressBarNew851 = itemView.findViewById(R.id.progressBarNew851);
            tvScoreParameterNew1 = itemView.findViewById(R.id.tvScoreParameterNew1);

            tvScoreParameter1 = itemView.findViewById(R.id.tvScoreParameter1);

            remarkLayoutNew11 = itemView.findViewById(R.id.remarkLayoutNew11);
            checkBox11 = itemView.findViewById(R.id.checkBoxNew11);

            edtRebuttalRemarkRaise = itemView.findViewById(R.id.edtRebuttalRemarkRaise);

            btnSaveRaiseRebuttal = itemView.findViewById(R.id.btnSaveRaiseRebuttal);

        }
    }


    private void actionRaiseRebuttal(String sSubParameterId, String sParameterId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        sUserId = SharedPrefManager.getInstance(context).getUserId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.RAISE_REBUTTAL,
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

                                Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("audit_id", sAuditId);
                params.put("sub_parameter_id", sSubParameterId);
                params.put("parameter_id", sParameterId);
                params.put("raised_by_user_id", sUserId);
                params.put("raw_data_id", "55458");
                params.put("rebuttal_remark", sRebuttalRemark);

                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
}