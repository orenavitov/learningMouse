import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mih {
    // lambda 表达式
    private static void test1() {
        //1.
        List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
        cost.stream().map(x -> x + x*0.05).forEach(x -> System.out.println(x));
        //2.
        double allCost = cost.stream().map(x -> x+x*0.05).reduce((sum,x) -> sum + x).get();
        //3.
        List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
    }

    // 函数式接口 https://www.cnblogs.com/dgwblog/p/11739500.html
    private static void test2() {

    }

    public static void main(String[] args) {

    }
}
