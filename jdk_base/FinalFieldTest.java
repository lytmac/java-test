package jdk_base;

public class FinalFieldTest {
	
	private final int x;
	private int y;

	private static FinalFieldTest fft;
	
	public FinalFieldTest() {
		x = 3;
		y= 4;
	}
	
	static void write() {
		fft = new FinalFieldTest();
	}
	
	static void read() {
		if(fft != null) {
			System.out.println("x: " + fft.x + "---y: " + fft.y);
		} else {
			System.out.println("fft is still not initial");			
		}
	}
	
	
	public static void main(String[] args) {
		Thread reader = new Thread(new Runnable() {
			@Override
			public void run() {
				FinalFieldTest.read();
			}
		});
		
		Thread writer = new Thread(new Runnable() {
	
			@Override
			public void run() {
				FinalFieldTest.write();
			}
		});
		
		writer.start();
		reader.start();
	}
	
}
