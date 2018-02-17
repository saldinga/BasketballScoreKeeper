package com.example.android.basketballscorekeeper;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countTimer;
    TextView timerText;
    Long timeConst = (long) (10 * 60 * 1000 + 100);
    Long timeLeft = timeConst;
    MediaPlayer player;
    boolean timerIsRunning = false;

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    int currentScoreA = 0;
    int currentScoreB = 0;

    int quarter = 1;
    int qurrentHistoryIdTeamA = R.id.hq1_team_a;
    int qurrentHistoryIdTeamB = R.id.hq1_team_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer);
        player = MediaPlayer.create(this, R.raw.alarm);
    }

    /////////////// Timer methods

    public void timerLoad(long time) {

        countTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long l) {

                String min;
                String sec;

                timeLeft = l;

                long minutes = l / (1000 * 60);
                min = Long.toString(minutes);
                if (minutes < 10) min = "0" + min;

                long seconds = (l - minutes * 1000 * 60) / 1000;
                sec = Long.toString(seconds);
                if (seconds < 10) sec = "0" + sec;

                timerText.setText(min + ":" + sec);
                timerIsRunning = true;
            }

            @Override
            public void onFinish() {
                timerText.setText("00:00");
                timeLeft = timeConst;
                player.start();
                timerIsRunning = false;
            }
        };
    }

    public void start(View v) {
        if (!timerIsRunning) {
            timerLoad(timeLeft);
            countTimer.start();
        }
    }

    public void pause(View v) {
        if (timerIsRunning) {
            countTimer.cancel();
            timerIsRunning = false;
        }
    }

    public void resetTime(View v) {
        if (timerIsRunning) {
            countTimer.cancel();
        }
        timeLeft = timeConst;
        timerText.setText("10:00");
        timerIsRunning = false;
    }

    public void startNewGame(View v) {
        resetTime(v);

        timerIsRunning = false;

        scoreTeamA = 0;
        scoreTeamB = 0;

        currentScoreA = 0;
        currentScoreB = 0;

        quarter = 1;
        qurrentHistoryIdTeamA = R.id.hq1_team_a;
        qurrentHistoryIdTeamB = R.id.hq1_team_b;

        int[] arrayOfId = {R.id.teamAScore, R.id.teamBScore, R.id.hq1_team_a, R.id.hq2_team_a,
                R.id.hq3_team_a, R.id.hq4_team_a, R.id.hq1_team_b, R.id.hq2_team_b,
                R.id.hq3_team_b, R.id.hq4_team_b};

        for (int id : arrayOfId) {
            display("0", id);
        }
        display("Q: I", R.id.quarter);
    }

    ///// Scoring methods

    public void addPoints(int points, View view) {
        if (view.getTag().toString().equals("teamA")) {
            if ((scoreTeamA >= 0 && points > 0 && currentScoreA >= 0) ||
                    (scoreTeamA > 0 && points < 0 && currentScoreA > 0)) {
                scoreTeamA += points;
                currentScoreA += points;
            }
            display(Integer.toString(scoreTeamA), R.id.teamAScore);
            display(Integer.toString(currentScoreA), qurrentHistoryIdTeamA);
        } else {
            if ((scoreTeamB >= 0 && points > 0 && currentScoreB >= 0) ||
                    (scoreTeamB > 0 && points < 0 && currentScoreB > 0)) {
                scoreTeamB += points;
                currentScoreB += points;
            }
            display(Integer.toString(scoreTeamB), R.id.teamBScore);
            display(Integer.toString(currentScoreB), qurrentHistoryIdTeamB);
        }
    }

    public void addThreePoints(View v) {
        addPoints(3, v);
    }

    public void addTwoPoints(View v) {
        addPoints(2, v);
    }

    public void addOnePoint(View v) {
        addPoints(1, v);
    }

    public void removeOnePoint(View v) {
        addPoints(-1, v);
    }

    // method new quarter

    public void nextQuarter(View v) {
        quarter += 1;
        display("10:00", R.id.timer);


        switch (quarter) {
            case 2:
                qurrentHistoryIdTeamA = R.id.hq2_team_a;
                qurrentHistoryIdTeamB = R.id.hq2_team_b;
                display("Q: II", R.id.quarter);
                break;
            case 3:
                qurrentHistoryIdTeamA = R.id.hq3_team_a;
                qurrentHistoryIdTeamB = R.id.hq3_team_b;
                display("Q: III", R.id.quarter);
                break;
            default:
                qurrentHistoryIdTeamA = R.id.hq4_team_a;
                qurrentHistoryIdTeamB = R.id.hq4_team_b;
                display("Q: IV", R.id.quarter);
                break;
        }

        if (timerIsRunning) {
            countTimer.cancel();
            timerIsRunning = false;
        }
        currentScoreA = 0;
        currentScoreB = 0;
        timeLeft = timeConst;
    }

    // method for displaying

    public void display(String text, int id) {
        TextView textView = findViewById(id);
        textView.setText(text);
    }
}
