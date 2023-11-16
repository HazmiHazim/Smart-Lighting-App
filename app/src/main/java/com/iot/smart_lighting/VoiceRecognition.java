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
    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";
    private static final String COMMAND_SEARCH = "commands";

    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "hey phoenix";

    public VoiceRecognition (Context context) {
        this.context = context;
        this.esp32 = new Esp32(context);
        setupRecognizer();
    }

    private void setupRecognizer() {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them
        try {
            Assets assets = new Assets(context);
            File assetsDir = assets.syncAssets();

            recognizer = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                    .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                    .setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false positives and false negatives
                    .getRecognizer();

            recognizer.addListener(this);

            // Create keyword-activation search.
            recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

            // Create grammar-based search for selection between demos
            File menuGrammar = new File(assetsDir, "menu.gram");
            recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);

            // Create grammar-based search for digit recognition
            File digitsGrammar = new File(assetsDir, "digits.gram");
            recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

            // Create language model search
            File languageModel = new File(assetsDir, "weather.dmp");
            recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

            // Phonetic search
            File phoneticModel = new File(assetsDir, "en-phone.dmp");
            recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);

            // Command Search
            File commandsGrammar = new File(assetsDir, "command.gram");
            recognizer.addGrammarSearch(COMMAND_SEARCH, commandsGrammar);
            switchSearch(KWS_SEARCH);
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
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        switch (text) {
            case KEYPHRASE:
                switchSearch(MENU_SEARCH);
                break;
            case DIGITS_SEARCH:
                switchSearch(DIGITS_SEARCH);
                break;
            case PHONE_SEARCH:
                switchSearch(PHONE_SEARCH);
                break;
            case FORECAST_SEARCH:
                switchSearch(FORECAST_SEARCH);
                break;
            case COMMAND_SEARCH:
                switchSearch(COMMAND_SEARCH);
            default:
                Log.d("Voice Recognition", "Listening: " + text);
                break;
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            Toast.makeText(context, "I'm Listening...", Toast.LENGTH_SHORT).show();
            String text = hypothesis.getHypstr();
            Log.d("Voice Result", "Result: " + text);
            executeCommand(text);
        }
    }

    @Override
    public void onError(Exception e) {
        // Do Something
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(KWS_SEARCH)) {
            recognizer.startListening(searchName);
        }
        else {
            recognizer.startListening(searchName, 10000);
        }
    }

    private void executeCommand(String textResult) {
        switch (textResult) {
            case "turn on lamp one":
                esp32.applyLamp("http://192.168.4.1/lamp1/on?value=255");
                Log.d("Command", "Command: Success Turn On Lamp 1");
                break;
            case "turn off lamp one":
                esp32.applyLamp("http://192.168.4.1/lamp1/off");
                Log.d("Command", "Command: Success Turn Off Lamp 1");
                break;
            case "turn on lamp two":
                esp32.applyLamp("http://192.168.4.1/lamp2/on?value=255");
                Log.d("Command", "Command: Success Turn On Lamp 2");
                break;
            case "turn off lamp two":
                esp32.applyLamp("http://192.168.4.1/lamp2/off");
                Log.d("Command", "Command: Success Turn Off Lamp 2");
                break;
            case "turn on lamp three":
                esp32.applyLamp("http://192.168.4.1/lamp3/on?value=255");
                Log.d("Command", "Command: Success Turn On Lamp 3");
                break;
            case "turn off lamp three":
                esp32.applyLamp("http://192.168.4.1/lamp3/off");
                Log.d("Command", "Command: Success Turn Off Lamp 3");
                break;
            default:
                //Toast.makeText(context, "Sorry! Unrecognized command.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
