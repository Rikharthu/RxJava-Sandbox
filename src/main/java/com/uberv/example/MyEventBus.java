package com.uberv.example;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MyEventBus {

    private PublishSubject<MyEvent> mEventPublisher;

    public MyEventBus() {
        this.mEventPublisher = PublishSubject.create();
    }

    public void subscribe(Consumer<MyEvent> eventConsumer) {
        mEventPublisher.subscribe(eventConsumer);
    }

    public void post(MyEvent event) {
        mEventPublisher.onNext(event);
    }
}
