package dai.android.learn.dttv.utils;

import android.annotation.SuppressLint;

public class TimesUtil {

    @SuppressLint("DefaultLocale")
    public static String getTime(int i) {
        String result = "";
        int minute = i / 60;
        int hour = minute / 60;
        int second = i % 60;
        minute %= 60;
        result = String.format("%02d:%02d:%02d", hour, minute, second);
        return result;
    }
}
