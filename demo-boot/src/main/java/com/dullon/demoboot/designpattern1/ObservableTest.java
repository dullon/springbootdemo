package com.dullon.demoboot.designpattern1;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：在此种模式中，一个目标物件管理所有相依于它的观察者物件，并且在它本身的状态改变时主动发出通知。这通常透过呼叫各观察者所提供的方法来实现。此种模式通常被用来实作事件处理系统。
 * 观察者模式分离了观察者和被观察者二者的责任，这样让类之间各自维护自己的功能，专注于自己的功能，会提高系统的可维护性和可重用性。
 * 观察者模式和监听器的区别：
   1，观察者模式中观察者的响应理论上讲针对特定的被观察者是唯一的（说理论上唯一的原因是，如果你愿意，你完全可以在update方法里添加一系列的elseif去产生不同的响应），而事件驱动则不是，因为我们可以定义自己感兴趣的事情，比如刚才，我们可以监听作者发布新书，我们还可以在监听器接口中定义其它的行为。再比如tomcat中，我们可以监听servletcontext的init动作，也可以监听它的destroy动作。
 *
   2，虽然事件驱动模型更加灵活，但也是付出了系统的复杂性作为代价的，因为我们要为每一个事件源定制一个监听器以及事件，这会增加系统的负担，各位看看tomcat中有多少个监听器和事件类就知道了。
 *
   3，另外观察者模式要求被观察者继承Observable类，这就意味着如果被观察者原来有父类的话，就需要自己实现被观察者的功能，当然，这一尴尬事情，我们可以使用适配器模式弥补，但也不可避免的造成了观察者模式的局限性。事件驱动中事件源则不需要，因为事件源所维护的监听器列表是给自己定制的，所以无法去制作一个通用的父类去完成这个工作。
 *
   4，被观察者传送给观察者的信息是模糊的，比如update中第二个参数，类型是Object，这需要观察者和被观察者之间有约定才可以使用这个参数。而在事件驱动模型中，这些信息是被封装在Event当中的，可以更清楚的告诉监听器，每个信息都是代表的什么。
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

