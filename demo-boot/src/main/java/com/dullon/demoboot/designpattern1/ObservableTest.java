package com.dullon.demoboot.designpattern1;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：在此种模式中，一个目标物件管理所有相依于它的观察者物件，并且在它本身的状态改变时主动发出通知。这通常透过呼叫各观察者所提供的方法来实现。此种模式通常被用来实作事件处理系统。
 * 观察者模式分离了观察者和被观察者二者的责任，这样让类之间各自维护自己的功能，专注于自己的功能，会提高系统的可维护性和可重用性。
 */
public class ObservableTest {

    public static void main(String [] args){
        Observable o = new Observable();
        o.add(new ConcreteObserver1());
        o.add(new ConcreteObserver2());
        o.changed();
    }
}
/*
被观察者
 */
class Observable {
    //观察者列表
    List<Observer> list = new ArrayList<>();
    //添加观察者（被订阅）
    boolean add(Observer o){
        if (!list.contains(o)) {
            return list.add(o);
        }
        return false;
    }
     //添加观察者（被取消订阅）源码：无需判定 直接返回 obs.removeElement(o);
    boolean remove(Observer o){
        if (list.contains(o)) {
            return list.remove(o);
        }
        return false;
    }
    //发布变化
    void changed(){
        System.out.println("被观察者发生改变");
        notifyObservers();
    }
    //通知观察者
    void notifyObservers(){
        for (Observer observer : list){
            System.out.println("通知观察者");
            observer.update(this);

        }
    }
}
/*
发布接口
 */
interface Observer{
    void update(Observable o);
}
//观察者1
class ConcreteObserver1 implements Observer{
    @Override
    public void update(Observable o) {
        System.out.println("观察者1观察:"+ o.getClass().getSimpleName() + "在改变。");
    }
}
//观察者2
class ConcreteObserver2 implements Observer{
    @Override
    public void update(Observable o) {
        System.out.println("观察者2观察:"+ o.getClass().getSimpleName() + "在改变。");
    }
}

