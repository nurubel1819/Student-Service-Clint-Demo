package com.example.studentserviceclintdemo.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.R;

public class FacebookViewActivity extends AppCompatActivity {

    WebView facebook_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_facebook_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        facebook_view = findViewById(R.id.web_view_facebook_id);

        // Configure WebView settings
        WebSettings webSettings = facebook_view.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Ensure links and redirects open in WebView instead of a browser
        facebook_view.setWebViewClient(new WebViewClient());

        // Optional: For handling JavaScript dialogs, favicons, etc.
        facebook_view.setWebChromeClient(new WebChromeClient());

        // Load a web page or HTML content
        facebook_view.loadUrl("https://www.facebook.com/nu.rubel.7?mibextid=ZbWKwL");
    }
    @Override
    public void onBackPressed() {
        // Handle back navigation within WebView
        if (facebook_view.canGoBack()) {
            facebook_view.goBack();
        } else {
            super.onBackPressed();
        }
    }
}