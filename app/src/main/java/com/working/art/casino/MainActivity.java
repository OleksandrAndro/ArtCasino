package com.working.art.casino;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.core.CoreActivity;
import com.core.CoreApp;

import static com.core.CoreActivity.INTENT_ADDRESS;
import static com.core.CoreActivity.INTENT_CLASS;
import static com.core.CoreActivity.INTENT_COLOR;
import static com.core.CoreActivity.INTENT_DRAWABLE;


public class MainActivity extends Activity {
    TextView playBalance, playLevel;
    ImageAdapter imageAdapter;
    SharedPreferences sharedpreferences;
    int balance, level;
    public static final String mypreference = "mypref";
    public static final String BalKey = "BalKey";
    public static final String Level = "levelKey";
    float xdpi, ydpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CoreApp.initializeAppAnalytics("","","","");

        /*startActivity(new Intent(MainActivity.this, CoreActivity.class)
                .putExtra(INTENT_ADDRESS, "http://domain/index.php")
                .putExtra(INTENT_DRAWABLE, "file:///assets/congratulations.gif")
                .putExtra(INTENT_COLOR, "#212121")
                .putExtra(INTENT_CLASS, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));*/

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        playBalance = findViewById(R.id.playBalance);
        playLevel = findViewById(R.id.playLevel);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if((sharedpreferences.contains(BalKey)) & (sharedpreferences.contains(Level))){

            balance = sharedpreferences.getInt(BalKey, 213710);
            level = sharedpreferences.getInt(Level, 1);
            SetFields();

        }
        else {
            balance = 213710;
            level = 1;
            SetFields();
        }

        Integer[] ThumbIds = {
                R.drawable.game_button1, R.drawable.game_button2,
                R.drawable.game_button3, R.drawable.game_button4,
                R.drawable.game_button5, R.drawable.game_button6
        };

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int density = displayMetrics.densityDpi;
        int heightPixels = displayMetrics.heightPixels;
        int weightPixels = displayMetrics.widthPixels;
        xdpi = displayMetrics.xdpi;
        ydpi = displayMetrics.ydpi;

        GridView gridview = findViewById(R.id.listOfGames);
        imageAdapter = new ImageAdapter(MainActivity.this, ThumbIds, (int)xdpi, (int)ydpi);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                switch (position){

                    case 0:
                        Intent intent = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent3);
                        break;

                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent4);
                        break;

                    case 5:
                        Intent intent5 = new Intent(MainActivity.this, Game1.class);
                        startActivity(intent5);
                        break;

                }
                /*Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();*/
            }
        });


    }



    public void SaveToPref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(BalKey, balance);
        editor.putInt(Level, level);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //level++;
            //balance++;
        SetFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        balance = sharedpreferences.getInt(BalKey, 213710);
        level = sharedpreferences.getInt(Level, 1);
        SetFields();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaveToPref();
    }

    public void SetFields(){
        int hightBal = balance/100000;
        int medBal = balance/100%1000;
        int lowBal = balance%100;
        playBalance.setText(String.format("%5d", balance));
        playLevel.setText("Level" + " " + level);
    }


}
