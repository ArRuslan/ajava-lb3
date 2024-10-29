package ua.nure.pv.task3;

import java.io.InputStream;

public class Part2 {

	private static final InputStream STD_INPUT = System.in;
	
	private static class MockedInputStream extends InputStream {
		private int pos = 0;
		private static final byte[] out = System.lineSeparator().getBytes();
		private final int timeout;

		public MockedInputStream(int timeout) {
			this.timeout = timeout;
		}

		public int read() {
			if(pos == 0) {
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException ignored) {
				}
			}

			if(pos >= out.length) {
				return -1;
			}

			return (int)out[pos++];
		}
	}

	public static void main(String[] args) {
        System.setIn(new MockedInputStream(2000));
		Spam.main(new String[0]);
		System.setIn(STD_INPUT);
	}

}
