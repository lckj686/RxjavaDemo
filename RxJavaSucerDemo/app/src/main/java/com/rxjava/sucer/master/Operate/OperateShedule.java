package com.rxjava.sucer.master.operate;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 观察者和被观察者的线程指定
 * subscribeOn(): 事件产生的线程 -> 被观察者 线程
 * observeOn(): 事件消费的线程 -> 观察者 线程
 * Created by sucer on 2016/4/9.
 */
public class OperateShedule {
    private String TAG = "OperateSubscribe";

    private Observer<String> mObserver;//观察者1
    private Subscriber<String> mSubscriber;//观察者2
    private Observable<String> mObservable; //被观察者

    public void init() {
        //被观察者
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "被观察者 -> Thread name:" + Thread.currentThread().getName());

                subscriber.onNext("A");

            }
        });

        //观察者1
        mObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "mObserver onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "mObserver onError()");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "mObserver onNext():" + s);
                Log.i(TAG, "观察者1 -> Thread name:" + Thread.currentThread().getName());
            }
        };

        //观察者2 （订阅者 extends 观察者）
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "mSubscriber onStart()");
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "mSubscriber onCompleted()");

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "mSubscriber onError()");
            }

            @Override
            public void onNext(String str) {
                Log.i(TAG, "mSubscriber onNext():" + str);
                Log.i(TAG, "观察者2 -> Thread name:" + Thread.currentThread().getName());
            }
        };
    }


    /**
     * 订阅，并且调度
     * subscribeOn(): 事件产生的线程 -> 被观察者 线程
     * observeOn(): 事件消费的线程 -> 观察者 线程
     */
    public void scheduleSingle() {
        mObservable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(mSubscriber);
    }

    /**
     * 多个订阅，并且调度
     * subscribeOn(): 事件产生的线程 -> 被观察者 线程
     * observeOn(): 事件消费的线程 -> 观察者 线程
     * 注：要一条一条写，不要每句都写一个mObservable上去，否则线程调度失败和lift原理相关
     */
    public void scheduleMultiple() {
        mObservable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(mSubscriber);

        mObservable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

}
