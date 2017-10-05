package com.example.qualtopgroup.sample;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Alberto Carrillo on 12/09/17.
 */

public class RxBus {

    private static final RxBus instance = new RxBus();
    private final Subject<RxEvent> rxBus;

    public RxBus() {
        PublishSubject<RxEvent> rxEventPublishSubject = PublishSubject.create();
        rxBus = rxEventPublishSubject.toSerialized();
    }

    public static RxBus getInstance() {
        return instance;
    }

    public Observable<RxEvent> getRxBus() {
        return rxBus;
    }

    public void post(final RxEvent rxEvent) {
        rxBus.onNext(rxEvent);
    }

    public static class RxEvent {
        String a;

        public RxEvent(String a) {
            this.a = a;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }
}