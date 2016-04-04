package com.zhexian.magic8ball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<QuestionResponseModel> questionResponseModelList;

    private ListView mHistoryListView;
    private QuestionResponseModelArrayAdapter mHistoryListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        questionResponseModelList = (ArrayList<QuestionResponseModel>) intent.getSerializableExtra(MainActivity.QUESTION_RESPONSE_LIST_KEY);
        mHistoryListViewAdapter = new QuestionResponseModelArrayAdapter(this, questionResponseModelList);

        mHistoryListView = (ListView) findViewById(R.id.historyListView);
        mHistoryListView.setAdapter(mHistoryListViewAdapter);
    }
}
