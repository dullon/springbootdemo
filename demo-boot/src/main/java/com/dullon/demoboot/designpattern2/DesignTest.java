package com.dullon.demoboot.designpattern2;

/**
 * 状态模式：当一个对象的内在状态改变时允许改变其行为，这个对象看起来像是改变了其类。
 * 应用场景
 * 状态模式主要解决的是当控制一个对象状态的条件表达式过于复杂时的情况。把状态的判断逻辑转移到表示不同状态的一系列类中，可以把复杂的判断逻辑简化。
 * 三个角色
 * Context：它就是那个含有状态的对象，它可以处理一些请求，这些请求最终产生的响应会与状态相关。
 * State：状态接口，它定义了每一个状态的行为集合，这些行为会在Context中得以使用。
 * ConcreteState：具体状态，实现相关行为的具体状态类。
 *几个明显的优点：
 *                 一、我们去掉了if else结构，使得代码的可维护性更强，不易出错，这个优点挺明显，如果试图让你更改跑动的方法，是刚才的一堆if else好改，还是分成了若干个具体的状态类好改呢？答案是显而易见的.
 *                 二、使用多态代替了条件判断，这样我们代码的扩展性更强，比如要增加一些状态，假设有加速20%，加速10%，减速10%等等等（这并不是虚构，DOTA当中是真实存在这些状态的），会非常的容易。                
 *                 三、状态是可以被共享的，这个在上面的例子当中有体现，看下Hero类当中的四个static final变量就知道了，因为状态类一般是没有自己的内部状态的，所有它只是一个具有行为的对象，因此是可以被共享的。
 *                 四、状态的转换更加简单安全，简单体现在状态的分割，因为我们把一堆if else分割成了若干个代码段分别放在几个具体的状态类当中，所以转换起来当然更简单，而且每次转换的时候我们只需要关注一个固定的状态到其他状态的转换。安全体现在类型安全，我们设置上下文的状态时，必须是状态接口的实现类，而不是原本的一个整数，这可以杜绝魔数以及不正确的状态码。

 *  状态模式也有它的缺点，不过它的缺点和大多数模式相似，有两点。
 *                   1、会增加的类的数量。
 *                   2、使系统的复杂性增加。
 *
 *         转自左潇龙博客：https://blog.csdn.net/zuoxiaolong8810/article/details/9775069
 */
public class DesignTest {
    public static void main(String[] args) throws InterruptedException {
        Hero hero = new Hero();
        hero.startRun();
        hero.setState(Hero.SPEED_UP);
        Thread.sleep(5000);
        hero.setState(Hero.SPEED_DOWN);
        Thread.sleep(5000);
        hero.setState(Hero.SWIM);
        Thread.sleep(5000);
        hero.stopRun();
    }

}
// Context 状态角色本体
class Hero {

    public static final RunState COMMON = new CommonState();//正常状态

    public static final RunState SPEED_UP = new SpeedUpState();//加速状态

    public static final RunState SPEED_DOWN = new SpeedDownState();//减速状态

    public static final RunState SWIM = new SwimState();//眩晕状态

    private RunState state = COMMON;//默认是正常状态

    private Thread runThread;//跑动线程

    //设置状态
    public void setState(RunState state) {
        this.state = state;
    }
    //停止跑动
    public void stopRun() {
        if (isRunning()) runThread.interrupt();
        System.out.println("--------------停止跑动---------------");
    }
    //开始跑动
    public void startRun() {
        if (isRunning()) {
            return;
        }
        final Hero hero = this;
        runThread = new Thread(new Runnable() {
            public void run() {
                while (!runThread.isInterrupted()) {
                    state.run(hero);
                }
            }
        });
        System.out.println("--------------开始跑动---------------");
        runThread.start();
    }

    private boolean isRunning(){
        return runThread != null && !runThread.isInterrupted();//判断线程 存在 或者 未中断。
    }

}
//State 抽象出 行为状态接口
interface RunState {

    void run(Hero hero);

}
class SwimState implements RunState{

    public void run(Hero hero) {
        System.out.println("--------------不能跑动---------------");
        try {
            Thread.sleep(2000);//假设眩晕持续2秒
        } catch (InterruptedException e) {}
        hero.setState(Hero.COMMON);
        System.out.println("------眩晕状态结束，变为正常状态------");
    }

}
//ConcreteState 具体的状态1
class SpeedDownState implements RunState{

    public void run(Hero hero) {
        System.out.println("--------------减速跑动---------------");
        try {
            Thread.sleep(4000);//假设减速持续4秒
        } catch (InterruptedException e) {}
        hero.setState(Hero.COMMON);
        System.out.println("------减速状态结束，变为正常状态------");
    }

}
//ConcreteState 具体的状态2
class SpeedUpState implements RunState{

    public void run(Hero hero) {
        System.out.println("--------------加速跑动---------------");
        try {
            Thread.sleep(4000);//假设加速持续4秒
        } catch (InterruptedException e) {}
        hero.setState(Hero.COMMON);
        System.out.println("------加速状态结束，变为正常状态------");
    }

}
//ConcreteState 具体的状态3
class CommonState implements RunState{

    public void run(Hero hero) {
        //正常跑动则不打印内容，否则会刷屏
    }
}