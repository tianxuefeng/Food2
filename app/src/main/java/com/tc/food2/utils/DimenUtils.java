package com.tc.food2.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * User: tianchao
 * Date: 15/9/21
 * Time: 下午11:07
 * PS: 学如逆水行舟，不进则退
 */
public class DimenUtils {
    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;


    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
        }
        return 0;
    }

    public static int dp2px(float value, Context context) {
        return (int) applyDimension(DP_TO_PX, value, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(float value, Context context) {
        return (int) applyDimension(SP_TO_PX, value, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(float value, Context context) {
        return (int) applyDimension(PX_TO_DP, value, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(float value, Context context) {
        return (int) applyDimension(PX_TO_SP, value, context.getResources().getDisplayMetrics());
    }
}
