package cmdata;
import java.io.*;
public class Write {
	StringBuffer xml;
	public Write(StringBuffer xml) {
		try {
			this.xml  = xml;
			File f = new File("cmdata/Database.txt");//file declaration
			FileOutputStream fout = new FileOutputStream(f);//declared file mai content jaane ke liye
			PrintWriter pw = new PrintWriter(fout,true);//declared file mai content likhne ke liye
			String line = xml;//declared file mai specified content daalne ke liye
			pw.println(line);
			pw.close();
			fout.close();

		}
		catch(IOException e) {
			System.out.println("Io caught");
		}

	}
}
