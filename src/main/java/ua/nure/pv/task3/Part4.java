package ua.nure.pv.task3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Part4 {

    private static int THREADS_COUNT = 4;

    private int n;

    private int m;

    private int[][] matrix;

    private Thread[] threads;

    public Part4() throws IOException {
        File part4 = new File("/mnt/B0A0B30BA0B2D6D6/programming/java/nure/java-advanced-task3-stub/part4.txt");

        BufferedReader reader = new BufferedReader(new FileReader(part4));
        String line;
        n = m = 0;
        while ((line = reader.readLine()) != null) {
            n++;
            if (m == 0) {
                m = line.split(" ").length;
            }
        }
        reader.close();

        matrix = new int[n][];

        reader = new BufferedReader(new FileReader(part4));
        for (int i = 0; i < n; i++) {
            matrix[i] = new int[m];
            String[] numbers = reader.readLine().split(" ");
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(numbers[j]);
            }
        }
    }

	void generateLargeMatrix(int n, int m) {
		this.n = n;
		this.m = m;
		matrix = new int[n][];
		Random rand = new Random();

		for (int i = 0; i < n; i++) {
			matrix[i] = new int[m];
			for (int j = 0; j < m; j++) {
				matrix[i][j] = rand.nextInt(100, 1000);
			}
		}
	}

    public int searchMax() {
        int max = matrix[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					break;
				}
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }

        return max;
    }

    public int searchMaxThreaded() {
		threads = new Thread[THREADS_COUNT];
		final int[] threads_max = new int[THREADS_COUNT];

		final int elements_per_thread = n * m / THREADS_COUNT + 1;
		for(int i = 0; i < THREADS_COUNT; i++) {
			threads_max[i] = matrix[0][0];
			final int finalI = i;
			threads[i] = new Thread(() -> {
				int start_pos = elements_per_thread * finalI;
				int this_n = start_pos / m;
				int this_m = start_pos % m;

				while(this_n < n && ((this_n * m + this_m) - start_pos) < elements_per_thread) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						return;
					}
					//System.out.printf("Processing element %d:%d%n", this_n, this_m);
					if(matrix[this_n][this_m] > threads_max[finalI]) {
						threads_max[finalI] = matrix[this_n][this_m];
					}
					this_m++;
					if(this_m == m) {
						this_m = 0;
						this_n++;
					}
				}
			});
			threads[i].start();
		}

		/*final int rows_per_thread = n / THREADS_COUNT + 1;
		for(int i = 0; i < THREADS_COUNT; i++) {
			threads_max[i] = matrix[0][0];
			final int finalI = i;
			threads[i] = new Thread(() -> {
				for(int ti = finalI; ti < rows_per_thread; ti++) {
					for (int j = 0; j < m; j++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            return;
                        }
                        if (matrix[ti][j] > threads_max[finalI]) {
							threads_max[finalI] = matrix[ti][j];
						}
					}
				}
			});
			threads[i].start();
		}*/

		int max = threads_max[0];
        for (int i = 0; i < THREADS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {

            }
            if(threads_max[i] > max) {
				max = threads_max[i];
			}
        }

        return max;
    }

    public static void main(String[] args) throws IOException {
        Part4 p = new Part4();
		long current = System.currentTimeMillis();
		System.out.printf("Search single-thread: %d, %dms%n", p.searchMax(), System.currentTimeMillis() - current);
		current = System.currentTimeMillis();
		System.out.printf("Search multi-thread: %d, %dms%n", p.searchMaxThreaded(), System.currentTimeMillis() - current);

		p.generateLargeMatrix(128, 128);

		current = System.currentTimeMillis();
		System.out.printf("Search single-thread: %d, %dms%n", p.searchMax(), System.currentTimeMillis() - current);
		current = System.currentTimeMillis();
		System.out.printf("Search multi-thread: %d, %dms%n", p.searchMaxThreaded(), System.currentTimeMillis() - current);
    }
}