package com.yorvoration.managerwork;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;
    DocumentReference documentReference;

    Intent intent;
    private EditText ediregemail,edilogpass;
    private ProgressBar progressBarlog;
    String rool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        MyDb = new SqlData(this);

        Button btnlogregister = findViewById(R.id.btnsubmit1);
        Button btnsubmit = findViewById(R.id.btnsubmit);
        progressBarlog = findViewById(R.id.progressBarlog);

        ediregemail = findViewById(R.id.ediregemail);
        edilogpass = findViewById(R.id.ediregpass);

        progressBarlog.setVisibility(View.GONE);
        btnlogregister.setOnClickListener(view -> {
            intent = new Intent(Login.this,Regstration.class);
            startActivity(intent);
        });
        btnsubmit.setOnClickListener(view -> {
            String email = ediregemail.getText().toString();
            final String password = edilogpass.getText().toString();

            if (TextUtils.isEmpty(email)) {
                ediregemail.setError(getString(R.string.bosh));
                return;
            }

            if (TextUtils.isEmpty(password)) {
                edilogpass.setError(getString(R.string.bosh));
                return;
            }
            progressBarlog.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, task -> {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpass.setError("kam");
                            } else {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpass.setError(getString(R.string.xatolik));
                                ediregemail.setError(getString(R.string.xatolik));
                                Toast.makeText(Login.this, getString(R.string.xatolik), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (password.length() < 6) {
                                edilogpass.setError("kam");
                            } else {
                                String userid1 = auth.getCurrentUser().getUid();
                                documentReference = db.document("User/" + userid1);
                                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        rool = documentSnapshot.getString("ROOL");

                                    }
                                });
                                String til = "en";
                                String rejim = "rejim";
                                String kalit = "1";
                                String parol = edilogpass.getText().toString();
                                Boolean result = MyDb.kiritish(userid1, til, rejim, kalit, parol);
                                progressBarlog.setVisibility(View.GONE);
                                if (result == true) {
                                    if(Objects.equals(rool,"studnet")){
                                        intent = new Intent(Login.this, Sample.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    if (Objects.equals(rool,"Staff")){
                                        intent = new Intent(Login.this, Staffe.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    progressBarlog.setVisibility(View.GONE);

                                } else {
                                    progressBarlog.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), getString(R.string.xatolik), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        });
    }
}
