package jdk_base.dynamic_proxy;

import net.sf.cglib.proxy.Enhancer;

public class CglibLazyBean {

    private String name;
    private int age;
    private PropertyBean lazyLoadPropertyBean;
    private PropertyBean dispatcherPropertyBean;

    public CglibLazyBean(String name, int age) { //构造函数只初始化'name'和'age'这两个字段
        System.out.println("lazy bean init");
        this.name = name;
        this.age = age;
        this.lazyLoadPropertyBean = createLazyLoadPropertyBean();
        this.dispatcherPropertyBean = createDispatcherPropertyBean();
    }


    /**
     * 只第一次懒加载
     *
     * @return
     */
    private PropertyBean createLazyLoadPropertyBean() {
        /**
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法，
         * 就会自动触发代理类回调）。
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());
        return pb;
    }

    /**
     * 每次都懒加载
     *
     * @return
     */
    private PropertyBean createDispatcherPropertyBean() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassDispatcher());
        return pb;
    }

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

    public PropertyBean getLazyLoadPropertyBean() {
        return lazyLoadPropertyBean;
    }

    public void setLazyLoadPropertyBean(PropertyBean lazyLoadPropertyBean) {
        this.lazyLoadPropertyBean = lazyLoadPropertyBean;
    }

    public PropertyBean getDispatcherPropertyBean() {
        return dispatcherPropertyBean;
    }

    public void setDispatcherPropertyBean(PropertyBean dispatcherPropertyBean) {
        this.dispatcherPropertyBean = dispatcherPropertyBean;
    }

    @Override
    public String toString() {
        return "LazyBean [name=" + name + ", age=" + age + ", lazyLoadPropertyBean="
                + lazyLoadPropertyBean + "]";
    }
}
