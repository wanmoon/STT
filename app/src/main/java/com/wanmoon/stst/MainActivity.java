package com.wanmoon.stst;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.AclNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView resultTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTEXT = (TextView)findViewById(R.id.TVresult);
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton){
            promptSpeechInput();
        }
    }

    private void promptSpeechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");

        try {
            startActivityForResult(i, 100);
        }catch(ActivityNotFoundException a){
            Toast.makeText(this, "Your device is not support!", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestcode, int result_code, Intent i){
        super.onActivityResult(result_code, result_code, i);

        switch (requestcode){
            case 100: if(result_code==RESULT_OK && i!=null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                resultTEXT.setText(result.get(0));
            }
                break;
        }
    }

}