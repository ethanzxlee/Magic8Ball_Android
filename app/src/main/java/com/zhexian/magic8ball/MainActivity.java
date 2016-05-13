package com.zhexian.magic8ball;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnEditorActionListener, TextToSpeech.OnInitListener, Response.Listener<String>, Response.ErrorListener {

    private static final String POST_QUESTION_RESPONSE_URL = "http://li859-75.members.linode.com/addEntry.php";
    private static final String USERNAME = "zxl653";

    private MagicEightBallModel mMagicEightBall;
    private EditText mTxtQuestion;
    private TextView mTxtResponse;
    private ImageView mImgMagicEightBall;
    private Button mBtnHistory;
    private RelativeLayout mRelativeLayoutMagicEightBall;
    private ArrayList<QuestionResponseModel> mQuestionResponseList;
    private TextToSpeech mTextToSpeech;
    private Boolean mTextToSpeechReady = false;

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

        mTextToSpeech = new TextToSpeech(getApplicationContext(), this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnHistory) {
            Intent intent = new Intent(this, HistoryActivity.class);
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
            alertDialogBuilder.setTitle(getResources().getString(R.string.empty_question_title));
            alertDialogBuilder.setMessage(getResources().getString(R.string.empty_question_message));
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), null);
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
                    Pair<Integer, Integer> response = mMagicEightBall.tellFortune();
                    Random random = new Random(System.currentTimeMillis());

                    mImgMagicEightBall.setImageResource(magicEightBallImages[random.nextInt(magicEightBallImages.length)]);
                    mTxtResponse.setText(getResources().getString(response.first));

                    postQuestionResponseModel(question, getResources().getString(response.first));
                    //playResponseWith(getResources().getString(response.first));

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


    private void playResponseWith(String text) {
        if (mTextToSpeechReady) {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));

            mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, hash);
        }
    }

    private void postQuestionResponseModel(final String question, final String response) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, POST_QUESTION_RESPONSE_URL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(QuestionResponseModel.QUESTION, question);
                params.put(QuestionResponseModel.ANSWER, response);
                params.put(QuestionResponseModel.USERNAME, USERNAME);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }
        };
        MagicEightBallApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTextToSpeech.setLanguage(Locale.getDefault());
            mTextToSpeechReady = true;
        }
    }

    @Override
    protected void onDestroy() {
        mTextToSpeech.shutdown();
        super.onDestroy();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("POST_RESPONSE_ERROR", error.getLocalizedMessage());
    }

    @Override
    public void onResponse(String response) {
        Log.d("POST_RESPONSE", response);
    }
}
