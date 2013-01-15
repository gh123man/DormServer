
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class FileManage {
	static final String Vnum = "1";

	Printer print;
	Serial sr;

	public FileManage() {
	}

	public FileManage(Printer print, Serial sr) {
		this.print = print;
		this.sr = sr;
	}

	/**
	 * sets up database if it doesnt exist
	 */
	public void setUp() {
		// creates db file link
		File dir = new File("db");
		// do we have a database?
		if (!dir.exists()) {
			System.out.println("db not found, creating new one...");
			try {
				// makes the database
				String mainfold = "db";
				boolean success = (new File(mainfold)).mkdir();
				if (success) {
					print.in("Directory: " + mainfold + " created!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("db found!");
		}
		File dir1 = new File("db/random");
		// do we have a database?
		if (!dir1.exists()) {
			System.out.println("db/random not found, creating new one...");
			try {
				// makes the database
				String mainfold1 = "db/random";
				boolean success = (new File(mainfold1)).mkdir();
				if (success) {
					System.out.println("Directory: " + mainfold1 + " created!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("random found!");
		}
	}

	/**
	 * handles input and routes it correctly.
	 * 
	 * @param line
	 * @return
	 */
	public String input(String line) {
		// splits client return data into data we can use
		String[] data = line.split("\\s+");

		if (data[0].equals("unlock")) {
			// 0 = action-account 1 = name 2 = password 3 = email
			return unlock(data);
		} else if (data[0].equals("lock")) {
			return lock(data);
		} else if (data[0].equals("color")) {
			return lights(data);
		}
		return "false";
	}
	
	private String lights(String[] data) {
		//sr.write("a01000000000000");
		//print.log("unlocked");
		String[] colors = data[1].split("-");
		
		sr.write(FormatColor.in(colors[0], colors[1], colors[2]));
		//print.in("color set to: " + data[1]);
		print.in("color set to: " + data[1]);
		return "true";
	}

	private String unlock(String[] data) {
		sr.write("a01000000000000");
		print.log("unlocked");
		print.in("unlocked");
		return "true";
	}
	
	private String lock(String[] data) {
		sr.write("a00000000000000");
		print.log("locked");
		print.in("locked");
		return "true";
	}

	private ArrayList<String> dumpFile(String[] data, String f1) {
		// unam, password, filename
		ArrayList<String> hold = new ArrayList<String>();
		try {
			File file = new File("db/" + data[1] + "/" + f1);
			// open file for reading
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// init some vars
			String line;
			// loop though whole file
			while ((line = br.readLine()) != null) {
				// write each line to an arraylist
				hold.add(line);
			}
			// close stuff
			fstream.close();
			in.close();
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hold;
	}

	/**
	 * validates user transaction
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean validate(String username, String password) {
		File dir = new File("db/" + username + "/data.dbs");

		try {
			if (dir.exists()) {
				FileInputStream fstream = new FileInputStream(dir);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				if (br.readLine().equals(password)) {
					return true;
				}
				fstream.close();
				in.close();
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * makes a new user
	 * 
	 * @param data
	 * @return
	 */
	private String adduser(String[] data) {
		// init some dirs
		File dir = new File("db/" + data[1]);
		if (dir.exists()) {
			// if it doesnt exist, jump ship!
			return "false";
		} else {
			try {
				// tries to make the dir, if its false, it exists, and return
				// false
				boolean success = (new File("db/" + data[1])).mkdir();
				if (success) {
					// if the file was created, make the needed user files
					File file = new File("db/" + data[1] + "/" + "data.dbs");
					FileWriter out = new FileWriter(file, true);
					// add in the username and password
					long timestamp = System.currentTimeMillis() / 1000;
					out.write(data[2] + "\n" + data[3] + "\n" + timestamp);
					// clost it up
					out.close();

					File file1 = new File("db/" + data[1] + "/"
							+ "friendrequests.dbs");
					file1.createNewFile();
					File file2 = new File("db/" + data[1] + "/" + "friends.dbs");
					file2.createNewFile();
					File file3 = new File("db/" + data[1] + "/" + "notice.dbs");
					file3.createNewFile();
					File file4 = new File("db/" + data[1] + "/"
							+ "storyrequests.dbs");
					file4.createNewFile();
					File file5 = new File("db/" + data[1] + "/" + "shared.dbs");
					file5.createNewFile();
					print.in("User: " + data[1] + " registered");
					// it worked!
					return "true";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "false";
		}
	}

	/**
	 * delets a folder.
	 * 
	 * @param folder
	 */
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}