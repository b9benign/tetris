package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The class Scoreboard contains methods to safe the score in a file after game over and to print the top 5 scores in the console.
 * 
 * @author Liam
 * @version 1.0
 * @see Path
 * @see FileReader
 */
public class Scoreboard {
    private static final String SCORE_FILE = "src/resources/scores.txt";
    private static boolean scoreWritten = false;
    private static boolean highscoresLogged = false;

    /**
     * The <code>writeScore</code> function reads scores from a file, adds a new score, sorts the scores in
     * descending order, keeps only the top 5 scores, and writes the updated scores back to the file.
     * 
     * @param score The <code>score</code> variable contains the final score after game over.
     * @see Path
     */
    public static void writeScore(int score) {
        if (scoreWritten) {
            return; // make sure that method is only executed once
        }
        scoreWritten = true;
        Path filePath = Paths.get(SCORE_FILE);
        try {
            // Read the scores from the file
            List<String> scores = Files.readAllLines(filePath);

            // Add the new score to the list
            scores.add(String.valueOf(score));

            // Sort the list in descending order
            scores.sort((a, b) -> Integer.compare(Integer.parseInt(b), Integer.parseInt(a)));

            // Keep only the top 5 scores
            scores = scores.subList(0, Math.min(5, scores.size()));

            // Write the scores back to the file
            Files.write(filePath, scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function <code>logHighscores</code> reads highscores from the file `scores.txt`
     * and prints the top 5 scores to the console.
     * 
     * @see FileReader
     */
    public static void logHighscores() {
        if (highscoresLogged) {
            return; // make sure that method is only executed once
        }
        highscoresLogged = true;
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/scores.txt"))) {
            String line;
            System.out.println("Highscores:");
            int i = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(i + ": " + line);
                i++;
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Highscores: " + e.getMessage());
        }
    }
}
