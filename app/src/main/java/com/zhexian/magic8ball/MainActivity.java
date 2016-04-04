package com.zhexian.magic8ball;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnEditorActionListener {

    private static final String QUESTION_RESPONSE_LIST_FILENAME = "QuestionResponseList.dat";
    public static final String QUESTION_RESPONSE_LIST_KEY = "QUESTION_RESPONSE_LIST_KEY";

    private MagicEightBallModel mMagicEightBall;
    private EditText mTxtQuestion;
    private TextView mTxtResponse;
    private ImageView mImgMagicEightBall;
    private Button mBtnHistory;
    private RelativeLayout mRelativeLayoutMagicEightBall;
    private ArrayList<QuestionResponseModel> mQuestionResponseList;

    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;

    private int[] magicEightBallImages = {
            R.drawable.circle1,
            R.drawable.circle2,
            R.drawable.circle3,
            R.drawable.circle4,
            R.drawable.circle5,
            R.drawable.circle6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMagicEightBall = new MagicEightBallModel();

        // Getting views
        mTxtQuestion = (EditText) findViewById(R.id.txtQuestion);
        mTxtResponse = (TextView) findViewById(R.id.txtResponse);
        mImgMagicEightBall = (ImageView) findViewById(R.id.imgMagicEightBall);
        mBtnHistory = (Button) findViewById(R.id.btnHistory);
        mRelativeLayoutMagicEightBall = (RelativeLayout) findViewById(R.id.relativeLayoutMagicEightBall);

        mBtnHistory.setOnClickListener(this);
        mTxtQuestion.setOnEditorActionListener(this);

        mFadeOutAnimation = new AlphaAnimation(1, 0);
        mFadeOutAnimation.setDuration(500);

        mFadeInAnimation = new AlphaAnimation(0, 1);
        mFadeInAnimation.setDuration(500);


        try {
            FileInputStream fileInputStream = openFileInput(QUESTION_RESPONSE_LIST_FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object object = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            mQuestionResponseList = (ArrayList<QuestionResponseModel>) object;
        } catch (IOException | ClassNotFoundException e) {
            mQuestionResponseList = new ArrayList<>();
        }


    }

    @Override
    public void onClick(View view) {
        if (view == mBtnHistory) {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra(QUESTION_RESPONSE_LIST_KEY, mQuestionResponseList);
            startActivity(intent);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView == mTxtQuestion && actionId == EditorInfo.IME_ACTION_GO) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            shakeMagicEightBall();
            return true;
        }
        return false;
    }

    public void shakeMagicEightBall() {
        if (mTxtQuestion.getText().toString().trim().length() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("No question asked");
            alertDialogBuilder.setMessage("Please ask a question before shaking the magic eight ball.");
            alertDialogBuilder.setPositiveButton("OK", null);
            alertDialogBuilder.show();
            return;
        }

        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == mFadeOutAnimation) {
                    String question = mTxtQuestion.getText().toString();
                    String response = mMagicEightBall.tellFortune();

                    Random random = new Random(System.currentTimeMillis());
                    mImgMagicEightBall.setImageResource(magicEightBallImages[random.nextInt(magicEightBallImages.length)]);
                    mTxtResponse.setText(response);

                    mQuestionResponseList.add(new QuestionResponseModel(question, response));

                    try {
                        FileOutputStream fileOutputStream = openFileOutput(QUESTION_RESPONSE_LIST_FILENAME, Context.MODE_PRIVATE);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(mQuestionResponseList);
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mRelativeLayoutMagicEightBall.startAnimation(mFadeInAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        mFadeOutAnimation.setAnimationListener(animationListener);
        mFadeInAnimation.setAnimationListener(animationListener);

        mRelativeLayoutMagicEightBall.startAnimation(mFadeOutAnimation);
    }
}
