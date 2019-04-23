package com.dullon.demoboot.designpattern1;

/**
 * 外观模式：外观模式是软件工程中常用的一种软件设计模式。它为子系统中的一组接口提供一个统一的高层接口。这一接口使得子系统更加容易使用。
 * 典型的三层架构 就是外观模式的经典例子 service层 调用dao 层 controller层 调用service。
 * 外观接口负责提供客户端定制的服务，外观实现则负责组合子系统中的各个类和接口完成这些服务，外观接口则是提供给客户端使用的，这样就解除了客户端与子系统的依赖，而让客户端只依赖于外观接口，这是一个优秀的解耦实践。
 *
 * 外观模式注意事项：
 *  1，实际使用当中，接口并不是必须的，虽说根据依赖倒置原则，无论是处于高层的外观层，还是处于底层的子系统，都应该依赖于抽象，但是这会导致子系统的每一个实现都要对应一个接口，从而导致系统的复杂性增加，所以这样做并不是必须的。
 *
 *   2，外观接口当中并不一定是子系统中某几个功能的组合，也可以是将子系统中某一个接口的某一功能单独暴露给客户端。
 *
 *   3，外观接口如果需要暴露给客户端很多的功能的话，可以将外观接口拆分为若干个外观接口，如此便会形成一层外观层。
 */
public class FaceClientTest {
    public static void main(String[] args) {
        Facade facade = new FacadeImpl();
        facade.method12();
        System.out.println("----------分割线-------------");
        facade.method23();
        System.out.println("----------分割线-------------");
        facade.method123();
    }

}

interface Sub1{
    void method1();
}

interface Sub2{
    void method2();
}

interface Sub3{
    void method3();
}

class Sub1Impl implements Sub1{
    @Override
    public void method1() {
        System.out.println("具体实现类1实现第一个接口");
    }
}

class Sub1Imp2 implements Sub2{
    @Override
    public void method2() {
        System.out.println("具体实现类2实现第二个接口");
    }
}

class Sub1Imp3 implements Sub3{
    @Override
    public void method3() {
        System.out.println("具体实现类3实现第三个接口");
    }
}
//抽象出总接口，将一系列子接口的功能进行整理，从而产生一个更高层的接口。
interface Facade{
     void method12();
     void method23();
     void method123();
}
//总接口实现类，进行具体的操作。
class FacadeImpl implements Facade{
    private Sub1 sub1;
    private Sub2 sub2;
    private Sub3 sub3;

    public FacadeImpl(){
        this.sub1 = new Sub1Impl();
        this.sub2 = new Sub1Imp2();
        this.sub3 = new Sub1Imp3();
    }

    public FacadeImpl(Sub1 sub1, Sub2 sub2, Sub3 sub3) {
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
    }

    @Override
    public void method12() {
        sub1.method1();
        sub2.method2();
    }

    @Override
    public void method23() {
        sub2.method2();
        sub3.method3();
    }

    @Override
    public void method123() {
        sub1.method1();
        sub2.method2();
        sub3.method3();
    }
}