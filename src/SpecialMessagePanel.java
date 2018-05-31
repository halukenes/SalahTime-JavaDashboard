import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpecialMessagePanel extends JPanel {
	private JTextField textField;
	private JButton btnNewButton;

	/**
	 * Create the panel.
	 */
	public SpecialMessagePanel() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 74, 490, 175);
		add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(388, 272, 112, 35);
		add(btnNewButton);
		
		JLabel lblSendMessageTo = new JLabel("Send Message to All Users");
		lblSendMessageTo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSendMessageTo.setBounds(10, 11, 225, 23);
		add(lblSendMessageTo);
		
		JLabel lblWriteYourMessage = new JLabel("Write your message below to send it to all users.");
		lblWriteYourMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWriteYourMessage.setBounds(10, 36, 269, 14);
		add(lblWriteYourMessage);

	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}
}
