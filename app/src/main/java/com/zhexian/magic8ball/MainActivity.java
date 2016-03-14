package com.zhexian.magic8ball;

import android.content.Context;
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

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnEditorActionListener {

    private MagicEightBallModel mMagicEightBall;
    private EditText mTxtQuestion;
    private TextView mTxtResponse;
    private ImageView mImgMagicEightBall;
    private Button mBtnShake;
    private RelativeLayout mRelativeLayoutMagicEightBall;

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
        mBtnShake = (Button) findViewById(R.id.btnShake);
        mRelativeLayoutMagicEightBall = (RelativeLayout) findViewById(R.id.relativeLayoutMagicEightBall);

        mBtnShake.setOnClickListener(this);
        mTxtQuestion.setOnEditorActionListener(this);

        mFadeOutAnimation = new AlphaAnimation(1, 0);
        mFadeOutAnimation.setDuration(500);

        mFadeInAnimation = new AlphaAnimation(0, 1);
        mFadeInAnimation.setDuration(500);

    }

    @Override
    public void onClick(View view) {
        if (view == mBtnShake) {
            shakeMagicEightBall();
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
                    Random random = new Random(System.currentTimeMillis());
                    mImgMagicEightBall.setImageResource(magicEightBallImages[random.nextInt(magicEightBallImages.length)]);
                    mTxtResponse.setText(mMagicEightBall.tellFortune());

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
