package com.example.srinu.downloadablefonts;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Handler zHandler = null;
    private TextView programmaticallyDownloadableFontTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        programmaticallyDownloadableFontTextView = findViewById(R.id.textView3);

        downloadFont();
    }

    public void downloadFont(){
        FontRequest fontRequest = new FontRequest("com.google.android.gms.fonts",
                "com.google.android.gms", "Finger Paint", R.array.google_fonts_certs);

        FontsContractCompat.FontRequestCallback fontRequestCallback =
                new FontsContractCompat.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        programmaticallyDownloadableFontTextView.setTypeface(typeface);
                    }

                    @Override
                    public void onTypefaceRequestFailed(int reason) {
                        Toast.makeText(MainActivity.this,
                                "Failed download font programmatically", Toast.LENGTH_LONG)
                                .show();
                    }
                };
        FontsContractCompat.requestFont(this, fontRequest, fontRequestCallback , getThreadHandler());
    }
    private Handler getThreadHandler() {
        if (zHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            zHandler = new Handler(handlerThread.getLooper());
        }
        return zHandler;
    }
}
