package jdk_concurrency;

public class ReorderTest {
	static int x = 0, y = 0;
	static int a = 0, b = 0;

	public static void main(String[] args) throws InterruptedException {
		int index = 0;
	    while(true) {
	    	doTest();
	    	index++;
	    	if(x == 0 && y== 0) {
	    		System.out.println("the index is: " + index);
	    		break;
	    	}
	    }
	}
	
	public static void doTest() throws InterruptedException {
		Thread one = new Thread(new Runnable() {
	        public void run() {
	            a = 1;
	            x = b;
	        }
	    });

	    Thread two = new Thread(new Runnable() {
	        public void run() {
	            b = 1;
	            y = a;
	        }
	    });
	    
	    one.start();
	    two.start();  
	    one.join();      //主线程等待线程one停止后继续运行
	    two.join();      //主线程等待线程two停止后继续运行
	    System.out.println("x: "+  x + " ------y:" + y);
	}
}
