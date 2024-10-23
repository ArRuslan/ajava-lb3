package ua.nure.pv.task3;

import java.io.IOException;
import java.io.InputStream;

public class Part2 {

	private static final InputStream STD_INPUT = System.in;
	
	private static class MockedInputStream extends InputStream {
		private boolean returned_newline = false;

		public int read() throws IOException {
			if(returned_newline) {
				return -1;
			}

			try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

			returned_newline = true;
            return '\n';
		}
	}

	public static void main(String[] args) throws InterruptedException {
        System.setIn(new MockedInputStream());
		Spam.main(new String[0]);
		System.setIn(STD_INPUT);
	}

}
