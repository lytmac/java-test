package jdk_base.dynamic_proxy;

import net.sf.cglib.proxy.Dispatcher;

public class ConcreteClassDispatcher implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("before Dispatcher...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("key-two");
        propertyBean.setValue("value-two");
        System.out.println("after Dispatcher...");
        return propertyBean;
    }
}
