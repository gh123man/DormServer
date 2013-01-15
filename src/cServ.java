
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class cServ {

	int threads = 0;

	public static void main(String[] args) throws IOException {

		System.out.println("server starting");

		cServ serv = new cServ();

		System.out.println("launching Printer");

		// false for debug off by default
		Printer print = new Printer(true);

		Serial sr = new Serial();

		System.out.println("launching commandline");

		new CMD(serv, print, sr).start();

		System.out.println("Setup finished, Server ready");

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(4466);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4466");

		}
		boolean listneing = true;

		while (listneing) {
			new CThread(serverSocket.accept(), print, sr).start();
		}

		serverSocket.close();

	}

	public void stop() {
		System.exit(1);
	}

}