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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.qdegrees.qaudittool.activity.AuditFormActivity;
import com.qdegrees.qaudittool.activity.CallListeningActivity;
import com.qdegrees.qaudittool.activity.LoginActivity;
import com.qdegrees.qaudittool.activity.PlayAudioFileActivity;
import com.qdegrees.qaudittool.common.SharedPrefManager;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    Activity mActivity;

    ImageView ivHome,ivAudit,ivMenu;
    TextView tvHighlight1,tvHighlight2,tvHighlight3;

    AutoCompleteTextView atvSelectProcess,atvSelectDateRange,atvSelectAgentName;
    LinearLayout linearLayoutHomeView,linearLayoutAuditForm,linearLayoutMenu;
    Button btnSearchAuditForm;

    ProgressBar proguBar50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mActivity = this;

        setTitle("Dashboard");

        proguBar50 = findViewById(R.id.proguBar50);
        proguBar50.setProgress(50);

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
        String[] saProcessName = {"Ticket Evaluation V1-Ticket Evaluation","Activation Evaluation V1-Activation Evaluation"
                ,"Whatsapp Evaluation V1-Whatsapp Evaluation","Voice Evaluation V2-Voice Evaluation"};
        ArrayAdapter adapterProcessType = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saProcessName);
        atvSelectProcess.setAdapter(adapterProcessType);


        atvSelectDateRange = findViewById(R.id.atvSelectDateRange);
        String[] saDateRange = {"Today","Yesterday","Last 7 Days","Last 30 Days","This Month","Last Month","Custom Range"};
        ArrayAdapter adapterDateRange = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saDateRange);
        atvSelectDateRange.setAdapter(adapterDateRange);


        atvSelectAgentName = findViewById(R.id.atvSelectAgentName);
        String[] saAgentName = {"Demo Agent","Abdul","Uchenna","Geraldo","Ayanda","Abdi","Juma Lola"};
        ArrayAdapter adapterAgentName = new ArrayAdapter(mActivity,
                R.layout.support_simple_spinner_dropdown_item,saAgentName);
        atvSelectAgentName.setAdapter(adapterAgentName);

        btnSearchAuditForm = findViewById(R.id.btnSearchAuditForm);
        btnSearchAuditForm.setOnClickListener(this::onClick);

        linearLayoutHomeView = findViewById(R.id.linearLayoutHomeView);
        linearLayoutAuditForm = findViewById(R.id.linearLayoutAuditForm);
        linearLayoutMenu = findViewById(R.id.linearLayoutMenu);

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

            if (sProcessName.isEmpty()) {
                Toast.makeText(mActivity, "Please Select Process", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(new Intent(mActivity, AuditFormActivity.class));
            }

        }

    }

}