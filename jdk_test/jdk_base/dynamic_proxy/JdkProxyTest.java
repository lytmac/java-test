package jdk_test.jdk_base.dynamic_proxy;

import java.lang.reflect.*;

/**
 * JDK动态代理测试代码
 * 代理类是public、final，而不是abstract
 * 代理类包名默认是com.sun.proxy，类名默认是$Proxy+自增的整数值。生成的代理类是java.lang.reflect.Proxy的子类
 * 代理类会按同一顺序准确地实现其创建时指定的接口
 * 如果代理类实现了非公共接口，那么它将在与该接口相同的包中定义。否则，代理类的包也是未指定的。注意，包密封将不阻止代理类在运行时在特定包中的成功定义，也不会阻止相同类加载器和带有特定签名的包所定义的类。
 * 由于代理类将实现所有在其创建时指定的接口，所以对其 Class 对象调用 getInterfaces 将返回一个包含相同接口列表的数组（按其创建时指定的顺序），对其 Class 对象调用 getMethods 将返回一个包括这些接口中所有方法的 Method 对象的数组，并且调用 getMethod 将会在代理接口中找到期望的一些方法。
 * 如果 Proxy.isProxyClass 方法传递代理类（由 Proxy.getProxyClass 返回的类，或由 Proxy.newProxyInstance 返回的对象的类），则该方法返回 true，否则返回 false。
 * 代理类的 java.security.ProtectionDomain 与由引导类加载器（如 java.lang.Object）加载的系统类相同，原因是代理类的代码由受信任的系统代码生成。此保护域通常被授予 java.security.AllPermission。
 * 每个代理类都有一个可以带一个参数（接口 InvocationHandler 的实现）的公共构造方法，用于设置代理实例的调用处理程序。并非必须使用反射 API 才能访问公共构造方法，通过调用 Proxy.newInstance 方法（将调用 Proxy.getProxyClass 的操作和调用带有调用处理程序的构造方法结合在一起）也可以创建代理实例。
 */
public class JdkProxyTest {

    /**
     * 代理接口
     */
    public interface BaseOne {
        void firstJob();

        void secondJob();
    }

    public interface BaseTwo {
        void thirdJob();
    }

    /**
     * 代理接口实现类
     */
    public static class BaseImpl implements BaseOne, BaseTwo {
        @Override
        public void firstJob() {
            System.out.println("base one first job is running!");
        }

        @Override
        public void secondJob() {
            System.out.println("base one second job is running!");
        }

        @Override
        public void thirdJob() {
            System.out.println("base two third job is running!");
        }

        //非接口定义方法，是无法得到增强的。生成动态代理类
        public void printName() {
            System.out.println("base implement");
        }
    }

    /**
     * 实现InvocationHandler的invoke方法，定义如何代理目标对象的原始方法。注是目标类里的所有方法都会得到这样的增强。包括toString() hashCode() equal()方法
     */
    public static class WavingInvocationHandler implements InvocationHandler {
        private Object target; //被代理的目标对象

        WavingInvocationHandler(Object target) {
            this.target = target;
        }

        //对原始方法的增强，缺点在于对所有的方法都是同一种增强方式，没法定制化。
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            System.out.println("=============before invoke job=============");
            Object result = method.invoke(target, args);  //执行目标对象中定于的原始方法
            System.out.println("============after invoke job=============\n");
            return result;
        }

        public Object bind() {
            return Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    this);
        }
    }

    public static void main(String[] args) {
        /**
         * sun.misc.ProxyGenerator.saveGeneratedFiles = true 表示生成的代理类将落在磁盘上生成的代理类路径就在$user.dir/com/sun/proxy/
         */
        System.out.println(System.getProperty("sun.misc.ProxyGenerator.saveGeneratedFiles"));
        System.out.println(System.getProperty("user.dir"));

        proxyInvoke();
        System.out.println("================================================");
        reflectInvoke();
    }

    private static void proxyInvoke() {
        BaseImpl target = new BaseImpl();

        /**
         * 动态代理类生成过程：1.代理类字节码生成；2.字节码通过指定类加载器加载到虚拟机中
         */
        Object proxy = Proxy.newProxyInstance(
                BaseImpl.class.getClassLoader(),                //创建了代理类之后需要使用该类加载器加载代理类
                new Class<?>[]{BaseOne.class, BaseTwo.class},   //代理目标类实现的接口类(支持代理多个接口，需代理目标类都实现)
                new WavingInvocationHandler(target));           //代理目标类如何做增强

        System.out.println("the proxy class name is: " + proxy.getClass().getName()); //代理类包名默认是com.sun.proxy，类名默认是$Proxy+自增的整数值
        System.out.println("===============================================\n");

        ((BaseOne) proxy).firstJob();
        ((BaseOne) proxy).secondJob();
        ((BaseTwo) proxy).thirdJob();

        System.out.println("===============================================\n");

        Object proxyBind = new WavingInvocationHandler(target).bind();
        ((BaseOne) proxyBind).firstJob();
        ((BaseOne) proxyBind).secondJob();
        ((BaseTwo) proxyBind).thirdJob();

        System.out.println("=================printName start=================");
        // 生成的代理类的超类是Proxy，并实现了代理目标类的所有的接口，与代理目标类无任何继承关系，所以proxy是不能强转成代理目标类的。
        //((BaseImpl)proxy).printName();
        System.out.println("=================printName end=================\n");

        System.out.println("=============object function start===============");
        //toString、hashCode、equals都得到了增强
        System.out.println(proxy.toString());
        System.out.println(proxy.hashCode());
        System.out.println(proxy.equals(target));
        System.out.println("==============object function end=================");

    }

    private static void reflectInvoke() {
        try {
            Class<?> proxyClass = Proxy.getProxyClass(BaseImpl.class.getClassLoader(), BaseOne.class, BaseTwo.class);
            final Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);
            final InvocationHandler invocationHandler = new WavingInvocationHandler(new BaseImpl());
            Object baseImplProxy = cons.newInstance(invocationHandler);
            ((BaseOne) baseImplProxy).firstJob();
            ((BaseOne) baseImplProxy).secondJob();
            ((BaseTwo) baseImplProxy).thirdJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 生成的代理类中的static块代码
 * static {
 * m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
 * m3 = Class.forName("jdk_test.jdk_base.dynamic_proxy.JdkProxyTest$BaseOne").getMethod("firstJob", new Class[0]);
 * m4 = Class.forName("jdk_test.jdk_base.dynamic_proxy.JdkProxyTest$BaseOne").getMethod("secondJob", new Class[0]);
 * m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
 * m5 = Class.forName("jdk_test.jdk_base.dynamic_proxy.JdkProxyTest$BaseTwo").getMethod("thirdJob", new Class[0]);
 * m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
 * }
 **/
