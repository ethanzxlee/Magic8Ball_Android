package com.zhexian.magic8ball;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    private final static String GET_ALL_ENTRIES_URL = "http://li859-75.members.linode.com/retrieveAllEntries.php";

    private ArrayList<QuestionResponseModel> questionResponseModelList;
    private ListView mHistoryListView;
    private QuestionResponseModelArrayAdapter mHistoryListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        questionResponseModelList = new ArrayList<>();

        mHistoryListViewAdapter = new QuestionResponseModelArrayAdapter(this, questionResponseModelList);
        mHistoryListView = (ListView) findViewById(R.id.historyListView);
        mHistoryListView.setAdapter(mHistoryListViewAdapter);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, GET_ALL_ENTRIES_URL, null, this, this);
        MagicEightBallApplication.getInstance().getRequestQueue().add(jsonArrayRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                String question = jsonObject.getString(QuestionResponseModel.QUESTION);
                String answer = jsonObject.getString(QuestionResponseModel.ANSWER);
                String username = jsonObject.getString(QuestionResponseModel.USERNAME);
                String imageUrl = jsonObject.getString(QuestionResponseModel.IMAGE_URL);

                questionResponseModelList.add(new QuestionResponseModel(question, answer, username, imageUrl));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mHistoryListViewAdapter.notifyDataSetChanged();
    }
}
