package generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 声明一个泛型接口Collection<T>, 声明一个改接口的子类Basket.
 * 要么子类也带上泛型类型, 要么指定具体的类型, 要么子类和接口都不带. 否则编译会报错.
 * error eg:
 * <pre>
 *     public class Basket implements Collection<T> {
 *         @Override
 *         public T next() {
 *             return null;
 *         }
 *     }
 * </pre>
 *
 * correct eg:
 * <pre>
 *     public class Basket<T> implements Collection<T> {
 *         @Override
 *         public T next() {
 *             return null;
 *         }
 *     }
 * </pre>
 * <pre>
 *     public class Basket implements Collection<Integer> {
 *         @Override
 *         public Integer next() {
 *             return null;
 *         }
 *     }
 * </pre>
 * <pre>
 *     public class Basket implements Collection {
 *         @Override
 *         public Object next() {
 *             return null;
 *         }
 *     }
 * </pre>
 *
 * @author liuweibo
 * @date 2018/6/20
 */
public class Basket<T> implements Collection<T> {

    @Override
    public T next() {
        T t;
        return null;
    }

    public void t(T t) {
        T t1;
    }

    public void print(List<Number> coll) {

    }

    public void test1() {
        List<Integer> list1 = Arrays.asList(1, 2);
        // 编译报错, List<Integer> 不是 List<Number> 的子类型
//        print(list1);
        // 解决办法, 利用通配符
        print2(list1);
    }

    /**
     * ? 是类型实参(可以看成是和Integer、String等真实的类型), 而不是像 T 等是类型形参. 切记切记.
     * @param coll
     */
    public void print2(List<? extends Number> coll) {
        // 报错, 因为 ? 表示不知道调用者要传入什么类型, 调用者可能传入List<Double>,
        // 也肯能传入List<Integer>.
//        coll.add(111);
    }

    public <E extends Number> void print3(List<E> coll) {
        // 道理同print3()方法类似
//        coll.add(11);
    }

    public void print4() {
        List<?> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
    }

}
