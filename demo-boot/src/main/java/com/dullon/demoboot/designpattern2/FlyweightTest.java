package com.dullon.demoboot.designpattern2;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * 享元模式：它使用共享物件，用来尽可能减少内存使用量以及分享资讯给尽可能多的相似物件；它适合用于只是因重复而导致使用无法令人接受的大量内存的大量物件。通常物件中的部分状态是可以分享。常见做法是把它们放在外部数据结构，当需要使用时再将它们传递给享元。
 *
 * 内蕴状态存储在享元内部，不会随环境的改变而有所不同，是可以共享的。
 * 外蕴状态是不可以共享的，它随环境的改变而改变的，因此外蕴状态是由客户端来保持（因为环境的变化是由客户端引起的）。
 *
 * (1) 抽象享元角色：为具体享元角色规定了必须实现的方法，而外蕴状态就是以参数的形式通过此方法传入。在Java中可以由抽象类、接口来担当。
 * (2) 具体享元角色：实现抽象角色规定的方法。如果存在内蕴状态，就负责为内蕴状态提供存储空间。
 * (3) 享元工厂角色：负责创建和管理享元角色。要想达到共享的目的，这个角色的实现是关键！
 * (4) 客户端角色：维护对所有享元对象的引用，而且还需要存储对应的外蕴状态。
 *
 * 优点：
 * 节约系统的开销。保证一个常用的对象只有一个！
 * 外部状态不会影响内部状态，可以在不同环境下进行共享。
 * 缺点：
 * 享元模式使逻辑变得更加复杂，需要将享元对象分出内部状态和外部状态。
 * 并且为了使对象可以共享，外部状态在很多情况下是必须有的，比如围棋的位置。当读取外部状态时明显会增加运行时间。
 *
 *使用场景：
 * 一个系统有大量细粒度化的对象，占据大量的内存。
 * 对象大部分属性可以外部化，并且能将外部的属性放入内部属性中来。
 * 使用享元模式需要维护享元池，所以要用那种常用的经常调用的对象可以使用享元模式。
 */
//clinet(客户端角色)客户端测试类
public class FlyweightTest {
    public static void main(String[] args) {
        IgoChessman b1,b2,w1,w2;
        IgoChessmanFactory igoChessmanFactory;
        //获取对应的工厂对象
        igoChessmanFactory = IgoChessmanFactory.getIgoChessmanFactory();
        //判断是不是同一个对象。就是看是不是共享了。
        System.out.println(igoChessmanFactory);
        b1 = IgoChessmanFactory.getIgoChessman("黑");
        b2= IgoChessmanFactory.getIgoChessman("黑");
        System.out.println(b1==b2);
        w1 =IgoChessmanFactory.getIgoChessman("白");
        w2 =IgoChessmanFactory.getIgoChessman("白");
        System.out.println(w1==w2);
        b1.display();
        w1.display();
        w2.display();
        b2.display();

    }
}
//(抽象享元类)将公共方法给抽象
abstract  class IgoChessman {
    //共享抽象方法棋子的颜色
    public abstract String chessColor();

    public void display() {
        System.out.println("棋子:"+this.chessColor());
    }
}
//WhiteChessman(具体享元角色)白棋类，实现具体的方法.
class WhiteChessman extends IgoChessman {

    @Override
    public String chessColor() {
        // TODO Auto-generated method stub
        return "白色";
    }

}
//WhiteChessman(具体享元角色)黑棋类，实现公共方法.
class BlackChessman extends IgoChessman {

    @Override
    public String chessColor() {
        // TODO Auto-generated method stub
        return "黑色";
    }

}
//IgoChessmanFactory(享元工厂角色)使用的是单例模式创建，将享元对象唯一化。
class IgoChessmanFactory {
    //首先私有化自己的类
    private static IgoChessmanFactory igoChessmanFactory = new IgoChessmanFactory();
    //创建享元池
    private static HashMap<String,IgoChessman > ht;

    //私有化构造函数自己创建自己 。
    private  IgoChessmanFactory() {
        //创建对应的棋子放进去.
        ht= new HashMap<String,IgoChessman >();
        IgoChessman white,black;
        white = new WhiteChessman();
        ht.put("白",white);
        black = new BlackChessman();
        //放入HASHTABLE
        ht.put("黑",black);

    }

    //提供一个全局访问的方法。
    /**
     *
     * <p>Title: getIgoChessmanFactory</p>
     * <p>Description: </p>
     * @return IgoChessmanFactory
     */
    public static IgoChessmanFactory getIgoChessmanFactory() {
        return igoChessmanFactory;
    }

    //工厂方法创建对象 》
    public  static IgoChessman getIgoChessman(String name) {

        return ht.get(name);

    }

}
