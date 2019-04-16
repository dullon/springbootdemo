package com.dullon.demoboot.designpattern1;

/**
 * 策略模式： 策略模式定义了一系列的算法，并将每一个算法封装起来，而且使它们还可以相互替换。策略模式让算法独立于使用它的客户而独立变化。
 * 就是有一系列的可相互替换的算法的时候，我们就可以使用策略模式将这些算法做成接口的实现，并让我们依赖于算法的类依赖于抽象的算法接口，这样可以彻底消除类与具体算法之间的耦合。
 */
public class StrategyTest {
    public static void main(String [] args){
        UseAbtr useAbtr = new UseAbtr();
        System.out.println("--------------");
        useAbtr.setStrategy(new StrategyAbtr1());
        useAbtr.methord();
        System.out.println("--------------");
        useAbtr.setStrategy(new StrategyAbtr2());
        useAbtr.methord();
        System.out.println("--------------");

    }
}
//把算法 抽象 到此策略接口
interface Strategy{
    void abtrMethod();
}
//策略1
class StrategyAbtr1 implements  Strategy{
    @Override
    public void abtrMethod() {
        System.out.println("策略1");
    }
}
//策略2
class StrategyAbtr2 implements  Strategy{
    @Override
    public void abtrMethod() {
        System.out.println("策略2");
    }
}
//调用策略的依赖类
class UseAbtr{
    //策略接口的成员变量
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void methord(){
        strategy.abtrMethod();
    }
}