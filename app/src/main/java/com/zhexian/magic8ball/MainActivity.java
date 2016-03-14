package com.zhexian.magic8ball;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String mName;
    private double mAge;
    private MagicEightBallModel mMagicEightBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAge = 21;
        mName = "Zhe Xian Lee";

        Log.i("INFO", "Zhe Xian Lee");
        Log.i("INFO", String.format("%.2f", mAge));
        Log.i("INFO", "My name is " + mName);

        List<String> questions = Arrays.asList(
                "Will I get full marks for this lab?",
                "Will the Cronulla sharks receive a premiership this year?",
                "Will I end up becoming a cat person when I get old?");

        List<String> myResponse = Arrays.asList(
                "Whatever",
                "😛");

        mMagicEightBall = new MagicEightBallModel(myResponse);

        for (int i = 0; i < questions.size(); i++) {
            Log.i("INFO", questions.get(i));
            Log.i("INFO", mMagicEightBall.tellFortune());
        }

        Log.i("INFO", mMagicEightBall.toString());
    }
}
