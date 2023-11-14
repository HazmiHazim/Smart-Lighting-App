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
    private static final String COMMAND_SEARCH = "command";
    private static final String KEYPHRASE = "computer";

    public VoiceRecognition (Context context) {
        this.context = context;
        this.esp32 = new Esp32(context);
        setupRecognizer();
    }

    private void setupRecognizer() {
        try {
            Assets assets = new Assets(context);
            File assetsDir = assets.syncAssets();

            recognizer = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                    .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                    .setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false positives and false negatives
                    .getRecognizer();

            recognizer.addListener(this);

            // Create grammar-based search for commands
            File commandGrammar = new File(assetsDir, "command.gram");
            recognizer.addKeywordSearch(COMMAND_SEARCH, commandGrammar);
        } catch (IOException exception){
            exception.printStackTrace();
            Toast.makeText(context, "Failed to set up voice recognition.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        // Do Something
    }

    // Stop recognizer to get the final result
    @Override
    public void onEndOfSpeech() {
        reset(KWS_SEARCH);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        // Do Something
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Log.d("Result", "Result: " + text);
            if (text.contains("turn on lamp one")) {
                esp32.applyLamp("http://192.168.4.1/lamp1/on?value=255");
                Log.d("Lamp 1: ", "Success");
            }
            if (text.contains("turn on lamp two")) {
                esp32.applyLamp("http://192.168.4.1/lamp2/on?value=255");
                Log.d("Lamp 2: ", "Success");
            }
            if (text.contains("turn on lamp three")) {
                esp32.applyLamp("http://192.168.4.1/lamp3/on?value=255");
                Log.d("Lamp 3: ", "Success");
            }
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

    private void reset(String searchName) {
        recognizer.stop();
        recognizer.startListening(searchName);
    }
}
