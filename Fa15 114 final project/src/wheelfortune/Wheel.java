package wheelfortune;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Mi Zhou
 */
public class Wheel {

	private final List<String> values;

	public Wheel(List<String> values) {
		this.values = values;
	}

	public String spin() {
		Random random = new Random();
		return values.get(random.nextInt(values.size()));
	}
}
