package wheelfortune;

import java.util.Scanner;

/**
 *
 * @author Mi Zhou
 */
public class HumanPlayer extends Player {

	private final Scanner in;

	public HumanPlayer(String name, Wheel wheel, Scanner in) {
		super(name, wheel);
		this.in = in;
	}

	@Override
	public char guessLetter() {
		String str = in.next().toLowerCase();
		return str.charAt(0);
	}

	@Override
	public String guessPhrase() {
		String line = in.nextLine().toLowerCase();
		return line;
	}

}
