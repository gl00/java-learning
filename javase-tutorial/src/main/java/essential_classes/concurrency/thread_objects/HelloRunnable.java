package essential_classes.concurrency.thread_objects;

public class HelloRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String[] args) {
		(new Thread(new HelloRunnable())).start();
	}

}