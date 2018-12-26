package com.efexcas.nefr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.core.CoreActivity;
import com.core.CoreApp;
import com.efexcas.nefr.R;

import static com.core.CoreActivity.INTENT_ADDRESS;
import static com.core.CoreActivity.INTENT_CLASS;
import static com.core.CoreActivity.INTENT_COLOR;
import static com.core.CoreActivity.INTENT_DRAWABLE;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CoreApp.initializeAppAnalytics("389974778414402","5f08bd35-aa3d-4367-9527-74970d33b92f","bi93VdoTrMbbupBcMLPsKe","UA-131409607-1");

        startActivity(new Intent(StartActivity.this, CoreActivity.class)
                .putExtra(INTENT_ADDRESS, "http://go.efexcas.shop/index.php")
                .putExtra(INTENT_DRAWABLE, "file:///android_asset/congratulations.gif")
                .putExtra(INTENT_COLOR, "#212121")
                .putExtra(INTENT_CLASS, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
