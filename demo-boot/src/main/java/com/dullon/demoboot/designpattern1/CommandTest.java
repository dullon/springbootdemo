package com.dullon.demoboot.designpattern1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 命令模式：在软件系统中，“行为请求者”与“行为实现者”通常呈现一种“紧耦合”。但在某些场合，比如要对行为进行“记录、撤销/重做、事务”等处理，这种无法抵御变化的紧耦合是不合适的。在这种情况下，如何将“行为请求者”与“行为实现者”解耦？将一组行为抽象为对象，实现二者之间的松耦合。这就是命令模式（Command Pattern）。
 * ---------------------
   用编程的语言来解释命令模式的使用场景或者说可以解决的问题，就是下面几点。
                 1，希望将行为请求者和行为实现者解耦，不直接打交道。
                 2，希望分离掉行为请求者一部分的责任，行为请求者只需要将命令发给调用者，不再主动的去让行为实现者产生行为，符合                    单一职责原则。
                 3，希望可以控制执行的命令列表，方便记录，撤销/重做以及事务等功能。
                 4，期待可以将请求排队，有序执行。
                 5，希望可以将请求组合使用。

 1，最大的优点，就是将行为请求者和行为实现者解耦。
 2，命令的添加特别方便，并且可以方便的制定各种命令和利用现有命令组合出新的命令。
 3，如果针对每一类具有共同接口的接受者制作一个调用者，可以控制命令的执行情况。
 ---------------------
案例来自————左潇龙
 原文：https://blog.csdn.net/zuoxiaolong8810/article/details/9153511
 间接调用，需求者 含有 调用者，调用者 含有 执行者， 需求者调用 调用者 通过调用者 在调用执行者。实现命令模式，类似责任链，
 目的不同：他的目的是 执行过程， 责任链目的是 执行后的结果。所以命令模式时过程导向，责任链模式是结果导向。
 */
public class CommandTest {
    public static void main(String[] args) {
        Programmer xiaozuo = new Programmer("小左");
        ProductManager productManager = new ProductManager(xiaozuo);

        Salesman salesmanA = new Salesman("A",productManager);
        Salesman salesmanB = new Salesman("B",productManager);
        Salesman salesmanC = new Salesman("C",productManager);
        Salesman salesmanD = new Salesman("D",productManager);

        salesmanA.putDemand();
        salesmanB.putDemand();
        salesmanB.putBug();
        salesmanC.putDemand();
        salesmanC.putProblem();
        salesmanD.putDemand();

        System.out.println("第一天产品经理分配任务");
        productManager.assign();
        productManager.printTaskList();
        System.out.println("第二天产品经理分配任务");
        productManager.assign();
        productManager.printTaskList();

    }

}

//任务总接口
interface Task{
    void handle();
}
//程序员类，真正执行命令的类
class Programmer {

    private String name;

    public Programmer(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void handleDemand(){
        System.out.println( name + "处理新需求");
    }

    public void handleBug(){
        System.out.println( name + "处理bug");
    }

    public void handleProblem(){
        System.out.println( name + "处理线上问题");
    }

}
//任务1 任务实现类
class Demand implements Task{

    private Programmer programmer;

    public Demand(Programmer programmer) {
        super();
        this.programmer = programmer;
    }

    public void handle() {
        programmer.handleDemand();
    }

    public String toString() {
        return "Demand [programmer=" + programmer.getName() + "]";
    }

}
//任务2 任务实现类
class Bug implements Task{

    private Programmer programmer;

    public Bug(Programmer programmer) {
        super();
        this.programmer = programmer;
    }

    public void handle() {
        programmer.handleBug();
    }

    public String toString() {
        return "Bug [programmer=" + programmer.getName() + "]";
    }

}
//任务3 任务实现类
class Problem implements Task{

    private Programmer programmer;

    public Problem(Programmer programmer) {
        super();
        this.programmer = programmer;
    }

    public void handle() {
        programmer.handleProblem();
    }

    public String toString() {
        return "Problem [programmer=" + programmer.getName() + "]";
    }

}
//产品经理， 分发任务的具体实例 ，可以存放或调用一些策略逻辑。
class ProductManager {

    private static final int TASK_NUMBER_IN_DAY = 4;//一天最多分派掉四个任务，多了推到第二天

    private List<Task> taskList;//任务列表

    private List<Programmer> programmerList;//产品经理应该认识所有的程序猿

    private int currentIndex;

    public ProductManager(Programmer... programmers) {
        super();
        if (programmers == null || programmers.length == 0) {
            throw new RuntimeException("产品经理手下没有程序员，任务分配不出去，无法正常工作！");
        }
        taskList = new ArrayList<Task>();
        programmerList = Arrays.asList(programmers);
    }

    //接受一个任务
    public void receive(Task task){
        taskList.add(task);
    }

    public void assign(){
        Task[] copy = new Task[taskList.size() > TASK_NUMBER_IN_DAY ? taskList.size() - TASK_NUMBER_IN_DAY : 0];
        for (int i = 0; i < TASK_NUMBER_IN_DAY && i < taskList.size(); i++) {
            taskList.get(i).handle();
        }
        System.arraycopy(taskList.toArray(), TASK_NUMBER_IN_DAY > taskList.size() ? taskList.size() : TASK_NUMBER_IN_DAY, copy, 0, copy.length);
        taskList = Arrays.asList(copy);
    }
    //产品经理可以选择程序猿，简单的循环选取。
    public Programmer chooseProgrammer(){
        return programmerList.get(currentIndex == programmerList.size() ? 0 : currentIndex++);
    }

    public void printTaskList(){
        if (taskList == null || taskList.size() == 0) {
            System.out.println("----------当前无任务--------");
            return;
        }
        System.out.println("---------当前剩下的任务列表--------");
        for (Task task : taskList) {
            System.out.println(task);
        }
        System.out.println("----------------------------------");
    }


}
//业务员类， 提出请求的请求端，只关联产品经理 ，不用关心到底谁做的需求。
class Salesman {

    private String name;

    private ProductManager productManager;

    public Salesman(String name) {
        super();
        this.name = name;
    }

    public Salesman(String name, ProductManager productManager) {
        super();
        this.name = name;
        this.productManager = productManager;
    }

    public void putDemand(){
        System.out.println( "业务员" + name + "提出新需求");
        productManager.receive(new Demand(productManager.chooseProgrammer()));
    }

    public void putBug(){
        System.out.println( "业务员" + name + "提出bug");
        productManager.receive(new Bug(productManager.chooseProgrammer()));
    }

    public void putProblem(){
        System.out.println( "业务员" + name + "提出线上问题");
        productManager.receive(new Problem(productManager.chooseProgrammer()));
    }

    public String getName() {
        return name;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

}

