package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;


public class StatsActivity extends AppCompatActivity {

    private static final String prefName = "scores";
    private static final String scoreOneKey = "playerOneScorePvP";
    private static final String scoreTwoKey = "playerTwoScorePvP";
    private static final String drawCountKey = "drawCountPvP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SharedPreferences sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);

        int[] score;
    }
}
