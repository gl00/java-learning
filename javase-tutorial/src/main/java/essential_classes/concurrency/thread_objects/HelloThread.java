package essential_classes.concurrency.thread_objects;

public class HelloThread extends Thread {
	@Override
	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String[] args) {
		(new HelloThread()).start();
	}
}
