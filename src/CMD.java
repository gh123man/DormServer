

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class CMD extends Thread {

	String in, out = "";
	Scanner scan;
	cServ serv;
	Printer print;
	Serial sr;

	public CMD(cServ serv, Printer print, Serial sr) {
		scan = new Scanner(System.in);
		this.serv = serv;
		this.print = print;
		this.sr = sr;
	}

	public void run() {
		handleCMD command = new handleCMD();
		while ((in = scan.nextLine()) != null) {
			command.input(in, serv, print, sr);
		}
	}

}
