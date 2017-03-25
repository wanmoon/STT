package com.wanmoon.stst;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView resultTEXT;
    public String voiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTEXT = (TextView)findViewById(R.id.TVresult);

        FAB();
    }

    public void FAB(){
        // in FAB button
        ImageView FAB_ic = new ImageView(this); // Create an icon
        FAB_ic.setImageDrawable(getResources().getDrawable(R.drawable.button_sub_action_dark));

        FloatingActionButton FAB_actionButton = new FloatingActionButton.Builder(this)
                .setContentView(FAB_ic)
                .build();

        //inside FAB button
        ImageView STT_ic = new ImageView(this);
        ImageView manual_ic = new ImageView(this);

        STT_ic.setImageDrawable(getResources().getDrawable(R.drawable.mic));
        manual_ic.setImageDrawable(getResources().getDrawable(R.drawable.button_action_dark_touch));

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton STT_btn = itemBuilder.setContentView(STT_ic).build();
        SubActionButton manual_btn = itemBuilder.setContentView(manual_ic).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(STT_btn)
                .addSubActionView(manual_btn)
                .attachTo(FAB_actionButton)
                .build();
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.speakNow){
            promptSpeechInput();
        }
    }

    private void promptSpeechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
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
            case 100: if(result_code == RESULT_OK && i!=null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultTEXT.setText(result.get(0));

                //keep text in string
                voiceText = resultTEXT.getText().toString();
            }
                break;
        }
    }
}
