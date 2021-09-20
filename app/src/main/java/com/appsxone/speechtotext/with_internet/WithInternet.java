package com.appsxone.speechtotext.with_internet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsxone.speechtotext.R;

import java.util.ArrayList;

public class WithInternet extends AppCompatActivity {
    LinearLayout micLayout;
    ImageView imgMic;
    TextView tvMic, tvResult;
    int count = 0;
    SpeechRecognizer speechRecognizer;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_internet);

        micLayout = findViewById(R.id.micLayout);
        imgMic = findViewById(R.id.imgMic);
        tvMic = findViewById(R.id.tvMic);
        tvResult = findViewById(R.id.tvResult);
        setTitle("With Internet");

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        micLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    //Start Listening
                    speechRecognizer.startListening(speechRecognizerIntent);
                    imgMic.setImageDrawable(getDrawable(R.drawable.mic_on));
                    tvMic.setText("Listening...");
                    loadingDialog();
                    count = 1;
                } else {
                    //Stop Listening
                    speechRecognizer.stopListening();
                    imgMic.setImageDrawable(getDrawable(R.drawable.mic_off));
                    tvMic.setText("Tap To Speak");
                    count = 0;
                }
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                tvResult.setText(data.get(0));

                tvMic.setText("Tap To Speak");
                imgMic.setImageDrawable(getDrawable(R.drawable.mic_off));
                speechRecognizer.stopListening();
                alertDialog.dismiss();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    public void loadingDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        //alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}