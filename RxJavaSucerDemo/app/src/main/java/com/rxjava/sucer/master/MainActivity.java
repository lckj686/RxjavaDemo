package com.rxjava.sucer.master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rxjava.sucer.master.operate.OperateSubject;
import com.rxjava.sucer.master.retrofit.DemoRetrofit;

import retrofit2.http.Path;


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

//        OperateMap map = new OperateMap();
//        map.mulFlatMap();
//        button_run_scheduler = (Button) findViewById(R.id.button_run_scheduler);
//        rx.Observable observable = RxView.clicks(button_run_scheduler);

        OperateSubject subject = new OperateSubject();
        subject.publishSubject();

        new DemoRetrofit().request();
    }


    private void sss(@Path(value = "123", encoded = false) Object ob){

    }


}
