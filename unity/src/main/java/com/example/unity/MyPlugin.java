package com.example.unity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.Locale;

public class MyPlugin extends UnityPlayerActivity {
    private TextToSpeech tts = null;
    private PluginCallback pluginCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(UnityPlayer.currentActivity, "from plugin activity", Toast.LENGTH_SHORT).show();
    }

    public void SetPluginCallback(PluginCallback _pluginCallback){
        pluginCallback = _pluginCallback;
    }

    public void Speak(String text){
        tts = new TextToSpeech(UnityPlayer.currentActivity, new TextToSpeech.OnInitListener() {
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

        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this.getApplicationContext());
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

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
                pluginCallback.OnResult("error");
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                pluginCallback.OnResult(data.get(0).toString());
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
