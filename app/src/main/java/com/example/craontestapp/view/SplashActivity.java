package com.example.craontestapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.craontestapp.R;
import com.example.craontestapp.viewmodel.SplashViewModel;
import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final String SPLASH_ACTIVITY_LOG = SplashActivity.class.getSimpleName();
    private SplashViewModel splashViewModel;
    private EntryActivity entryActivity = new EntryActivity();

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

        // TODO: avoid to instantiate each time a new a activity
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        splashViewModel.skipToMainActivity(this, entryActivity);
    }
}
