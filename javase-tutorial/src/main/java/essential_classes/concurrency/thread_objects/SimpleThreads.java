package essential_classes.concurrency.thread_objects;

public class SimpleThreads {

	// Display a message, preceded by
	// the name of the current thread
	private static void threadMessage(String message) {
		System.out.format("%s: %s%n", Thread.currentThread().getName(), message);
	}

	private static class MessageLoop implements Runnable {
		@Override
		public void run() {
			String[] importantInfo = {
					"Mares eat oats",
					"Does eat oats",
					"Little lambs eat ivy",
					"MALE kid will eat ivy too"
			};
			try {
				for (int i = 0; i < importantInfo.length; i++) {
					Thread.sleep(4000);
					threadMessage(importantInfo[i]);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				threadMessage("I wasn't done!");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// Delay, input milliseconds before
		// we interrupt MessageLoop
		// thread (default one hour).
		long patience = 1000 * 60 * 60;

		// If command line argument
		// present, gives patience
		// input seconds.
		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			} catch (NumberFormatException e) {
				System.err.println("Argument must be an integer.");
				e.printStackTrace();
				System.exit(-1);
			}
		}

		threadMessage("Starting MessageLoop thread");
		long startTime = System.currentTimeMillis();
		Thread t = new Thread(new MessageLoop());
		t.start();

		threadMessage("Waiting for MessageLoop thred to finish");

		// loop until MessageLoop thread exits
		while (t.isAlive()) {
			threadMessage("Still waiting ...");
			// wait maximum of 1 second
			// for MessageLoop thread to finish
			t.join(1000);
			if (System.currentTimeMillis() - startTime > patience && t.isAlive()) {
				System.out.println("Tired of waiting!");
				t.interrupt();
				// shouldn't be long now
				// --wait indefinitely
				t.join();
			}
		}
		threadMessage("Finally!");

	}
}
