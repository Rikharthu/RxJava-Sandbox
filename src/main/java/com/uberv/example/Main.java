package com.uberv.example;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Random rnd = new Random();

        Observable<Integer> o = Observable.create(emitter -> {
            int a;
            while (true) {
                a = rnd.nextInt();
                emitter.onNext(a);
            }
        });

        Observable<Integer> sampler = o.sample(500, TimeUnit.MILLISECONDS);
        Disposable sampling = sampler.subscribe(integer -> System.out.println(integer));
    }
}
