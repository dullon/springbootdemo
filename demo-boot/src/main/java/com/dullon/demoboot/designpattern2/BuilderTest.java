package com.dullon.demoboot.designpattern2;

/**
 * 建造者模式：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 实用范围
 * 1 当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。
 * 2 当构造过程必须允许被构造的对象有不同表示时。
 * 角色
 * 1 builder：为创建一个产品对象的各个部件指定抽象接口。
 * 2 ConcreteBuilder：实现Builder的接口以构造和装配该产品的各个部件，定义并明确它所创建的表示，并 提供一个检索产品的接口。
 * 3 Director：构造一个使用Builder接口的对象。
 * 4 Product：表示被构造的复杂对象。ConcreteBuilder创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，包括将这些部件装配成最终产品的接口。
 *
 * 缺点
 *   1、对象的构建过程非常复杂，每次客户端调用的时候，都要自己控制构建过程，这会导致构建过程的重复，而且非常容易忘记某一个步骤。
 *   2、对象的表示是可以变化的，所以如果要重复制造几个表示相同的对象的时候，只能每次都将过程和表示重复一遍。
 *
 * 优点
 *   1、最主要的好处是，使得对象的构建与表示分离，可以让表示独立于过程而独立的变化，并且客户端不再需要关心具体的构建过程和表示。
 *   2、由于固定的表示由具体的制造者提供，所以要重复制造几个表示相同的对象的时候，只需要让指挥者控制制造者再进行一次即可。
 */
public class BuilderTest {
    public static void main(String[] args) {

        //套餐A
        MealA a = new MealA();
        //准备套餐A的服务员
        KFCWaiter waiter = new KFCWaiter(a);
        //获得套餐
        Meal mealA = waiter.construct();
        System.out.print("套餐A的组成部分:");
        System.out.println("食物："+mealA.getFood()+"；   "+"饮品："+mealA.getDrink());
        System.out.println("---------------------------");
        //准备套餐B的服务员
        KFCWaiter waiterB = new KFCWaiter(new MealB());
        //获得套餐
        Meal mealB = waiterB.construct();
        System.out.print("套餐B的组成部分:");
        System.out.println("食物："+mealB.getFood()+"；   "+"饮品："+mealB.getDrink());
    }
}
//builder 抽象的建造者
abstract class MealBuilder {
    Meal meal = new Meal();

    public abstract void buildFood();

    public abstract void buildDrink();

    public Meal getMeal(){
        return meal;
    }
}
//ConcreteBuilder 建造者实例1
class MealA extends MealBuilder{

    public void buildDrink() {
        meal.setDrink("可乐");
    }

    public void buildFood() {
        meal.setFood("薯条");
    }

}
//ConcreteBuilder 建造者实例2
class MealB extends MealBuilder{

    public void buildDrink() {
        meal.setDrink("柠檬果汁");
    }

    public void buildFood() {
        meal.setFood("鸡翅");
    }

}
//Product 具体的被建造对象
class Meal {
    private String food;
    private String drink;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }
}
//Director 指挥者（用于组合建造者和被建造者，并生成各种产品。核心功能）
class KFCWaiter {
    private MealBuilder mealBuilder;

    public KFCWaiter(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }


    public Meal construct(){
        //准备食物
        mealBuilder.buildFood();
        //准备饮料
        mealBuilder.buildDrink();

        //准备完毕，返回一个完整的套餐给客户
        return mealBuilder.getMeal();
    }
}