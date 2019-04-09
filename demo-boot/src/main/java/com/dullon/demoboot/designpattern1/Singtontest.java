package com.dullon.demoboot.designpattern1;

/**
 * 单例模式：它的定义就是确保某一个类只有一个实例，并且提供一个全局访问点。在整个应用中，同一时刻，有且只能有一种状态。
 */
public class Singtontest {
    //第一种 不考虑并发的单例模式
    //首先私有的静态的 本体成员变量（实例）
    private static Singtontest singtontest ;
    //私有的构造方法
    private Singtontest(){
    }
    //公有的静态方法返回需要单例的实例（一般为静态工厂方法）
    public static Singtontest getSingtontest(){
        if (singtontest == null){
            singtontest = new Singtontest();
        }
        return singtontest;
    }
}
//该方式虽然考虑到了并发 但是 因为每次调用都要先加锁 在进行判断，是极大的浪费。
class Singtontest1{
    private static Singtontest1 singtontest1;

    private Singtontest1(){}

    public synchronized static Singtontest1 getSingtontest(){
        if (singtontest1 == null){
            singtontest1 = new Singtontest1();
        }
        return singtontest1;
    }
}
//该种优化 在同步外先进行非空判断，这样如果不是空 就可以先进行单例返回，当为空的时候 再进行加锁 再判断 属于双层判断单例。
class Singtontest2{
    private static Singtontest2 singtontest2;

    private Singtontest2(){}

    public static Singtontest2 getSingtontest(){
        if (singtontest2 != null){
           return singtontest2;
        }
        synchronized (Singtontest2.class){
            if (singtontest2 == null) {
                singtontest2 = new Singtontest2();
            }
        }
        return singtontest2;
    }
}
//上述单例中仍然存在风险，在JVM中执行创建实例的这一步操作的时候，其实是分了好几步去进行的，也就是说创建一个新的对象并非是原子性操作。在有些JVM中上述做法是没有问题的，但是有些情况下是会造成莫名的错误。为了避免这种错误 我们考虑内部类进行优化
//首先来说一下，这种方式为何会避免了上面莫名的错误，主要是因为一个类的静态属性只会在第一次加载类时初始化，这是JVM帮我们保证的，所以我们无需担心并发访问的问题。所以在初始化进行一半的时候，别的线程是无法使用的，因为JVM会帮我们强行同步这个过程。另外由于静态变量只初始化一次，所以singleton仍然是单例的。

class Singtontest3{
    public static Singtontest3 getIingtontest3(){
        return InnerSington3.singtontest3;
    }
    private Singtontest3(){}

    private static class InnerSington3{
        static Singtontest3 singtontest3 = new Singtontest3();
    }

}