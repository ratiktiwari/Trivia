package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.controller.AppController;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SAVE_STATE = "Save_data_prefs";

    //make four packages - data, controller, model and ui in com.example.trivia

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private TextView scoreTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    private String updatedScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.imageButtonNext);
        previousButton = findViewById(R.id.imageButtonPrevious);
        trueButton = findViewById(R.id.buttonTrue);
        falseButton = findViewById(R.id.buttonFalse);
        questionTextView = findViewById(R.id.questionTextView);
        questionCounterTextView = findViewById(R.id.counterText);
        scoreTextView = findViewById(R.id.scoreText);

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);


        //The below style of onClickListener is very lengthy to be written, so we used different way by implementing the View.OnClickListener interface
//        falseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());  //questionArrayList.get(currentQuestionIndex)  is an object of Question Class because questionArrayList is an ArrayList of Question objects. That is why we are able to use .getAnswer() method from Question Class.

                questionCounterTextView.setText(currentQuestionIndex + "/" + questionArrayList.size());

//                Log.d("Hello", "onCreate: "+ questionArrayList);

            }
        });

//        Log.d("Hello", "onCreate: "+ questionList);
        //this above code will get only an empty List to you.  But Why?????????

        //Because in above all code we are calling getQuestions() method which in turn uses volley to connect to the internet and store the data
        //in the Question object which takes some significant time. But in above code .getQuestion() is executed very fast and
        //and suddenly it also executes the Log.d statement also, and you know that getting Json and connecting to HTTPS is an AsyncTask which means
        //it does not executes serially which means whether the connection is done or not the main will be continued to be executed.
        //So, by the time Log.d is called, our AsyncTask or task of getting data from internet and storing it as objects
        //of Question Class is not completed and hence we get empty questionList. To solve this problem we have created AnswerAsyncResponse. Read Below :



        //-----------------------------------------------------------------------------------------------------------------------------------





//        As the we are calling the API to fetch the data through http which is an async call
//Which means that  before completing the http call and getting the data the main method execution will be completed
// As we using the Volley library to make http call in getQuestion method that method as whole become async method
// sync means if the above line of code doesn't complete you cant execute next line of the code
//Async means even if the above line of code  is not  completed you can execute next line of the code
//As the main is calling getQuestions and that is async method  (because it is using http to call the API using Volley  Library )which means that main can continue to run even  main method even if the getQuestion method is not completed.
// Hence we are getting empty questionList ArrayList in the main method
// To handle this we implement a call back feature
//In getQuestions method you pass an object of an interface by implementing the method present in the interface
//for eg :
// new QuestionBank().getQuestion (new AnswerListAsyncResponse() {
//       @Override
//public void processedFinished(ArrayList<Question> questionArrayList) {
//         Log.d("TAG" , "processFinished" + questionArrayList);
//                    }
//)
//that object will be stored in the  getQuestion  method with a name of callback
//now this callback has a method call processfinished.
//once the complete async  request is completed by running complete for Loop
//now  that thread will call processfinished method with the questionArrayList
//the data in the questionArrayList will be passed in the processedFinished
//and the implementation of the processedFinished method we are displaying the result


//      Now if we want to show log.d inside of onCreate ten we can do below code:


//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        Log.d("Hello", "onCreate: "+ questionList);
//
//                    }
//                },
//                94
//        );




    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imageButtonPrevious:
                if(currentQuestionIndex!=0) {
                    currentQuestionIndex = (currentQuestionIndex - 1);
                    updateQuestion();
                }
                break;
            case R.id.imageButtonNext:
                currentQuestionIndex = (currentQuestionIndex+1) % questionList.size();  //so that we don't get out of bound
                updateQuestion();
                break;
            case R.id.buttonTrue:
                checkAnswer(true);
                break;
            case R.id.buttonFalse:
                checkAnswer(false);
                break;
        }
    }

    private void checkAnswer(boolean userAnswerChoice) {

        boolean correctAnswer = questionList.get(currentQuestionIndex).isAnswerTrue();
        disableButtons();

        int toastMessageId = 0;
        if(correctAnswer == userAnswerChoice)
        {
            updateScore(1);
            fadeView();
            toastMessageId = R.string.correctAnswer;

        }
        else
        {
            updateScore(0);
            shakeAnimation();
            toastMessageId = R.string.wrongAnswer;

        }

        Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        enableButtons();
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex + "/" + questionList.size());


//        Log.d("Hello", "onCreate: "+ questionList);
    }

    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);

        final CardView cardView = findViewById(R.id.cardView);

        cardView.startAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                cardView.setCardBackgroundColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeView()
    {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f); //from opaque to transparent
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);//0 means 1 time and 1 means 2 times
        alphaAnimation.setRepeatMode(Animation.REVERSE);  //first fade out and then come again from transparent to opaque

        cardView.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void updateScore(int flag)
    {
        int currentScore = Integer.parseInt(scoreTextView.getText().toString().replaceAll("[\\D]", "")); //Replace all non-digit with blank: the remaining string contains only digits.
        updatedScore = Integer.toString(currentScore);
        if(flag == 1) {
            updatedScore = Integer.toString(currentScore + 100);
            scoreTextView.setText(String.format("Score: %s", updatedScore));
        }
        else
        {
            if(currentScore>0) {
                updatedScore = Integer.toString(currentScore - 100);
                scoreTextView.setText(String.format("Score: %s", updatedScore));
            }
        }

//        SharedPreferences sharedPreferences =getSharedPreferences(SAVE_STATE,MODE_PRIVATE);
//
//        SharedPreferences.Editor editor =sharedPreferences.edit();
//
//        editor.putString("score", updatedScore);
//        editor.apply();

    }

    private void  disableButtons()
    {
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    private void enableButtons()
    {
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }


}