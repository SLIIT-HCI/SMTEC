package com.example.wildusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.getStartBtn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StartUp.class);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent i = new Intent(getApplicationContext(), StartUp.class);
                        //startActivity(i);
                        finish();
                    }
                }, 9000);
            }
        });


    }


/******************************** mini menu **********************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mini_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activeTime:
                Intent i1 = new Intent(getApplicationContext(), activeTime.class);
                startActivity(i1);
                return true;

            case R.id.Instructions:
                Intent i2 = new Intent(getApplicationContext(), Instructions.class);
                startActivity(i2);
                return true;

            case R.id.settings:
                Intent i3 = new Intent(getApplicationContext(), settings.class);
                startActivity(i3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /******************************************* end of mini-menu ***********************************************************/


}