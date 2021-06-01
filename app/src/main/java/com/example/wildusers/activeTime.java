package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.bendik.simplerangeview.SimpleRangeView;

public class activeTime extends AppCompatActivity {

    SimpleRangeView rangeView;
    TextView timeRange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_time);

        rangeView = findViewById(R.id.rangeSlider);
        timeRange = findViewById(R.id.timeRange);

        rangeView.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i, int i1) {
                timeRange.setText(String.valueOf(i)+" - "+String.valueOf(i1));
            }
        });

        rangeView.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                timeRange.setText(String.valueOf(i));
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                timeRange.setText(String.valueOf(i));
            }
        });

        rangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return String.valueOf(i);
            }
        });
    }
}