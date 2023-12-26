package com.task.ui.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class UiUtils {

    public static double convertPrice(String price) {
        return NumberUtils.toDouble(StringUtils.substring(price, 1).replaceAll(",", ""));
    }
}
