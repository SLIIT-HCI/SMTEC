package com.example.smtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentTimeSelection extends AppCompatActivity {

    RadioButton btn_now,btn_postpone;
    Spinner postponeTimeList;
    Button btn_expTimeConfirmed;
    View view;
    boolean btn_pressed = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_time_selection);

        btn_now = (RadioButton)findViewById(R.id.id_runNow);
        btn_postpone = (RadioButton)findViewById(R.id.id_postpone);
        postponeTimeList = (Spinner) findViewById(R.id.spinner_postponeTime);
        btn_expTimeConfirmed = (Button)findViewById(R.id.btn_confirmed);

        addItemsOnSpinnerCuisine();
        addListenerOnSpinnerItemSelection();

        btn_expTimeConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(ExperimentTimeSelection.this, UserSelfRateActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.id_runNow:
                if (checked){

                }
                break;
            case R.id.id_postpone:
                if (checked){

                }
                break;
        }
    }
    public void addItemsOnSpinnerCuisine() {

        postponeTimeList = (Spinner) findViewById(R.id.spinner_postponeTime);
        List<String> list = new ArrayList<String>();
        list.add("05:00 min");
        list.add("10:00 min");
        list.add("15:00 min");
        list.add("30:00 min");
        list.add("60:00 min");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postponeTimeList.setAdapter(dataAdapter);
    }
    public void addListenerOnSpinnerItemSelection() {
        postponeTimeList = (Spinner) findViewById(R.id.spinner_postponeTime);
        postponeTimeList.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
