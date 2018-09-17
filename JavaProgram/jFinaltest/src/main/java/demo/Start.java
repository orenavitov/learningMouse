package demo;

import com.jfinal.core.JFinal;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class Start {
    public static void main(String[] args) {
        JFinal.start("src/main/webapp",80, "/", 5);
    }
}
