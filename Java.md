# JAVA

## 继承Seriable后为什么声明一个Long类型的值

## ClassLoader

动态加载class文件到内存， 使用双亲委派模型， 首先由父类进行加载， 父类找不到时子类进行加载， 默认提供的3个classLoader:

1. BootStrapClassLoader

负责加载jdk中的核心类库， 如： rt.jar resources.jar charsets.jar

2. Extension ClassLoadaer

负责加载java的扩展类库， 默认加载java_home/jre/lib/ext/ 目录下的所有jar

3. App ClassLoader

负责加载应用程序classpath目录下的所有jar和class文件

