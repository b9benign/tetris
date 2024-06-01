package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Scoreboard {
    private static final String SCORE_FILE = "src/resources/scores.txt";
    private static boolean scoreWritten = false;
    private static boolean highscoresLogged = false;

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
