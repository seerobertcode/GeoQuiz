package com.athome.android.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private int mCurrentIndex = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
    };
    // Counters to keep track of number of questions answered and how many correct
    private int answeredQuestions = 0;
    private int correctAnswers = 0;
    // Array to save the state of which questions were answered the size of mQuestion Bank
    private boolean[] whichAnswered = new boolean[mQuestionBank.length];

    // KEYS to save state if user rotates device and the Activity is recreated
    private static final String KEY_ANSWERED_QUESTIONS = "number_answered";
    private static final String KEY_CORRECT_ANSWERS = "number_correct";
    private static final String KEY_WHICH_QUESTIONS_ANSWERED = "which_answered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            answeredQuestions = savedInstanceState.getInt(KEY_ANSWERED_QUESTIONS);
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS);
            whichAnswered = savedInstanceState.getBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED);

            for (int i = 0; i < mQuestionBank.length; i++) {
                mQuestionBank[i].setQuestionAnswered(whichAnswered[i]);
            }
        }
        //QUESTION TEXT VIEW
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        //TRUE BUTTON TOAST
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });
        //FALSE BUTTON TOAST
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        //NEXT BUTTON FUNCTIONALITY
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        //saving state for other keys and variables
        savedInstanceState.putInt(KEY_ANSWERED_QUESTIONS, answeredQuestions);
        savedInstanceState.putInt(KEY_CORRECT_ANSWERS, correctAnswers);
        for (int i = 0; i < mQuestionBank.length; i++) {
            whichAnswered[i] = mQuestionBank[i].isQuestionAnswered();
        }
        savedInstanceState.putBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED, whichAnswered);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (!mQuestionBank[mCurrentIndex].isQuestionAnswered()) {
            buttonsEnabled(true);
        } else {
            buttonsEnabled(false);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
        buttonsEnabled(false);
        answeredQuestions++;
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            correctAnswers++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        calculateScore();
    }

    //method to enable or disable buttons
    private void buttonsEnabled(boolean enabled) {
        mTrueButton.setEnabled(enabled);
        mFalseButton.setEnabled(enabled);
    }

    //score calculation
    private void calculateScore() {
        int totalQuestions = mQuestionBank.length;
        int score = correctAnswers * 100 / totalQuestions;
        //show score after all questions are answered
        if (answeredQuestions == totalQuestions) {
            String message = "You scored " + score + "% correct answers. The Score wil now reset!";
            Toast toastScore = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toastScore.setGravity(Gravity.TOP, 0,0);
            toastScore.show();
            //reset part
            correctAnswers = 0;
            answeredQuestions = 0;
            for (Question question : mQuestionBank) {
                question.setQuestionAnswered(false);
            }
        }

    }
}