package com.zhexian.magic8ball;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    private RelativeLayout mContainer;
    private ImageView mImgBackground;


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
        //        setContentView(R.layout.activity_main);

        // Create a relative layout as the container
        mContainer = new RelativeLayout(this);

        // Set the layout params of the container
        FrameLayout.LayoutParams mContainerLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainerLayoutParams.setMargins(0, 0, 0, 0);
        mContainer.setLayoutParams(mContainerLayoutParams);

        // ImageView for background image
        mImgBackground = new ImageView(this);
        mImgBackground.setScaleType(ImageView.ScaleType.FIT_XY);
        mImgBackground.setImageResource(R.drawable.background);

        // Layout params of the background image
        RelativeLayout.LayoutParams mImgBackgroundLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImgBackgroundLayoutParams.setMargins(0, 0, 0, 0);
        mImgBackgroundLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mImgBackgroundLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mImgBackground.setLayoutParams(mImgBackgroundLayoutParams);

        // EditText for entering question
        mTxtQuestion = new EditText(this);
        mTxtQuestion.setHint("Ask a question");
        mTxtQuestion.setImeOptions(EditorInfo.IME_ACTION_GO);
        mTxtQuestion.setSingleLine(true);
        mTxtQuestion.setInputType(EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        mTxtQuestion.setId(1);
        mTxtQuestion.setOnEditorActionListener(this);


        // Layout params of the EditText
        RelativeLayout.LayoutParams mTxtQuestionLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 144);
        mTxtQuestionLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mTxtQuestionLayoutParams.setMargins(8, 8, 8, 8);
        mTxtQuestion.setLayoutParams(mTxtQuestionLayoutParams);

        // Shake button
        mBtnShake = new Button(this);
        mBtnShake.setText("Shake");
        mBtnShake.setId(2);
        mBtnShake.setOnClickListener(this);

        // Layout params of the shake button
        RelativeLayout.LayoutParams mBtnShakeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 144);
        mBtnShakeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mBtnShakeLayoutParams.setMargins(8, 8, 8, 8);
        mBtnShake.setLayoutParams(mBtnShakeLayoutParams);

        // Relative layout for the magic eight ball and response
        mRelativeLayoutMagicEightBall = new RelativeLayout(this);

        // Layout params of the relative layout
        RelativeLayout.LayoutParams mRelativeLayoutMagicEightBallLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRelativeLayoutMagicEightBallLayoutParams.addRule(RelativeLayout.BELOW, mTxtQuestion.getId());
        mRelativeLayoutMagicEightBallLayoutParams.addRule(RelativeLayout.ABOVE, mBtnShake.getId());
        mRelativeLayoutMagicEightBall.setLayoutParams(mRelativeLayoutMagicEightBallLayoutParams);

        // TextView for the response
        mTxtResponse = new TextView(this);
        mTxtResponse.setTextColor(Color.WHITE);

        // Layout params of the TextView
        RelativeLayout.LayoutParams mTxtResponseLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTxtResponseLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mTxtResponseLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mTxtResponse.setLayoutParams(mTxtResponseLayoutParams);

        // ImageView for the magic eight ball
        mImgMagicEightBall = new ImageView(this);
        mImgMagicEightBall.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mImgMagicEightBall.setImageResource(R.drawable.circle1);

        // Layout params of the ImageView
        RelativeLayout.LayoutParams mImgMagicEightBallLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImgMagicEightBallLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mImgMagicEightBallLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mImgMagicEightBallLayoutParams.setMargins(16, 16, 16, 16);
        mImgMagicEightBall.setLayoutParams(mImgMagicEightBallLayoutParams);

        // Add views
        mContainer.addView(mImgBackground);
        mContainer.addView(mTxtQuestion);
        mContainer.addView(mBtnShake);
        mContainer.addView(mRelativeLayoutMagicEightBall);
        mRelativeLayoutMagicEightBall.addView(mImgMagicEightBall);
        mRelativeLayoutMagicEightBall.addView(mTxtResponse);
        setContentView(mContainer);

        mMagicEightBall = new MagicEightBallModel();

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
