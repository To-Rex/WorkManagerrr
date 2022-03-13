package com.yorvoration.managerwork;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Guest extends AppCompatActivity {
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.guest);
    }
}
