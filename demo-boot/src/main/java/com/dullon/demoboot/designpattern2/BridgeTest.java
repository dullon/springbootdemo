package com.dullon.demoboot.designpattern2;

/**
 * 桥接模式：即将抽象部分与它的实现部分分离开来，使他们都可以独立变化。
 * 桥接模式将继承关系转化成关联关系，它降低了类与类之间的耦合度，减少了系统中类的数量，也减少了代码量。
 * 抽象化：其概念是将复杂物体的一个或几个特性抽出去而只注意其他特性的行动或过程。在面向对象就是将对象共同的性质抽取出去而形成类的过程。
 * 实现化：针对抽象化给出的具体实现。它和抽象化是一个互逆的过程，实现化是对抽象化事物的进一步具体化。
 * 脱耦：脱耦就是将抽象化和实现化之间的耦合解脱开，或者说是将它们之间的强关联改换成弱关联，将两个角色之间的继承关系改为关联关系。
 * 优点
 *       1、分离抽象接口及其实现部分。提高了比继承更好的解决方案。
 *       2、桥接模式提高了系统的可扩充性，在两个变化维度中任意扩展一个维度，都不需要修改原有系统。
 *       3、实现细节对客户透明，可以对用户隐藏实现细节。
 * 缺点
 *       1、桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。
 *       2、桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围具有一定的局限性。
 *
 */
public class BridgeTest {
    public static void main(String[] args) {
        //白色
        Color white = new White();
        //正方形
        Shape square = new Square();
        //白色的正方形
        square.setColor(white);
        square.draw();

        //通过内部类动态扩展
        Shape rectange = new Rectangle();
        rectange.setColor(new Color() {
            @Override
            public void bepaint(String shape) {
                System.out.println("红色的" + shape);
            }
        });
        rectange.draw();
    }
}
//第一种风格抽象类（也可以抽象接口） 形状 拥有另一个风格的属性。抽象方法为本风格提供多态。
abstract class Shape {
    Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw();
}
//圆形
class Circle extends Shape{

    public void draw() {
        color.bepaint("圆形");
    }
}
//长方形
class Rectangle extends Shape{

    public void draw() {
        color.bepaint("长方形");
    }

}
//正方形
class Square extends Shape{

    public void draw() {
        color.bepaint("正方形");
    }

}
//第二种风格抽象接口（也可以抽象类） 颜色
interface Color {
    public void bepaint(String shape);
}
//白色
class White implements Color{

    public void bepaint(String shape) {
        System.out.println("白色的" + shape);
    }

}
//灰色的
class Gray implements Color{

    public void bepaint(String shape) {
        System.out.println("灰色的" + shape);
    }
}
//黑色的
class Black implements Color{

    public void bepaint(String shape) {
        System.out.println("黑色的" + shape);
    }
}