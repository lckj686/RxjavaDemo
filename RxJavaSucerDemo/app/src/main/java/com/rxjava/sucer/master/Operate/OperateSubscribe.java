package com.rxjava.sucer.master.operate;

import android.util.Log;

import com.rxjava.sucer.master.bean.SourceData;

import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 订阅流程。被观察者和观察者（类观察者） 都有几种不同的表现形式
 *
 * 被观察者：Observable  被观察者的几种创建过程:Observable.create  Observable.just Observable.from
 * 观察者或类观察者：Observer  Subscriber  Action
 *
 *
 * Created by sucer on 2016/4/9.
 */
public class OperateSubscribe {
    private String TAG = "OperateSubscribe";

    private Observer<String> mObserver;//观察者1
    private Subscriber<String> mSubscriber;//观察者2

    private Observable<String> mObservable; //被观察者
    private Observable<String> mObservable2; //被观察者

    public void init() {


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
                Log.i(TAG, "mSubscriber onNext(): " + str);
                Log.i(TAG, "Subscriber -> Thread name:" + Thread.currentThread().getName());
            }
        };

        //被观察者
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "被观察者 -> Thread name:" + Thread.currentThread().getName());

                subscriber.onNext("A");
                subscriber.onNext("B");
                subscriber.onNext("C");
            }
        });


    }

    /**
     * 订阅操作
     * 被观察者一订阅观察者，马上会调用被观察者的call 方法
     */
    public void subscribeCreate() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("A");
                subscriber.onNext("B");
                subscriber.onNext("C");
            }
        });
        observable.subscribe(mSubscriber);//1种
//        mObservable.subscribe(mObserver);//1种
    }


    /**
     * Jsut 等效于 create 等效于 From
     */
    public void subscribeJust() {
        Observable<SourceData> observable = Observable.just(new SourceData("s1", "p1", "p2", "p3"));

        observable.subscribe(new Subscriber<SourceData>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "观察者： onCompleted() ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "观察者： onError() ");
            }

            @Override
            public void onNext(SourceData s) {

                Log.i(TAG, "观察者： data = " + s.toString());
            }
        });
    }

    public void subscribeFrom() {
        SourceData[] ss
                = new SourceData[]{new SourceData("s1", "p1", "p2", "p3"),
                new SourceData("s2", "p1", "p2", "p3"),
                new SourceData("s3", "p1", "p2", "p3")};

        Observable<SourceData> observable = Observable.from(ss);
        observable.subscribe(new Subscriber<SourceData>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "观察者： onStart ");
            }

            @Override
            public void setProducer(Producer p) {
                super.setProducer(p);
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "观察者： onCompleted() ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "观察者： onError() ");
            }

            @Override
            public void onNext(SourceData s) {

                Log.i(TAG, "观察者： data = " + s.toString());
            }
        });
    }


    /**
     * Action 的效果 等价于 Observer
     */
    public void subscribedAction(){
        SourceData[] ss
                = new SourceData[]{new SourceData("s1", "p1", "p2", "p3"),
                new SourceData("s2", "p1", "p2", "p3"),
                new SourceData("s3", "p1", "p2", "p3")};

        Observable<SourceData> observable = Observable.from(ss);
        observable.subscribe(new Action1<SourceData>() {
            @Override
            public void call(SourceData sourceData) {

                Log.i(TAG, "subscribeAction data = " + sourceData.toString());
            }
        });
    }



    /**
     * 被两个观察者订阅
     */
    public void subscribeMultiple() {
        mObservable.subscribe(mSubscriber);
        mObservable.subscribe(mObserver);
    }

}
