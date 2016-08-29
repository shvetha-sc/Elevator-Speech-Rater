package com.hackathon.elevatorpitchrater;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;




public class MainActivity extends AppCompatActivity {
    Button playSpeech, recordSpeech, stop;
    MediaRecorder audioRecorder;
    HashMap<String,ArrayList<String>> wordtag=new HashMap<String,ArrayList<String>>();
    HashMap<String,ArrayList<String>> wordsynonyns=new HashMap<String,ArrayList<String>>();
    int finalcount=0;
    public static final int ACTIVITY_RECORD_SOUND = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    protected static final int RESULT_SPEECH = 1;
    private TextView txtText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtText = (TextView) findViewById(R.id.convertedSpeech);
    }

    /* This method will open the default recording app and display the recorded speech in a textbox */
    public void recordSpeech(View view)
    {
        //Intent intent = new Intent(this, AudioRecorder.class);
        //startActivity(intent);
        //Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageToStore));
        //startActivityForResult(intent, ACTIVITY_RECORD_SOUND);

        //Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        //startActivityForResult(intent, ACTIVITY_RECORD_SOUND);



        /* working code */
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, RESULT_SPEECH);
            txtText.setText("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Device doesn't support Speech to Text Conversion!!!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));

                }
                break;
            }

        }
    }



    public void rateMyPitch(View view)
    {
        final RequestParams params = new RequestParams();
        RaterClass raterObject = new RaterClass(txtText.getText().toString());
        invokeWS(params, raterObject.getCountOfWords(txtText.getText().toString()));
    }

    public void ratingFunction()
    {
        RaterClass raterObject = new RaterClass(txtText.getText().toString());
        HashMap<String,ArrayList<String>> obtainedHashMapForString = new HashMap<String,ArrayList<String>>();
        System.out.println("Wordtag is: " + wordtag);
        obtainedHashMapForString = wordtag;
/*
        //obtainedHashMapForString = raterObject.getCountOfWords(txtText.getText().toString());
        //obtainedHashMapForString = getFromSomeService(raterObject.getCountOfWords(text.get(0)));

        //TEMPPPP:
        obtainedHashMapForString = new HashMap<String, ArrayList<String>>();
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("noun");
        obtainedHashMapForString.put("hello",temp);
        temp = new ArrayList<String>();
        temp.add("noun");
        temp.add("verb");
        temp.add("adjective");
        obtainedHashMapForString.put("name",temp);
        temp = new ArrayList<>();
        temp.add("noun");
        temp.add("verb");
        obtainedHashMapForString.put("like",temp);
        temp = new ArrayList<>();
        temp.add("noun");
        obtainedHashMapForString.put("software",temp);
        temp = new ArrayList<>();
        temp.add("noun");
        obtainedHashMapForString.put("master's",temp);
        temp = new ArrayList<>();
        temp.add("adjective");
        obtainedHashMapForString.put("do",temp);
        temp = new ArrayList<>();
        temp.add("adjective");
        obtainedHashMapForString.put("do",temp);
        temp = new ArrayList<>();
        temp.add("adjective");
        obtainedHashMapForString.put("do",temp);
        temp = new ArrayList<>();
        temp.add("adjective");
        obtainedHashMapForString.put("do",temp);
        temp = new ArrayList<>();
        temp.add("adjective");
        obtainedHashMapForString.put("do",temp);
*/

        raterObject.categorizeHashMap(obtainedHashMapForString);
        HashMap<String,String> raterParameters = new HashMap<String,String>();

        int rating = raterObject.speechRater();
        raterParameters = raterObject.raterParameters;
        openFinalRating(raterParameters,rating);
    }

    public void openFinalRating(HashMap<String,String> raterParameters,int rating)
    {
        //raterParameters = null;
        //rating = 3;
        Intent intent = new Intent(this, FinalRating.class);
        intent.putExtra("hashmap",raterParameters);
        intent.putExtra("rating",rating+"");
        startActivity(intent);
    }

    public void invokeWS(RequestParams params, HashMap wordcount){
        // Show Progress Dialog
        // Make RESTful webservice call using AsyncHttpClient object
        final int wordcountsize=wordcount.size();
        //  final DatabaseExampleActivity db=new DatabaseExampleActivity(getApplicationContext());
        AsyncHttpClient client = new AsyncHttpClient();
        Iterator it = wordcount.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry pair = (Map.Entry) it.next();
            // avoids a ConcurrentModificationException
            client.get("http://words.bighugelabs.com/api/2/44ceaba399e89258411ff288d85fb131/" + pair.getKey().toString() + "/json", params, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    // prgDialog.hide();
                    finalcount++;
                    Log.v("MainActivity", pair.getKey().toString() + ":" + response);
                    try {
                        // JSON Object
                        JSONObject jb = new JSONObject(response);
                        //JSONArray js = jb.getJSONArray("noun");
                        Iterator<String> iter = jb.keys();
                        ArrayList<String> tags = new ArrayList<String>();
                        while (iter.hasNext()) {
                            //Log.v("Test1","test1");
                            String key = iter.next();
                            try {
                                Object value = jb.get(key);
                                tags.add(key);
                                if(key.equals("noun"))
                                {
                                    //  Log.v("Test2",key.toString());
                                    JSONObject obj=new JSONObject(value.toString());
                                    ArrayList<String> synonyms=new ArrayList<String>();
                                    Iterator<String> syn = obj.keys();
                                    while(syn.hasNext())
                                    {
                                        String type=syn.next();
                                        //Log.v("Synonyms" + pair.getKey().toString() + ":" , type);
                                        if(type.equals("sim") || type.equals("syn")) {
                                            try {
                                                Object synonym = obj.get(type);
                                                //Log.v("Synonyms" + pair.getKey().toString() + ":" + type,synonym.toString());
                                                StringTokenizer st=new StringTokenizer(synonym.toString(),",");
                                                while (st.hasMoreElements()) {
                                                    synonyms.add(st.nextElement().toString());

                                                }
                                                wordsynonyns.put(pair.getKey().toString(),synonyms);
                                                Log.v("Synonyms List",wordsynonyns.toString());
                                            }
                                            catch(JSONException e)
                                            {

                                            }

                                        }
                                    }

                                }
                                // Log.v(pair.getKey() + "Value" , value.toString());

                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        wordtag.put(pair.getKey().toString(),tags);
                        if(finalcount==wordcountsize)
                        {
                            ratingFunction();
                            Log.v("Final result",wordtag.toString());
                        }
                        // Log.v("Size","Length" + js.length());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                }

                // When the response returned by REST has Http response code other than '200'
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    // Hide Progress Dialog
                    // prgDialog.hide();
                    // When Http response code is '404'
                    if (statusCode == 404) {
                       // Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code other than 404, 500
                    else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
       /* */
    }

}
