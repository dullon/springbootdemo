package com.dullon.demoboot.designpattern2;

/**
 * 备忘录模式（Memento Pattern）: 又叫做快照模式（Snapshot Pattern）或Token模式
 * 定义：在不破坏封闭的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可将该对象恢复到原先保存的状态。
 *
 * 1.Originator(发起人)：负责创建一个备忘录Memento，用以记录当前时刻自身的内部状态，并可使用备忘录恢复内部状态。Originator可以根据需要决定Memento存储自己的哪些内部状态。
 * 2.Memento(备忘录)：负责存储Originator对象的内部状态，并可以防止Originator以外的其他对象访问备忘录。备忘录有两个接口：Caretaker只能看到备忘录的窄接口，他只能将备忘录传递给其他对象。Originator却可看到备忘录的宽接口，允许它访问返回到先前状态所需要的所有数据。
 * 3.Caretaker(管理者):负责备忘录Memento，不能对Memento的内容进行访问或者操作。
 * 一、备忘录模式的优点
 * 1、有时一些发起人对象的内部信息必须保存在发起人对象以外的地方，但是必须要由发起人对象自己读取，这时，
 * 使用备忘录模式可以把复杂的发起人内部信息对其他的对象屏蔽起来，从而可以恰当地保持封装的边界。
 * 2、本模式简化了发起人类。发起人不再需要管理和保存其内部状态的一个个版本，客户端可以自行管理他们所需
 * 要的这些状态的版本。
 * 3、当发起人角色的状态改变的时候，有可能这个状态无效，这时候就可以使用暂时存储起来的备忘录将状态复原。
 * 二、备忘录模式的缺点：
 * 1、如果发起人角色的状态需要完整地存储到备忘录对象中，那么在资源消耗上面备忘录对象会很昂贵。
 * 2、当负责人角色将一个备忘录 存储起来的时候，负责人可能并不知道这个状态会占用多大的存储空间，从而无法提醒用户一个操作是否很昂贵。
 * 3、当发起人角色的状态改变的时候，有可能这个协议无效。如果状态改变的成功率不高的话，不如采取“假如”协议模式。
 */

public class RoleMementoTest {

    public static void main(String[] args) {
        // 测试程序
        // 新建角色
        PlayRole role = new PlayRole(100, 100, 100);
        // 新建管理者
        Caretaker taker = new Caretaker();
        // 角色初始状态
        System.out.println("游戏刚开始，角色各属性:");
        role.showState();
        // 利用备忘录模式保存当前状态
        System.out.println("\n【保存游戏状态！】\n");
        taker.setMemento(role.createMemento());
        role.setAggressivity(20);
        role.setDefencivity(30);
        role.setVitality(0);
        // 大战过后，角色能力值下降
        System.out.println("与Boss对战后，角色各项能力已大大下降:");
        role.showState();
        // 恢复保存的角色状态
        role.setMemento(taker.getMemento());
        //taker.getMemento();
        System.out.println("\n【恢复保存的角色状态！】");
        System.out.println("\n恢复后角色的当前状态：");
        role.showState();
    }
}
//(发起人) 创建备忘录
class PlayRole {
    private int vitality;
    private int aggressivity;
    private int defencivity;
    public PlayRole(int vitality, int aggressivity, int defencivity) {
        super();
        this.vitality = vitality;
        this.aggressivity = aggressivity;
        this.defencivity = defencivity;
    }
    public PlayRole() {}
    public int getVitality() {
        return vitality;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
    public int getAggressivity() {
        return aggressivity;
    }
    public void setAggressivity(int aggressivity) {
        this.aggressivity = aggressivity;
    }
    public int getDefencivity() {
        return defencivity;
    }
    public void setDefencivity(int defencivity) {
        this.defencivity = defencivity;
    }
    public RoleMemento createMemento() {
        RoleMemento memento = new RoleMemento();
        memento.setAggressivity(aggressivity);
        memento.setDefencivity(defencivity);
        memento.setVitality(vitality);
        return memento;
    }
    public void setMemento(RoleMemento memento) {
        this.aggressivity = memento.getAggressivity();
        this.defencivity = memento.getDefencivity();
        this.vitality = memento.getVitality();
    }
    public void showState() {
        System.out.println("攻击力：" + this.aggressivity + "|防御力：" + this.defencivity
                + "|生命力：" + this.vitality);
    }
}

//备忘录类
class RoleMemento {
    private int vitality;
    private int aggressivity;
    private int defencivity;
    public int getVitality() {
        return vitality;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
    public int getAggressivity() {
        return aggressivity;
    }
    public void setAggressivity(int aggressivity) {
        this.aggressivity = aggressivity;
    }
    public int getDefencivity() {
        return defencivity;
    }
    public void setDefencivity(int defencivity) {
        this.defencivity = defencivity;
    }
}
//管理者类
class Caretaker {
    RoleMemento memento;
    public RoleMemento getMemento() {
        return memento;
    }
    public void setMemento(RoleMemento memento) {
        this.memento = memento;
    }
}

