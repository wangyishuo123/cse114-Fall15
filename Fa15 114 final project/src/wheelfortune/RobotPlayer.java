package wheelfortune;

import java.util.Random;

/**
 *
 * @author Mi Zhou
 */
public class RobotPlayer extends Player {

	private final Random random;

	public RobotPlayer(String name, Wheel wheel) {
		super(name, wheel);
		this.random = new Random();
	}

	@Override
	public char guessLetter() {
		char letter = (char) ('a' + random.nextInt(26));
		return letter;
	}

	@Override
	public String guessPhrase() {
		return "";
	}

}
