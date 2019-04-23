package com.dullon.demoboot.designpattern1;

/**
 * 装饰器模式：装饰模式指的是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。（百度百科）
 */
public class DecoratorTest {
    public static void main(String [] args){
        ComponentInterface componentInterface = new MyComponent();
        componentInterface.method();
        System.out.println("-----------分割线------------");
        DecoratorA decoratorA = new DecoratorA(componentInterface);
        decoratorA.method();//原有方法
        decoratorA.methodNew();//装饰器新方法
        System.out.println("-----------分割线------------");
        DecoratorB decoratorB = new DecoratorB(componentInterface);
        decoratorB.methodNew();//装饰器新方法
        decoratorB.method();//原有方法
        System.out.println("-----------分割线------------");
        DecoratorB decoratorB1 = new DecoratorB(decoratorA);
        decoratorB1.method();//二次装饰
        decoratorB1.methodNew();//装饰器方法
/*
查看结果 ，有点像监听器，而分层包装 有些像责任链模式，需要进一步了解。
 */
    }
}
//被装饰的对象抽象出来的 总接口
interface ComponentInterface{
    void method();
}
//被装饰对象（可以）
class MyComponent implements  ComponentInterface{
    @Override
    public void method() {
        System.out.println("原生方法");
    }
}
//装饰对象抽象出的基类（可以没有）
abstract class DecoratorAbstract implements ComponentInterface{

    //装饰器中有被装饰接口的对象。
    ComponentInterface ci;

    public DecoratorAbstract(ComponentInterface ci) {
        this.ci = ci;
    }

    @Override
    public void method() {
        ci.method();
    }
}
//装饰器A对被装饰的类进行具体包装。
class  DecoratorA extends DecoratorAbstract{

    public DecoratorA(ComponentInterface ci) {
        super(ci);
    }


    @Override
    public void method() {
        System.out.println("针对被装饰器的包装A");
        super.method();
        System.out.println("包装A完成");
    }

    public void methodNew() {
        System.out.println("包装A扩展的方法");
    }
}
//装饰器B对被装饰的类进行具体包装。
class  DecoratorB extends DecoratorAbstract{

    public DecoratorB(ComponentInterface ci) {
        super(ci);
    }


    @Override
    public void method() {
        System.out.println("针对被装饰器的包装B");
        methodNew();
        super.method();
        System.out.println("包装B完成");
    }

    public void methodNew() {
        System.out.println("包装B扩展的方法");
    }
}
