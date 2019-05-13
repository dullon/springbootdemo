package com.dullon.demoboot.designpattern2;

import java.util.*;
import java.util.List;


/**
 * 访问者模式：表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素类的前提下定义作用于这些元素的新操作。
 *角色：
    1.Visitor 抽象访问者角色，为该对象结构中具体元素角色声明一个访问操作接口。该操作接口的名字和参数标识了发送访问请求给具体访问者的具体元素角色，这样访问者就可以通过该元素角色的特定接口直接访问它。
    2.ConcreteVisitor.具体访问者角色，实现Visitor声明的接口。
    3.Element 定义一个接受访问操作(accept())，它以一个访问者(Visitor)作为参数。
    4.ConcreteElement 具体元素，实现了抽象元素(Element)所定义的接受操作接口。
    5.ObjectStructure 结构对象角色，这是使用访问者模式必备的角色。它具备以下特性：能枚举它的元素；可以提供一个高层接口以允许访问者访问它的元素；如有需要，可以设计成一个复合对象或者一个聚集（如一个列表或无序集合）。

  优点：
    1、使得数据结构和作用于结构上的操作解耦，使得操作集合可以独立变化。
    2、添加新的操作或者说访问者会非常容易。
    3、将对各个元素的一组操作集中在一个访问者类当中。
    4、使得类层次结构不改变的情况下，可以针对各个层次做出不同的操作，而不影响类层次结构的完整性。
    5、可以跨越类层次结构，访问不同层次的元素类，做出相应的操作。
   缺点：
     1、增加新的元素会非常困难。
     2、实现起来比较复杂，会增加系统的复杂性。
     3、破坏封装，如果将访问行为放在各个元素中，则可以不暴露元素的内部结构和状态，但使用访问者模式的时候，为了让访问者能获取到所关心的信息，元素类不得不暴露出一些内部的状态和结构，就像收入和支出类必须提供访问金额和单子的项目的方法一样。
   适用性：
     1、数据结构稳定，作用于数据结构的操作经常变化的时候。
     2、当一个数据结构中，一些元素类需要负责与其不相关的操作的时候，为了将这些操作分离出去，以减少这些元素类的职责时，可以使用访问者模式。
     3、有时在对数据结构上的元素进行操作的时候，需要区分具体的类型，这时使用访问者模式可以针对不同的类型，在访问者类中定义不同的操作，从而去除掉类型判断。


 * 静态分派以及多分派：  静态分派就是按照变量的静态类型进行分派，从而确定方法的执行版本，静态分派在编译时期就可以确定方法的版本。
 *
 * 在静态分派判断的时候，我们根据多个判断依据（即参数类型和个数）判断出了方法的版本，那么这个就是多分派的概念，因为我们有一个以上的考量标准，也可以称为宗量。所以JAVA是静态多分派的语言。
 *
 *  动态分派以及单分派：对于动态分派，与静态相反，它不是在编译期确定的方法版本，而是在运行时才能确定。
 *
 *  摘自左潇龙博客：https://blog.csdn.net/zuoxiaolong8810/article/details/9787251
 */

public class VisitorTest {
    public static void main(String[] args) {
        AccountBook accountBook = new AccountBook();
        //添加两条收入
        accountBook.addBill(new IncomeBill(10000, "卖商品"));
        accountBook.addBill(new IncomeBill(12000, "卖广告位"));
        //添加两条支出
        accountBook.addBill(new ConsumeBill(1000, "工资"));
        accountBook.addBill(new ConsumeBill(2000, "材料费"));

        Viewer boss = new Boss();
        Viewer cpa = new CPA();
        Viewer cfo = new CFO();

        //两个访问者分别访问账本
        accountBook.show(cpa);
        accountBook.show(boss);
        accountBook.show(cfo);

        ((Boss) boss).getTotalConsume();
        ((Boss) boss).getTotalIncome();
    }

}



//单个单子的接口（相当于Element）
interface IBill {
    void accept(Viewer viewer);
}


//抽象单子类，一个高层次的单子抽象
abstract class AbstractBill implements IBill{

    protected double amount;

    protected String item;

    public AbstractBill(double amount, String item) {
        super();
        this.amount = amount;
        this.item = item;
    }

    public double getAmount() {
        return amount;
    }

    public String getItem() {
        return item;
    }

}
//收入单子
class IncomeBill extends AbstractBill{

    public IncomeBill(double amount, String item) {
        super(amount, item);
    }

    public void accept(Viewer viewer) {
        if (viewer instanceof AbstractViewer) {
            ((AbstractViewer)viewer).viewIncomeBill(this);
            return;
        }
        viewer.viewAbstractBill(this);
    }

}
//消费的单子
class ConsumeBill extends AbstractBill{

    public ConsumeBill(double amount, String item) {
        super(amount, item);
    }

    public void accept(Viewer viewer) {
        if (viewer instanceof AbstractViewer) {
            ((AbstractViewer)viewer).viewConsumeBill(this);
            return;
        }
        viewer.viewAbstractBill(this);
    }

}
//超级访问者接口（它支持定义高层操作）
interface Viewer{

    void viewAbstractBill(AbstractBill bill);

}

//比Viewer接口低一个层次的访问者接口
abstract class AbstractViewer implements Viewer{

    //查看消费的单子
    abstract void viewConsumeBill(ConsumeBill bill);

    //查看收入的单子
    abstract void viewIncomeBill(IncomeBill bill);

    public final void viewAbstractBill(AbstractBill bill){}
}
//老板类，查看账本的类之一，作用于最低层次结构
class Boss extends AbstractViewer{

    private double totalIncome;

    private double totalConsume;

    //老板只关注一共花了多少钱以及一共收入多少钱，其余并不关心
    public void viewConsumeBill(ConsumeBill bill) {
        totalConsume += bill.getAmount();
    }

    public void viewIncomeBill(IncomeBill bill) {
        totalIncome += bill.getAmount();
    }

    public double getTotalIncome() {
        System.out.println("老板查看一共收入多少，数目是：" + totalIncome);
        return totalIncome;
    }

    public double getTotalConsume() {
        System.out.println("老板查看一共花费多少，数目是：" + totalConsume);
        return totalConsume;
    }

}
//注册会计师类，查看账本的类之一，作用于最低层次结构
class CPA extends AbstractViewer{

    //注会在看账本时，如果是支出，则如果支出是工资，则需要看应该交的税交了没
    public void viewConsumeBill(ConsumeBill bill) {
        if (bill.getItem().equals("工资")) {
            System.out.println("注会查看账本时，如果单子的消费目的是发工资，则注会会查看有没有交个人所得税。");
        }
    }
    //如果是收入，则所有的收入都要交税
    public void viewIncomeBill(IncomeBill bill) {
        System.out.println("注会查看账本时，只要是收入，注会都要查看公司交税了没。");
    }

}
//财务主管类，查看账本的类之一，作用于高层的层次结构
class CFO implements Viewer {

    //财务主管对每一个单子都要核对项目和金额
    public void viewAbstractBill(AbstractBill bill) {
        System.out.println("财务主管查看账本时，每一个都核对项目和金额，金额是" + bill.getAmount() + "，项目是" + bill.getItem());
    }

}
//账本类（相当于ObjectStruture）
class AccountBook {
    //单子列表
    private List<IBill> billList = new ArrayList<IBill>();
    //添加单子
    public void addBill(IBill bill){
        billList.add(bill);
    }
    //供账本的查看者查看账本
    public void show(Viewer viewer){
        for (IBill bill : billList) {
            bill.accept(viewer);
        }
    }
}
