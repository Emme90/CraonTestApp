package com.example.craontestapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.craontestapp.R;
import com.example.craontestapp.viewmodel.SplashViewModel;
import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final String SPLASH_ACTIVITY_LOG = SplashActivity.class.getSimpleName();
    private Handler handler = new Handler();
    private SplashViewModel splashViewModel;

    @BindView(R.id.splashProgressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Log.i(SPLASH_ACTIVITY_LOG, "Entrato nella splash");

        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Runnable runnable = () -> {
            Intent intent = new Intent(SplashActivity.this, EntryActivity.class);
            startActivity(intent);
            finish();

        };
        handler.postDelayed(runnable, 3000);
    }
}
