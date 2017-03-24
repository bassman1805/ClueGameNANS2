package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class controlGUI extends JPanel {
	private JTextField name;

	public controlGUI(){
		setLayout(new GridLayout(2,0));
		/*JPanel panel = createFunctionPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel = comboPanel();
		add(panel, BorderLayout.SOUTH);*/
	}

	private JPanel createFunctionPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JLabel nameLabel = new JLabel("Player:");
		name = new JTextField(10);
		
		panel.add(nameLabel);
		panel.add(name);
		
		panel.add(createButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton nextPlayer = new JButton("Next Player");
		JButton suggestion = new JButton("Suggestion");
		JPanel panel = new JPanel();
		// Need to add action listener to each button (just for testing functionality)
		nextPlayer.addActionListener(new ButtonListener());
		suggestion.addActionListener(new ButtonListener());
		
		panel.add(nextPlayer, BorderLayout.CENTER);
		panel.add(suggestion, BorderLayout.SOUTH);
		return panel;
	}
	
	//This will be the method for every one of 3 panels at the bottom
	private JPanel feedbackPanel(String title, String value){
		JPanel panel = new JPanel();
		// this panel will have 2 rows with 1 column, along with a title
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel(value);
		name = new JTextField(5);
		panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		
		return panel;
	}
	
	private JPanel comboPanel(){
		JPanel panel = new JPanel();
		//This will implement 3 panels all in one row
		panel.setLayout(new GridLayout(1,3));
		
		panel.add(feedbackPanel("Die", "Current Roll:"));
		panel.add(feedbackPanel("Guess", "Guessed Suggestion:"));
		panel.add(feedbackPanel("Result", "Guess Result:"));
		
		return panel;
	}

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Button pressed");
		}

	}

	public static void main(String[] args){
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game GUI");
		frame.setSize(500, 300);	
		// Create the JPanel and add it to the JFrame
		controlGUI gui = new controlGUI();
		JPanel panel = gui.createFunctionPanel();
		gui.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel = gui.comboPanel();
		gui.add(panel, BorderLayout.SOUTH);
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
