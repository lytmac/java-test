package jdk_base.class_loader;

/**
 * 该测试用例用来说明类加载器的一些行为:
 * 1. AppClassLoader、ExtClassLoader都是定义在sun.misc.Launcher类中的内部类，都是间接继承自ClassLoader内，虽然重写了loadClass()，但是依然遵循了父类加载委托模型。
 * 2. ExtClassLoader与AppClassLoader并没有继承关系，只是在Launcher启动时为AppClassLoader.parent字段赋值为ExtClassLoader。两者是组合关系，而非继承。
 * 3. ExtClassLoader与AppClassLoader最终都使用了ClassLoader.loadClass()委派给parent去加载。如果parent为NULL，调用native方法启用BootstrapClassLoader去加载。
 *
 * 尤其要注意：AppClassLoader、ExtClassLoader、BootstrapClassLoader并无继承关系。只是通过组合实现了一种层级关系。
 *
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader base = ClassLoaderTest.class.getClassLoader();
        //sun.misc.Launcher$AppClassLoader@2a139a55
        System.out.println(base);

        ClassLoader parent = base.getParent();
        //sun.misc.Launcher$ExtClassLoader@7852e922
        System.out.println(parent);

        //null
        System.out.println(parent.getParent());
    }
}
