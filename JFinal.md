# JFinal
## 环境搭建
![项目结构](C:/Users/米昊/Desktop/米昊/JFinal/1519698616(1).png)

## pom文件

```` 
<dependencies>
        <dependency>
            <groupId>com.jfinal</groupId>
            <artifactId>jfinal</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.0.1</version>
        </dependency>
        <dependency>
            <groupId>com.jfinal</groupId>
            <artifactId>jetty-server</artifactId>
            <version>8.1.8</version>
        </dependency>
        <dependency>
            <groupId>de.ruedigermoeller</groupId>
            <artifactId>fst</artifactId>
            <version>2.31</version>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.9.0</version>
        </dependency>
    </dependencies>
```` 

## web.xml文件

```` 
    <filter>
        <filter-name>jfinal</filter-name>
        <filter-class>com.jfinal.core.JFinalFilter</filter-class>
        <init-param>
            <param-name>configClass</param-name>
            <param-value>demo.DemoConfig</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>jfinal</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
````

## demo代码 

### person.java

````
private String name;
    private int age;

    public void Persion() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
````

### DemoConfig.java

```` 
public class DemoConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        constants.setDevMode(true);
    }

    public void configRoute(Routes routes) {
        routes.add("/hello", HelloController.class);
    }

    public void configPlugin(Plugins plugins) {
        RedisPlugin bbsRedis = new RedisPlugin("bbs", "localhost");
        plugins.add(bbsRedis);

        RedisPlugin newsRedis = new RedisPlugin("news", "localhost");
        plugins.add(newsRedis);
    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }
}
````

### HelloController.java

````
public class HelloController extends Controller{

    public void index() {
        Cache bbsCache = Redis.use("bbs");
        Persion persion = new Persion();
        persion.setAge(25);
        persion.setName("mih");
        bbsCache.set("mih", persion);
        Persion mih = bbsCache.get("mih");
        renderText(String.valueOf(mih.getAge()));
    }
}
````

### Start.java
```` 
public class Start {
    public static void main(String[] args) {
        JFinal.start("src/main/webapp",80, "/", 5);
    }
}
````


