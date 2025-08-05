package domain;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IceCreamPOS extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables to keep track of the order
	private double total = 0.0;
	private String order = "";

	// GUI components
	private JLabel flavorLabel = new JLabel("Flavor:");
	private JComboBox<String> flavorCombo = new JComboBox<>();
	private JLabel sizeLabel = new JLabel("Size:");
	private JRadioButton smallButton = new JRadioButton("Small");
	private JRadioButton mediumButton = new JRadioButton("Medium");
	private JRadioButton largeButton = new JRadioButton("Large");
	private ButtonGroup sizeGroup = new ButtonGroup();
	private JCheckBox coneCheck = new JCheckBox("Cone");
	private JCheckBox sprinklesCheck = new JCheckBox("Sprinkles");
	private JLabel orderLabel = new JLabel("Order:");
	private JTextArea orderArea = new JTextArea();
	private JLabel totalLabel = new JLabel("Total:");
	private JTextField totalField = new JTextField();
	private JButton addButton = new JButton("Add");
	private JButton clearButton = new JButton("Clear");
	private JLabel payLabel = new JLabel("Pay:");
	private JTextField payField = new JTextField();
	private JButton payButton = new JButton("Pay");

	public IceCreamPOS() {
		// Set up the frame
		setTitle("Ice Cream Shop POS");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		getContentPane().setBackground(new Color(173, 216, 230));
		flavorLabel.setForeground(new Color(0, 0, 128));
		sizeLabel.setForeground(new Color(0, 0, 128));
		orderLabel.setForeground(new Color(0, 0, 128));
		totalLabel.setForeground(new Color(0, 0, 128));

		// Add the flavor combo box
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		add(flavorLabel, c);
		flavorCombo.addItem("Vanilla");
		flavorCombo.addItem("Chocolate");
		flavorCombo.addItem("Strawberry");
		c.gridx = 1;
		add(flavorCombo, c);

		// Add the size radio buttons
		c.gridx = 0;
		c.gridy = 1;
		add(sizeLabel, c);
		smallButton.setSelected(true);
		sizeGroup.add(smallButton);
		sizeGroup.add(mediumButton);
		sizeGroup.add(largeButton);
		c.gridx = 1;
		add(smallButton, c);
		c.gridx = 2;
		add(mediumButton, c);
		c.gridx = 3;
		add(largeButton, c);

		// Add the cone and sprinkles check boxes
		c.gridx = 0;
		c.gridy = 2;
		add(coneCheck, c);
		c.gridx = 1;
		add(sprinklesCheck, c);

		// Add the order area and scroll pane
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		orderArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(orderArea);
		add(scrollPane, c);

		// Add the total label and text field
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		add(totalLabel, c);
		c.gridx = 1;
		totalField.setEditable(false);
		add(totalField, c);

		// Add the add and clear buttons
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		addButton.addActionListener(new AddButtonListener());
		add(addButton, c);
		c.gridy = 6;
		clearButton.addActionListener(new ClearButtonListener());
		add(clearButton, c);

		c.gridx = 0;
		c.gridy = 7;
		add(payLabel, c);
		c.gridx = 1;
		add(payField, c);
		c.gridx = 2;
		payButton.addActionListener(new PayButtonListener());
		add(payButton, c);
		payField.setEditable(true);
		payField.setPreferredSize(new Dimension(100, 20));

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Display the frame
		setVisible(true);
	}

	private class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Add the current order to the order area
			String flavor = (String) flavorCombo.getSelectedItem();
			String size = "";
			if (smallButton.isSelected()) {
				size = "Small";
			} else if (mediumButton.isSelected()) {
				size = "Medium";
			} else {
				size = "Large";
			}
			String cone = "";
			if (coneCheck.isSelected()) {
				cone = " with cone";
			}
			String sprinkles = "";
			if (sprinklesCheck.isSelected()) {
				sprinkles = " with sprinkles";
			}
			order += flavor + " " + size + cone + sprinkles + "\n";
			orderArea.setText(order);

			// Update the total
			double price = 0.0;
			if (size.equals("Small")) {
				price = 2.5;
			} else if (size.equals("Medium")) {
				price = 3.5;
			} else {
				price = 4.5;
			}
			if (coneCheck.isSelected()) {
				price += 0.5;
			}
			if (sprinklesCheck.isSelected()) {
				price += 0.25;
			}
			total += price;
			totalField.setText("$" + String.format("%.2f", total));
		}
	}

	private class ClearButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Clear the order area and reset the total
			order = "";
			orderArea.setText("");
			total = 0.0;
			totalField.setText("$0.00");
		}
	}

		private class PayButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					double payment = Double.parseDouble(payField.getText());
					double change = payment-total;
			
					if (payment > 0 && payment >= total) {
						// Process payment and generate receipt
						 Date currentDate = new Date();
						 SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
						 String date = dateFormat.format(currentDate);


						String receipt = "Receipt\nDate: " + date +"\nTotal:" +total+ "\nAmount paid:"
								+ payment + "\nChange:" + change;

						JOptionPane.showMessageDialog(null, receipt);
						 
					} else {
						JOptionPane.showMessageDialog(null, "Invalid input. Payment should be greater than "+total+ "\nError"
								);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				payField.setText("");
				orderArea.setText("");
				totalField.setText("$0.00");
			}

		}
		
		public static void main(String[] args) {
			new IceCreamPOS();
		}

}
