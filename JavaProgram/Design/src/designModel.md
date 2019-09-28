# 设计模式

http://c.biancheng.net/design_pattern/

## 创建模式

### 工厂模式

## 结构型模式

### 代理模式

```
interface A {
    method();
}

//实现B
class B implements A {
    public void method() {

    }
}

//代理类
class C implements A {
    private static B b = new B();
    public void method() {
        preMethod();
        b.method();
        postMethod();
    }

    public void preMethod() {}

    public void postMethod(){}
}
```

### 适配器模式

```
//原来的接口
interface A {
    method();
}

//新的接口
class B {
    public void newMethod() {}
}

//适配器
class C extends B implements A {
    public void method() {
        newMethod();
    }
}

```

### 桥接模式

```

// 被桥接的接口
interface A {
    method()
}

// 被桥接的类
class B implements A {
    public void method(){}
}

// 执行类
class C {
    protect A a;
    protect C(A a) {
        this.a = a
    }

    public void operation(){}
}

// 桥接类
class D extends C {
    public D(A a) {
        super(a)
    }
    public void operation() {
        //桥接体现
        a.method()
    }
}


```

### 装饰模式

```
//被装饰类
class A {
    public void method()
}
//装饰类B
class B extends A {
    public void method() {
        super().method();
        addMethod();
    }

    //添加的装饰方法
    public void addMethod(){}
}
//装饰类C
class C extends A {
    public void method() {
        super().method();
        addMethod();
    }

    //添加的装饰方法
    public void addMethod(){}
}

```

## 行为模式

### 策略模式

```
// 所有策略的抽象接口
interface Strategy {
    // 要实现的策略方法
    public void strategyMethod();
}

// 策略A
class StrategyA implements Strategy {
    public void strategyMethod() {}
}

// 策略B
class StrategyB implements Strategy {
    public void strategyMethod() {}
}

// 供外部调用的类
class Context {
    private Strategy strategy;
    public Strategy getStrategy() {
        return stratefy;
    }
    public void setStrategy() {
        this.strategy = strategy;
    }
    public void strategyMethod() {
        // 调用具体的策略
        strategy.strategyMethod();
    }
}
```

### 命令模式

```
//抽象命令
interface Command {
    public void execute();
}
// 具体命令
class CommandA implements Command {
    // 每一个命令需要一个接收者
    private Receiver receiver;
    public CommandA() {
        receiver = new Receiver();
    }
    // 接收者执行命令
    public void execute() {
        receiver.action();
    }
}
// 接收者
class Receiver {
    public void action() {}
}
// 调用者， 发出命令的人
class Invoker {
    private Command command;
    public Invoker(Command command) {
        this.command = command;
    }
    public void setCommand(Command command) {
        this.command = command;
    }
    public void call() {
        command.execute();
    }
}
```

### 责任链模式

```
// 抽象处理者（责任链上的一环）
abstract class Handler {
    private Handler next;
    public void setNext(Handler next) {
        this.next = next
    }
    public Handler getNext() {
        return next;
    }
    // 待实现的处理方法
    public abstract void Handle();
}

// 具体的处理者
class HandlerA extend Handler {
    public void Handle() {
        // 如果能处理， 执行以下方法
        if() {

        }
        else {
            // 不能处理， 交给责任链上的下一环
            if (getNext() != null) {
                getNext().Handle();
            }
            // 没有下一环， 该请求无法处理
            else {

            }
        }
    }
}

// 具体的处理者
class HandlerB extend Handler {
    public void Handle() {
        // 如果能处理， 执行以下方法
        if() {

        }
        else {
            // 不能处理， 交给责任链上的下一环
            if (getNext() != null) {
                getNext().Handle();
            }
            // 没有下一环， 该请求无法处理
            else {

            }
        }
    }
}

```

### 状态模式

```
// 抽象状态类
abstract class State {
    // 在该状态下的处理方法， 同时在上下文中设置由此状态转移的下一个状态
    public abstract void Handle(Context context);
}

// 状态类A
class StateA extends State {
    public void Handle(Context context) {
        // 假设StateA的下一个状态是StateB
        context.setState(new StateB());
    }
}

// 状态类B
class StateB extends State {
    public void Handle(Context context) {
        // 假设StateB的下一个状态是StateA
        context.setState(new StateA());
    }
}

class Context {
    private State state;
    public Context() {
        // 假设初始状态为A
        this.state = new StateA();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void Handle() {
        state.Handle(this)
    }
}
```

### 观察者模式

```
class Subject {
    // 保存订阅的观察者
    protect List<Observer> observers = new ArrayList<Observer>()

    //增加观察者
    public void add(Oberver observer) {
        this.observers.add(observer)
    }

    // 删除观察者
    public void remove(Observer observer) {
        this.observers.remove(observer)
    }

    // 通知观察者
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.response()
        }
    }

// 抽象观察者
interface Observer {
    // 收到通知时的动作
    void respone();
}

// 具体观察者A
class ObserverA implements Observer {
    public void respone() {}
}
```

### 迭代器模式

```

```


### 访问者模式

```
// 抽象访问者， 定义对每种数据的处理方式
interface Visitor {
    // 对ConcreteElementA的处理方式
    void visit(ConcreteElementA element);
    // 对ConcreteElementB的处理方式
    void visit(ConcreteElementB element);
}

// 具体访问者A
class VisitorA {

    void visit(ConcreteElementA element){
        element.operationA()
    }

    void visit(ConcreteElementB element){
        element.operationB()
    }
}

// 具体访问者B
class VisitorB {

    void visit(ConcreteElementA element){
        element.operationA()
    }

    void visit(ConcreteElementB element){
        element.operationB()
    }
}

// 抽象元素
interface Element {
    // 接收访问者
    void accept(Visitor visitor)
}

// 具体元素A
class ConcreteElementA implements Element {
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 元素A的具体操作
    public void operationA(){}
}

// 具体元素B
class ConcreteElementB implements Element {
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 元素A的具体操作
    public void operationB(){}
}
```
