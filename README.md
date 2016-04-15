###1、概述
现在讨论rxjava的人多了，传的有点神。讨论的人多了，自然也要学学，不然就low了。花了点时间琢磨了下，确实也是比较好的一个东西，会用在项目里  
先上参考材料  
[中文][http://gank.io/post/560e15be2dca930e00da1083](http://gank.io/post/560e15be2dca930e00da1083)  
[github]https://github.com/ReactiveX/RxJava   
[github]https://github.com/ReactiveX/RxAndroid/wiki  


###2、特点
1、是观察者模式（监听者模式）  
普通的观察者模式，场景类操作：  
-a) 声明 观察者，被观察者  
-b) 被观察者 <注册观察者   
-c) 被观察者通知观察者

rxjava的观察者模式，场景类操作：  
-a) 声明 观察者，被观察者   
-b) 被观察者 <被观察者订阅   （直接做通知操作）

2、有调度器：可以把观察者，被观察者设置在指定线程  

3、事件对象可变换：观察者和被观察者的事件对象可以不一样，经过变换操作 响应观察者 可多次变换    
-a) 变换1：纯粹的对象变换  
-b) 变换2：有中间被观察者介入的变换

###3、描述
对三个特点进行展开描述  
####3.1、观察者模式的简单使用  
被观察者：  
- Observable      <br>

被观察者的构造方法：  
- Observable.create  
- Observable.just  
- Observable.from 

 
观察者(类观察者)：  
- Observer  
- Subscriber  
- Action


主意：

####3.2、有调度器 可以把观察者和被观察者设置在指定的线程
几个调度器：  
Schedulers.immediate():  默认的当前线程
Schedulers.newThread():   新线程
Schedulers.io():  io读写线程
Schedulers.computation():  计算处理线程（可不用）

```java
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
```

####3.3、变换
纯粹的事件对象变换：被观察者事件对象是A  -> 观察者事件对象是B 纯粹的对象变换
```java
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
```

被观察者介入的事件对象变换：被观察者事件对象是A  -> 观察者事件对象是B 变换过程有中间被观察者介入
```java
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
```

###subject
subject = 观察者加被观察者
 * 四种subject  
 * AsyncSubject  
 * BehaviorSubject  
 * PublishSubject  
 * ReplaySubject  
参见:[https://github.com/mcxiaoke/RxDocs/blob/master/Subject.md](https://github.com/mcxiaoke/RxDocs/blob/master/Subject.md)   
例子代码在demo中有

###demo
平时这里是写参考材料的，但是已经写到开头去了。针对rxjava的一些抽象概念做了个demo  
demogithub地址:  
[github][https://github.com/lckj686/RxjavaDemo](https://github.com/lckj686/RxjavaDemo)

- 作为普通观察者模式的使用：OperateSubscribe.java
- 设置观察者与被观察者在不同线程：OperateShedule.java
- 观察者，被观察者 的事件对象不同时的变换：OperateMap.java