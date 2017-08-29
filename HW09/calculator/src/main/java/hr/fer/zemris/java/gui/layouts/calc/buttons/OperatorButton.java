package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;
import hr.fer.zemris.java.gui.layouts.calc.operators.DivideOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.IOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.MinusOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.MultiplyOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.PlusOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.PotentionOperator;
import hr.fer.zemris.java.gui.layouts.calc.operators.RootOperator;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

/**
 * Class represents operator button. Operator is stored in {@link CalcMemory}.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class OperatorButton extends AbstractButton {
	private String inverzName;
	private String realName;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs {@link OperatorButton} with given name.
	 * 
	 * @param name
	 *            given name
	 */
	public OperatorButton(String name) {
		super(name);
		initComponent();
		realName = name;
	}

	/**
	 * Sets inverz name of given button.
	 * 
	 * @param name
	 *            inverz name
	 */
	public void setInverzName(String name) {
		inverzName = name;
	}

	/**
	 * Getter for inverz name.
	 * 
	 * @return value of inverz name
	 */
	public String getInverzName() {
		return inverzName;
	}

	/**
	 * Getter for real name.
	 * 
	 * @return value of real name
	 */
	public String getRealName() {
		return realName;
	}

	private static Map<String, IOperator> operators;
	static {
		operators = new HashMap<>();
		IOperator[] strategy = { new PlusOperator(), new MinusOperator(),
				new MultiplyOperator(), new DivideOperator(),
				new PotentionOperator(), new RootOperator(), };

		for (IOperator operator : strategy) {
			operators.put(operator.getName(), operator);
		}
	}

	/**
	 * If first time is pressed operator is only stored in memory, otherwise
	 * prevoius operator is applied on number in memory and display number.
	 */
	@Override
	public void work(JLabel display, CalcMemory memory) {

		Double number = 0.0;

		number = Double.parseDouble(display.getText());

		IOperator operator = operators.get(getText());

		if (memory.operator == null) {
			memory.number1 = number;

		} else {
			number = memory.number1 = memory.operator.applyOperator(
					memory.number1, number);
		}

		if (!Double.isFinite(memory.number1)) {
			display.setText("Math ERROR. Press reset.");
			memory.resetNedded = true;
			return;
		}

		memory.newNumber = true;
		memory.operator = operator;

		display.setText(number.toString());
	}

}
