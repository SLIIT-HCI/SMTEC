package com.example.smtec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_experiment = (Button) findViewById(R.id.button_start);

        btn_experiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Experiment Clicked",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ExperimentSelection.class);
                startActivity(intent);

            }
        });
    }
}