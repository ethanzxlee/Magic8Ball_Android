package com.zhexian.magic8ball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by ZheXian on 04/04/2016.
 */
public class QuestionResponseModelArrayAdapter extends ArrayAdapter<QuestionResponseModel> {

    public QuestionResponseModelArrayAdapter(Context context, List<QuestionResponseModel> questionResponseModelList) {
        super(context, R.layout.history_list_item, questionResponseModelList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {
            // Initialise view holder
            viewHolder = new ViewHolder();

            // Inflate view
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.history_list_item, parent, false);

            // Find views
            viewHolder.questionTextView = (TextView) view.findViewById(R.id.history_list_item_question);
            viewHolder.responseTextView = (TextView) view.findViewById(R.id.history_list_item_response);
            viewHolder.profilePictureImageView = (NetworkImageView) view.findViewById(R.id.history_list_item_profile_pic);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.position = position;
        viewHolder.questionTextView.setText(getItem(position).getQuestion());
        viewHolder.responseTextView.setText(getItem(position).getResponse());
        viewHolder.profilePictureImageView.setImageUrl(getItem(position).getImageUrl(), MagicEightBallApplication.getInstance().getImageLoader());

        return view;
    }

    private class ViewHolder {
        int position;
        TextView questionTextView;
        TextView responseTextView;
        NetworkImageView profilePictureImageView;
    }
}
