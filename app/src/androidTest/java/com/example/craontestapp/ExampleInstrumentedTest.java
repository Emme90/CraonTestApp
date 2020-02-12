package com.example.craontestapp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.craontestapp.util.Util;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String DHN = "DHN";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.craontestapp", appContext.getPackageName());
    }

    @Test
    public void checkDurationConverter(){
        String result;
        int duration = 120;
        result = Util.convertTimeToDuration(duration);
        Log.i(DHN, result);
    }

    @Test
    public void checkDateConverter(){
        String startDate = "2019-09-17";
        String result = Util.formattingDate(startDate);
        Log.i(DHN, result);
    }
}
