package com.example.nurda.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class StaringScreenCompany extends AppCompatActivity {
    private static final int REUQEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighScore;
    private Spinner spinner;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighScore = findViewById(R.id.text_view_highscore);
        spinner = findViewById(R.id.spinner_dificulty);

        String [] difficultyLevels = Question.getDifficultyLevels();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,difficultyLevels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        loadHighScore();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REUQEST_CODE_QUIZ){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if (score>highscore)
                    updateHighscore(score);
            }
        }
    }

    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE,0);
        textViewHighScore.setText("Highscore: "+highscore);

    }

    private void updateHighscore(int highscoreNew) {
        highscore=highscoreNew;
        textViewHighScore.setText("Highscore: "+highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE,highscore);
        editor.apply();

    }

    public void startQuiz(){
        String difficulty  = spinner.getSelectedItem().toString();
        Intent intent = new Intent(StaringScreenCompany.this,QuizActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY,difficulty);
        startActivityForResult(intent,REUQEST_CODE_QUIZ);


    }
}
