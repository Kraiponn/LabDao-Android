package com.ksn.kraiponn.labdao;

import android.app.Application;

import com.ksn.kraiponn.labdao.manager.Contextor;

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.newInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
