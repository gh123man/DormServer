public class handleCMD {

	public String input(String data, cServ serv, Printer print, Serial sr) {

		String[] input = data.split(" ");

		if (data.equals("stop")) {
			System.out.println("stopping server...");
			serv.stop();
		} else if (data.equals("debug")) {
			boolean tmp;
			print.set(tmp = !print.get());
			if (tmp) {
				System.out.println("debug on");
			} else {
				System.out.println("debug off");
			}
		} else if (input[0].equals("write")) {
			if (input.length < 2) {
				System.out.println("not enough args");
			} else {
				sr.write(input[1]);
				System.out.println(input[1]);
			}

		} else if (input[0].equals("loop")) {
			for (int i = 255; i > 100; i--) {
				try {
				Thread.sleep(100);
				} catch (Exception e) {
				}
				System.out.println("a10000" + Integer.toString(i) + Integer.toString(i) + Integer.toString(i));
				sr.write("a10000" + Integer.toString(i) + Integer.toString(i) + Integer.toString(i));
			
			}

		}
		return "false";
	}

}
