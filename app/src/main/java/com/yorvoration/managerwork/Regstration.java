package com.yorvoration.managerwork;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yorvoration.managerwork.fragment.FragmentReg1;

import java.util.Objects;

public class Regstration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regstartion);
        Objects.requireNonNull(getSupportActionBar()).hide();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentReg1 fragmentReg1 = new FragmentReg1();
        fragmentTransaction.replace(R.id.fragmentlayoutregster, fragmentReg1);
        fragmentTransaction.commit();

    }
}
