# 设计模式
http://www.cnblogs.com/maowang1991/archive/2013/04/15/3023236.html
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
