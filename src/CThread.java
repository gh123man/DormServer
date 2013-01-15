
import java.net.*;
import java.io.*;

public class CThread extends Thread {

	private Socket socket = null;
	Printer print;
	Serial sr;

	public CThread(Socket socket, Printer print, Serial sr) {
		super("CThread");
		this.socket = socket;
		this.print = print;
		this.sr = sr;
	}

	public void run() {

		try {
			FileManage datab = new FileManage(print, sr);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				out.println(datab.input(inputLine));
			}

			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			System.err.println("Send/receve failed");
		}
	}
}
