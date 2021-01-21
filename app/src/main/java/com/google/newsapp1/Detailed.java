package com.google.newsapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Detailed extends AppCompatActivity {

    TextView tvTitle,tvSource,tvTime,tvDec;
    WebView WebView;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitle =findViewById(R.id.tvTitle);
        tvSource =findViewById(R.id.tvSource);
        tvTime =findViewById(R.id.tvDate);
        tvDec =findViewById(R.id.tvDes);

        imgView =findViewById(R.id.image);
        WebView =findViewById(R.id.tvweb);

        intentgetter();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void intentgetter() {
        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("title"));
        tvSource.setText(intent.getStringExtra("source"));
        tvTime.setText(intent.getStringExtra("time"));
        //intent.getStringExtra("url");

        Picasso.get().load(intent.getStringExtra("imageurl")).into(imgView);
        tvDec.setText(intent.getStringExtra("desc"));
       //intent.getStringExtra("url");

        WebView.getSettings().setDomStorageEnabled(true);
        WebView.getSettings().setJavaScriptEnabled(true);
        WebView.getSettings().setLoadsImagesAutomatically(true);
        WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebView.setWebViewClient(new WebViewClient());
        WebView.loadUrl(Objects.requireNonNull(intent.getStringExtra("url")));

    }
}