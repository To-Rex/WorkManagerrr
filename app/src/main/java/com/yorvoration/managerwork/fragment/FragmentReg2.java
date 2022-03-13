package com.yorvoration.managerwork.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yorvoration.managerwork.R;
import com.yorvoration.managerwork.SqlData;

public class FragmentReg2 extends Fragment {
    private SqlData MyDb;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private FirebaseAuth auth;
    private final int PICK_IMAGE_REQUEST = 71;
    String UID, TIL, REJIM, KALIT, PAROL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg2,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        MyDb = new SqlData(getContext());
        db = FirebaseFirestore.getInstance();
        Button btnfrg2next = view.findViewById(R.id.btnfrg2next);

        btnfrg2next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentReg3 fragmentReg3 = new FragmentReg3();
                fragmentTransaction.replace(R.id.fragmentlayoutregster, fragmentReg3);
                fragmentTransaction.commit();
            }
        });

    }
}
