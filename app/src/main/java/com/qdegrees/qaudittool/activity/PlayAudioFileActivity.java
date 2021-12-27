package com.qdegrees.qaudittool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.qdegrees.qaudittool.MainMenuActivity;
import com.qdegrees.qaudittool.R;
import com.qdegrees.qaudittool.adapter.AudioFileListAdapter;
import com.qdegrees.qaudittool.common.RequestHandler;
import com.qdegrees.qaudittool.common.SharedPrefManager;
import com.qdegrees.qaudittool.common.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayAudioFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static ImageView gifShow;
    private static Activity mActivity;
    private static TextView tvMaxDuration,tvCurrentDuration,tvAudioFileName,tvFilter;

    private static RecyclerView recyclerViewAudioList;
    ProgressDialog progressDialog;
    private static String[] saCallId,saCallLink,saCallStatus,saCallLocation,saLocation,saProcessName,saLobName,saDispositionName,saCampaign_name;
    private static int value = 0,minuteValue = 00,secondsValue = 00;


    // For Play Audio File in Application
    private static MediaPlayer mediaPlayer;
    private static ImageView ivPlayPauseBtn;
    private String sFilterLocation,sFilterLob,sFilterProcessID,sFilterCallType,sFilterCallSubType,
            sFilterCampaignName,sFilterDisposition,sFilterCircle,sFilterBrandName,sFilterCallStatus;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio_file);

        setTitle("Play Audio");
        mActivity = this;


        sFilterLocation = getIntent().getStringExtra("sFilterLocation");
        sFilterLob = getIntent().getStringExtra("sFilterLob");
        sFilterProcessID = getIntent().getStringExtra("sFilterProcessID");
        sFilterCallType = getIntent().getStringExtra("sFilterCallType");
        sFilterCallSubType = getIntent().getStringExtra("sFilterCallSubType");
        sFilterCampaignName = getIntent().getStringExtra("sFilterCampaignName");
        sFilterDisposition = getIntent().getStringExtra("sFilterDisposition");
        sFilterCircle = getIntent().getStringExtra("sFilterCircle");
        sFilterBrandName = getIntent().getStringExtra("sFilterBrandName");
        sFilterCallStatus = getIntent().getStringExtra("sFilterCallStatus");


        //Toast.makeText(mActivity, sFilterProcessID, Toast.LENGTH_SHORT).show();





        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Data Loading...");
        progressDialog.setCancelable(false);

        gifShow = findViewById(R.id.gifShow);

        tvMaxDuration = findViewById(R.id.tvMaxDuration);
        tvCurrentDuration = findViewById(R.id.tvCurrentDuration);

        tvAudioFileName = findViewById(R.id.tvAudioFileName);
        tvFilter = findViewById(R.id.tvFilter);
        tvFilter.setOnClickListener(this::onClick);

        recyclerViewAudioList = findViewById(R.id.recyclerViewAudioList);
        recyclerViewAudioList.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewAudioList.setHasFixedSize(true);

        gettingListOfAllAudio();




        dialog  = new ProgressDialog(mActivity);

        ivPlayPauseBtn = findViewById(R.id.ivPlayPauseBtn);
        ivPlayPauseBtn.setOnClickListener(this);

        seekBar = findViewById(R.id.seekBar);
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // mediaPlayer = (MediaPlayer) MediaPlayer.create(this, Uri.parse(url));

        //seekBar.setMax(mediaPlayer.getDuration() / 1000);  //where mFileDuration is mMediaPlayer.getDuration();

        //Make sure you update Seekbar on UI thread
        mActivity.runOnUiThread(new Runnable() {

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;

                    if (mCurrentPosition < 10) {
                        tvCurrentDuration.setText( "00:0" + String.valueOf(mCurrentPosition));
                    }else {

                        if (mCurrentPosition > 59) {

                            minuteValue = mCurrentPosition/60;
                            secondsValue = mCurrentPosition - 60;

                            if (secondsValue < 10) {

                                if (minuteValue < 10) {
                                    tvCurrentDuration.setText( "0" + String.valueOf(minuteValue) + ":" + "0" + String.valueOf(secondsValue));
                                }else {
                                    tvCurrentDuration.setText( "0" + String.valueOf(minuteValue) + ":" + "0" + String.valueOf(secondsValue));
                                }

                            }else {
                                if (minuteValue < 10) {
                                    tvCurrentDuration.setText( "0" + String.valueOf(minuteValue) + ":" + String.valueOf(secondsValue));
                                }else {
                                    tvCurrentDuration.setText( "0" + String.valueOf(minuteValue) + ":" + String.valueOf(secondsValue));
                                }
                            }

                        }else {
                            tvCurrentDuration.setText( "00:" + String.valueOf(mCurrentPosition));
                        }

                    }
                    seekBar.setProgress(mCurrentPosition);
                }else {
                    //btnPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24_black);
                    ivPlayPauseBtn.getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24_black);
                }
                handler.postDelayed(this, 1000);
            }
        });


    }




    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {

        if (v == tvFilter) {
            Intent intent = new Intent(mActivity, AudioFilterActivity.class);
            startActivity(intent);
        }

        if (v == ivPlayPauseBtn) {

            if (value == 0) {

                dialog.setMessage("Buffering...");
                dialog.show();

                value = 1;

                // for play song
                try {
                    mediaPlayer.setDataSource(mActivity, Uri.parse(saCallLink[0]));
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();

                            Glide.with(mActivity).load(R.drawable.sound_wave).into(gifShow);

                            ivPlayPauseBtn.setImageResource(R.drawable.ic_baseline_pause_24);
                            dialog.dismiss();
                        }
                    });
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ivPlayPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24_black);;
                }else {
                    mediaPlayer.start();
                    ivPlayPauseBtn.setImageResource(R.drawable.ic_baseline_pause_24);;
                }

            }

        }

        /*if (v == btnPrevious) {
            dialog.show();
            if (cv==0) {
                currentValueofArrayIndex = currentValueofArrayIndex - 1;
            }
            cv=1;
            if (currentValueofArrayIndex > 0) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                //switchSongPrevious();
            } else {
                dialog.dismiss();
                Toast.makeText(this, "First Song", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == btnNext) {
            //Toast.makeText(this, "It is not Working", Toast.LENGTH_SHORT).show();
            dialog.show();
            if (currentValueofArrayIndex < songId.length) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                //switchSongNext();
            } else {
                dialog.dismiss();
                Toast.makeText(this, "Song Finish", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    private void gettingListOfAllAudio() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.SEARCHING,
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

                                saCallId = new String[array1.length()];
                                saCallLink = new String[array1.length()];
                                saCallStatus = new String[array1.length()];
                                saCallLocation = new String[array1.length()];

                                for (int i=0; i < array1.length(); i++) {
                                    JSONObject data = array1.getJSONObject(i);

                                    saCallId[i] = data.getString("call_id");
                                    saCallLink[i] = data.getString("call_link");
                                    saCallStatus[i] = data.getString("good_bad_call");
                                    saCallLocation[i] = data.getString("location");

                                    tvAudioFileName.setText(saCallId[0] + " (" + saCallLocation[0] + ")");

                                    recyclerViewAudioList.setAdapter(new AudioFileListAdapter(mActivity,
                                            saCallId,saCallLink,-1,saCallStatus,saCallLocation));

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

                params.put("location", sFilterLocation);
                params.put("lob", sFilterLob);
                params.put("process_id", sFilterProcessID);
                params.put("call_type", sFilterCallType);
                params.put("call_sub_type", sFilterCallSubType);
                params.put("campaign_name", sFilterCampaignName);
                params.put("disposition", sFilterDisposition);
                params.put("circle", sFilterCircle);
                params.put("brand_name", sFilterBrandName);
                params.put("good_bad_call", sFilterCallStatus);

                return params;
            }
        };

        RequestHandler.getInstance(mActivity).addToRequestQueue(stringRequest);


    }


    public static void playAudiofile(String audioURL, String sCallId, int pos) {

        mediaPlayer.stop();
        mediaPlayer.reset();

        dialog.setMessage("Buffering...");
        dialog.show();

        // for play song
        try {
            mediaPlayer.setDataSource(mActivity, Uri.parse(audioURL));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    value = 1;

                    tvAudioFileName.setText(sCallId);

                    Glide.with(mActivity).load(R.drawable.sound_wave).into(gifShow);

                    ivPlayPauseBtn.setImageResource(R.drawable.ic_baseline_pause_24);
                    dialog.dismiss();

                    recyclerViewAudioList.setAdapter(new AudioFileListAdapter(mActivity,
                            saCallId,saCallLink,pos,saCallStatus,saCallLocation));
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                getFilterOption();

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
                View mView = LayoutInflater.from(this).inflate(R.layout.filter_alert_view, null);
                mBuilder.setCancelable(false);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                atvProcessType = mView.findViewById(R.id.atvProcessType);

                atvLocation = mView.findViewById(R.id.atvLocation);

                atvLOB = mView.findViewById(R.id.atvLOB);

                atvDisposition = mView.findViewById(R.id.atvDisposition);

                atvcampaign_name = mView.findViewById(R.id.atvcampaign_name);


                // for Close Alert Dailog
                TextView tvCloseFilterView = mView.findViewById(R.id.tvCloseFilterView);
                tvCloseFilterView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // For apply
                Button btnApplyFilter = mView.findViewById(R.id.btnApplyFilter);
                btnApplyFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    sAddress = edtAddress.getText().toString();
                    sArea = edtArea.getText().toString();
                    sCity = edtCity.getText().toString();
                    sNumber = edtNumber.getText().toString();
                    swhatsApp = edtWhatsApp.getText().toString();
                    sFacebook = edtFacebook.getText().toString();
                    sTwitter = edtTwitter.getText().toString();
                    sInstagram = edtInsta.getText().toString();
                    sAbout = edtAbout.getText().toString();
                    sService = edtService.getText().toString();


                        dialog.dismiss();

                    }
                });

                dialog.show();
                dialog.setCancelable(true);

                return true;
        }

        return super.onOptionsItemSelected(item);
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

                                    ArrayAdapter adapterLocation = new ArrayAdapter(PlayAudioFileActivity.this,
                                            R.layout.support_simple_spinner_dropdown_item,saLocation);
                                    atvLocation.setAdapter(adapterLocation);

                                }

                                JSONArray processJsonArray = dataJsonDataObject.getJSONArray("process");

                                saProcessName = new String[processJsonArray.length()];

                                for (int i=0; i < processJsonArray.length(); i++) {
                                    JSONObject processData = processJsonArray.getJSONObject(i);

                                    saProcessName[i] = processData.getString("name");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(PlayAudioFileActivity.this,
                                            R.layout.support_simple_spinner_dropdown_item,saProcessName);
                                    atvProcessType.setAdapter(adapterLocation);

                                }

                                JSONArray lobJsonArray = dataJsonDataObject.getJSONArray("lob");
                                saLobName = new String[lobJsonArray.length()];
                                for (int i=0; i < lobJsonArray.length(); i++) {
                                    JSONObject lobData = lobJsonArray.getJSONObject(i);

                                    saLobName[i] = lobData.getString("lob");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(PlayAudioFileActivity.this,
                                            R.layout.support_simple_spinner_dropdown_item,saLobName);
                                    atvLOB.setAdapter(adapterLocation);

                                }

                                JSONArray dispositionJsonArray = dataJsonDataObject.getJSONArray("disposition");
                                saDispositionName = new String[dispositionJsonArray.length()];
                                for (int i=0; i < dispositionJsonArray.length(); i++) {
                                    JSONObject dispositionData = dispositionJsonArray.getJSONObject(i);

                                    saDispositionName[i] = dispositionData.getString("disposition");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(PlayAudioFileActivity.this,
                                            R.layout.support_simple_spinner_dropdown_item,saDispositionName);
                                    atvDisposition.setAdapter(adapterLocation);

                                }

                                JSONArray campaign_nameJsonArray = dataJsonDataObject.getJSONArray("campaign_name");
                                saCampaign_name = new String[campaign_nameJsonArray.length()];
                                for (int i=0; i < campaign_nameJsonArray.length(); i++) {
                                    JSONObject campaign_nameData = campaign_nameJsonArray.getJSONObject(i);

                                    saCampaign_name[i] = campaign_nameData.getString("campaign_name");

                                    ArrayAdapter adapterLocation = new ArrayAdapter(PlayAudioFileActivity.this,
                                            R.layout.support_simple_spinner_dropdown_item,saCampaign_name);
                                    atvcampaign_name.setAdapter(adapterLocation);

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

    }*/

}