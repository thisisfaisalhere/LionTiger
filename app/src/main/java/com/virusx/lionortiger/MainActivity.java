package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {

    private GridLayout playerGrid, androidGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Home");

        playerGrid = findViewById(R.id.playerGrid);
        androidGrid = findViewById(R.id.androidGrid);

        playerGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        androidGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidActivity.class);
                startActivity(intent);
            }
        });
    }
}
