package wheelfortune;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mi Zhou
 */
public class Board {

	private final char[] letters;
	private final Map<Character, Boolean> displayMap;
	private final Map<Character, Integer> countMap;

	public Board(String phrase) {
		letters = phrase.toCharArray();
		displayMap = new HashMap<>();
		countMap = new HashMap<>();
		for (char letter : letters) {
			displayMap.put(letter, Boolean.FALSE);
			if (countMap.get(letter) == null) {
				countMap.put(letter, 1);
			} else {
				countMap.put(letter, countMap.get(letter) + 1);
			}
		}
	}

	public void uncover(char guessLetter) {
		displayMap.put(guessLetter, Boolean.TRUE);
	}

	public void uncoverAll() {
		for (Character letter : displayMap.keySet()) {
			displayMap.put(letter, Boolean.TRUE);
		}
	}

	int getCount(char letter) {
		return countMap.get(letter);
	}

	public boolean contains(char letter) {
		return displayMap.containsKey(letter);
	}

	public boolean alreadyDisplay(char letter) {
		return displayMap.get(letter);
	}

	public void display() {
		for (char letter : letters) {
			if (letter == ' ' || displayMap.get(letter)) {
				System.out.print(letter);
			} else {
				System.out.print("_");
			}
			System.out.print(" ");
		}
		System.out.println();
	}

}
