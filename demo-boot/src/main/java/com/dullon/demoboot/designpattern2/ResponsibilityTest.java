package com.dullon.demoboot.designpattern2;

/**
 * 责任链模式 ： 为了避免请求的发送者和接收者之间的耦合关系，使多个接受对象都有机会处理请求。将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它为止。发出这个请求的客户端并不知道链上的哪一个对象最终处理这个请求，这使得系统可以在不影响客户端的情况下动态地重新组织和分配责任。
 *
 * 抽象处理者(Handler)角色：定义出一个处理请求的接口。如果需要，接口可以定义 出一个方法以设定和返回对下家的引用。这个角色通常由一个Java抽象类或者Java接口实现。上图中Handler类的聚合关系给出了具体子类对下家的引用，抽象方法handleRequest()规范了子类处理请求的操作。
 * 具体处理者(ConcreteHandler)角色：具体处理者接到请求后，可以选择将请求处理掉，或者将请求传给下家。由于具体处理者持有对下家的引用，因此，如果需要，具体处理者可以访问下家。
 * 请求处理者角色Client : 设置不同的具体处理者的层级。
 *
 * 责任链优点
 * 解耦请求者和发送者。
 * 简化具体责任对象，因为它不知道链的结构，只要处理自己对应的工作即可。
 * 可以动态的增加或者删除责任对象。
 *
 * 责任链缺点
 * 并不能保证请求一定会被执行，可能落到链尾之外【可以是优点也可以是缺点】
 * 如果出错，不太容易除错。可能需要到每个具体的责任对象一 一排查。
 * 一个责任链需要整个具体责任链对象遍历，然后设置不同的层级。
 */
/*
 * 作者：凶残的程序员
 * 原文：https://blog.csdn.net/qian520ao/article/details/73558275
 */
public class ResponsibilityTest {
    public static void main(String [] args){
        Handler rubbishHandler = new RubbishHandler();//创建Handler实例
        Handler negativeHanlder = new NegativeHanlder();
        Handler talentedHanlder = new TalentedHanlder();
        Handler jayHanlder = new JayHanlder();

            rubbishHandler.setSuccessor(negativeHanlder);//然后关联层级
            negativeHanlder.setSuccessor(talentedHanlder);
            talentedHanlder.setSuccessor(jayHanlder);


        Response response_new = rubbishHandler.handlerRequest("周杰伦先生你好，我是卓伟，我这边有一张侯佩琴的...");
            System.out.println(response_new.getDealMan() + "【处理了该事件】" + response_new.getMsg());

        Response response_new2 = rubbishHandler.handlerRequest("昆凌说老周啊，该生三胎了啊");
            System.out.println(response_new2.getDealMan() + "【处理了该事件】" + response_new2.getMsg());
    }
}
//抽象的处理类 也可以是接口
abstract class Handler {
    private Handler successor;

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    abstract Response handlerRequest(String msgChain);
}
// 需要管理的任务
class Response {

    private String msg;
    private String dealMan;
    public Response(String msg, String dealMan) {
        this.msg = msg;
        this.dealMan = dealMan;
    }
    public String getMsg() {
        return msg;
    }
    public String getDealMan() {
        return dealMan;
    }
}
//具体管理者 1
class RubbishHandler extends Handler {

    @Override
    Response handlerRequest(String msgChain) {
        if (msgChain.contains("安利") || msgChain.contains("健身吗靓仔") || msgChain.contains("信用卡办理")) {
            return new Response("清空垃圾信息", "智能机器管家");
        } else {
            return getSuccessor().handlerRequest(msgChain);
        }
    }
}
//具体管理者 2
class NegativeHanlder extends Handler {

    @Override
    Response handlerRequest(String msgChain) {
        if (msgChain.contains("卓伟") || msgChain.contains("赵五儿") || msgChain.contains("宋祖德")) {
            return new Response("马上拿1000万封口费", "大妮");
        } else {
            return getSuccessor().handlerRequest(msgChain);
        }
    }
}
//具体管理者 3
class TalentedHanlder extends Handler {

    @Override
    Response handlerRequest(String msgChain) {
        if (msgChain.contains("张惠妹") || msgChain.contains("林夕") || msgChain.contains("张学友")) {
            return new Response("执笔写歌词", "方文山");
        } else {
            return getSuccessor().handlerRequest(msgChain);
        }
    }
}
//具体管理者 4
class JayHanlder extends Handler {

    @Override
    Response handlerRequest(String msgChain) {
        return new Response("媳妇，别闹", "周杰伦");
    }
}
