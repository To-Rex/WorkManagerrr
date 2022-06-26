package app.doubleh.wakeup;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    int veek = 1, t2Hour, t2minut;
    String vd  = "";
    TextView txttimeinfo;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarmclock);

        Executor executor = ContextCompat.getMainExecutor(this);

        AnalogClock clockk = findViewById(R.id.clockk);
        txttimeinfo = findViewById(R.id.txttimeinfo);



        Alarm();
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                veek = 0;
                mediaPlayer.stop();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(" ")
                .setSubtitle(" Ekranga qarang")
                .setNegativeButtonText(" ")
                .build();


        clockk.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            t2Hour = hourOfDay;
                            t2minut = minute;

                            String time = t2Hour + ":" + t2minut;
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat f24format = new SimpleDateFormat("HH:mm");
                            try {
                                Date date = f24format.parse(time);
                                //SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
                                txttimeinfo.setText(f24format.format(date).substring(0, 5));
                                vd = f24format.format(date).substring(0, 5);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 24, 0, true);
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(t2Hour, t2minut);
            timePickerDialog.show();
        });
    }

    private void Alarm(){
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            Alarm();
            if (veek == 1){
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                if (vd.equals(currentDateandTime)){
                    playmusic();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> biometricPrompt.authenticate(promptInfo), 600);
                }
            }
        },1000);
    }

    private void playmusic() {
        if (veek == 1) {
            mediaPlayer.start();
            playmusic1();
        }else {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                playmusic();
            },1000);
        }
    }
    private void playmusic1() {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (veek == 1) {
                        mediaPlayer.start();
                        playmusic1();
                    }else {
                        playmusic1();
                    }
                },9000);
    }

    @Override
    public void onBackPressed() {
        if (veek == 0){
            finish();
        }
    }
}