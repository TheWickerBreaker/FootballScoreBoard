package com.thewickerbreaker.footballscoreboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timer;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    int timerCount = 900;

    Button shareButton;
    Button upButton;

    ImageView test;

    LinearLayout possessionButtons, downButtons, placementButtons, quarterButtons, scoreButtons,
            mainControlButtons, menuButtons, timeButtons;



    private Bitmap myBitmap;

    ImageView homeMarker, visitorMarker;

    int down = 1;
    int ballOn = 0;
    int toGo = 0;
    int quarter = 1;
    int homeScore = 0;
    int visitorScore = 0;

    Boolean homesBall = true;
    private File imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (ImageView) findViewById(R.id.test);

        possessionButtons = (LinearLayout) findViewById(R.id.possession_buttons);
        downButtons = (LinearLayout) findViewById(R.id.down_buttons);
        placementButtons = (LinearLayout) findViewById(R.id.placement_buttons);
        quarterButtons = (LinearLayout) findViewById(R.id.quarter_buttons);
        scoreButtons = (LinearLayout) findViewById(R.id.score_buttons);
        mainControlButtons = (LinearLayout) findViewById(R.id.main_control_buttons);
        menuButtons = (LinearLayout) findViewById(R.id.menu_buttons);
        timeButtons = (LinearLayout) findViewById(R.id.time_layout);

        upButton = (Button) findViewById(R.id.upButton);
        shareButton = (Button) findViewById(R.id.share_button);

        homeMarker = (ImageView) findViewById(R.id.home_marker);
        visitorMarker = (ImageView) findViewById(R.id.visitor_marker);

        homeMarker.setVisibility(View.VISIBLE);
        visitorMarker.setVisibility(View.INVISIBLE);

        homesBall = true;



        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timer = (TextView) findViewById(R.id.timer);

        seekBar.setMax(900);
        timerCount = 900;
        seekBar.setProgress(timerCount);

        seekBar.setEnabled(true);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    public void possessionClick(View view) {

        possessionButtons.setVisibility(View.VISIBLE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);

    }

    public void homeClick(View view) {

        homeMarker.setVisibility(View.VISIBLE);
        visitorMarker.setVisibility(View.INVISIBLE);

        homesBall = true;

    }

    public void visitorClick(View view) {

        visitorMarker.setVisibility(View.VISIBLE);
        homeMarker.setVisibility(View.INVISIBLE);

        homesBall = false;
    }

    public void possessionClose(View view) {

        possessionButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);
    }

    public void downClick(View view) {

        downButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);

    }

    public void minusDown(View view) {

        down = down - 1;

        if (down < 1) {
            down = 1;
        }

        displayDown(down);

    }

    public void plusDown(View view) {

        down = down + 1;

        if (down > 4) {
            down = 4;
        }

        displayDown(down);

    }

    public void displayDown(int down) {
        TextView downAdjust = (TextView) findViewById(R.id.down_adjust);
        downAdjust.setText(String.valueOf(down));
    }

    public void downClose(View view) {

        downButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);
    }

    public void placementClick(View view) {

        placementButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
    }

    public void minusBallOn(View view) {

        ballOn = ballOn - 1;

        if (ballOn < 0) {
            ballOn = 0;
        }

        displayBallOn(ballOn);

    }

    public void plusBallOn(View view) {

        ballOn = ballOn + 1;

        if (ballOn > 50) {
            ballOn = 50;
        }

        displayBallOn(ballOn);

    }

    public void displayBallOn(int ballOn) {
        TextView ballOnAdjust = (TextView) findViewById(R.id.ball_on_adjust);
        ballOnAdjust.setText(String.valueOf(ballOn));
    }

    public void placementClose(View view) {

        placementButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);
    }

    public void minusToGo(View view) {

        toGo = toGo - 1;

        if (toGo < 0) {
            toGo = 0;
        }

        displayToGo(toGo);

    }

    public void plusToGo(View view) {

        toGo = toGo + 1;

        if (toGo > 100) {
            toGo = 100;
        }

        displayToGo(toGo);

    }

    public void displayToGo(int toGo) {
        TextView toGoAdjust = (TextView) findViewById(R.id.to_go_adjust);
        toGoAdjust.setText(String.valueOf(toGo));
    }

    public void quarterClick(View view) {

        quarterButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
    }

    public void minusQuarter(View view) {

        quarter = quarter - 1;

        if (quarter < 1) {
            quarter = 1;
        }

        displayQuarter(quarter);

    }

    public void plusQuarter(View view) {

        quarter = quarter + 1;

        if (quarter > 4) {
            quarter = 4;
        }

        displayQuarter(quarter);

    }

    public void displayQuarter(int quarter) {
        TextView qtrAdjust = (TextView) findViewById(R.id.qtr_adjust);
        qtrAdjust.setText(String.valueOf(quarter));
    }

    public void quarterClose(View view) {

        quarterButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);
    }


    public void scoreClick(View view) {

        scoreButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
    }

    public void addTouchdown(View view) {

        if (homesBall == true) {

            homeScore = homeScore + 6;
            displayHomeScore(homeScore);

        } else {

            visitorScore = visitorScore + 6;
            displayVisitorScore(visitorScore);

        }

    }

    public void addExtraPoint(View view) {

        if (homesBall == true) {

            homeScore = homeScore + 1;
            displayHomeScore(homeScore);

        } else {

            visitorScore = visitorScore + 1;
            displayVisitorScore(visitorScore);

        }

    }

    public void addTwoPoints(View view) {

        if (homesBall == true) {

            homeScore = homeScore + 2;
            displayHomeScore(homeScore);

        } else {

            visitorScore = visitorScore + 2;
            displayVisitorScore(visitorScore);

        }

    }

    public void addFieldGoal(View view) {

        if (homesBall == true) {

            homeScore = homeScore + 3;
            displayHomeScore(homeScore);

        } else {

            visitorScore = visitorScore + 3;
            displayVisitorScore(visitorScore);

        }

    }

    public void addSafety(View view) {

        if (homesBall == true) {

            homeScore = homeScore + 2;
            displayHomeScore(homeScore);

        } else {

            visitorScore = visitorScore + 2;
            displayVisitorScore(visitorScore);

        }

    }


    public void scoreClose(View view) {

        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);
    }

    public void homeBoardClick(View view) {

        homesBall = true;

        homeMarker.setVisibility(View.VISIBLE);
        visitorMarker.setVisibility(View.INVISIBLE);

        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
    }

    public void visitorBoardClick(View view) {

        homesBall = false;

        homeMarker.setVisibility(View.INVISIBLE);
        visitorMarker.setVisibility(View.VISIBLE);

        menuButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.VISIBLE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
    }

    public void displayHomeScore(int homeScore) {
        TextView homeScoreTxt = (TextView) findViewById(R.id.home_score);
        homeScoreTxt.setText(String.valueOf(homeScore));
    }

    public void displayVisitorScore(int visitorScore) {
        TextView visitorScoreTxt = (TextView) findViewById(R.id.visitor_score);
        visitorScoreTxt.setText(String.valueOf(visitorScore));
    }

    public void resetClick(View view) {

        countDownTimer.cancel();
        timerCount = 900;
        seekBar.setProgress(timerCount);
        counterIsActive = false;
        seekBar.setEnabled(true);

        homeMarker.setVisibility(View.VISIBLE);
        visitorMarker.setVisibility(View.INVISIBLE);

        homesBall = true;

        down = 1;
        displayDown(down);

        ballOn = 0;
        displayBallOn(ballOn);

        toGo = 0;
        displayToGo(toGo);

        quarter = 1;
        displayQuarter(quarter);

        homeScore = 0;
        displayHomeScore(homeScore);

        visitorScore = 0;
        displayVisitorScore(visitorScore);

    }

    public void resetTimer() {


        counterIsActive = false;
        seekBar.setEnabled(true);

        countDownTimer.cancel();

    }

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);

        if (seconds <= 9) {

            secondsString = "0" + secondsString;
        }

        timer.setText(Integer.toString(minutes) + ":" + secondsString);

    }

    public void timerTapped(View viw) {

        timeButtons.setVisibility(View.VISIBLE);
        upButton.setVisibility(View.GONE);
        menuButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);

        if (counterIsActive == false) {


            counterIsActive = true;
            seekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {


                public void onTick(long millisecondsUntilDone) {
                    timerCount = timerCount - 1;
                    seekBar.setProgress(timerCount);
                    updateTimer((int) millisecondsUntilDone / 1000);


                }

                public void onFinish() {

                    return;


                }


            }.start();

        } else {

            counterIsActive = false;
            seekBar.setEnabled(true);
            countDownTimer.cancel();

        }
    }

    public void upButton(View view) {
        upButton.setVisibility(View.GONE);
        menuButtons.setVisibility(View.VISIBLE);

        possessionButtons.setVisibility(View.GONE);
        downButtons.setVisibility(View.GONE);
        placementButtons.setVisibility(View.GONE);
        quarterButtons.setVisibility(View.GONE);
        scoreButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.GONE);


    }

    public void controlsClick(View view) {
        menuButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);

    }

    public void shareClick(View view) {
        // TODO: 8/31/2016 find out how to share a screenshot on social media.
    }

    public void closeClick(View view) {
        upButton.setVisibility(View.VISIBLE);
        menuButtons.setVisibility(View.GONE);


    }

    public void mainCloseClick(View view) {
        menuButtons.setVisibility(View.VISIBLE);
        mainControlButtons.setVisibility(View.GONE);
    }

    public void timeClose(View view) {
        timeButtons.setVisibility(View.GONE);
        mainControlButtons.setVisibility(View.VISIBLE);

    }

    public void timeClick(View view) {
        mainControlButtons.setVisibility(View.GONE);
        timeButtons.setVisibility(View.VISIBLE);

    }

    public void resetTime(View view) {

        if (counterIsActive == false) {

            timerCount = 900;
            seekBar.setProgress(timerCount);
            counterIsActive = false;
            seekBar.setEnabled(true);

        }
    }

    public void startTimerTapped(View view) {
        if (counterIsActive == false) {


            counterIsActive = true;
            seekBar.setEnabled(false);


            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {


                public void onTick(long millisecondsUntilDone) {
                    timerCount = timerCount - 1;
                    seekBar.setProgress(timerCount);
                    updateTimer((int) millisecondsUntilDone / 1000);


                }

                public void onFinish() {

                    return;


                }


            }.start();
        }
    }

    public void stopTimerTapped(View view){
        counterIsActive = false;
        seekBar.setEnabled(true);
        countDownTimer.cancel();
    }



   public void shareClicked(View view){

       final RelativeLayout scoreBoard = (RelativeLayout) findViewById(R.id.score_board);

       assert scoreButtons != null;
       scoreButtons.post(new Runnable() {
           @Override
           public void run() {
               Bitmap pic = takeScreenshot(scoreButtons);

               try{


                   if (pic != null){
                       saveScreenShot(pic);
                   }

               } catch (Exception e){

                   e.printStackTrace();
               }
           }
       });
   }


    private Bitmap takeScreenshot(View view){
        Bitmap screenshot = null;

        try {
            int width = view.getWidth();
            int height = view.getHeight();

            screenshot = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


            Canvas c = new Canvas(screenshot);
            view.draw(c);

            test.setImageBitmap(screenshot);
        } catch (Exception e){

            e.printStackTrace();
        }

        return screenshot;
    }

    private void saveScreenShot(Bitmap bm){

        ByteArrayOutputStream baos = null;
        File file = null;

        try{

            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 40, baos);



            file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.png");
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(baos.toByteArray());
            fos.close();



        }catch (Exception e){

            e.printStackTrace();
        }

    }



}