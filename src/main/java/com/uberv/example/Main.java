package com.uberv.example;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.Random;

public class Main {

    // TODO more info https://medium.com/@seventyone/a-simple-event-bus-with-rxjava-46cc1858df23
    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();

        String str = "Hello, World!";

        Main main = new Main();

        MyEventBus eventsBus = MyEventBus.instanceOf();

        eventsBus.subscribe(main, OnNewFloatEvent.class, (Consumer<OnNewFloatEvent>) myEvent -> {
            System.out.println("new float event" + myEvent.getValue());
        });

        Disposable floatSubscribtion = eventsBus
                .observable(OnNewFloatEvent.class)
                .subscribe(event -> {
                    System.out.println("OnNewFloatEvent : " + event.getValue());
                });

//        Disposable allSubscribtion = eventsBus
//                .observable(MyEvent.class)
//                .subscribe(event -> {
//                    System.out.println("OnNewXYZEvent : ");
//                });

        new Thread(() -> {
            int counter = 0;
            while (true) {
                int value = rnd.nextInt(100);
                eventsBus.post(new OnNewIntegerEvent(value));
                if (value > 20 && value < 50) {
                    eventsBus.postObject(new OnNewFloatEvent(rnd.nextFloat()));
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter++ == 20) {
                    eventsBus.unsubscribe(main);
                }
                if(counter==50){
                    eventsBus.subscribe(main, OnNewFloatEvent.class, (Consumer<OnNewFloatEvent>) myEvent -> {
                        System.out.println("new float event" + myEvent.getValue());
                    });
                }
            }
        }).start();
    }
}
