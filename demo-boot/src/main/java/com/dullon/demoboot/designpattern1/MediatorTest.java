package com.dullon.demoboot.designpattern1;

/**
 * 中介者模式：所谓中介者模式就是用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 *
 * 1、 结构上起到中转作用。通过中介者对象对关系的封装，使得具体的同事类不再需要显示的引用其他对象，它只需要通过中介者就可以完成与其他同事类之间的通信。
 *       2、 行为上起到协作作用。中介者对同事类之间的关系进行封装，同事类在不需要知道其他对象的情况下通过中介者与其他对象完成通信。在这个过程中同事类是不需要指明中介者该如何做，中介者可以根据自身的逻辑来进行协调，对同事的请求进一步处理，将同事成员之间的关系行为进行分离和封装。
 *
 *
 *
 *         ConcreteColleague: 具体同事类。每个具体同事类都只需要知道自己的行为即可，但是他们都需要认识中介者。
 */
public class MediatorTest {
    public static void main(String[] args) {
        //一个房主、一个租房者、一个中介机构
        MediatorStructure mediator = new MediatorStructure();

        //房主和租房者只需要知道中介机构即可
        HouseOwner houseOwner = new HouseOwner("张三", mediator);
        Tenant tenant = new Tenant("李四", mediator);

        //中介结构要知道房主和租房者
        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        tenant.constact("听说你那里有三室的房主出租.....");
        houseOwner.constact("是的!请问你需要租吗?");
    }
}
//Mediator: 抽象中介者。定义了同事对象到中介者对象之间的接口或者抽象类
abstract class Mediator {
    //申明一个联络方法
    public abstract void constact(String message,Colleague colleague);
}


//ConcreteMediator: 具体中介者。实现抽象中介者的方法，它需要知道所有的具体同事类，同时需要从具体的同事类那里接收信息，并且向具体的同事类发送信息。
class MediatorStructure extends Mediator{
    //首先中介结构必须知道所有房主和租房者的信息 可以为list列表
    private HouseOwner houseOwner;
    private Tenant tenant;

    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void constact(String message, Colleague colleague) {
        if(colleague == houseOwner){          //如果是房主，则租房者获得信息
            tenant.getMessage(message);
        }
        else{       //反正则是房主获得信息
            houseOwner.getMessage(message);
        }
    }
}

//Colleague: 抽象同事类。
abstract class Colleague {
    protected String name;
    protected Mediator mediator;

    Colleague(String name,Mediator mediator){
        this.name = name;
        this.mediator = mediator;
    }

}
//具体同事类
class HouseOwner extends Colleague{

    HouseOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    /**
     * @desc 与中介者联系
     * @param message
     * @return void
     */
    public void constact(String message){
        mediator.constact(message, this);
    }

    /**
     * @desc 获取信息
     * @param message
     * @return void
     */
    public void getMessage(String message){
        System.out.println("房主:" + name +",获得信息：" + message);
    }
}
//具体同事类
class Tenant extends Colleague{

    Tenant(String name, Mediator mediator) {
        super(name, mediator);
    }

    /**
     * @desc 与中介者联系
     * @param message
     * @return void
     */
    public void constact(String message){
        mediator.constact(message, this);
    }

    /**
     * @desc 获取信息
     * @param message
     * @return void
     */
    public void getMessage(String message){
        System.out.println("租房者:" + name +",获得信息：" + message);
    }
}
