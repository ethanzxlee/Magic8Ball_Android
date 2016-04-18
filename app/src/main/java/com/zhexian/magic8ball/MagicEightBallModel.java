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


    private static List<Pair<String, Integer>> initialResponseArray() {

        return Arrays.asList(new Pair<>("It is certain", R.raw.it_is_certain),
                new Pair<>("It is decidedly so", R.raw.it_is_decidedly),
                new Pair<>("Without a doubt", R.raw.without_a_doubt),
                new Pair<>("Yes, definitely", R.raw.yes_definitely),
                new Pair<>("You may rely on it", R.raw.you_may_rely_on_it),
                new Pair<>("As I see it, yes", R.raw.as_i_see_it_yes),
                new Pair<>("Most likely", R.raw.most_likely),
                new Pair<>("Outlook good", R.raw.outlook_good),
                new Pair<>("Yes", R.raw.yes),
                new Pair<>("Signs point to yes", R.raw.signs_point_to_yes),
                new Pair<>("Reply hazy try again", R.raw.reply_hazy_try_again),
                new Pair<>("Ask again later", R.raw.ask_again_later),
                new Pair<>("Better not tell you now", R.raw.better_not_tell_you_now),
                new Pair<>("Cannot predict now", R.raw.cannot_predict_now),
                new Pair<>("Concentrate and ask again", R.raw.concentrate_and_ask_again),
                new Pair<>("Don't count on it", R.raw.dont_count_on_it),
                new Pair<>("My reply is no", R.raw.my_reply_is_no),
                new Pair<>("My sources say no", R.raw.my_sources_say_no),
                new Pair<>("Outlook not so good", R.raw.outlook_not_so_good),
                new Pair<>("Very doubtful", R.raw.very_doubtful));
    }

    private List<Pair<String, Integer>> responseArray;

    public MagicEightBallModel() {
        responseArray = new ArrayList<>(initialResponseArray());
    }

    public MagicEightBallModel(List<Pair<String, Integer>> extraResponseArray) {
        responseArray = new ArrayList<>(initialResponseArray());
        responseArray.addAll(extraResponseArray);
    }

    public Pair<String, Integer> tellFortune() {
        Random random = new Random(System.currentTimeMillis());
        if (responseArray.size() > 0) {
            return responseArray.get(random.nextInt(responseArray.size()));
        }
        return new Pair<>("", 0);
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
