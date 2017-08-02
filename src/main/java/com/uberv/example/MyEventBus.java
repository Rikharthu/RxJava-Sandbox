package com.uberv.example;

import com.sun.istack.internal.NotNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MyEventBus {

    private PublishSubject<Object> mEventPublisher;

    public static MyEventBus sInstance;

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

    public <T> Observable<T> observable(@NotNull Class<T> eventClass){
        return this.mEventPublisher
                .filter(o->o!=null)
                .filter(eventClass::isInstance)
                .cast(eventClass);
    }

    public void postObject(Object event) {
        if (sInstance.mEventPublisher.hasObservers()) {
            sInstance.mEventPublisher.onNext(event);
        }
    }
}
