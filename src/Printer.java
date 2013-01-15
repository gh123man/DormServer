

import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Printer {

	boolean state;
	String temp;

	public Printer(boolean state) {
		this.state = state;
	}

	public void in(String data) {
		if (state) {
			System.out.println(data);
		}
		log(data);
	}
	
	public void log(String data) {
		try {
		FileWriter out = new FileWriter("log.txt", true);
		out.write(data + "\n");
		out.close();
		} catch (Exception e) {
			System.err.println("printer log failed");
		}
	}

	public void set(boolean state) {
		this.state = state;
	}

	public boolean get() {
		return state;
	}
}
