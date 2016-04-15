package com.rxjava.sucer.master.Operate;

import android.util.Log;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Description:subject = Observable + Observer 既可以做观察者也可以做被观察者
 * https://github.com/mcxiaoke/RxDocs/blob/master/Subject.md
 * 四种subject
 * AsyncSubject
 * BehaviorSubject
 * PublishSubject
 * ReplaySubject
 * Created by liw on 2016/4/14.
 */
public class OperateSubject {
    private String TAG = "OperateSubject";

    private Subject subject = new SerializedSubject(PublishSubject.create());

    public void publishSubject() {



        Subscription s1 = subject.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "Action1  " + o);
            }
        });

        Subscription s2 = subject.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "Action2  " + o);
            }
        });

        Subscription s3 = subject.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "Action3  " + o);
            }
        });

        subject.onNext(3);

        if (!s3.isUnsubscribed()){
            s3.unsubscribe();
        }
        subject.onNext("33333");

        ;
        Log.d(TAG, "asObservable :" + subject.asObservable());
        Log.d(TAG, "asObservable :" + subject.asObservable());
    }

}
