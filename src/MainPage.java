import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Choice;
import javax.swing.JPanel;
import java.util.prefs.Preferences;

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
			                                                       "Special Day Message" };
	private              DatabaseMapDataSet[] userMapDataSet;
	private              Preferences          prefs            = Preferences.userRoot().node(MainPage.class.getName());
	private              String               untilnowID       = "untilnowID";
	private              String               untildayID       = "untildayID";
	private              String               startingID       = "startingID";
	private              String               endingID         = "endingID";
	private              String               untilnowIDR      = "untilnowIDR";
	private              String               untildayIDR      = "untildayIDR";
	private              String               startingIDR      = "startingIDR";
	private              String               endingIDR        = "endingIDR";
	private              String               untilnowIDS      = "untilnowIDS";
	private              String               untildayIDS      = "untildayIDS";
	private              String               startingIDS      = "startingIDS";
	private              String               endingIDS        = "endingIDS";
	private              DatabaseConnection   connection;

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

	public MainPage() {
		
		try {
			connection = new DatabaseConnection();
			userMapDataSet = connection.getdatafor_map();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		final GoogleMapsPanel mapView1 = new GoogleMapsPanel(options, userMapDataSet);
		mapView1.setBounds(10, 36, 864, 614);
		frame.getContentPane().add(mapView1);
		mapView1.setVisible(true);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					userMapDataSet = connection.getdatafor_map();
					frame.getContentPane().remove(mapView1);
					frame.setBounds(100, 100, 901, 700);
					frame.setBounds(100, 100, 900, 700);
					final GoogleMapsPanel mapView1 = new GoogleMapsPanel(options, userMapDataSet);
					mapView1.setBounds(10, 36, 864, 614);
					frame.getContentPane().add(mapView1);
					mapView1.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(785, 10, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		Choice choice = new Choice();
		choice.setBounds(10, 10, 769, 20);
		for (String item : choicePanelItems)
			choice.add(item);
		choice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (choice.getSelectedItem()) {
				case "Number of Users":
					frame.getContentPane().removeAll();
					frame.setBounds(100, 100, 900, 700);
					choice.setBounds(10, 10, 769, 20);
					frame.getContentPane().add(btnNewButton);
					frame.getContentPane().add(choice);
					MapViewOptions options = new MapViewOptions();
					options.importPlaces();
					GoogleMapsPanel mapView = new GoogleMapsPanel(options, userMapDataSet);
					mapView.setBounds(10, 36, 864, 614);
					frame.getContentPane().add(mapView);
					mapView.setVisible(true);
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								userMapDataSet = connection.getdatafor_map();
								frame.getContentPane().remove(mapView);
								frame.setBounds(100, 100, 901, 700);
								frame.setBounds(100, 100, 900, 700);
								final GoogleMapsPanel mapView = new GoogleMapsPanel(options, userMapDataSet);
								mapView.setBounds(10, 36, 864, 614);
								frame.getContentPane().add(mapView);
								mapView.setVisible(true);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					break;
				case "Bot Traffic":
					frame.getContentPane().removeAll();
					frame.getContentPane().add(choice);
					BotTrafficPanel trafficPanel = new BotTrafficPanel();
					frame.setBounds(100, 100, 550, 400);
					choice.setSize(510, 20);
					trafficPanel.setBounds(10, 36, 550, 310);
					frame.getContentPane().add(trafficPanel);
					trafficPanel.setVisible(true);
					trafficPanel.getBtnShowTheGraph().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(trafficPanel.getRdbtnDayUntilNow().isSelected()) {
								prefs.putBoolean(untilnowID, true);
								prefs.putInt(untildayID, Integer.parseInt(trafficPanel.getTextdayuntilNow().getText()));
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(TrafficLineChart.class);
						            }
						        }.start();
							} else if (trafficPanel.getRdbtnNewRadioButton().isSelected()) {
								prefs.putBoolean(untilnowID, false);
								prefs.put(startingID, trafficPanel.getTextStartingDay().getText());
								prefs.put(endingID, trafficPanel.getTextEndingDay().getText());
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(TrafficLineChart.class);
						            }
						        }.start();
							}	
						}
					});
					break;
				case "Sura Metrics":
					new Thread() {
			            @Override
			            public void run() {
			                javafx.application.Application.launch(SuraPieChart.class);
			            }
			        }.start();
					break;
				case "City Metrics":
					new Thread() {
			            @Override
			            public void run() {
			                javafx.application.Application.launch(CityPieChart.class);
			            }
			        }.start();
					break;
				case "Reminder Metrics":
					new Thread() {
			            @Override
			            public void run() {
			                javafx.application.Application.launch(CReminderMetrics.class);
			            }
			        }.start();
					break;
				case "Uses of 'Read Sura'":
					frame.getContentPane().removeAll();
					frame.getContentPane().add(choice);
					ReadSuraPanel suraPanel = new ReadSuraPanel();
					frame.setBounds(100, 100, 550, 400);
					choice.setSize(510, 20);
					suraPanel.setBounds(10, 36, 550, 310);
					frame.getContentPane().add(suraPanel);
					suraPanel.setVisible(true);
					suraPanel.getBtnShowTheGraph().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(suraPanel.getRdbtnDayUntilNow().isSelected()) {
								prefs.putBoolean(untilnowIDR, true);
								prefs.putInt(untildayIDR, Integer.parseInt(suraPanel.getTextdayuntilNow().getText()));
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(CReadingSura.class);
						            }
						        }.start();
							} else if (suraPanel.getRdbtnNewRadioButton().isSelected()) {
								prefs.putBoolean(untilnowIDR, false);
								prefs.put(startingIDR, suraPanel.getTextStartingDay().getText());
								prefs.put(endingIDR, suraPanel.getTextEndingDay().getText());
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(CReadingSura.class);
						            }
						        }.start();
							}	
						}
					});
					break;
				case "Uses of 'Pray Times'":
					frame.getContentPane().removeAll();
					frame.getContentPane().add(choice);
					PrayTimePanel prayPanel = new PrayTimePanel();
					frame.setBounds(100, 100, 550, 400);
					choice.setSize(510, 20);
					prayPanel.setBounds(10, 36, 550, 310);
					frame.getContentPane().add(prayPanel);
					prayPanel.setVisible(true);
					prayPanel.getBtnShowTheGraph().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(prayPanel.getRdbtnDayUntilNow().isSelected()) {
								prefs.putBoolean(untilnowIDS, true);
								prefs.putInt(untildayIDS, Integer.parseInt(prayPanel.getTextdayuntilNow().getText()));
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(CSalahTime.class);
						            }
						        }.start();
							} else if (prayPanel.getRdbtnNewRadioButton().isSelected()) {
								prefs.putBoolean(untilnowIDS, false);
								prefs.put(startingIDS, prayPanel.getTextStartingDay().getText());
								prefs.put(endingIDS, prayPanel.getTextEndingDay().getText());
								new Thread() {
						            @Override
						            public void run() {
						                javafx.application.Application.launch(CSalahTime.class);
						            }
						        }.start();
							}	
						}
					});
					break;
				case "Special Day Message":
					frame.getContentPane().removeAll();
					frame.getContentPane().add(choice);
					SpecialMessagePanel messagePanel = new SpecialMessagePanel();
					frame.setBounds(100, 100, 550, 400);
					choice.setSize(510, 20);
					messagePanel.setBounds(10, 36, 570, 310);
					frame.getContentPane().add(messagePanel);
					messagePanel.setVisible(true);
					messagePanel.getBtnNewButton().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (connection.sendMessage_toAllUsers(messagePanel.getTextField().getText())) {
								JOptionPane.showMessageDialog(messagePanel, "The message is sent successfully!");
							} else {
								JOptionPane.showMessageDialog(messagePanel, "Failed to send the message!");
							}
						}
					});
					break;
				default:
					break;
				}
			}
		});
		frame.getContentPane().add(choice);

	}
}
