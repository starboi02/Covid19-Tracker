package com.example.covid_19tracker;

import android.app.Activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class themeUtils {
    private static int currentTheme;
    public final static int LIGHT = 1;
    public final static int DARK = 0;
    public static void changeToTheme(Activity activity, int theme)
    {
        currentTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (currentTheme){
            default:
            case DARK:
                activity.setTheme(R.style.AppTheme);
                break;
            case LIGHT:
                activity.setTheme(R.style.LightTheme);
                break;
        }
    }
    public static void onFragmentCreateSetTheme(FragmentActivity activity) {
        switch (currentTheme){
            default:
            case DARK:
                activity.setTheme(R.style.AppTheme);
                break;
            case LIGHT:
                activity.setTheme(R.style.LightTheme);
                break;
        }
    }
}
