package test;

import sun.nio.ch.ThreadPool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadTest {


    public static String test() {
        try {
            List<String> test = new ArrayList<String>();
            test.add("m");
            return "mi";
        } catch (Exception ex) {

        } finally {
            System.out.println("final");
        }
        return "hao";
    }

    public static void main(String args[]) {
        test();
    }

}
