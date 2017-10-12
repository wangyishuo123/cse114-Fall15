package wheelfortune;

/**
 *
 * @author Mi Zhou
 */
public abstract class Player {

	protected final String name;
	protected final Wheel wheel;
	protected int total;

	public Player(String name, Wheel wheel) {
		this.name = name;
		this.wheel = wheel;
		this.total = 0;
	}

	public String getName() {
		return name;
	}

	public int getTotal() {
		return total;
	}

	public void add(int prize) {
		total += prize;
	}

	public void lose(int lose) {
		total -= lose;
		if (total < 0) {
			total = 0;
		}
	}

	public void goBankrupt() {
		total = 0;
	}

	public String spinWheel() {
		return wheel.spin();
	}

	public abstract char guessLetter();

	public abstract String guessPhrase();
}
