package org.wycliffeassociates.translationrecorder;

import org.wycliffeassociates.translationrecorder.Reporting.Logger;

/**
 * Created by sarabiaj on 12/22/2015.
 */
public class Timer {
    private static long mStart;
    private static long end;
    public static void start(){
        mStart = System.currentTimeMillis();
    }
    public static void elapsed(){
        Logger.i(Timer.class.toString(), "Time elapsed: " + (System.currentTimeMillis() - mStart));
    }
}
