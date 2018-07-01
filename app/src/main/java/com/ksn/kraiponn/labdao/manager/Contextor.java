package com.ksn.kraiponn.labdao.manager;

import android.content.Context;

public class Contextor {

    private static Contextor sInstance;

    public static Contextor newInstance() {
        if (sInstance == null) {
            sInstance = new Contextor();
        }

        return sInstance;
    }

    private Context mContext;

    private Contextor() {
        //
    }

    public void init(Context context) {
        mContext = context;
    }

}
