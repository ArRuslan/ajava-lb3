package ua.nure.pv.task3;

public class Part3 {

	private final int threads_num;
	private final int iters;
	private final int sleep_ms;

	private int c1;
	private int c2;
	
	public Part3(int n, int k, int t) {
		threads_num = n;
		iters = k;
		sleep_ms = t;
	}
	
	public void reset() {
		c1 = c2 = 0;
	}	

	public void test() {
		Thread[] threads = new Thread[threads_num];

		for(int i = 0; i < threads_num; i++) {
			threads[i] = new Thread(() -> {
				for(int j = 0; j < iters; j++) {
					System.out.printf("%d %d%n", c1, c2);
					c1++;
                    try {
                        Thread.sleep(sleep_ms);
                    } catch (InterruptedException e) {
                        return;
                    }
					c2++;
                }
			});
			threads[i].start();
		}

		for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                continue;
            }
        }

		System.out.printf("Non-sync result: %d %d%n", c1, c2);
	}

	public void testSync() {
		Thread[] threads = new Thread[threads_num];

		for(int i = 0; i < threads_num; i++) {
			threads[i] = new Thread(() -> {
				for(int j = 0; j < iters; j++) {
					synchronized (Part3.this) {
						System.out.printf("%d %d%n", c1, c2);
						c1++;
						try {
							Thread.sleep(sleep_ms);
						} catch (InterruptedException e) {
							return;
						}
						c2++;
					}
				}
			});
			threads[i].start();
		}

		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				continue;
			}
		}

		System.out.printf("Sync result: %d %d%n", c1, c2);
	}	
	
	public static void main(String[] args) {
		Part3 p = new Part3(3, 100, 5);
		p.test();
		p.reset();
		p.testSync();
	}	

}