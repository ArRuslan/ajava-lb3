package ua.nure.pv.task3;

import java.util.Scanner;

public class Spam {

	private Thread[] threads;
	
	public Spam(String[] messages, int[] timeouts) {
		threads = new Thread[messages.length];

		for(int i = 0; i < messages.length; i++) {
			final int finalI = i;
			threads[i] = new Thread(() -> {
				try {
					Thread.sleep(timeouts[finalI]);
				} catch (InterruptedException e) {
                    return;
                }
				System.out.println(messages[finalI]);
            });
		}
	}
	
	public void start() {
		for(Thread thread : threads) {
			if(!thread.isAlive()) {
				thread.start();
			}
		}
	}

	public void stop() {
		for(Thread thread : threads) {
			if(thread.isAlive()) {
				thread.interrupt();
				try {
					thread.join();
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
	}

	public static void main(String[] args) {
		Spam spam = new Spam(
			new String[] {"AAAAA", "777", "@@"},
			new int[] {333, 555, 1555});
		spam.start();
		
		// wait for the Enter pressing
		Scanner sc = new Scanner(System.in);
		sc.nextLine();

		// if the Enter has been pressed
		spam.stop();
	}

}