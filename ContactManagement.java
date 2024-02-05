import java.awt.*;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cmdata.*;
//BY DEFAULT JFRAME LAYOUT IS BORDERLAYOUT SO SET INDIVIDUAL COMPONENTS FOR A PROPER OUTPUT
public class ContactManagement extends JFrame implements ActionListener {
	JButton jb1,jb2,jb3,jb4;
	DefaultTableModel myModel;
	JTable table;
	JTextField searchField;
	// JTextField jt1,jt2,jt3,jt4,jt5,jt6;
	// JLabel j1,j2,j3,j4,j5,j6;
	// String Name,PhoneNo,Address,EmailId,Dob,Gender;

	ContactManagement() {

		//Frame Requirements
		super("Contact Management System");
		setSize(500,500);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	    myModel = new DefaultTableModel();
		table = new JTable(myModel);
		
		myModel.addColumn("NAME");
		myModel.addColumn("PhoneNo");
		myModel.addColumn("Address");
		myModel.addColumn("EmailId");
		myModel.addColumn("DOB");
		myModel.addColumn("GENDER");

		// JTable table = new JTable(data,colHeads);
		table.setFont(new Font("Serif",Font.PLAIN,20));
		table.setRowHeight(40);
		table.setGridColor(Color.black);
		table.setBackground(Color.orange);
		table.setForeground(Color.black);	

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane jsp = new JScrollPane(table,v,h);

		JLabel jb = new JLabel("Contact Management");
		jb.setFont(new Font("Serif",Font.PLAIN,30));
		// jb.setHorizontalAlignment(JLabel.CENTER);//aligns according to the x axis

		//add(jb,BorderLayout.NORTH);//INDIVIDUALLY SET
		add(jsp,BorderLayout.CENTER);//INDIVIDUALLY SET 

		jb1 = new JButton("ADD");
		jb2 = new JButton("UPDATE");
		jb3 = new JButton("DELETE");
		jb4 = new JButton("SEARCH");

		searchField = new JTextField("",15);



		jb1.setFont(new Font("Serif",Font.PLAIN,20));
		jb2.setFont(new Font("Serif",Font.PLAIN,20));
		jb3.setFont(new Font("Serif",Font.PLAIN,20));
		jb4.setFont(new Font("Serif",Font.PLAIN,20));
		searchField.setFont(new Font("Serif",Font.PLAIN,20));




		jb1.setBackground(Color.PINK);
		jb2.setBackground(Color.YELLOW);
		jb3.setBackground(Color.RED);
		jb4.setBackground(Color.ORANGE);



		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);

		JPanel jp = new JPanel();

		jp.add(jb);
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.add(searchField);
		jp.add(jb4);

		add(jp,BorderLayout.NORTH);//INDIVIDUALLY SET
		new ReadXml(myModel);
		
	}
	public void actionPerformed(ActionEvent ae) {
		String str = ae.getActionCommand();
		if(str.equals("ADD")) {
			JDialog jda = new JDialog(this,"Add");
			jda.setVisible(true);
			jda.setSize(450,450);
			jda.setLayout(new GridLayout(7,2));
			new Data(this,jda,myModel,"add",table);
		}
		else if(str.equals("UPDATE")) {
			int selectedIndex = table.getSelectedRow();
			if(selectedIndex!=-1) {
				JDialog updateDialog = new JDialog(this,"Update");
				updateDialog.setVisible(true);
				updateDialog.setSize(450,450);
				updateDialog.setLayout(new GridLayout(7,2));
				new Data(this,updateDialog,myModel,"update",table);
			}
			else {
				new Data(this,"update");//frame object aur ek string
			}
		}
		else if(str.equals("DELETE")) {
			int selectedIndex = table.getSelectedRow();
			if(selectedIndex!=-1) {
				new Data(table,myModel);//this  constructor used for deleting purposes
			}
			else {
				new Data(this,"delete");
			}
			
		}
			
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ContactManagement();
			}
		});
	}
}
