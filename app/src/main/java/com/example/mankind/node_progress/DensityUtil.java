package com.example.mankind.node_progress;


import android.content.Context;
import android.view.View;

/**
 * The type Density util.
 */
public class DensityUtil {

    /**
     * Dip 2 px int.
     *
     * @param context the context
     * @param dpValue the dp value
     * @return the int
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}