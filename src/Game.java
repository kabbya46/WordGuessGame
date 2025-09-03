import java.util.Scanner;
import java.util.HashSet;

public class Game {
    private Player player;
    private WordBank wordBank;
    private Scanner scanner;

    private final String[] HANGMAN_STAGES = {
        "\n +---+\n |   |\n O   |\n/|\\  |\n/ \\  |\n     |\n=========",
        "\n +---+\n |   |\n O   |\n/|\\  |\n/    |\n     |\n=========",
        "\n +---+\n |   |\n O   |\n/|\\  |\n     |\n     |\n=========",
        "\n +---+\n |   |\n O   |\n/|   |\n     |\n     |\n=========",
        "\n +---+\n |   |\n O   |\n |   |\n     |\n     |\n=========",
        "\n +---+\n |   |\n O   |\n     |\n     |\n     |\n=========",
        "\n +---+\n |   |\n     |\n     |\n     |\n     |\n========="
    };

    public Game(Player player) {
        this.player = player;
        this.wordBank = new WordBank();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean keepPlaying = true;

        while (keepPlaying) {
            String[] wordData = wordBank.getRandomWord();
            String word = wordData[0];
            String hint = wordData[1];

            playRound(word, hint);

            System.out.println("Your current score: " + player.getScore());
            System.out.print("Do you want to play another round? (y/n): ");
            String choice = scanner.next().toLowerCase();
            keepPlaying = choice.equals("y");
            System.out.println("\n------------------------------------\n");
        }

        System.out.println("Thanks for playing! Final Score: " + player.getScore());
    }

    private void playRound(String word, String hint) {
        char[] guessedWord = new char[word.length()];
        for (int i = 0; i < guessedWord.length; i++) guessedWord[i] = '_';

        HashSet<Character> guessedLetters = new HashSet<>();
        int attemptsLeft = 6;

        System.out.println("New Word! Hint: " + hint);

        while (attemptsLeft > 0 && String.valueOf(guessedWord).contains("_")) {
            System.out.println("Word: " + String.valueOf(guessedWord));
            System.out.println("Attempts left: " + attemptsLeft);
            System.out.println(HANGMAN_STAGES[attemptsLeft]);
            System.out.print("Guess a letter: ");
            char guess = scanner.next().toLowerCase().charAt(0);

            if (!Character.isLetter(guess)) {
                System.out.println("Please enter a valid letter!");
                continue;
            }
            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter!");
                continue;
            }

            guessedLetters.add(guess);
            boolean correct = false;

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    guessedWord[i] = guess;
                    correct = true;
                }
            }

            if (correct) {
                System.out.println("Good guess!");
                player.addScore(10);
            } else {
                System.out.println("Wrong guess!");
                attemptsLeft--;
                player.deductScore(5);
            }

            System.out.println();
        }

        if (String.valueOf(guessedWord).equals(word)) {
            System.out.println("Congratulations! You guessed the word: " + word);
        } else {
            System.out.println("Out of attempts! The word was: " + word);
        }
    }
}
