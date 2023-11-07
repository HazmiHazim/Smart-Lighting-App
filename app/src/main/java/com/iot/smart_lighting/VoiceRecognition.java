package com.iot.smart_lighting;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class VoiceRecognition implements RecognitionListener {

    private Context context;
    private SpeechRecognizer recognizer;
    private Esp32 esp32;
    private static final String KWS_SEARCH = "wakeup";
    private static final String KEYPHRASE = "hey babe";

    private static final String GRAMMAR_SEARCH = "commands";

    public VoiceRecognition (Context context) {
        this.context = context;
        this.esp32 = new Esp32(context);
        setupRecognizer();
    }

    private void setupRecognizer() {
        try {
            Assets assets = new Assets(context);
            File assetsDir = assets.syncAssets();

            recognizer = SpeechRecognizerSetup
                    .defaultSetup()
                    .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                    .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                    .setKeywordThreshold(1e-40f)
                    .getRecognizer();

            recognizer.addListener(this);
            // Create keyword-activation search.
            recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
            recognizer.startListening(KWS_SEARCH);
        } catch (IOException exception){
            exception.printStackTrace();
            Toast.makeText(context, "Failed to set up voice recognition.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        // Do Something
    }

    @Override
    public void onEndOfSpeech() {
        // Do Something
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }
        String text = hypothesis.getHypstr();
        Log.d("SpeechRecognition", "Recognized: " + text);

        if (text.equals("hey babe")) {
            esp32.applyLamp("http://192.168.4.1/lamp1/on?value=255");
            recognizer.cancel();
        } else if (text.equals("turn on lamp two")) {
            esp32.applyLamp("http://192.168.4.1/lamp1/off");
            recognizer.cancel();
        }
        recognizer.cancel();
        recognizer.startListening(KWS_SEARCH);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Log.i("Voice Result", "onResult Test: " + text);
        }
    }

    @Override
    public void onError(Exception e) {
        // Do Something
    }

    @Override
    public void onTimeout() {
        // Do Something
    }
}
