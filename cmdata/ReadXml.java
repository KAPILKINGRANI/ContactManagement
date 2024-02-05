package cmdata;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
public class ReadXml {
	DefaultTableModel myModel;
	public ReadXml(DefaultTableModel myModel) {
		try {	
				this.myModel = myModel;
				FileInputStream fis = new FileInputStream("cmdata/Database.txt");
				BufferedReader  br = new BufferedReader(new InputStreamReader(fis));
				String line;
				StringBuffer xml = new StringBuffer("");
				while((line=br.readLine())!= null) {
				xml.append(line);
				xml.append("\n");
				}
				System.out.println(xml);
				Vector<Vector> xmlPass = new Vector<>();
				
				String groupRegexName ="(<Name>)(.*?)(</Name>)\n(<PhoneNo>)(.*?)(</PhoneNo>)\n(<Address>)(.*?)(</Address>)\n(<EmailId>)(.*?)(</EmailId>)\n(<DOB>)(.*?)(</DOB>)\n(<Gender>)(.*?)(</Gender>)\n";				  
				Pattern groupPatternReluctant = Pattern.compile(groupRegexName);
				Matcher groupMatcherReluctant = groupPatternReluctant.matcher(xml);
				// System.out.println(groupMatcherReluctant.matches());//exactly wahi string honi chaiye hai

				while(groupMatcherReluctant.find()) {
					//har baar vector ban na chaiye har ek contact ke liye taaki fir usko Vector of Vector mai daal saake
					//Vector of vector mai har index pe data vector hai which will be used for passing the data directly to table
					Vector<String> xmlDataString = new Vector<>();
					xmlDataString.add(groupMatcherReluctant.group(2));
					xmlDataString.add(groupMatcherReluctant.group(5));
					xmlDataString.add(groupMatcherReluctant.group(8));
					xmlDataString.add(groupMatcherReluctant.group(11));
					xmlDataString.add(groupMatcherReluctant.group(14));
					xmlDataString.add(groupMatcherReluctant.group(17));
					//System.out.println(xmlDataString);
					xmlPass.add(xmlDataString);
				}
				Enumeration vEnum = xmlPass.elements();
				//ismai sab ki data hai hai
				//toh vector variable create karke usmai ek ek karke store karvaenge
				//fir yeh vector variable direct table mai feke
				while(vEnum.hasMoreElements()) {
					Vector<Vector> vR= new Vector<>();
					vR = (Vector)vEnum.nextElement();
					myModel.insertRow(myModel.getRowCount(),vR);
				}
				br.close();
				fis.close();

		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found Exception");
		}
		catch(IOException e) {
			System.out.println("IOException Caught!!!!");
		}	
	}
}
