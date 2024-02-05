package cmdata;

import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;

public class Data implements ActionListener {
	JTextField tname, tphoneNo, taddress, temailId, tdob, tgender;
	JLabel j1, j2, j3, j4, j5, j6;
	String name, phoneNo, address, emailId, dob, gender;
	DefaultTableModel myModel;// iske wajah se table mai manipulation ho skaa
	StringBuffer xml = new StringBuffer("<contactlist>");
	JTable table;// selection of rows iske pass hai
	String operation;
	int selectedIndex;
	JDialog jda;
	JFrame jf;
	JButton submit;
	// validation ke liye status variables bana rahe
	boolean genderStatus, nameStatus, emailIdStatus, mobileNumberStatus;

	public Data(JFrame jf, JDialog jda, DefaultTableModel myModel, String operation, JTable table) {
		this.myModel = myModel;
		this.table = table;
		this.operation = operation;
		this.jda = jda;
		this.jf = jf;
		j1 = new JLabel("Name:-");
		j2 = new JLabel("PhoneNo:-");
		j3 = new JLabel("Address:-");
		j4 = new JLabel("EmailId:-");
		j5 = new JLabel("DOB(DD/MM/YYYY):-");
		j6 = new JLabel("Gender(M/F)");

		tname = new JTextField(15);
		tphoneNo = new JTextField(15);
		taddress = new JTextField(15);
		temailId = new JTextField(15);
		tdob = new JTextField(15);
		tgender = new JTextField(15);

		selectedIndex = table.getSelectedRow();
		// ye null wala exception nahi aane dega
		if (operation.equals("update") && selectedIndex != -1) {
			Vector v1 = (Vector) myModel.getDataVector().elementAt(selectedIndex);// selected row
			tname.setText((String) v1.elementAt(0));
			tphoneNo.setText((String) v1.elementAt(1));
			taddress.setText((String) v1.elementAt(2));
			temailId.setText((String) v1.elementAt(3));
			tdob.setText((String) v1.elementAt(4));
			tgender.setText((String) v1.elementAt(5));
		}

		jda.add(j1);
		jda.add(tname);

		jda.add(j2);
		jda.add(tphoneNo);

		jda.add(j3);
		jda.add(taddress);

		jda.add(j4);
		jda.add(temailId);

		jda.add(j5);
		jda.add(tdob);

		jda.add(j6);
		jda.add(tgender);

		submit = new JButton("SUBMIT");
		submit.addActionListener(this);
		jda.add(submit);

	}

	public Data(JTable table, DefaultTableModel myModel) {
		this.table = table;
		this.myModel = myModel;
		int selectedIndex = table.getSelectedRow();
		if (selectedIndex != -1) {
			myModel.removeRow(selectedIndex);
			tableData();
		}
	}

