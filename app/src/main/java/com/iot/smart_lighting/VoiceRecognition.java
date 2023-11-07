package com.iot.smart_lighting;

import android.content.Context;
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

    private static final String KW_SEARCH = "wakeup";
    private static final String KEYPHRASE = "stop";

    public VoiceRecognition (Context context) {
        this.context = context;
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
            recognizer.addKeyphraseSearch(KW_SEARCH, KEYPHRASE);
            recognizer.startListening(KW_SEARCH);
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
        if (text.equals(KEYPHRASE)) {
            recognizer.cancel();
            listenForCommand();
            recognizer.startListening(KW_SEARCH);
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        // Do Something
    }

    @Override
    public void onError(Exception e) {
        // Do Something
    }

    @Override
    public void onTimeout() {
        // Do Something
    }

    public void listenForCommand() {
        // Do Something
    }
}
