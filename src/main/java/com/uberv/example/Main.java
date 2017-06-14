package com.uberv.example;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();

        MyEventBus eventsBus = new MyEventBus();
        eventsBus.subscribe(new Consumer<MyEvent>() {
            @Override
            public void accept(MyEvent myEvent) throws Exception {
                System.out.println("Received Event of type " + myEvent.getClass().getSimpleName());
                if (myEvent instanceof OnNewIntegerEvent) {
                    OnNewIntegerEvent onNewIntEvent = (OnNewIntegerEvent) myEvent;
                    System.out.println("OnNewIntegerEvent : " + onNewIntEvent.getValue());
                }
            }
        });

        while (true) {
            eventsBus.post(new OnNewIntegerEvent(rnd.nextInt()));
            Thread.sleep(300);
        }
    }
}
