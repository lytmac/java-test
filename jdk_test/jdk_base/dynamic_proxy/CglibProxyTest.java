package jdk_base.dynamic_proxy;


import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * CGLIB动态代理测试代码
 * CGLIB有一个重要的限制：目标类必须提供一个默认的构造函数
 * 动态生成一个子类以覆盖非final的方法，同时绑定钩子回调自定义的拦截器
 */
public class CglibProxyTest {

//    public class BaseClass {
//        public BaseClass() { //代理目标类必须提供一个默认的构造函数
//        }
//
//        public void firstJob() {
//            System.out.println("base one first job is running!");
//        }
//
//        public void secondJob() {
//            System.out.println("base one second job is running!");
//        }
//
//        public void thirdJob() {
//            System.out.println("base two third job is running!");
//        }
//    }

    /**
     * 是一种比较特殊的Callback
     */
    public static class WavingMethodInterceptor implements MethodInterceptor {
        /**
         * 由CGLIB在创建的代理类中调用该增强后的方法去代理原目标类的方法
         *
         * @param object 代理目标类
         * @param method 代理方法描述对象
         * @param params 方法参数
         * @param proxy  代理类
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object object, Method method, Object[] params, MethodProxy proxy) throws Throwable {
            System.out.println("=======before invoke job=======");
            Object result = proxy.invokeSuper(object, params);  //执行目标对象中定于的原始方法
            System.out.println("=======after invoke job========\n");
            return result;
        }
    }

    /**
     * cglib支持方法级的增强，对不同的方法可以有不同的回调方式。CallBackFilter就是一个分发器，为不同方法分配回调方法。
     */
    public static class WavingCallbackFilter implements CallbackFilter {

        /**
         * 返回的值为数字，代表了Callback数组中的索引位置，为方法设定Callback方法
         * enhancer在创建代理对象时会调用该方法，为代理目标类中的每个方法设定Callback方法
         */
        @Override
        public int accept(Method method) { //0,1对应Callback数组的下标
            if (method.getName().equals("firstJob")) {
                System.out.println("filter firstJob == 0");
                return 0;
            }
            if (method.getName().equals("secondJob")) {
                System.out.println("filter secondJob == 0");
                return 0;
            }
            if (method.getName().equals("thirdJob")) {
                System.out.println("filter thirdJob == 1");
                return 1;
            }

            /**
             * 要代理的方法不包括final方法。accept是不会对final方法做任何操作的。
             */
            if (method.getName().equals("forthJob")) {
                System.out.println("filter forthJob == 1");
                return 1;
            }

            /**
             * equals、toString、hashCode、clone.这四个方法都会经过accept的处理
             */
            System.out.println(method.getName() + ": 未明确声明的方法！");
            return 0;
        }

    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/cdliuyang25/Code/Git/java-test");
        cglibInvoke();
        cglibLazyLoader();
    }


    private static void cglibInvoke() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BaseClass.class);
        enhancer.setCallbackFilter(new WavingCallbackFilter());

        Callback noOpCallback = NoOp.INSTANCE;
        Callback logCallback = new WavingMethodInterceptor();
        enhancer.setCallbacks(new Callback[]{noOpCallback, logCallback});

        BaseClass proxy = (BaseClass) enhancer.create();
        //代理类类名: jdk_test.jdk_base.dynamic_proxy.BaseClass$$EnhancerByCGLIB$$47c48571
        System.out.println(proxy.getClass().getName());
        proxy.firstJob();
        proxy.secondJob();
        proxy.thirdJob();
        proxy.forthJob(); //final方法默认跳过增强
    }

    private static void cglibLazyLoader() {
        CglibLazyBean bean = new CglibLazyBean("lytmac", 29);
        System.out.println(bean.getName());
        System.out.println(bean.getAge());

        System.out.println("===============================");
        //构造函数并不会执行该属性的初始化，而是延迟到调用get方法才执行
        PropertyBean lazyLoadPropertyBean = bean.getLazyLoadPropertyBean();
        System.out.println(lazyLoadPropertyBean.toString());


        PropertyBean dispatcherPropertyBean1 = bean.getDispatcherPropertyBean();
        System.out.println(dispatcherPropertyBean1.toString());

        System.out.println("===============================");
        //Dispatcher会每次都调用
        PropertyBean dispatcherPropertyBean2 = bean.getDispatcherPropertyBean();
        System.out.println(dispatcherPropertyBean2.toString());



    }

}

