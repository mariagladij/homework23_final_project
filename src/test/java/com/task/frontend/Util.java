package com.task.frontend;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public final class Util {
    private Util() {
    }

    public static double convertPrice(String price) {
        return NumberUtils.toDouble(StringUtils.substring(price, 1).replaceAll(",", ""));
    }

    public static void delay(double sec) {
        try {
            Thread.sleep((long) (sec * 1000L));
        } catch (InterruptedException ignored) {
        }
    }
}
