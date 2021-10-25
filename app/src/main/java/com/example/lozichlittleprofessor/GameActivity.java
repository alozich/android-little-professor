package com.example.lozichlittleprofessor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    GameParam currentGame;
    private final String MYAPP = "LittleProfessor";
    private int max;
    private int score;
    private String[] ops;
    int numOps = 0;
    int answer;
    int numTries = 3;
    private final long TOTALTIME = 15000;
    private TextView timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentGame = MainActivity.game;
        Log.i(MYAPP, "Player: " + currentGame.getPlayerName());
        Log.i(MYAPP, "Level: " + currentGame.getLevel());
        switch (currentGame.getLevel()) {
            case "Beginner":
                max = 9;
                break;
            case "Intermediate":
                max = 99;
                break;
            case "Expert":
                max = 999;
                break;
        }
        Log.i(MYAPP, "Max: " + max);
        numOps = currentGame.getOperationsOn().length;


        //counter = m;
        CountDownTimer theTimer = new CountDownTimer(TOTALTIME, 500) {
            @Override
            public void onTick(long m) {
                Log.i("here", m + " ");
                timeLeft = (TextView) findViewById(R.id.tvTimeLeft);
                timeLeft.setText("Remaining: " + String.valueOf(m / 1000));
            }

            @Override
            public void onFinish() {
                MainActivity.recentScore = score;
                EndActivity();
            }
        };
        setContentView(R.layout.activity_game);
        theTimer.start();
        makeProblem();

    }
    private void EndActivity()
    {
        this.finish();
    }
    private void makeProblem() {
        Random r = new Random();
        int min = 0;
        int num1 = r.nextInt((max - min) + 1) + min;
        int num2 = r.nextInt((max - min) + 1) + min;
        int operation = r.nextInt(numOps);

        TextView tv = findViewById(R.id.tvProblem);

        switch (currentGame.getOperationsOn()[operation]) {
            case "+":
                answer = num1 + num2;
                tv.setText(num1 + " + " + num2);
                break;
            case "-":
                answer = num1 - num2;
                tv.setText(num1 + " - " + num2);
                break;
            case "x":
                answer = num1 * num2;
                tv.setText(num1 + " * " + num2);
                break;
            case "/":
                answer = num1 / num2;
                tv.setText(num1 + " / " + num2);
                break;
        }

        Log.i(MYAPP, "Answer: " + answer);
        numTries = 3;
    }
    private void UpdateScore()
    {
        TextView tv = findViewById(R.id.tvScore);
        String s = "Score: " + score;
        tv.setText(s);
    }
    public void checkButtonClick(View view)
    {
        Button b = (Button) view;
        EditText et = findViewById(R.id.editTextNumber);
        int input = Integer.parseInt(et.getText().toString());
        if (input == answer)
        {
            if(numTries == 3)
                score += 10;
            else if(numTries == 2)
                score += 5;
            else if (numTries == 1)
                score += 3;
            makeProblem();
            UpdateScore();
            et.setText("");
        }
        else
        {
            numTries--;
            if (numTries == 0)
                makeProblem();
            et.setText("");
            et.setHint("Incorrect");

        }
    }
}