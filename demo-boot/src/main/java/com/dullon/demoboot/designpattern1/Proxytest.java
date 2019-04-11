package com.dullon.demoboot.designpattern1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式：首先代理模式，可以分为两种，一种是静态代理，一种是动态代理。
 *
 */
public class Proxytest {
    public static void main(String [] args){
        SchoolGirl lan = new SchoolGirl();
        lan.setName("兰");
        //----------------直接调用--------------------
        Pursuit ming = new Pursuit(lan);
        ming.giveDolls();
        //----------------直接调用--------------------
        //----------------代理调用--------------------
        ProxyClass pc = new ProxyClass(lan);

        pc.giveDolls();
        //----------------代理调用--------------------
        //----------------动态代理调用--------------------
        ProxyPursuit pc2 = (ProxyPursuit)new DynamicProxy().getProxyObject(new Pursuit(lan));
        pc2.giveDolls();

        //----------------动态代理调用--------------------

    }
}
/*
代理接口
*/
interface ProxyPursuit{
    public void giveDolls();
}
/*
追求者类：实际实体类
 */
class Pursuit implements ProxyPursuit{
    private SchoolGirl mm;
    public Pursuit(SchoolGirl mm){
        this.mm = mm;
    }

    public void giveDolls(){
        System.out.println("送她洋娃娃");
    }
}
/*
被追求者类：
 */
class SchoolGirl{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
代理类
 */
class ProxyClass implements ProxyPursuit{
    private Pursuit gg;
    public ProxyClass(SchoolGirl mm){
       gg = new Pursuit(mm);
    }

    public void giveDolls(){
        gg.giveDolls();
    }
}
//-------------一行内容为静态代理，即代理类只能代理被指定的实体类。----------
    /**
     * 动态代理 有两种模式 一是 实现JDK 自带代理接口InvocationHandler， 二是 继承CGLib 提供的代理类。
     */
/*
注：被代理的类必须实现接口，未实现接口则没办法完成动态代理。
 */
class DynamicProxy implements InvocationHandler{

    private Object obj;

    //动态获取代理对象 参数一  类加载器， 参数二 被代理对象的抽象接口 参数三 代理实体。 输出 代理对象
    public Object getProxyObject(Object obj) {
            this.obj = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader()
                    ,obj.getClass().getInterfaces(),this);
        }
    //执行 代理方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //可加入 功能增强
        System.out.println("方法执行前增强");
        Object result = method.invoke(obj, args);
        System.out.println("方法执行后增强");

        return result;
    }
}