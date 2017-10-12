package wheelfortune;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mi Zhou
 */
public class Config {

	public static final List<String> PHRASES;
	public static final List<String> WHEEL_VALUES;

	static {
		PHRASES = new ArrayList<>();
		WHEEL_VALUES = new ArrayList<>();

		// load wheel values
		try (BufferedReader phrasesReader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream("res/wheel values.txt")))) {
			for (String line; (line = phrasesReader.readLine()) != null;) {
				WHEEL_VALUES.add(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}

		// load phrases
		try (BufferedReader phrasesReader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream("res/test examples1.txt")))) {
			for (String line; (line = phrasesReader.readLine()) != null;) {
				PHRASES.add(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	private Config() {

	}
}
