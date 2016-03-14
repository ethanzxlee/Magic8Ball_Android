package com.zhexian.magic8ball;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by ZheXian on 07/03/2016.
 *
 * Java doesn't support using emojis in variable names. Android Studio would recognise it as a syntax error
 * when an emoji is put in a variable name.
 * Putting an emoji in the middle of a String seems to be fine. However, Android Studio wouldn't render the emoji
 * properly, some rectangle would be displayed
 *
 */
public class MagicEightBallModel extends Object {


    private static List<String> initialResponseArray() {
        return Arrays.asList("It is certain",
                "It is decidedly so",
                "Without a doubt",
                "Yes, definitely",
                "You may rely on it",
                "As I see it, yes",
                "Most likely",
                "Outlook good",
                "Yes",
                "Signs point to yes",
                "Reply hazy try again",
                "Ask again later",
                "Better not tell you now",
                "Cannot predict now",
                "Concentrate and ask again",
                "Don't count on it",
                "My reply is no",
                "My sources say no",
                "Outlook not so good",
                "Very doubtful");
    }

    private List<String> responseArray;

    public MagicEightBallModel() {
        responseArray = new ArrayList<>(initialResponseArray());
    }

    public MagicEightBallModel(List<String> extraResponseArray) {
        responseArray = new ArrayList<>(initialResponseArray());
        responseArray.addAll(extraResponseArray);
    }

    public String tellFortune() {
        Random random = new Random(System.currentTimeMillis());
        if (responseArray.size() > 0) {
            return responseArray.get(random.nextInt(responseArray.size()));
        }
        return "";
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
