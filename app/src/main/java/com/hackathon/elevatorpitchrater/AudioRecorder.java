package com.hackathon.elevatorpitchrater;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class AudioRecorder extends Activity {
    protected static final int RESULT_SPEECH = 1;


    private TextView txtText;
    private String recordedSpeech = null;
    Button startRecording, stopRecording, playRecordedPitch;
    MediaRecorder audioRecorder ;//= new MediaRecorder();
    private TextView txtSpeechInput;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);

    }



    public void recordElevatorPitch(View view)
    {
        /*recordedSpeech = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(recordedSpeech);


        try
        {
            audioRecorder.prepare();
            audioRecorder.start();
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Context context = getApplicationContext();
        CharSequence message = "Recording in Progress";
        int duration = Toast.LENGTH_LONG;

        Toast toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();*/

    }



    public void stopRecordingPitch(View view)
    {
        audioRecorder.stop();
        audioRecorder.release();

        Context context = getApplicationContext();
        CharSequence message = "Elevator Pitch Recording Complete";
        int duration = Toast.LENGTH_LONG;

        Toast toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();
    }

    public void playElevatorPitch(View view)
    {

        MediaPlayer recordedSpeechPlay = new MediaPlayer();
        try{
            recordedSpeechPlay.setDataSource(recordedSpeech);
            recordedSpeechPlay.prepare();
            recordedSpeechPlay.start();
            Context context = getApplicationContext();
        CharSequence message = "Playing the recorded Elevator Pitch";
        int duration = Toast.LENGTH_LONG;

        Toast toastMessage = Toast.makeText(context, message, duration);
        toastMessage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }



}
