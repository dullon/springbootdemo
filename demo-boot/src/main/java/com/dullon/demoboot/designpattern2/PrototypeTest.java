package com.dullon.demoboot.designpattern2;

/**
 * 原型模式： 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。、
 *
 * 在JAVA语言中使用原型模式是非常简单的，这是因为Object类当中提供了一个本地方法clone，而JAVA中的任何类只要实现了Cloneable标识接口，就可以使用clone方法来进行对象的拷贝。
 *
 * 使用场景：
 * 1、对象的创建非常复杂，可以使用原型模式快捷的创建对象。
 * 2、在运行过程中不知道对象的具体类型，可使用原型模式创建一个相同类型的对象，或者在运行过程中动态的获取到一个对象的状态。
 *
 * 然而如果要实现深度拷贝，则需要将实现了Cloneable接口并重写了clone方法的类中，所有的引用类型也全部实现Cloneable接口并重写clone方法，而且需要将引用类型的属性全部拷贝一遍。
 *
 * 优点：
 * 1、由于clone方法是由虚拟机直接复制内存块执行，所以在速度上比使用new的方式创建对象要快。
 * 2、可以基于原型，快速的创建一个对象，而无需知道创建的细节。
 * 3、可以在运行时动态的获取对象的类型以及状态，从而创建一个对象。
 *
 * 缺点:就是实现深度拷贝比较困难，需要很多额外的代码量。
 *
 *   不过实际当中我们使用原型模式时，也可以写一个基类实现Cloneable接口重写clone方法，然后让需要具有拷贝功能的子类继承自该类，这是一种节省代码量的常用方式。Effective Java 2第11条 谨慎使用克隆。
 */
public class PrototypeTest {

    public static void main(String[] args) {
        DeepPrototype prototype1 = new DeepPrototype();
        System.out.println(prototype1);
        System.out.println(prototype1.getField());
        DeepPrototype prototype2 = prototype1.clone();
        System.out.println(prototype2);
        System.out.println(prototype2.getField());
    }

}

class Field implements Cloneable{

    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    protected Field clone() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception);
        }
        return (Field) object;
    }
}

class DeepPrototype implements Cloneable {

    private int x;
    private int y;
    private int z;
    private Field field;

    public DeepPrototype() {
        this.x = 2;
        this.y = 3;
        this.z = 4;
        this.field = new Field();
        this.field.setA(5);
    }

    public Field getField() {
        return field;
    }

    protected DeepPrototype clone() {
        Object object = null;
        try {
            object = super.clone();
            ((DeepPrototype) object).field = this.field.clone();
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception);
        }
        return (DeepPrototype) object;
    }

    public String toString() {
        return "[" + x + "," + y + "," + z + "," + field.getA() + "]";
    }
}