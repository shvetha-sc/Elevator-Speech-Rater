package com.hackathon.elevatorpitchrater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class FinalRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_rating);


        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashmap");
        System.out.println(hashMap);
        String message = intent.getStringExtra("rating");
        TextView txtView = (TextView) findViewById(R.id.finalRatingValue);
        txtView.setText(message);

        TextView txtViewGood = (TextView) findViewById(R.id.goodDetails);
        TextView txtViewBad = (TextView) findViewById(R.id.missDetails);
        for(HashMap.Entry<String ,String > e : hashMap.entrySet())
        {
            if(e.getKey().equals("Greeting"))
            {
                if(e.getValue()!=null)
                {
                    txtViewGood.append("> You started it off well.\n");
                    txtViewGood.append("\n");
                }
                else {
                    txtViewGood.append("\n");
                    txtViewBad.append("> Oops you were off to a bad Start. You could have greeted the interviewer\n");
                    txtViewGood.append("\n");
                }
            }

            if(e.getKey().equals("Profession"))
            {
                if(e.getValue()!=null)
                {
                    txtViewGood.append("\n");
                    txtViewGood.append("> You mentioned your current job/student status\n");
                    txtViewGood.append("\n");
                }
                else {
                    txtViewGood.append("\n");
                    txtViewBad.append("> Well...you missed out on your current status\n");
                    txtViewGood.append("\n");
                }
            }
            if(e.getKey().equals("Education"))
            {
                if(e.getValue()!=null)
                {
                    txtViewGood.append("\n");
                    txtViewGood.append("> You mentioned your Education level\n");
                    txtViewGood.append("\n");
                }
                else
                    txtViewGood.append("\n");
                    txtViewBad.append("> Did you attend school ? \n");
                    txtViewGood.append("\n");
            }

            if(e.getKey().equals("RepeatedAdjectives"))
            {
                if(e.getValue()!=null && Integer.parseInt(e.getValue().toString().split(":")[1])>3 ) {

                    txtViewGood.append("\n");
                    txtViewBad.append("> You tend to overuse adjectives.Avoid using the same adjective over and over again.\n");
                    //txtViewGood.append("\n");
                    txtViewBad.append("You used "+e.getValue().toString().split(":")[0]+" way too many times");
                    txtViewGood.append("\n");
                }
            }

            if(e.getKey().equals("RepeatedVerbs"))
            {
                if(e.getValue()!=null && Integer.parseInt(e.getValue().toString().split(":")[1])>3 ) {

                    txtViewGood.append("\n");
                    txtViewBad.append("> You tend to overuse verbs.Avoid using the same verb over and over again.\n");
                    //txtViewGood.append("\n");
                    txtViewBad.append("You said "+e.getValue().toString().split(":")[0]+" way too many times\n");
                    txtViewGood.append("\n");
                }
            }


            if(e.getKey().equals("LengthOfSpeech"))
            {
                if(e.getValue()!=null && Integer.parseInt(e.getValue().toString())>500 ) {

                    txtViewGood.append("\n");
                    txtViewBad.append("> Keep your speech short and sweet.");
                    txtViewGood.append("\n");
                }
            }
        }
        if(txtViewGood.getText().length() ==0)
            txtViewGood.append("You need to improve.");
        if(txtViewBad.getText().length() ==0)
            txtViewBad.append("Your speech was amaszing");

    }


}
