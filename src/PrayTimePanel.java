import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrayTimePanel extends JPanel {
	private JTextField textStartingDay;
	private JTextField textEndingDay;
	private JTextField textdayuntilNow;
	private JButton btnShowTheGraph;
	private JRadioButton rdbtnDayUntilNow;
	private JRadioButton rdbtnNewRadioButton;

	/**
	 * Create the panel.
	 */
	public PrayTimePanel() {
		setLayout(null);
		
		textStartingDay = new JTextField();
		textStartingDay.setBounds(43, 118, 86, 20);
		add(textStartingDay);
		textStartingDay.setColumns(10);
		
		rdbtnNewRadioButton = new JRadioButton("Between limited time");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton.setBounds(33, 66, 154, 23);
		add(rdbtnNewRadioButton);
		
		textEndingDay = new JTextField();
		textEndingDay.setColumns(10);
		textEndingDay.setBounds(229, 118, 86, 20);
		add(textEndingDay);
		
		rdbtnDayUntilNow = new JRadioButton("Day until now");
		rdbtnDayUntilNow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnDayUntilNow.setBounds(33, 160, 109, 23);
		add(rdbtnDayUntilNow);
		
		textdayuntilNow = new JTextField();
		textdayuntilNow.setColumns(10);
		textdayuntilNow.setBounds(43, 212, 86, 20);
		add(textdayuntilNow);
		
		btnShowTheGraph = new JButton("Show the Graph");
		btnShowTheGraph.setBounds(289, 242, 130, 23);
		add(btnShowTheGraph);
		
		JLabel lblNewLabel = new JLabel("The Traffic Information of the Bot");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(100, 11, 256, 20);;
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please choose of the days below to see a chart of traffic information.");
		lblNewLabel_1.setBounds(59, 39, 360, 20);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Starting Day (e.g. 18.05.2018)");
		lblNewLabel_2.setBounds(43, 96, 156, 20);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Ending Day (e.g. 18.05.2018)");
		lblNewLabel_3.setBounds(229, 96, 154, 20);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Day Count (e.g. 5)");
		lblNewLabel_4.setBounds(43, 190, 154, 20);
		add(lblNewLabel_4);

	}

	public JTextField getTextStartingDay() {
		return textStartingDay;
	}

	public void setTextStartingDay(JTextField textStartingDay) {
		this.textStartingDay = textStartingDay;
	}

	public JTextField getTextEndingDay() {
		return textEndingDay;
	}

	public void setTextEndingDay(JTextField textEndingDay) {
		this.textEndingDay = textEndingDay;
	}

	public JTextField getTextdayuntilNow() {
		return textdayuntilNow;
	}

	public void setTextdayuntilNow(JTextField textdayuntilNow) {
		this.textdayuntilNow = textdayuntilNow;
	}

	public JButton getBtnShowTheGraph() {
		return btnShowTheGraph;
	}

	public void setBtnShowTheGraph(JButton btnShowTheGraph) {
		this.btnShowTheGraph = btnShowTheGraph;
	}

	public JRadioButton getRdbtnDayUntilNow() {
		return rdbtnDayUntilNow;
	}

	public void setRdbtnDayUntilNow(JRadioButton rdbtnDayUntilNow) {
		this.rdbtnDayUntilNow = rdbtnDayUntilNow;
	}

	public JRadioButton getRdbtnNewRadioButton() {
		return rdbtnNewRadioButton;
	}

	public void setRdbtnNewRadioButton(JRadioButton rdbtnNewRadioButton) {
		this.rdbtnNewRadioButton = rdbtnNewRadioButton;
	}
}
