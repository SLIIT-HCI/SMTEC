package com.example.smtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExperimentSelection extends AppCompatActivity {

    Button btn_select_experiment1,btn_select_experiment2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_selection);

        btn_select_experiment1 = (Button) findViewById(R.id.id_exp1_btn);
        btn_select_experiment2 = (Button)findViewById(R.id.id_exp2_btn);

        btn_select_experiment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExperimentSelection.this,"Lab user Experiment Clicked",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ExperimentSelection.this, InitialExperimentActivity.class);
                startActivity(intent);

            }
        });

        btn_select_experiment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExperimentSelection.this,"wild user Experiment Clicked",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ExperimentSelection.this, BreakTimeActivity.class);
                startActivity(intent);

            }
        });

    }
}
