package ua.nure.pv.task3;

public class Part1 {
/*
Створіть дочірній потік, який протягом приблизно 1 секунди друкуватиме своє ім'я кожні триста мілісекунд. Реалізуйте це трьома способами:
  * за допомогою наслідування класу Thread;
  * за допомогою реалізації інтерфейсу Runnable;
  * використовуючи посилання на статичний метод, який реалізує вказану функціональність.
Продемонструйте роботу всіх трьох варіантів. Виконання має бути послідовним: спочатку відпрацьовує одна реалізація, потім друга, а потім третя.
*/

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread1();
		t1.start();
		System.out.println("Waiting for thread 1...");
		t1.join();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				long start_time = System.currentTimeMillis();
				while(System.currentTimeMillis() - start_time < 1000) {
					try {
						System.out.println(Thread.currentThread().getName());
						Thread.sleep(300);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		});
		t2.start();
		System.out.println("Waiting for thread 2...");
		t2.join();

		Thread t3 = new Thread(Part1::thread_3);
		t3.start();
		System.out.println("Waiting for thread 3...");
		t3.join();
	}

	public static void thread_3() {
		long start_time = System.currentTimeMillis();
		while(System.currentTimeMillis() - start_time < 1000) {
			try {
				System.out.println(Thread.currentThread().getName());
				Thread.sleep(300);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	private static class Thread1 extends Thread {
		@Override
		public void run() {
			long start_time = System.currentTimeMillis();
			while(System.currentTimeMillis() - start_time < 1000) {
				try {
					System.out.println(Thread.currentThread().getName());
					Thread.sleep(300);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
}
