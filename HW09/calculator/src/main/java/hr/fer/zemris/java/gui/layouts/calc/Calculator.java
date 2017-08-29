package hr.fer.zemris.java.gui.layouts.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.calc.buttons.ClearButton;
import hr.fer.zemris.java.gui.layouts.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.layouts.calc.buttons.FunctionButton;
import hr.fer.zemris.java.gui.layouts.calc.buttons.IButton;
import hr.fer.zemris.java.gui.layouts.calc.buttons.OperatorButton;
import hr.fer.zemris.java.gui.layouts.calc.buttons.StackButton;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that extends {@link JFrame} and represents simple calculator with basic
 * functions.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.setVisible(true);
		});
	}

	/**
	 * Constructs new calculator on location (20,50)
	 */
	public Calculator() {
		setLocation(20, 50);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method used for GUI initialization. Creates buttons and action listeners.
	 */
	private void initGUI() {

		JPanel p = new JPanel(new CalcLayout(3));
		JLabel label = new JLabel("0.0");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setOpaque(true);
		label.setBackground(new Color(249, 206, 36));

		CalcMemory memory = new CalcMemory();

		ActionListener action = e -> {
			IButton button = (IButton) e.getSource();
			if (memory.resetNedded
					&& !((JButton) button).getText().equals("res")) {
				return;
			}
			button.work(label, memory);
		};

		FunctionButton[] functions = new FunctionButton[] {
				new FunctionButton("1/x", "1/x"),
				new FunctionButton("sin", "asin"),
				new FunctionButton("log", "10^x"),
				new FunctionButton("cos", "acos"),
				new FunctionButton("ctg", "actg"),
				new FunctionButton("ln", "e^x"),
				new FunctionButton("tan", "atan") };

		OperatorButton potention = new OperatorButton("x^n");
		potention.setInverzName("n\u221Ax");
		ActionListener lnvAction = e -> {
			JCheckBox checkBox = (JCheckBox) e.getSource();
			if (checkBox.isSelected()) {
				paintSecondScenario(functions, potention);
			} else {
				paintFirstScenario(functions, potention);
			}

		};

		p.add(label, "1,1");

		p.add(addActionListener(new OperatorButton("="), action), "1,6");
		p.add(addActionListener(new ClearButton("clr"), action), "1,7");

		p.add(addActionListener(new DigitButton("7"), action), "2,3");
		p.add(addActionListener(new DigitButton("8"), action), "2,4");
		p.add(addActionListener(new DigitButton("9"), action), "2,5");
		p.add(addActionListener(new OperatorButton("/"), action), "2,6");
		p.add(addActionListener(new ClearButton("res"), action), "2,7");

		p.add(addActionListener(new DigitButton("4"), action), "3,3");
		p.add(addActionListener(new DigitButton("5"), action), "3,4");
		p.add(addActionListener(new DigitButton("6"), action), "3,5");
		p.add(addActionListener(new OperatorButton("*"), action), "3,6");
		p.add(addActionListener(new StackButton("push"), action), "3,7");

		p.add(addActionListener(new DigitButton("1"), action), "4,3");
		p.add(addActionListener(new DigitButton("2"), action), "4,4");
		p.add(addActionListener(new DigitButton("3"), action), "4,5");
		p.add(addActionListener(new OperatorButton("-"), action), "4,6");
		p.add(addActionListener(new StackButton("pop"), action), "4,7");

		p.add(addActionListener(new DigitButton("0"), action), "5,3");
		p.add(addActionListener(new FunctionButton("+/-", "+/-"), action),
				"5,4");
		p.add(addActionListener(new DigitButton("."), action), "5,5");
		p.add(addActionListener(new OperatorButton("+"), action), "5,6");

		p.add(addActionListener(functions[0], action), "2,1");
		p.add(addActionListener(functions[1], action), "2,2");
		p.add(addActionListener(functions[2], action), "3,1");
		p.add(addActionListener(functions[3], action), "3,2");
		p.add(addActionListener(potention, action), "5,1");
		p.add(addActionListener(functions[4], action), "5,2");
		p.add(addActionListener(functions[5], action), "4,1");
		p.add(addActionListener(functions[6], action), "4,2");

		JCheckBox lnv = new JCheckBox("lnv");
		lnv.addActionListener(lnvAction);
		p.add(lnv, "5,7");

		getContentPane().add(p);
		pack();
		setMinimumSize(getMinimumSize());

	}

	/**
	 * Method changes button name if {@link Checkbox} is pressed.
	 * 
	 * @param functions
	 *            functions to be changed
	 * @param potention
	 *            potention to be changed
	 */
	private void paintSecondScenario(FunctionButton[] functions,
			OperatorButton potention) {
		for (FunctionButton function : functions) {
			function.setText(function.getInverzName());
		}
		potention.setText(potention.getInverzName());

	}

	/**
	 * Method changes button names on their inverz name.
	 * 
	 * @param functions
	 *            functions to be changed
	 * @param potention
	 *            potention to be changed
	 */
	private void paintFirstScenario(FunctionButton[] functions,
			OperatorButton potention) {
		for (FunctionButton function : functions) {
			function.setText(function.getRealName());
		}
		potention.setText(potention.getRealName());
	}

	/**
	 * Method adds {@link ActionListener} and returns given button.
	 * 
	 * @param button
	 *            button whom {@link ActionListener} is added
	 * @param action
	 *            {@link ActionListener} that is added to given button
	 * @return given button
	 */
	private JButton addActionListener(JButton button, ActionListener action) {
		button.addActionListener(action);
		return button;
	}
}
