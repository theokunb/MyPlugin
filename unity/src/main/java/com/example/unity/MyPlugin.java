package com.example.unity;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MyPlugin {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private Activity unityActivity;
    private TextToSpeech tts = null;


    public MyPlugin(Activity activity){
        unityActivity = activity;
    }

    public void Speak(String text){
        tts = new TextToSpeech(unityActivity.getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.getDefault());
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void StartSpeech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        unityActivity.startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
    }
}
