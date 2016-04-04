package com.zhexian.magic8ball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZheXian on 04/04/2016.
 */
public class QuestionResponseModelArrayAdapter extends ArrayAdapter<QuestionResponseModel> {

    private static class ViewHolder {
        TextView questionTextView;
        TextView responseTextView;
    }


    public QuestionResponseModelArrayAdapter(Context context, List<QuestionResponseModel> questionResponseModelList) {
        super(context, android.R.layout.simple_list_item_2, questionResponseModelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

            viewHolder.questionTextView = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.responseTextView = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.questionTextView.setText(getItem(position).getQuestion());
        viewHolder.responseTextView.setText(getItem(position).getResponse());

        return convertView;
    }
}
