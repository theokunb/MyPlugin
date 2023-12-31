package com.example.unity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.unity3d.player.UnityPlayer;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Locale;

public class MyPlugin extends UnityPlayerActivity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 1;
    private TextToSpeech tts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(UnityPlayer.currentActivity, "from plugin activity", Toast.LENGTH_SHORT).show();
    }

    public void Speak(String text){
        tts = new TextToSpeech(UnityPlayer.currentActivity.getApplicationContext(), new TextToSpeech.OnInitListener() {
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

        UnityPlayer.UnitySendMessage("CallbackReciever","RecieveSttResult","intent created");

        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(UnityPlayer.currentActivity);

        UnityPlayer.UnitySendMessage("CallbackReciever","RecieveSttResult","recognizer created");

        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                System.out.println("Ready for speech");
            }

            @Override
            public void onBeginningOfSpeech() {
                System.out.println("Speech starting");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                UnityPlayer.UnitySendMessage("CallbackReciever","RecieveSttResult", "error");
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String res = data.get(0).toString();
                UnityPlayer.UnitySendMessage("CallbackReciever","RecieveSttResult", res);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };

        recognizer.setRecognitionListener(listener);
        recognizer.startListening(intent);
    }
}
