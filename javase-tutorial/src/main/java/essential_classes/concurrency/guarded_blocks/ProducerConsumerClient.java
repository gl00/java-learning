package essential_classes.concurrency.guarded_blocks;

public class ProducerConsumerClient {
	public static void main(String[] args) {
		Drop drop = new Drop();
		new Thread(new Producer(drop)).start();
		new Thread(new Consumer(drop)).start();
	}
}
