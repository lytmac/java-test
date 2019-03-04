package jdk_base.generic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GenericTest {

    public static void main(String[] args) {
        testWildcard();
        printGenericType();
        checkTypeErasure();
        explodeLimitOfCompilerByReflect();
    }

    /**
     * 通配符：<?>、 <? extends BaseClass>、 <? super SubClass>
     *
     */
    private static void testWildcard() {
        //三种通配符分别表示的是不对类型做任何限定、限定类型的上界和限定类型的下界
        //使用泛型通配符这一特性时，Java从编译器层面就杜绝对泛型对象持有者的修改操作。因为通配符无法明确

        List<?> list1 = new ArrayList<String>();
        //list1.add("ass");

        List<? extends Exception> list2 = new ArrayList<>();
        //list2.add(new Exception());

        List<? super RuntimeException> list3 = new ArrayList<>();
        list3.add(new RuntimeException());

        List<Exception> list4 = new ArrayList<>();
        list4.add(new RuntimeException());

    }

    public static class Example<T, E extends Exception> {
        private T object;
        private E exception;

        public Example(T object, E exception) {
            this.object = object;
            this.exception = exception;
        }


        public T getObject() {
            return object;
        }

        public E getException() {
            return exception;
        }
    }

    private static void printGenericType() {
        Example<String, RuntimeException> example = new Example<>("example", new RuntimeException());

        Class clazz = example.getClass();
        System.out.println("the class of example is: " + clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            /**
             * 打印结果:
             * the type of the filed: object is : class java.lang.Object
             * the type of the filed: exception is : class java.lang.Exception
             *
             * 泛型的类型是其所能明确的非泛型上界。
             * 所以无论形参传了什么类型上去，在运行期间的类型都被擦除了，只能看到Object或者明确指定的基类
             */
            System.out.println("the type of the filed: " + field.getName() + " is : " + field.getType());
        }
    }

    /**
     * List<String>、List<Integer> 并不能持有相同的对象。
     * 并没有List<String>、List<Integer>这两种不同的Class类型。在编译期间，泛型就已经被擦除了。只保留了原生的List类型
     */
    private static void checkTypeErasure() {
        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();

        Class strListClass = strList.getClass();
        Class intListClass = intList.getClass();

        System.out.println("the List<String> class type is: " + strListClass);
        System.out.println("the List<Integer> class type is: " + intListClass);
    }

    /**
     * 通过反射绕开编译器对于泛型形参的校验
     */
    private static void explodeLimitOfCompilerByReflect() {
        List<Integer> intList = new ArrayList<>();

        intList.add(123);
        try {
            /**
             * 因为在编译期间，无论传入的形参是什么类型，都将会擦除类型转为Object(未通过: ? extends BaseClass 定义上届的前提下)。
             */
            Method add = intList.getClass().getDeclaredMethod("add", Object.class);

            add.invoke(intList, 123005341324L);
            add.invoke(intList, 4.56f);

            for(Object obj : intList) {
                System.out.println("the item is: " + obj);
            }

        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
        }
    }
}
