package wycliffeassociates.recordingapp;

import android.app.Activity;

/**
 * Created by sarabiaj on 9/22/2015.
 */
public class VolumeMeter {
    private VolumeMeter(){}

    public static void changeVolumeBar(Activity context, double decibel){
        final double db = decibel;
        final Activity ctx = context;
        System.out.println(db);

        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(db > -1){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.max);
                    System.out.println(db);
                }
                else if (db < -1 && db > -1.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_16);
                }
                else if (db < -1.5 && db > -2){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_15);
                }
                else if (db < -2 && db > -2.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_14);
                }
                else if (db < -2.5 && db > -3){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_13);
                }
                else if (db < -3 && db > -3.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_12);
                }
                else if (db < -3.5 && db > -4){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_11);
                }
                else if (db < -4 && db > -4.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_10);
                }
                else if (db < -4.5 && db > -5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_9);
                }
                else if (db < -5 && db > -5.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_8);
                }
                else if (db < -5.5 && db > -6){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_7);
                }
                else if (db < -6 && db > -6.5){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_6);
                }
                else if (db < -6.5 && db > -7){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_5);
                }
                else if (db < -7 && db > -9){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_4);
                }
                else if (db < -9 && db > -10){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_3);
                }
                else if (db < -10 && db > -11){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_2);
                }
                else if (db < -11 && db > -12){
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.vol_1);
                }
                else {
                    ctx.findViewById(R.id.volumeBar).setBackgroundResource(R.drawable.min);
                }
            }
        });
    }
}