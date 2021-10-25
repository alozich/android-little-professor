package com.example.lozichlittleprofessor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final String MYAPP = "LittleProfessor";
    private EditText etName;
    private int checkedRB;
    private final String[] levels = {"Beginner", "Intermediate", "Expert"};
    private CheckBox[] checkBoxes;
    private final int numCB = 4;
    private final String[] operations = {"+", "-", "x", "/"};
    private TextView tvFirst;
    private TextView tvSecond;
    private TextView tvThird;

    //using public static data transfer
    //using only two activities, much easier to use this method
    //using two variables
    public static GameParam game = new GameParam();
    public static int recentScore;

    //Hi-Scores
    private int firstScore;
    private int secondScore;
    private int thirdScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout myLayout = new LinearLayout(this);
        myLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tvName = new TextView(this);
        tvName.setText("Name");
        myLayout.addView(tvName);

        etName = new EditText(this);
        etName.setHint("The Little Professor");
        etName.setInputType(1);         //TYPE_CLASS_TEXT = 1
        myLayout.addView(etName);

        TextView tvLevel = new TextView(this);
        tvLevel.setText("Level");
        myLayout.addView(tvLevel);

        RadioGroup rgLevel = new RadioGroup(this);
        myRGCheckChange rbCC = new myRGCheckChange();
        rgLevel.setOnCheckedChangeListener(rbCC);

        int numRB = 3;
        RadioButton[] rbs = new RadioButton[numRB];
        for(int i = 0; i < numRB; i++)
        {
            rbs[i] = new RadioButton(this);
            rbs[i].setChecked(false);
            rbs[i].setText(levels[i]);
            rgLevel.addView(rbs[i]);
        }
        rgLevel.check(rbs[0].getId());  //setting default to beginner
        myLayout.addView(rgLevel);

        TextView tvOperation = new TextView(this);
        tvOperation.setText("Operations");
        myLayout.addView(tvOperation);

        checkBoxes = new CheckBox[numCB];
        for(int i = 0; i < numCB; i++)
        {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setChecked(false);
            checkBoxes[i].setText(operations[i]);
            myLayout.addView(checkBoxes[i]);
        }

        Button playButton = new Button(this);
        playButton.setText("Play");
        playButton.setEnabled(true);
        onPlayButtonClicked pbClicked = new onPlayButtonClicked();
        playButton.setOnClickListener(pbClicked);
        myLayout.addView(playButton);

        TextView tvHiScore = new TextView(this);
        tvFirst = new TextView(this);
        tvSecond = new TextView(this);
        tvThird = new TextView(this);

        tvHiScore.setText("Scores");
        tvFirst.setText("");
        tvSecond.setText("");
        tvThird.setText("");

        myLayout.addView(tvHiScore);
        myLayout.addView(tvFirst);
        myLayout.addView(tvSecond);
        myLayout.addView(tvThird);

        setContentView(myLayout);
    }

    private class myRGCheckChange implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup grp, int checkedID) {
            RadioButton rb = (RadioButton) grp.findViewById(checkedID);
            Log.i(MYAPP, rb.getText().toString());
            checkedRB = checkedID;
        }
    }

    private class onPlayButtonClicked implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
            Log.i(MYAPP, "Play button clicked");

            //Checking if user entered a name
            if (etName.getText().toString().isEmpty())  //if user does not enter a name
            {
                Toast.makeText(getApplicationContext(), "Enter a name.", Toast.LENGTH_SHORT).show();
                etName.requestFocus();
            }

            //Checking for good data on the operation checkboxes
            boolean ready = false;
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    ready = true;
                    break;
                }
            }

            //Getting ready to send operations over
            if(ready)
            {
                int numOperationsOn = 0;
                for(int i = 0; i < numCB; i++)
                {
                    if(checkBoxes[i].isChecked())
                        numOperationsOn++;
                }
                String[] operationsOn = new String[numOperationsOn];
                int count = 0;
                for(int i = 0; i < numCB; i++)
                {
                    if(checkBoxes[i].isChecked())
                    {
                        operationsOn[count] = checkBoxes[i].getText().toString();
                        count++;
                    }
                }

                Log.i(MYAPP, Arrays.toString(operationsOn));

                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                game.setPlayerName(etName.getText().toString());
                RadioButton rb = findViewById(checkedRB);
                game.setLevel(rb.getText().toString());
                game.setOperations(operationsOn);
                Log.i(MYAPP, game.getOperationsOn() + "");
                startActivity(myIntent);
            }

            }
        }

    private void SetHiScores()
    {
        if(recentScore > firstScore)
        {
            tvThird.setText(tvSecond.getText());
            tvSecond.setText(tvFirst.getText());
            String s = game.getPlayerName() + ": " + recentScore + "";
            tvFirst.setText(s);
            firstScore = recentScore;
        }
        else if(recentScore > secondScore)
        {
            tvThird.setText(tvSecond.getText());
            String s = game.getPlayerName() + ": " + recentScore + "";
            tvSecond.setText(s);
            secondScore = recentScore;
        }
        else if(recentScore > thirdScore)
        {
            String s = game.getPlayerName() + ": " + recentScore + "";
            tvThird.setText(recentScore + "");
            thirdScore = recentScore;
        }
    }

    protected void onRestart(){
        super.onRestart();
        Log.i(MYAPP, "restart");
        SetHiScores();
    }
}
