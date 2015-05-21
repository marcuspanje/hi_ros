import java.io.*;

public class EditText {

	public static void main(String[] args) {
		try	{ 
			BufferedReader in = new BufferedReader(new FileReader("hex_output.txt"));
			String output = "";
			while(true) {
				int c = in.read();
				if (c == -1) break;
				if (c < 48) continue;
				char[] char_arr = {(char) c};
			
				String s = new String(char_arr);
				output = output + s;
			}
			System.out.println(output);
		}
		catch (Exception e) {
			System.out.println("Problem"); 
		}
	}

}
