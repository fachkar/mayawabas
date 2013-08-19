package com.druid.mayawabas;

import com.druid.mayawabas.SurfView;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;


public class Mayactivity extends Activity {
    public volatile SurfView mSurfView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mayactivity);
        mSurfView = (SurfView) findViewById(R.id.maya_SurfView);
        mSurfView.pMayactivity = this;
       
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_mayactivity);
        mSurfView = (SurfView) findViewById(R.id.maya_SurfView);
        mSurfView.pMayactivity = this;
    }
}
