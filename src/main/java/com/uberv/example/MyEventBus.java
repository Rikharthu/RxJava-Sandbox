package com.uberv.example;

import com.sun.istack.internal.NotNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyEventBus {

    private PublishSubject<Object> mEventPublisher;

    public static MyEventBus sInstance;
    Map<Object, List<Disposable>> map = new HashMap<>();

    public static MyEventBus instanceOf() {
        if (sInstance == null) {
            sInstance = new MyEventBus();
        }
        return sInstance;
    }


    public MyEventBus() {
        this.mEventPublisher = PublishSubject.create();
    }

    public void subscribe(Consumer<MyEvent> eventConsumer) {
        mEventPublisher.subscribe((Observer<? super Object>) eventConsumer);
    }

    public void post(MyEvent event) {
        mEventPublisher.onNext(event);
    }

    public <T> Observable<T> observable(@NotNull Class<T> eventClass) {
        return this.mEventPublisher
                .filter(o -> o != null)
                .filter(eventClass::isInstance)
                .cast(eventClass);
    }

    public void postObject(Object event) {
        if (sInstance.mEventPublisher.hasObservers()) {
            sInstance.mEventPublisher.onNext(event);
        }
    }

    public <T> void subscribe(Object owner, Class<T> type,Consumer<T> consumer) {
        List<Disposable> subscribtions = map.get(owner);
        if (subscribtions == null) {
            subscribtions = new ArrayList<>();
            map.put(owner, subscribtions);
        }
        Disposable subscribtion = observable(type).subscribe(consumer);
        subscribtions.add(subscribtion);
    }

    public void unsubscribe(Object owner) {
        List<Disposable> subscribtions = map.get(owner);
        if (subscribtions != null && subscribtions.size() > 0) {
            for (Disposable subscribtion : subscribtions) {
                if (subscribtion != null && !subscribtion.isDisposed()) {
                    subscribtion.dispose();
                }
            }
        }
    }

}
