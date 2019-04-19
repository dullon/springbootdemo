package com.dullon.demoboot.designpattern1;

/**
 * 模板方法模式和适配器模式：
 *    模板方法模式，一般是为了统一子类的算法实现步骤，所使用的一种手段或者说是方式。它在父类中定义一系列算法的步骤，而将具体的实现都推迟到子类。  最典型的形式就是一个接口，一个抽象父类，父类中会有一系列的抽象方法，而在子类中去一一实现这些方法。
 *
 *    适配器模式，和模板方法模式相反，父类统一把一些基本的方法实现，抽象出公用重点的方法，由子类实现形成重点方法的多态性。
 *    注意适配器模式 一般是项目完成后有所遗漏时作为补丁引用，是项目的一种补救措施，如果提前能考虑到最好直接作用于模板方法内。
 */
public class TemplateTest {

}

interface PageBuilder {
    String bulidHtml();
}

abstract class AbstractPageBuilder implements PageBuilder{

    private StringBuffer stringBuffer = new StringBuffer();

    public String bulidHtml() {
        //首先加入doctype,因为都是html页面,所以我们父类不需要推迟给子类实现,直接在父类实现
        stringBuffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        //页面下面就是成对的一个HTML标签，我们也在父类加入,不需要给子类实现
        stringBuffer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        //下面就应该是head标签里的内容了,这个我们父类做不了主了,推迟到子类实现,所以我们定义一个抽象方法,让子类必须实现
        appendHead(stringBuffer);
        //下面是body的内容了，我们父类依然无法做主，仍然推迟到子类实现
        appendBody(stringBuffer);
        //html标签的关闭
        stringBuffer.append("</html>");
        return stringBuffer.toString();
    }

    //第一个模板方法
    protected abstract void appendHead(StringBuffer stringBuffer);

    //第二个模板方法
    protected abstract void appendBody(StringBuffer stringBuffer);

}
//子类继承 模板抽象类，完成模板抽象类的模板方法。形成多态性。
class MyPageBuilder extends AbstractPageBuilder{

    @Override
    protected void appendHead(StringBuffer stringBuffer) {
        stringBuffer.append("<head><title>Demo</title></head>");
    }

    @Override
    protected void appendBody(StringBuffer stringBuffer) {
        stringBuffer.append("<body><h1>Hello,world！</h1></body>");
    }

    public static void main(String[] args) {
        PageBuilder pageBuilder = new MyPageBuilder();
        System.out.println(pageBuilder.bulidHtml());
    }
}
//接口
interface Person {

    void speak();

    void listen();

    void work();

}
//增加一个适配器
abstract class AbstractPerson implements Person{
    @Override
    public void speak() {
    }

    @Override
    public void listen() {
    }

    @Override
    public void work() {
        System.out.println("every one work");
    }
}
//这样就可以针对需要来重写需要的方法。
class  PersonA extends  AbstractPerson{
    @Override
    public void speak() {
        System.out.println("sey goodbye");
    }
}
//这样就可以针对需要来重写需要的方法。
class  PersonB extends  AbstractPerson{
    @Override
    public void listen() {
        System.out.println("listen to music ");
    }
}