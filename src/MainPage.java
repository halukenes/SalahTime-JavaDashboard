import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Choice;
import javax.swing.JPanel;

import com.teamdev.jxmaps.MapViewOptions;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class MainPage {

	private              JFrame               frame;
	private static final String[]             choicePanelItems = { "Number of Users", "Bot Traffic", "Sura Metrics", "City Metrics",
			                                                       "Reminder Metrics", "Uses of 'Read Sura'", "Uses of 'Pray Times'", 
			                                                       "Special Message Statistics" };
	private              DatabaseMapDataSet[] userMapDataSet;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		try {
			DatabaseConnection connection = new DatabaseConnection();
			userMapDataSet = connection.getdatafor_traffic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 864, 614);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		final GoogleMapsPanel mapView = new GoogleMapsPanel(options, userMapDataSet);
		mapView.setBounds(10, 36, 864, 614);
		frame.getContentPane().add(mapView);
		mapView.setVisible(true);
		
		Choice choice = new Choice();
		choice.setBounds(10, 10, 769, 20);
		for (String item : choicePanelItems)
			choice.add(item);
		choice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (choice.getSelectedItem()) {
				case "Number of Users":
					MapViewOptions options = new MapViewOptions();
					options.importPlaces();
					final GoogleMapsPanel mapView = new GoogleMapsPanel(options, userMapDataSet);
					mapView.setBounds(10, 36, 864, 614);
					frame.getContentPane().add(mapView);
					mapView.setVisible(true);
					break;
				case "Bot Traffic":
					LineGraph a = new LineGraph();
					new Thread() {
			            @Override
			            public void run() {
			                javafx.application.Application.launch(a.getClass());
			            }
			        }.start();
					frame.setBounds(100, 100, 900, 300);
					System.out.println("j");
					break;

				default:
					break;
				}
			}
		});
		frame.getContentPane().add(choice);

		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(785, 10, 89, 23);
		frame.getContentPane().add(btnNewButton);

	}
}
