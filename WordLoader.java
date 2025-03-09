import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class for loading words from a file.
 */
public class WordLoader {
	
	/**
     * Loads words from a given file.
     *
     * @param fileName The name of the file containing words
     * @return An array of words loaded from the file
     * @throws IOException If an error occurs while reading the file
     */
	public static String[] loadWords(String fileName) throws IOException {
		List<String> words = new ArrayList<>();
		
		// Try-with-resources to automatically close the file reader
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			
			// Read each line, trim whitespace, and add it to the words list
			while ((line = br.readLine()) != null) {
				words.add(line.trim());
			}
		}
		
		// Convert the list of words to an array and return
		return words.toArray(new String[0]);
	}
}