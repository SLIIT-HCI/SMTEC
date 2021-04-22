package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wildusers.Database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class activity_sample_text1_1 extends AppCompatActivity {


    DatabaseHelper dbHelper;
    ImageView next;
    EditText text1;
    TextView phrase1;

    //ArrayList[] phrases = new ArrayList<String>();
    ArrayList<String> phrases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_1);


        next = (ImageView) findViewById(R.id.next);
        text1 = (EditText) findViewById(R.id.typeText);
        phrase1 = (TextView) findViewById(R.id.text1);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text1_1.this, activity_sample_text1_2.class);
                startActivity(i);
            }
        });
    }


//    public void setPhrase(Integer count){
//
//        /*phrase_array[0] ="You are ready to learn and do your best, but you are also nervous.";
//        phrase_array[1] = "Sometimes the most difficult questions have the simplest solutions";
//        phrase_array[2] = "Congratulations on your new job";
//        phrase_array[3] = "Starting a new job is exciting but stressful.";
//        phrase_array[4] = "Tomorrow is second Saturday.";
//        phrase.setText(phrase_array[count]); */
//
//        List<String> allTexts = Phrases_from_Database.getInputTexts();
//
//            for(Integer index = 0 ; index < allTexts.size() ; index ++){
//                phrase_array[index] = allTexts.get(index);
//            }
//
//        phrase1.setText(phrase_array[count]);
//
//        phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
//
//        phrase1.setText(phrases[count]);
//    }


//    public void setPhrase(int count){
//
//     /* phrases = dbHelper.getPhrases();
//      System.out.println("phrases: "+phrases.get(0));
//       if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
//            phrase.setText(phrases.get(count).toString());
//       }  */
//
//        phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
//
//        phrase1.setText(phrases[count]);
//    }

    public void setPhrase(int count){

        //phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
        //System.out.println("phrases: "+phrases.get(0));

        // phrase.setText(phrases[count]);

        phrases = dbHelper.getPhrases();
        phrases = dbHelper.getPhrases();
        System.out.println("phrases: "+phrases.get(0));
        if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
            phrase1.setText(phrases.get(count).toString());
        }

    }


    private String[] loadIntoTextView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] phrases = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            phrases[i] = obj.getString("phrase");
        }
        //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrases);
        // phrase.setText((CharSequence) arrayAdapter);
        return phrases;
    }

    private String[] getPhrasesFromAppServer(final String urlWebService) {

        final String[] value = new String[30];
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    for(String text : loadIntoTextView(s)){
                        for(int i =0;i<value.length;i++){
                            value[i] = text;
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

        return value;
    }
}