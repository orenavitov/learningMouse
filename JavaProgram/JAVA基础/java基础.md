## JAVA代码编译过程


http://www.blogjava.net/xylz/archive/2010/07/08/325587.html

JAVA类的编译过程就是.java文件到.class文件的过程：.java文件 -> 词法分析 -> 语法分析 -> 语义分析 -> 注解处理 -> .class文件

## 类加载机制

1. 根加载器BootStrapClassLoader

加载路径： JVA_HOME/JRE/lib/rt.jar

2. 扩展加载器 ExtensionClassLoader

加载路径: JAVA_HOME/JRE/lib/ext/*.jar

3. 系统应用加载器AppClassLoader

加载路径： 系统classpath

4. 用户自定义加载器CustomerClassLoader

加载路径: 自定义路径

在程序执行前要检查类是否被加载， 检查的过程是检查CustomerClassLoaer加载的类 -> AppClassLoader加载的类 -> ExtensionClassLoaer加载的类 -> BootStrapClassLoader加载的类， 如果发现类未被加载， 进行加载， 加载的方式， 按以下顺序在加载路径中寻找该类： BootStrapClassLoader -> ExtensionClassLoader -> AppClassLoader -> CustomerClassLoader

## 类执行机制

1. 连接， 分三个步骤：

* 验证verifying：验证类符合Java规范和JVM规范，和编译阶段的语法语义分析不同

* 准备preparing：为类的静态变量分配内存，初始化为系统的初始值。（不初始化静态代码块）。对于final static修饰的变量，直接赋值为用户的定义值

* 解析resolving：将符号引用（字面量描述）转为直接引用（对象和实例的地址指针、实例变量和方法的偏移量）

2. 类的初始化

![imagetext](./pictures/p5.png)

注意： 使用static final 修饰的常量不会出发类的初始化

## JAVA中的内存分配

## JAVA字节码文件（.class）

## JAVA中的不可变对象

不可变对象指对象创建后它的状态（对象的数据，也就是兑现那个中的属性值）就不能改变， java库中的不可变对象有String, 各种包装类（Inetger, Long, Float）, BigInteger, BigDecimal;

### 不可变对象的实现方法

1. 确保类不能被继承 - 将类声明为final, 或者使用静态工厂并声明构造器为private；

2. 声明属性为private 和 final；

3. 如果类有任何可变对象属性, 那么当它们在类和类的调用者间传递的时候必须被保护性拷贝，如为什么说String是不可变的， 如下图：

![imagetext](./pictures/p4.png)

String 通过byte[] value存储字节， 但是getBytes方法并不返回value本身， 而是重新生成一个byte[];

### 不可变对象的好处

1. 线程安全

2. clone简单

### 为什么Final修饰的变量、方法是线程安全的


## JAVA反射

### Constructor类及其方法

### Field类及其方法

### Method类及其方法

### 

## JAVA如何创建一个对象

1. new

2. 反射

3. clone

4. 序列化

## JAVA序列化


## JAVA HashMap的实现原理

hashMap使用数组链表实现的， 如下图

![imagetext](./pictures/p1.png)

构造方法如下图

![imagetext](./pictures/p2.png)

Put操作如下：

![imagetext](./pictures/p3.png)

首先调用每个key的hashCode方法， 然后计算每个Key的散列位置， 在相应位置形成第一个节点（hashMap在相同散列位置的链表节点插入使用头节点插入的方法）



## Object中的公共方法

1. equals()

2. clone()

3. getClass()

4. notify() && notifyAll() && wait()

## 封装类使用的好处

## JAVA中的hashCode()

如果不重写hashCode()， 返回JVM中的32位内存地址

## JAVA Class

首先RTTI(Run-Time Type Identification)运行时类型识别， 其作用是在运行时识别一个对象的类型和类的信息，这里分两种：传统的”RRTI”,它假定我们在编译期已知道了所有类型(在没有反射机制创建和使用类对象时，一般都是编译期已确定其类型，如new对象时该类必须已定义好)，另外一种是反射机制，它允许我们在运行时发现和使用类型的信息。在Java中用来表示运行时类型信息的对应类就是Class类。

对于同一个对象， 无论创建多少实例， 在内存中只有一个Class对象。

获得Class对象的方法：

* Object.class

* obj(一个实例).getclass()

* Class.forName("")

## JAVA泛型


# JAVA 并发

## JAVA中的synchronized

简单的说synchronized(xxx), xxx可以是普通实例， Class对象， this, static修饰的静态成员变量；
其中普通实例, this表示锁实例， 不锁Class； 
Class对象， static修饰的静态成员变量表示锁Class对象；
synchroniezed还可以放在方法声明前， 如果修饰的静态方法， 表示锁Class对象， 如果是放在非static修饰的方法声明前表示锁实例；
一个线程放弃锁的情况有三种：
* 执行完锁中的代码
* 发生异常
* 当前线程被阻塞， 如wait()
注意：实例锁， 类锁是两种不通的锁

```
public class JavaTest3 {
    final Object obj1 = new Object();
    static final Object obj2 = new Object();
    class synchronizedClass {
        synchronized void synchronizedMethod1() {
            for (int i = 0; i < 100; i ++) {
                System.out.println(Thread.currentThread().getName() + " hi!");
            }

        }
        synchronized void synchronizedMethod2() {
            for (int i = 0; i < 100; i ++) {
                System.out.println(Thread.currentThread().getName() + " hello!");
            }
        }

        void synchronizedMethod3() {
            synchronized(this) {
                for (int i = 0; i < 100; i ++) {
                    System.out.println(Thread.currentThread().getName() + " good!");
                }

            }
        }

    }

    synchronized static void synchronizedMethod4() {
        for (int i = 0; i < 100; i ++) {
            System.out.println(Thread.currentThread().getName() + " goodBye!");
        }
    }

    void synchronizedMethod5() {
        try {
            synchronized (obj1) {
                for (int i = 0; i < 100; i ++) {
                    System.out.println(Thread.currentThread().getName() + " good morning!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void synchronizedMethod6() {
        try {
            synchronized (obj2) {
                for (int i = 0; i < 100; i ++) {
                    System.out.println(Thread.currentThread().getName() + " good night!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        JavaTest3 jt3 = new JavaTest3();
        JavaTest3 jt3_ = new JavaTest3();
        synchronizedClass synchronizedclass = jt3.new synchronizedClass();
        synchronizedClass synchronizedclass_ = jt3.new synchronizedClass();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedclass.synchronizedMethod1();
            }
        }, "t1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedclass.synchronizedMethod2();
            }
        }, "t2");
        thread1.start();
        thread2.start();
    }
}
```

## JAVA中的LOCK

![imagetext](./pictures/p6.png)

https://www.cnblogs.com/aishangJava/p/6555291.html

## JAVA NIO

java NIO由以下几个核心部分构成：

* Channels

* Buffers

* Selectors

Channel的实现有

* FileChannel

* DatagramChannel

* SocketChannel

* ServerSocketChannel

Buffer的实现有：


* ByteBuffer

* CharBuffer

* DoubleBuffer

* FloatBuffer

* IntBuffer

* ShortBuffer

使用buffer一般有以下几个步骤：

1. 写数据到buffer

2. 调用flip()方法

3. 从buffer中读取数据

4. 调用clear()方法或者Compact()方法

buffer三个属性：capacity(容量), position（当前读写的位置）, limit（写模式下Limit就是capacity, 读模式下可以指定）

分配buffer的大小：

ByteBuffer buf = ByteBuffer.allocate(48)

向buf中写入数据：

* 从Channel写到buffer中

```
int bytesRead = inChannel.read(buf)
```

* 通过put方法写入数据

```
buf.put(xxx)
```

从buffer中读取数据

* 从buffer读取数据到Channel

```
int byteWriten = inChannel.write(buf)
```

* 使用get()方法从buffer中读取数据

```
byte b = buf.get()
```

flip()方法：

将buffer从写模式切换到读模式， 并且将position置零， 将limit设置为原来position的数值

rewind()方法：

将position重新置零

clear()方法：

将position置零， limit设置为capacity也就是说会忽略掉buffer中原有的数据；

compact()方法：

compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据

mark()与reset()方法：

mark()会标记一个position, 使用reset()会返回到标记的这个position

![imagetext](./pictures/p7.png)

Select允许在一个线程中处理多个Channel

### Scatter/ Gather

scatter/gather用于描述从Channel中读取或者写入数据

scatter(分散)指将Channel中的数据分散到几个Buffer中；

gather(聚集)指将buffer中的数据聚集到一个Channel中；

```
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);
ByteBuffer[] bufferArray = { header, body };
channel.read(bufferArray);
```

```
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);
ByteBuffer[] bufferArray = { header, body };
channel.write(bufferArray);
```

### Channel间的通信

transferFrom()方法：

```
RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
FileChannel      fromChannel = fromFile.getChannel();
RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
FileChannel      toChannel = toFile.getChannel();
//从什么位置开始传
long position = 0;
//传多少字节， 如果toChannel剩余空间小于count， 则传送的
//字节数会少于count
long count = fromChannel.size();
toChannel.transferFrom(position, count, fromChannel);

```

### Select

Select的创建：

```
Selector selector = Selector.open()
```

Select绑定Channel:

使用Selector时通道必须处于非阻塞模式， 所以FileChannel无法使用Selector;

register的第二个参数是一个“interests”集合， 表示这个通道对什么事件感兴趣：

* Connection 某个channel成功连接到另一个服务器称为“连接就绪”

* Accept 一个server socket channel准备好接收新进入的连接称为“接收就绪”

* Read 一个有数据可读的通道可以说是“读就绪”

* write 等待写数据的通道可以说是“写就绪”

```
channel.configureBlocking(false);
SelectionKey key = channel.register(selector,
    Selectionkey.OP_READ)
```

如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来，如下：

```
int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
```

SelectionKey中包含的内容：

* interest集合

* ready集合

* Channel

* Selector

完整示例代码：

```
Selector selector = Selector.open();
channel.configureBlocking(false);
SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
while(true) {
    //如果没有channel准备就绪， select()会阻塞
  int readyChannels = selector.select();
  if(readyChannels == 0) continue;
  Set selectedKeys = selector.selectedKeys();
  Iterator keyIterator = selectedKeys.iterator();
  while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
  }
}
```

通过SelectorKey返回Channel:

```
Selectorkey.channel()
```