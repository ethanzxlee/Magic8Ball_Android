package com.zhexian.magic8ball;


import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by ZheXian on 07/03/2016.
 *
 */
public class MagicEightBallModel {


    private static List<Pair<Integer, Integer>> initialResponseArray() {

        return Arrays.asList(new Pair<>(R.string.it_is_certain, R.raw.it_is_certain),
                new Pair<>(R.string.it_is_decidedly_so, R.raw.it_is_decidedly),
                new Pair<>(R.string.without_a_doubt, R.raw.without_a_doubt),
                new Pair<>(R.string.yes_definitely, R.raw.yes_definitely),
                new Pair<>(R.string.you_may_rely_on_it, R.raw.you_may_rely_on_it),
                new Pair<>(R.string.as_i_see_it_yes, R.raw.as_i_see_it_yes),
                new Pair<>(R.string.most_likely, R.raw.most_likely),
                new Pair<>(R.string.outlook_good, R.raw.outlook_good),
                new Pair<>(R.string.yes, R.raw.yes),
                new Pair<>(R.string.signs_point_to_yes, R.raw.signs_point_to_yes),
                new Pair<>(R.string.reply_hazy_try_again, R.raw.reply_hazy_try_again),
                new Pair<>(R.string.ask_again_later, R.raw.ask_again_later),
                new Pair<>(R.string.better_not_tell_you_now, R.raw.better_not_tell_you_now),
                new Pair<>(R.string.cannot_predict_now, R.raw.cannot_predict_now),
                new Pair<>(R.string.concentrate_and_ask_again, R.raw.concentrate_and_ask_again),
                new Pair<>(R.string.dont_count_on_it, R.raw.dont_count_on_it),
                new Pair<>(R.string.my_reply_is_no, R.raw.my_reply_is_no),
                new Pair<>(R.string.my_sources_say_no, R.raw.my_sources_say_no),
                new Pair<>(R.string.outlook_not_so_good, R.raw.outlook_not_so_good),
                new Pair<>(R.string.very_doubtful, R.raw.very_doubtful));
    }

    private List<Pair<Integer, Integer>> responseArray;

    public MagicEightBallModel() {
        responseArray = new ArrayList<>(initialResponseArray());
    }

//    public MagicEightBallModel(List<Pair<String, Integer>> extraResponseArray) {
//        responseArray = new ArrayList<>(initialResponseArray());
//        responseArray.addAll(extraResponseArray);
//    }

    public Pair<Integer, Integer> tellFortune() {
        Random random = new Random(System.currentTimeMillis());
        if (responseArray.size() > 0) {
            return responseArray.get(random.nextInt(responseArray.size()));
        }
        return new Pair<>(0, 0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < responseArray.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(responseArray.get(i));
        }
        return builder.toString();
    }
}
