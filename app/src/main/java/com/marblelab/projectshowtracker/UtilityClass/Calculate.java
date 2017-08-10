package com.marblelab.projectshowtracker.UtilityClass;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by Abid Hasan on 8/1/2017.
 */

public class Calculate {
    private int height;
    private int weight;
    public static int grid_number;
    private float dpi;
    private WindowManager mWindowManager;
    private DisplayMetrics mDisplayMetrics;
    private Context context;
    private static Calculate instance;
    private int card_height;
    private int card_weight;
    private int orientation;

    public Calculate(Context context,int card_height,int card_weight) {
        this.context = context;
        this.card_height=card_height;
        this.card_weight=card_weight;
        orientation=context.getResources().getConfiguration().orientation;
        computeHeightWeidth(context);
        computeGridNumber();
    }

    private void computeGridNumber() {


        if (orientation==context.getResources().getConfiguration().ORIENTATION_PORTRAIT){
            int card_px= (int) (card_weight*(dpi/160));
            grid_number= (weight/card_px);
            Log.i("Number",String.valueOf(dpi));
            Log.i("Number",String.valueOf(card_px));
            Log.i("Number1",String.valueOf(weight));
            Log.i("Number2",String.valueOf(weight/card_px));
        }else {
            int card_px= (int) (card_weight*(dpi/160));
            grid_number= (height/card_px);
            Log.i("Number",String.valueOf(dpi));
            Log.i("Number",String.valueOf(card_px));
            Log.i("Number1",String.valueOf(weight));
            Log.i("Number2",String.valueOf(weight/card_px));
        }
    }

    public static synchronized Calculate getInstance(Context context,int card_weight,int card_height){
        if(instance==null){
            instance=new Calculate(context,card_height,card_weight);
        }
        return instance;
    }

    public void computeHeightWeidth(Context context){
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        height = mDisplayMetrics.heightPixels;
        weight = mDisplayMetrics.widthPixels;
        dpi = mDisplayMetrics.density * 160f;
        Log.i("DPI",String.valueOf(dpi));
        Log.i("height",String.valueOf(height));
        Log.i("weight",String.valueOf(weight));
    }
    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getGrid_number() {
        return grid_number;
    }
}
