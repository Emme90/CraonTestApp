package com.example.craontestapp.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.craontestapp.view.SplashActivity;

public class SplashViewModel extends AndroidViewModel {

    private static boolean ACTIVITY_STARTED = false;

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public void skipToMainActivity(Context context, Activity toActivity) {
        SplashActivity activity = (SplashActivity) context;
        if (!ACTIVITY_STARTED) {
            ACTIVITY_STARTED = true;
            Intent intent = new Intent(context, toActivity.getClass());
            activity.startActivity(intent);
            activity.finish();
        } else {
            activity.finish();
            ACTIVITY_STARTED = false;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
