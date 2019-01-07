package com.test_game.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;

import static com.test_game.demo.MainActivity.BalKey;
import static com.test_game.demo.MainActivity.Level;

public class Game1 extends AppCompatActivity {

    ImageView bottom_bar, bet_plus, bet_minus;
    TextView win, level_bet, playBalance, playLevel, textLevel;
    SharedPreferences sharedpreferences;
    int balance, level, betLevel=1, winLevel;
    boolean startCycleGame = false;
    float xdpi, ydpi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game1);

        win = findViewById(R.id.win);
        level_bet = findViewById(R.id.level_bet);
        playBalance = findViewById(R.id.playBalance);
        playLevel = findViewById(R.id.playLevel);
        bottom_bar = findViewById(R.id.bottom_bar);
        bet_plus = findViewById(R.id.bet_minus);
        bet_minus = findViewById(R.id.bet_plus);

        bottom_bar.bringToFront();
        win.bringToFront();
        level_bet.bringToFront();
        bet_plus.bringToFront();
        bet_minus.bringToFront();

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if((sharedpreferences.contains(BalKey)) & (sharedpreferences.contains(Level))){

            balance = sharedpreferences.getInt(BalKey, 213017);
            level = sharedpreferences.getInt(Level, 1);
            SetFields();
        }
        else {
            balance = 213710;
            level = 1;
            SetFields();
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int density = displayMetrics.densityDpi;
        int heightPixels = displayMetrics.heightPixels;
        int weightPixels = displayMetrics.widthPixels;
        xdpi = displayMetrics.xdpi;
        ydpi = displayMetrics.ydpi;
        int xdpi1 = Math.round(weightPixels/density);
        int ydpi1 = Math.round(heightPixels/density);

        initWheel(R.id.slot_1);
        initWheel(R.id.slot_2);
        initWheel(R.id.slot_3);
        initWheel(R.id.slot_4);
        initWheel(R.id.slot_5);




        Button button = findViewById(R.id.button);
        button.bringToFront();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCycleGame = false;
                startGame();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    startCycleGame = true;
                    startGame();

                    Toast.makeText(getApplicationContext(), "Auto Spin enabled, click to disable ",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
        });

        level_bet.setText(String.format("%5d",betLevel));

    }

    private void updateBalance(){

       Integer item1 = getWheel(R.id.slot_1).getCurrentItem();
       Integer item2 = getWheel(R.id.slot_2).getCurrentItem();
       Integer item3 = getWheel(R.id.slot_3).getCurrentItem();
       Integer item4 = getWheel(R.id.slot_4).getCurrentItem();
       Integer item5 = getWheel(R.id.slot_5).getCurrentItem();

       if(item1.equals(item2) & item2.equals(item3) & item3.equals(item4) & item4.equals(item5)){
           balance = balance +(betLevel*100);
           winLevel = betLevel*100;
       }
       else if((item1.equals(item2) & item2.equals(item3) & item3.equals(item4)) || (item2.equals(item3) & item3.equals(item4) & item4.equals(item5))){
           balance = balance +(betLevel*10);
           winLevel = betLevel*10;
       }

       else if((item1.equals(item2) & item2.equals(item3)) || (item2.equals(item3)) & (item3.equals(item4)) || (item3.equals(item4) & item4.equals(item5))){
           balance = balance + (betLevel*5);
           winLevel = betLevel*5;
       }

       else if(item1.equals(item2) || item2.equals(item3) || item3.equals(item4) || item4.equals(item5)){
           balance = balance + (betLevel*2);
           winLevel = betLevel*2;
           //win.setText(String.format("%5d", temp3));
       }

       else
           {if(balance-(betLevel*5)>0)
           balance = balance - (betLevel*5);
               winLevel = 0;
           }
    }

    public void startGame(){

            mixWheel(R.id.slot_1);
            mixWheel(R.id.slot_2);
            mixWheel(R.id.slot_3);
            mixWheel(R.id.slot_4);
            mixWheel(R.id.slot_5);

    }

    public void SetFields(){
        int hightBal = balance/100000;
        int medBal = balance/100%1000;
        int lowBal = balance%100;
        playBalance.setText(String.format("%5d", balance));
        //playLevel.setText("Level" + " " + level);
        win.setText(String.format("%5d", winLevel));
    }

    public void SaveToPref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(BalKey, balance);
        editor.putInt(Level, level);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //SaveToPref();
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.bet_plus:
                betLevel++;
                level_bet.setText(String.format("%5d",betLevel));
                break;

            case R.id.bet_minus:
                if(betLevel>1)
                betLevel--;
                level_bet.setText(String.format("%5d",betLevel));
                break;
        }

    }

    private void initWheel(int id) {
        //WheelView wheel = getWheel(id);
        WheelView wheel = findViewById(id);
        wheel.setViewAdapter(new SlotMachineAdapter(this));
        wheel.setCurrentItem((int)(Math.random() * 10));

       // wheel.addChangingListener(changedListener);
       // wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
        wheel.setVisibleItems(3);
        wheel.setDrawShadows(true);
        wheel.addScrollingListener(scrolledListener);
        //wheel.setWheelBackground(R.drawable.bg1);
    }

    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-8 + (int)(Math.random() * 5), 2000);
    }

    private int wheelIdSame(int id){
        return getWheelValue(id);
    }

    private int getWheelValue(int id) {
        return getWheel(id).getCurrentItem();
    }

    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }

    private boolean wheelScrolled = false;

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateBalance();
            if(startCycleGame)
            startGame();
            SetFields();
            SaveToPref();
        }
    };

    private class SlotMachineAdapter extends AbstractWheelAdapter {

        // Layout inflater
        Integer[] ThumbSpin = new Integer[]{
            R.drawable.blue_diamond, R.drawable.cart_holder,
                    R.drawable.ten_play, R.drawable.heart_play,
                    R.drawable.round_casino, R.drawable.round_pik,
                    R.drawable.round_rollet, R.drawable.coctail
        };
        private Context context;

        private List<SoftReference<Bitmap>> images;

        /**
         * Constructor
         */
        public SlotMachineAdapter(Context context) {
            this.context = context;
            images = new ArrayList<SoftReference<Bitmap>>(ThumbSpin.length);
            for (int id : ThumbSpin) {
                images.add(new SoftReference<Bitmap>(loadImage(id)));
            }
        }

        @Override
        public int getItemsCount() {
            return ThumbSpin.length;
        }

        // Layout params for image view
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            ImageView img;

            if (cachedView != null) {
                img = (ImageView) cachedView;
            } else {
                img = new ImageView(context);
            }

            img.setLayoutParams(params);

            SoftReference<Bitmap> bitmapRef = images.get(index);
            Bitmap bitmap = bitmapRef.get();
            if (bitmap == null) {
                bitmap = loadImage(ThumbSpin[index]);
                images.set(index, new SoftReference<Bitmap>(bitmap));
            }
            img.setImageBitmap(bitmap);

            /*Bitmap bitmap = BitmapFactory.decodeFile(ThumbSpin.get(index));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, widthWheel, heightWheel, true);
            bitmap.recycle();

            img.setImageBitmap(scaled);*/

            return img;
        }

        private Bitmap loadImage(int id) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, (int)xdpi/2, (int)ydpi/2, true);
            bitmap.recycle();
            return scaled;
        }
    }
}
