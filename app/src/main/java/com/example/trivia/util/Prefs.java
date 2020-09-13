package com.example.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity) {  //activity is the context passed here because SharedPreferences is a separate class and we want to fetch the data into another activity or activity class so we have to pass its context when we are creating the object of our Prefs class.
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighestScore(int score)
    {
        int currentScore = score;
        int lastScore = preferences.getInt("highest_score", 0);

        if(currentScore>lastScore)
        {
            //hence we have a new highest score

            preferences.edit().putInt("highest_score", currentScore).apply();
        }
    }

    public int getHighestScore()
    {
        return preferences.getInt("highest_score", 0);
    }
}
