package ua.nure.pv.task3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Part5 {
	
	private int n;
	private int k;
	private RandomAccessFile raf;
	private Thread[] threads;

	Part5(int n, int k) {
		this.n = n;
		this.k = k;
	}

	public void run() throws FileNotFoundException {
		File file = new File("part5.txt");
		if(file.exists()) {
			file.delete();
		}
		raf = new RandomAccessFile("part5.txt", "rw");

		threads = new Thread[n];
		for(int i = 0; i < n; i++) {
			final int finalI = i;
			threads[i] = new Thread(() -> {
				for(int j = 0; j <= k; j++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }

					synchronized (Part5.this) {
						try {
							raf.seek(finalI * (k + 1L) + j);
							raf.write(j == k ? '\n' : '0' + finalI);
						} catch (IOException e) {
							return;
						}
					}
                }
			});
			threads[i].start();
		}

		for(int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Part5 p = new Part5(10, 20);
		p.run();

	}

}