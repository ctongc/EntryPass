package ood.designpatterns.observer;

import ood.designpatterns.observer.observers.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Publisher
 * 被观察者
 */
public class EcommercePlatform {

    private final List<Subscriber> subscriberList;

    public EcommercePlatform() {
        this.subscriberList = new ArrayList<>();
    }

    public void addSubscriber(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    public void newSaleEvent() {
        this.notifyAllObservers();
    }

    private void notifyAllObservers() {
        for (Subscriber subscriber : subscriberList) {
            subscriber.update();
        }
    }
}
