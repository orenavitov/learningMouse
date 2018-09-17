package demo.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mihao on 2018/3/25.
 */
public class TestModel {

    private List<Integer> ints;
    private List myList1;
    private List myList2;
    private Set mySet;
    private Map myMap;

    public void init() {
        System.out.println("TestModel 初始化");
    }

    public void destory() {
        System.out.println("TestModel 销毁");
    }

    public List getMyList1() {
        return myList1;
    }

    public void setMyList1(List myList1) {
        this.myList1 = myList1;
    }

    public List getMyList2() {
        return myList2;
    }

    public void setMyList2(List myList2) {
        this.myList2 = myList2;
    }

    public Set getMySet() {
        return mySet;
    }

    public void setMySet(Set mySet) {
        this.mySet = mySet;
    }

    public Map getMyMap() {
        return myMap;
    }

    public void setMyMap(Map myMap) {
        this.myMap = myMap;
    }

    public List getInts() {
        return ints;
    }

    public void setInts(List ints) {
        this.ints = ints;
    }
}
