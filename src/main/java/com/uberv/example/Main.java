package com.uberv.example;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    // TODO more info https://medium.com/@seventyone/a-simple-event-bus-with-rxjava-46cc1858df23
    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();

        MyEventBus eventsBus = MyEventBus.instanceOf();
//        eventsBus.subscribe(myEvent -> {
//            System.out.println("Received Event of type " + myEvent.getClass().getSimpleName());
//            if (myEvent instanceof OnNewIntegerEvent) {
//                OnNewIntegerEvent onNewIntEvent = (OnNewIntegerEvent) myEvent;
//                System.out.println("OnNewIntegerEvent : " + onNewIntEvent.getValue());
//            }
//        });
//
//        eventsBus.subscribe(myEvent -> {
//            System.out.println("2) Received Event of type " + myEvent.getClass().getSimpleName());
//            if (myEvent instanceof OnNewIntegerEvent) {
//                OnNewIntegerEvent onNewIntEvent = (OnNewIntegerEvent) myEvent;
//                System.out.println("2) OnNewIntegerEvent : " + onNewIntEvent.getValue());
//            }
//        });

        Disposable floatSubscribtion=eventsBus
                .observable(OnNewFloatEvent.class)
                .subscribe(event -> {
                    System.out.println("OnNewFloatEvent : " + event.getValue());
                });

        Disposable allSubscribtion=eventsBus
                .observable(MyEvent.class)
                .subscribe(event -> {
                    System.out.println("OnNewXYZEvent : ");
                });

        new Thread(() -> {
            while (true) {
                int value = rnd.nextInt(100);
                eventsBus.post(new OnNewIntegerEvent(value));
                if(value>20 && value<50){
                    eventsBus.postObject(new OnNewFloatEvent(rnd.nextFloat()));
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
