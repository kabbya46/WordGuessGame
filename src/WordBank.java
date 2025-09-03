import java.util.Random;

public class WordBank {
    private String[][] words = {
        {"java", "A popular programming language."},
        {"developer", "Someone who writes code."},
        {"keyboard", "Input device for typing."},
        {"computer", "Electronic device for processing data."},
        {"programming", "The act of writing code."}
    };

    private Random rand = new Random();

    public String[] getRandomWord() {
        return words[rand.nextInt(words.length)];
    }
}
