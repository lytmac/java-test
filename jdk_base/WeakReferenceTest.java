package jdk_base;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

        static class Car {
                private double price;
                private String colour;

                public Car(double price, String colour) {
                        this.price = price;
                        this.colour = colour;
                }

                public double getPrice() {
                        return price;

                }

                public void setPrice(double price) {
                        this.price = price;
                }

                public String getColour() {
                        return colour;
                }

                public void setColour(String colour) {
                        this.colour = colour;
                }

                public String toString() {
                        return colour + "car costs $" + price;
                }
        }

        public static void collectObj() {
                /**
                 * carStrong 是一个strong reference, carWeak是一个weak reference
                 * java的编译器在发现进入while循环之后, car已经没有被使用了, 所以进行了优化(将其置空?)
                 */
                Car carStrong = new Car(22000, "silver");
                WeakReference<Car> carWeak = new WeakReference<Car>(carStrong);

                int i = 0;
                while (true) {
                        if (carWeak.get() != null) {
                                i++;
                                System.out.println("Object is alive for " + i + " loops - " + carWeak);
                        } else {
                                // weak reference指向的对象的被回收了
                                System.out.println("Object has been collected.");
                                break;
                        }
                }
        }

        public static void unCollectObj() {

                Car carStrong = new Car(22000, "silver");
                WeakReference<Car> carWeak = new WeakReference<Car>(carStrong);

                int i = 0;
                while (true) {
                        /**
                         * 这里会引用strong reference，将导致对象不会被回收
                         */
                        System.out.println("here is the strong reference 'car' " + carStrong);
                        if (carWeak.get() != null) {
                                i++;
                                System.out.println("Object is alive for " + i + " loops - " + carWeak);
                        } else {
                                System.out.println("Object has been collected.");
                                break;
                        }
                }
        }
        
        public static void main(String[] args) {
                //collectObj();
                unCollectObj();
        }
}
