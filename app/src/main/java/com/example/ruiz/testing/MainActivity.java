package com.example.ruiz.testing;

//Testing Github version control
//Stop The Clock v0.4

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    //private static final String FORMAT = "%02d:%02d:%02d";
    TextView millitext, matchText;
    int millisec;
    CountDownTimer cdmilli = null;
    Chronometer gameTime;
    //double milliTime, millis;
    Button start, stop, playAgain;
    long numCheck1 = 0;
    long numCheck2 = 0;
    long secCheck = 0;
    long milCheck = 0;
    Random r = new Random();
    Boolean milliMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchText = (TextView)findViewById(R.id.textView2);
        millitext = (TextView)findViewById(R.id.textViewCountdown);
        //milliTime = System.currentTimeMillis();
        //millis = System.currentTimeMillis() - milliTime;
        //start = (Button) findViewById(R.id.button2); //For Chronometer
        //stop = (Button) findViewById(R.id.button3); //For Chronometer
        //playAgain = (Button) findViewById(R.id.button4);
        //gameTime = (Chronometer) findViewById(R.id.chronometer); //For Chronometer
/*
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameTime.setBase(SystemClock.elapsedRealtime());
                gameTime.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameTime.stop();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameTime.setBase(SystemClock.elapsedRealtime());
            }
        });*/

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

    }

    //Nav Bar
    private void addDrawerItems() {
        String[] osArray = { "Easy", "Medium", "Hard"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Still Under Development", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Boom button
    public void buttonSurprise(View view) {
        if(cdmilli!=null) {
            Toast testToast = Toast.makeText(this,"YOU CAN'T RESET WHILE TIMER IS TICKING",Toast.LENGTH_SHORT);
            testToast.show();
        } else if(cdmilli == null){
            Toast testToast = Toast.makeText(this, "BOOM", Toast.LENGTH_SHORT);
            testToast.show();
            numCheck1 = r.nextInt(50 - 35) + 35;
            numCheck2 = r.nextInt(800 - 200) + 200;
            matchText.setText("" + String.format("%02d.%03d",numCheck1,numCheck2));
        }
    }

    //Start
    public void startTimer(View view) {
        if(cdmilli!=null) {
            Toast testToast = Toast.makeText(this, "TIMER ALREADY TICKING", Toast.LENGTH_SHORT);
            testToast.show();
        } else if(cdmilli==null && numCheck1==0) {
            Toast testToast = Toast.makeText(this,"PRESS 'BOOM' BEFORE PLAYING",Toast.LENGTH_SHORT);
            testToast.show();
        } else {
            cdmilli = new CountDownTimer(60000, 1) {
                public void onTick(long millisUntilFinished) {

                    long m = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    long s = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - m * 60 * 1000);
                    long ms = millisUntilFinished - m * 60 * 1000 - s * 1000;
                    secCheck = s;
                    milCheck = ms;

                    millitext.setText("" + String.format("%02d.%03d",
                        /*TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))*/s, ms));
                }

                public void onFinish() {
                    millitext.setText("You Lose");
                    cdmilli = null;
                }
            };
            cdmilli.start();
        }
    }
/*
    public void startTimer(View view) {
        cdmilli = new CountDownTimer(3000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
            }
        };
        cdmilli.start();
    }*/

    //Stop
    public void cancelTimer(View view) {
        if (cdmilli == null) {
            Toast testToast = Toast.makeText(this,"THE GAME HASN'T STARTED YET",Toast.LENGTH_SHORT);
            testToast.show();
        }
        if (cdmilli != null) {
            cdmilli.cancel();
            if ((numCheck2 - 200) <= milCheck && milCheck <= (numCheck2 + 200)){
                milliMatch = true;
            } else {
                milliMatch = false;
            }
            if (numCheck1 == secCheck && milliMatch) {
                Toast testToast = Toast.makeText(this,"CLOSE ENOUGH",Toast.LENGTH_SHORT);
                testToast.show();
            } else if (numCheck1 == secCheck && numCheck2 == milCheck) {
                Toast testToast = Toast.makeText(this,"PERFECT",Toast.LENGTH_SHORT);
                testToast.show();
            } else {
                Toast testToast = Toast.makeText(this,"NOT NICE",Toast.LENGTH_SHORT);
                testToast.show();
            }
            cdmilli = null;
        }
    }
}
