package com.working.art.casino;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    TextView playBalance, playLevel;
    ImageAdapter imageAdapter;
    SharedPreferences sharedpreferences;
    int lowPartBalance, mediumPartBalance, highPartBalance, level;
    public static final String mypreference = "mypref";
    public static final String LowBalKey = "lowBalKey";
    public static final String MediumBalKey = "medBalKey";
    public static final String HighBalKey = "highBalKey";
    public static final String Level = "levelKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBalance = findViewById(R.id.balanceText);
        playLevel = findViewById(R.id.levelText);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if((sharedpreferences.contains(LowBalKey)) & (sharedpreferences.contains(MediumBalKey)) & (sharedpreferences.contains(Level)) & (sharedpreferences.contains(HighBalKey))){

            mediumPartBalance = sharedpreferences.getInt(MediumBalKey, 137);
            lowPartBalance = sharedpreferences.getInt(LowBalKey, 10);
            highPartBalance = sharedpreferences.getInt(HighBalKey, 2);
            level = sharedpreferences.getInt(Level, 1);
            SetFields();

        }
        else {
            mediumPartBalance = 137;
            lowPartBalance = 10;
            highPartBalance = 2;
            level = 1;
            SetFields();

        }

        Integer[] ThumbIds = {
                R.drawable.game_button1, R.drawable.game_button2,
                R.drawable.game_button3, R.drawable.game_button4,
                R.drawable.game_button5, R.drawable.game_button6
        };

        GridView gridview = findViewById(R.id.listOfGames);
        imageAdapter = new ImageAdapter(MainActivity.this, ThumbIds);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void SaveToPref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(LowBalKey, lowPartBalance);
        editor.putInt(MediumBalKey, mediumPartBalance);
        editor.putInt(HighBalKey, highPartBalance);
        editor.putInt(Level, level);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        level++;
        if(lowPartBalance < 100)
            lowPartBalance++;
        else
            lowPartBalance = 1;
            mediumPartBalance = 138;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaveToPref();
    }

    public void SetFields(){
        playBalance.setText(highPartBalance + "." + mediumPartBalance +"."+ lowPartBalance);
        playLevel.setText("Level" + " " + level);
    }
}
