package hr.fer.zemris.java.gui.layouts.calc.buttons;

import hr.fer.zemris.java.gui.layouts.calc.CalcMemory;
import hr.fer.zemris.java.gui.layouts.calc.functions.ArcCosinusFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.ArcSinusFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.ArcTangensFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.CosinusFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.CotangentFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.ExpFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.IFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.InverzFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.LnFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.LogFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.NegationFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.Potention10Function;
import hr.fer.zemris.java.gui.layouts.calc.functions.SinusFunction;
import hr.fer.zemris.java.gui.layouts.calc.functions.TangensFunction;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

/**
 * Represents function button. Class calculates given display value with function.
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class FunctionButton extends AbstractButton {
	
	private String realName;
	private String inverzName;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs new button with given real name, and inverz function name.
	 * @param name given real name
	 * @param inverzName inverz function name
	 */
	public FunctionButton(String name, String inverzName) {
		super(name);
		initComponent();
		this.realName = name;
		this.inverzName = inverzName;
	}
	
	/**
	 * Getter for real name
	 * @return real name value
	 */
	public String getRealName() {
		return realName;
	}
	
	/**
	 * Getter for inverz name
	 * @return inverz name value
	 */
	public String getInverzName() {
		return inverzName;
	}

	private static Map<String, IFunction> functions;
	static {
		functions = new HashMap<>();
		IFunction[] strategy = { new SinusFunction(), new CosinusFunction(),
				new LogFunction(), new TangensFunction(),
				new CotangentFunction(), new InverzFunction(),
				new LnFunction(), new NegationFunction(),
				new ArcSinusFunction(), new ArcCosinusFunction(),
				new Potention10Function(), new ArcTangensFunction(),
				new ExpFunction(), };
		for (IFunction function : strategy) {
			functions.put(function.getName(), function);
		}
	}
	
	/**
	 * Calculates function on given display value and sets display value.
	 */
	@Override
	public void work(JLabel display, CalcMemory memory) {
		
		IFunction function = functions.get(getText());
		Double result;
		
		double number=Double.parseDouble(display.getText());
		result= function.apply(number);

		if (!Double.isFinite(result)) {
			display.setText("Math ERROR. Press reset.");
			memory.resetNedded = true;
			return;
		}
		memory.newNumber=true;
		display.setText(result.toString());

	}
}