	public Data(JFrame j, String operation) {
		if (operation.equals("update")) {
			JOptionPane.showMessageDialog(jf, "Please Select From Table Before Clicking Update", "Error!",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (operation.equals("delete")) {
			JOptionPane.showMessageDialog(jf, "Please Select From Table Before Clicking Delete", "Error!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void tableData() {
		Vector<String> vd = new Vector<>();
		Vector<String> vH = new Vector<>();
		vH.add("Name");
		vH.add("PhoneNo");
		vH.add("Address");
		vH.add("EmailId");
		vH.add("DOB");
		vH.add("Gender");

		String tempXML = "";

		for (int j = 0; j < myModel.getRowCount(); j++) {
			vd = (Vector) myModel.getDataVector().elementAt(j);// this is for row
			xml.append("\n<contact>");
			for (int k = 0; k < vH.size(); k++) {
				// tempXML = vH.elementAt(k)+":"+vd.elementAt(k);
				xmltags(vH.elementAt(k), vd.elementAt(k));
			}
			xml.append("\n</contact>");
			System.out.println();
		}
		xml.append("\n</contactlist>");
		writeXml(xml);
	}

	public void validate(String name, String phoneNo, String address, String emailId, String dob, String gender) {
		// ================Name Checking======================================
		String nameR = name;
		// FName MiddleName LastName
		String nameRegex = "^([a-zA-Z])+(\\s){0,1}([a-zA-Z])*(\\s){0,1}([a-zA-Z])*";

		Pattern namePattern = Pattern.compile(nameRegex);

		Matcher nameMatcher = namePattern.matcher(nameR);
		if (!nameMatcher.matches()) {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			nameStatus = false;
			JOptionPane.showMessageDialog(jf, "Please Enter A Valid Name", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			nameStatus = true;
		}

		// ==========================PhoneNo
		// checking=============================================================
		String mobileNumber = phoneNo;
		String mobileNumberRegex = "^[6-9]\\d{9}$";

		Pattern mobileNumberPattern = Pattern.compile(mobileNumberRegex);
		Matcher mobileNumberMatcher = mobileNumberPattern.matcher(mobileNumber);
		if (!mobileNumberMatcher.matches()) {
			// tphoneNo.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			mobileNumberStatus = false;
			JOptionPane.showMessageDialog(jf, "Please Enter A Valid Mobile Number", "Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			// after invalid error the user now enters valid then the status must be updated
			// tphoneNo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			mobileNumberStatus = true;
		}

		// ==============EmailId
		// checking=============================================================
		String email = emailId;
		String emailRegex = "^[A-Za-z_]([\\.A-Za-z0-9\\+-_]+)*@([A-Za-z_]([A-Za-z0-9-])*)(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z0-9]{2,})$";
		Pattern emailPattern = Pattern.compile(emailRegex);

		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.matches()) {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			emailIdStatus = false;
			JOptionPane.showMessageDialog(jf, "Please Enter A Valid Email Address", "Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			emailIdStatus = true;
		}
		// ========================Gender
		// Checking==========================================================
		String Gender = gender;
		String genderRegex = "(?i)[M/F]";
		Pattern genderPattern = Pattern.compile(genderRegex);

		Matcher genderMatcher = genderPattern.matcher(Gender);
		if (!genderMatcher.matches()) {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			genderStatus = false;
			JOptionPane.showMessageDialog(jf, "Please Enter A Valid Gender(M/F)", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			// temailId.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			genderStatus = true;
		}
	}

	public void actionPerformed(ActionEvent ae) {
		name = tname.getText();
		phoneNo = tphoneNo.getText();
		address = taddress.getText();
		emailId = temailId.getText();
		dob = tdob.getText();
		gender = tgender.getText();
		if (name.equals("") || phoneNo.equals("") || address.equals("") || emailId.equals("") || dob.equals("")
				|| gender.equals("")) {
			JOptionPane.showMessageDialog(jf, "All Input Fields Are Mandatory", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			validate(name, phoneNo, address, emailId, dob, gender);
		}
		// System.out.println(emailIdStatus);

		if (nameStatus == true && mobileNumberStatus == true && emailIdStatus == true && genderStatus == true) {
			// important
			if (operation.equals("update") && selectedIndex != -1) {
				myModel.removeRow(selectedIndex);
				myModel.insertRow(selectedIndex, new Object[] { name, phoneNo, address, emailId, dob, gender });
				jda.dispose();
			} else if (operation.equals("add")) {
				myModel.insertRow(myModel.getRowCount(), new Object[] { name, phoneNo, address, emailId, dob, gender });
				jda.dispose();
			}
			tableData();
			// Vector<String> vd = new Vector<>();
			// Vector<String> vH = new Vector<>();
			// vH.add("Name");
			// vH.add("PhoneNo");
			// vH.add("Address");
			// vH.add("EmailId");
			// vH.add("DOB");
			// vH.add("Gender");

			// String tempXML ="";

			// for(int j=0;j<myModel.getRowCount();j++) {
			// vd = (Vector)myModel.getDataVector().elementAt(j);//this is for row
			// xml.append("\n<contact>");
			// for(int k=0;k<vH.size();k++) {
			// // tempXML = vH.elementAt(k)+":"+vd.elementAt(k);
			// xmltags(vH.elementAt(k),vd.elementAt(k));
			// System.out.println(tempXML);
			// }
			// xml.append("\n</contact>");
			// System.out.println();
			// }
			// xml.append("\n</contactlist>");
			// writeXml(xml);
		}

	}

	void xmltags(String key, String value) {
		xml.append("\n");
		xml.append("<" + key + ">" + value + "</" + key + ">");
	}

	void writeXml(StringBuffer xml) {
		try {
			File f = new File("cmdata/Database.txt");// file declaration
			FileOutputStream fout = new FileOutputStream(f);// declared file mai content jaane ke liye
			PrintWriter pw = new PrintWriter(fout, true);// declared file mai content likhne ke liye
			StringBuffer line = new StringBuffer();
			line = xml;// declared file mai specified content daalne ke liye
			pw.println(line);
			pw.close();
			fout.close();
		} catch (IOException e) {
			System.out.println("Io caught");
		}
	}
}
