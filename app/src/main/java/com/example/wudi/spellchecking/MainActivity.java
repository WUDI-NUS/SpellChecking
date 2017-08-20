package com.example.wudi.spellchecking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SpellCheckerSession.SpellCheckerSessionListener {
    //user input
    EditText inputWord = (EditText) findViewById(R.id.inputWord);
    //confirm button
    Button checker = (Button) findViewById(R.id.check);
    //EditText to String
    private String word = inputWord.getText().toString();
    //test line below
    //private String word = "greader";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //check if the input is one word
                String[] array = word.trim().split(" ");
                if(array.length==1) {
                    word = array[0];
                    //check spelling of the word
                    fetchSuggestions();
                }
                else Toast.makeText(getApplicationContext(),"Please enter only one word",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchSuggestions(){


        TextServicesManager tsm = (TextServicesManager) getSystemService(TEXT_SERVICES_MANAGER_SERVICE);
        SpellCheckerSession session = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);

        session.getSentenceSuggestions(new TextInfo[]{new TextInfo(word)},3); //obtain 3 suggestion words
    }
    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        //if it is a sentence keep the below loop to check thru all words
        //for(SentenceSuggestionsInfo result:results){
        //this n is the count for each word currently checked by the loop
        //       int n = result.getSuggestionsCount();
        //loop thru all suggestions for the particular word
        //since there is only one word, guaranteed in onclicklistener, so the word reside at position 0
        for(int i = 0; i < results[0].getSuggestionsCount(); i++){
            int m = results[0].getSuggestionsInfoAt(i).getSuggestionsCount();
            if((results[0].getSuggestionsInfoAt(i).getSuggestionsAttributes() &
                    SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) == SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO )
                Toast.makeText(getApplicationContext(),"word not found",Toast.LENGTH_SHORT).show();
            else
                //start intent game play
                //put a toast for testing
                Toast.makeText(getApplicationContext(),"word found",Toast.LENGTH_SHORT).show();
        }
        //}
    }
}
