package wheelfortune;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Mi Zhou
 */
public class Referee {

	public static void main(String[] args) throws Exception {
		new Referee().startGame();
	}

	private final Wheel wheel;
	private final Board board;
	private final String phrase;

	private final Player humanPlayer;
	private final Player robotPlayer;

	private Player curPlayer; // current player

	private final Scanner in;

	public Referee() {
		wheel = new Wheel(Config.WHEEL_VALUES); // init wheel

		// get a random phrase
		Random random = new Random();
		int phraseIndex = random.nextInt(Config.PHRASES.size());
		phrase = Config.PHRASES.get(phraseIndex);
		System.out.println("The Selected Phrase: " + phrase);

		board = new Board(phrase.toLowerCase()); // init board

		in = new Scanner(System.in); // get System in

		System.out.println("Welcome to Hangman!");
		System.out.println("What is your name?");
		System.out.print("> ");
		String humanPlayerName = in.nextLine(); // get player's name
		humanPlayer = new HumanPlayer(humanPlayerName, wheel, in);
		robotPlayer = new RobotPlayer("Computer", wheel);
		curPlayer = humanPlayer;
	}

	private void displayScores() {
		System.out.format("%s: %d\n", humanPlayer.getName(), humanPlayer.getTotal());
		System.out.format("%s: %d\n\n", robotPlayer.getName(), robotPlayer.getTotal());
	}

	private void displayWinner(Player player) {
		System.out.format("\n%s's guess is correct!\n", player.getName());
		System.out.format("%s wins!\n\n", player.getName());
	}

	private void displayLosing(Player player, int losing) {
		System.out.format("\n%s's turn.\n", curPlayer.getName());
		System.out.format("%s spins a %d wedge.\n", curPlayer.getName(), -losing);
		System.out.format("%s loses %d from his total.\n\n", curPlayer.getName(), losing);
	}

	private void displayBankruptcy(Player player) {
		System.out.format("\n%s's turn.\n", player.getName());
		System.out.format("%s spins Bankruptcy!\n", player.getName());
		System.out.format("%s's total drops to 0.\n\n", player.getName());
	}

	private void changePlayer() {
		if (curPlayer == humanPlayer) {
			curPlayer = robotPlayer;
		} else {
			curPlayer = humanPlayer;
		}
	}

	public void startGame() {
		boolean someoneWins = false;

		while (true) {
			System.out.format("-- %s's turn!\n", curPlayer.getName());
			System.out.print("Spin the Wheel?(y) ");
			in.next();
			String value = curPlayer.spinWheel();
			System.out.format("\n%s spins a %s wedge.\n", curPlayer.getName(), value);

			int prize = 0;
			switch (value) {
				case "Bankruptcy":
					break;
				case "1Million":
					prize = 1_000_000;
					break;
				default:
					prize = Integer.valueOf(value);
					break;
			}

			if (prize == 0) { // Bankruptcy
				curPlayer.goBankrupt();

				displayBankruptcy(curPlayer);

				board.display();
				changePlayer();
			} else if (prize < 0) {
				curPlayer.lose(-prize);

				displayLosing(curPlayer, prize);

				board.display();
				changePlayer();
			} else { // prize > 0
				System.out.println("The word is:");
				board.display();
				displayScores();

				System.out.format("What would %s like to do?\n", curPlayer.getName());
				System.out.println("(1) Guess a Letter (2) Guess the Phrase");
				System.out.print(">");
				int choice = in.nextInt();
				if (choice == 1) {
					if (curPlayer == humanPlayer) {
						System.out.println("Input your guessed letter here.");
						System.out.print(">");
					}
					char guessLetter = curPlayer.guessLetter();
					if (board.contains(guessLetter)) {
						if (board.alreadyDisplay(guessLetter)) {
							System.out.format("%s guesses %c.\n", curPlayer.getName(), guessLetter);
							System.out.println("This letter has already been guessed!");
							changePlayer();
						} else {
							int letterCount = board.getCount(guessLetter);
							System.out.format("%s guesses %d %c.\n",
									curPlayer.getName(), letterCount, guessLetter);
							System.out.format("%s adds %d*%d=%d to his total.\n",
									curPlayer.getName(), letterCount, prize, letterCount * prize);
							curPlayer.add(letterCount * prize);

							board.uncover(guessLetter);
							board.display();
						}
					} else {
						System.out.format("%s guesses %c.\n", curPlayer.getName(), guessLetter);
						System.out.format("%s's guess is incorrect.\n\n", curPlayer.getName());
						changePlayer();
					}
				} else { // choose 2
					System.out.println("Input your guessed phrase here.");
					System.out.print(">");
					in.nextLine(); // eat Enter char
					String guessPhrase = curPlayer.guessPhrase();
					if (guessPhrase.equalsIgnoreCase(phrase)) {
						curPlayer.add(prize);

						displayWinner(curPlayer);

						board.uncoverAll();
						board.display();

						someoneWins = true;
					} else {
						System.out.format("%s's guess is incorrect!\n\n", curPlayer.getName());
						changePlayer();
					}
				}
			}

			displayScores();

			if (someoneWins) {
				break;
			}
		}
	}

}
