package com.rxjava.sucer.master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rxjava.sucer.master.Operate.OperateMap;


public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private Button button_run_scheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OperateShedule shedule = new OperateShedule();
//        shedule.init();
//        shedule.scheduleMultiple();

        OperateMap map = new OperateMap();
        map.mulFlatMap();

    }


}
