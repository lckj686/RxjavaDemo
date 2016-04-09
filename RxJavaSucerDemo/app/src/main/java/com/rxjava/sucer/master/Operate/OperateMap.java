package com.rxjava.sucer.master.Operate;

import android.util.Log;

import com.rxjava.sucer.master.bean.MiddleData;
import com.rxjava.sucer.master.bean.SourceData;
import com.rxjava.sucer.master.bean.TargetData;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * OperateMap():
 * map事件对象的直接变换
 * flatMap 有被观察者的接入变换
 * 被观察者可以有多个map 或者flatmap
 * Created by sucer on 2016/4/9.
 */
public class OperateMap {
    private String TAG = "OperateMap";


    public void singleMap() {
        Log.d(TAG, "singleMap()");
        Observable observable = Observable.create(new Observable.OnSubscribe<SourceData>() {
            @Override
            public void call(Subscriber<? super SourceData> subscriber) {

                SourceData data = new SourceData("source", "p1", "p2", "p3");
                Log.i(TAG, "observable " + data.toString());
                subscriber.onNext(data);

            }
        });

        observable
                .map(new Func1<SourceData, TargetData>() {

                    @Override
                    public TargetData call(SourceData sourceData) {
                        TargetData data = new TargetData("midle <- " + sourceData.getName(), sourceData.getParam1());
                        Log.i(TAG, "map   data = " + data.toString());
                        return data;
                    }
                })


                .subscribe(new Action1<TargetData>() {
                    @Override
                    public void call(TargetData val) { // 参数类型 Bitmap
                        Log.d(TAG, "subscribe: " + val);

                    }
                });
    }

    public void mulMap() {
        Log.d(TAG, "mulMap()");
        Observable observable = Observable.create(new Observable.OnSubscribe<SourceData>() {
            @Override
            public void call(Subscriber<? super SourceData> subscriber) {

                SourceData data = new SourceData("source", "p1", "p2", "p3");
                Log.i(TAG, "observable " + data.toString());
                subscriber.onNext(data);

            }
        });

        observable
                .map(new Func1<SourceData, MiddleData>() {

                    @Override
                    public MiddleData call(SourceData sourceData) {
                        MiddleData data = new MiddleData("midle <- " + sourceData.getName(), sourceData.getParam1(), sourceData.getParam2());
                        Log.i(TAG, "map data = " + data.toString());
                        return data;
                    }
                })
                .map(new Func1<MiddleData, TargetData>() {
                    @Override
                    public TargetData call(MiddleData middleData) {
                        TargetData data = new TargetData("target <- " + middleData.getName(), middleData.getParam1());
                        Log.i(TAG, "map2 data = " + data.toString());
                        return data;
                    }
                })

                .subscribe(new Action1<TargetData>() {
                    @Override
                    public void call(TargetData val) { // 参数类型 Bitmap
                        Log.d(TAG, "subscribe: " + val);

                    }
                });
    }

    public void flatMap() {
        Log.d(TAG, "flatMap()");
        Observable observable = Observable.create(new Observable.OnSubscribe<SourceData>() {
            @Override
            public void call(Subscriber<? super SourceData> subscriber) {

                SourceData data = new SourceData("source", "p1", "p2", "p3");
                Log.i(TAG, "observable: " + data.toString());
                subscriber.onNext(data);


            }
        });

        //OperateMap(): 事件对象的直接变换
        // 观察者  与被观察者  对象匹配
        observable
                .flatMap(new Func1<SourceData, Observable<TargetData>>() {
                    @Override
                    public Observable<TargetData> call(final SourceData s) {

                        //被观察者
                        Observable<TargetData> observable = Observable.create(new Observable.OnSubscribe<TargetData>() {
                            @Override
                            public void call(Subscriber<? super TargetData> subscriber) {
                                TargetData data = new TargetData("targetData <- " + s.getName(), s.getParam1());
                                Log.i(TAG, "map observable data:  " + data.toString());
                                subscriber.onNext(data);


                            }
                        });
                        return observable;
                    }
                })


                .subscribe(new Subscriber<TargetData>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "onCompleted() ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError " + e.toString());
                    }

                    @Override
                    public void onNext(TargetData s) {
                        Log.d(TAG, "onNext " + s.toString());
                    }
                });
    }

    public void mulFlatMap() {
        Log.d(TAG, "flatMap()");
        Observable observable = Observable.create(new Observable.OnSubscribe<SourceData>() {
            @Override
            public void call(Subscriber<? super SourceData> subscriber) {

                SourceData
                        data = new SourceData("source", "p1", "p2", "p3");
                Log.i(TAG, data.toString());
                subscriber.onNext(data);
                data = new SourceData("source2", "p1", "p2", "p3");
                Log.i(TAG, data.toString());
                subscriber.onNext(data);
                data = new SourceData("source3", "p1", "p2", "p3");
                Log.i(TAG, data.toString());
                subscriber.onNext(data);

            }
        });

        //OperateMap(): 事件对象的直接变换
        // 观察者  与被观察者  对象匹配
        observable
                .flatMap(new Func1<SourceData, Observable<MiddleData>>() {
                    @Override
                    public Observable<MiddleData> call(final SourceData s) {


                        //被观察者
                        Observable<MiddleData> observable = Observable.create(new Observable.OnSubscribe<MiddleData>() {
                            @Override
                            public void call(Subscriber<? super MiddleData> subscriber) {
//

                                if (s.getName().contains("2")) {
                                    //传递
                                    subscriber.onError(new Exception("异常"));
                                } else {
                                    MiddleData data = new MiddleData("middle <- " + s.getName(), s.getParam1(), s.getParam2());
                                    //传递
                                    subscriber.onNext(data);

                                }

                            }
                        });

                        return observable;
                    }
                })
                .flatMap(new Func1<MiddleData, Observable<TargetData>>() {
                    @Override
                    public Observable<TargetData> call(final MiddleData m) {

                        //被观察者
                        Observable<TargetData> observable = Observable.create(new Observable.OnSubscribe<TargetData>() {
                            @Override
                            public void call(Subscriber<? super TargetData> subscriber) {

                                TargetData targetData = new TargetData("target <- " + m.getName(), m.getParam1());

                                //传递
                                subscriber.onNext(targetData);

                            }
                        });

                        return observable;
                    }
                })

                .subscribe(new Subscriber<TargetData>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "onCompleted() ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError " + e.toString());
                    }

                    @Override
                    public void onNext(TargetData s) {
                        Log.d(TAG, "onNext " + s.toString());
                    }
                });
    }
}
