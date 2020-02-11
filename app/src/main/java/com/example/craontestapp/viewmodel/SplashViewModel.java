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


//    public static final long SECOND_TO_START = 3000;
//    private static final String ENTRATO_NEL_METODO_SKIP = SplashActivity.class.getSimpleName();
//    private static final String ACTIVITY_INFO = "ActivityInfo";
//    private static boolean ACTIVITY_STARTED = false;
//    private Handler mHandler = new Handler();
//
//    public SplashViewModel(@NonNull Application application) {
//        super(application);
//    }
//
//    public void skipToMainActivity(Context context, Activity toActivity) {
//        SplashActivity activity = (SplashActivity) context;
//        if (activity.isTaskRoot()) {
//            Log.i(ACTIVITY_INFO, "" + activity.toString() + activity.isTaskRoot());
//            Runnable runnable = () -> {
//                if (!ACTIVITY_STARTED) {
//                    ACTIVITY_STARTED = true;
//                    Intent intent = new Intent(context, toActivity.getClass());
//                    activity.startActivity(intent);
//                    activity.finish();
//                    Log.i(ENTRATO_NEL_METODO_SKIP, "Entrato in skipToMainActivity");
//                } else {
//                    activity.finish();
//                }
//            };
//            mHandler.postDelayed(runnable, SECOND_TO_START);
//        } else {
//            activity.finish();
//        }
//    }
