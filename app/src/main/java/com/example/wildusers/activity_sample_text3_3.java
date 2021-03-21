
package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class activity_sample_text3_3 extends AppCompatActivity {
    Button submit33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text3_3);

        submit33 = (Button) findViewById(R.id.sampleSubmit33);
        submit33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text3_3.this, rating.class);
                startActivity(i);
            }
        });
    }
}