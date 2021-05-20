package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class webViewPlayStore extends AppCompatActivity {

    private WebView webView;
    Button backToSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_play_store);

        webView = (WebView) findViewById(R.id.webViewPlayStore);
        webView.loadUrl("https://play.google.com/store/apps/details?id=com.google.android.inputmethod.latin");

        backToSettings = findViewById(R.id.backToSettingsBTN);
        backToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}