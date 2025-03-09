import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Class representing the core logic of a HangMan game.
 * Manages the word to guess, guessed letters, and game state.
 */
public class HangManGame {
	private String word; // The word to be guessed
	private char[] guessedWord; // The current state of the guessed word
	private Set<Character> guessedLetters; // A set of letters guessed so far
	private int wrongGuesses; // Counter for incorrect guesses
	private final int maxGuesses = 7; // Maximum number of incorrect guesses allowed
	
	 /**
     * Constructs a new HangManGame instance with a random word.
     *
     * @param words An array of possible words to guess
     */
	 public HangManGame(String[] words) {
		 Random random = new Random();
		 this.word = words[random.nextInt(words.length)].toLowerCase(); // Choose a random word
		 this.guessedWord = new char[word.length()];
		 
		// Initialize the guessed word as underscores
		 for (int i = 0; i < guessedWord.length; i++) {
			 guessedWord[i] = '_';
		 }
		 
		  this.guessedLetters = new HashSet<>();
		  this.wrongGuesses = 0;
	 }
	 
	 
	 /**
	 * Processes a player's guess and updates the game state.
	 *
	 * @param letter The guessed letter
	 * @return True if the letter is in the word, false otherwise
	 */
	 public boolean guessLetter(char letter) {
		 letter = Character.toLowerCase(letter); // Normalize to lowercase
		 if (guessedLetters.contains(letter)) {
			 return false; // Letter has already been guessed	
		}
		 guessedLetters.add(letter); // Add the letter to guessed letters
		 
		 boolean correct = false;
		 
		// Update guessedWord if the letter is correct
		 for (int i = 0; i < word.length(); i++) {
			 if (word.charAt(i) == letter) {
				 guessedWord[i] = letter;
				 correct = true;
			 }
		 }
		 
		  if(!correct) {
			  wrongGuesses++;  // Increment wrong guesses if the letter is incorrect
		  }
		  
		  return correct;
	 }
	 
	 
	 /**
	 * Returns the current state of the guessed word.
	 *
	 * @return The guessed word as a String
	 */
	 public String getGuessedWord() {
		 return new String(guessedWord);
	 }
	 
	 
	 
	 /**
	  * Returns the set of letters guessed so far.
	  *
	  * @return A Set of guessed characters
	  */	 
	 public Set<Character> getGuessedLetters() {
		 return guessedLetters;
	 }
	 
	 /**
	 * Checks if the game is over (either the word is guessed or max guesses reached).
	 *
	 * @return True if the game is over, false otherwise
	 */
	 public boolean isGameOver() {
		 return wrongGuesses >= maxGuesses || getGuessedWord().equals(word);
	 }
	 


	/**
	* Checks if the word has been fully guessed.
	*
	* @return True if the word is fully guessed, false otherwise
	*/
	 public boolean isWordGuessed() {
		 return getGuessedWord().equals(word);
	 }
	 
	 
	 
	/**
	* Returns the number of incorrect guesses made.
	*
	* @return The number of wrong guesses
	*/ 
	 public int getWrongGuesses() {
		 return wrongGuesses;
	 }
	 
	 
	 
	 /**
	 * Returns the word to be guessed.
	 *
	 * @return The target word
	 */
	 public String getWord() {
		 return word;
	 }
	 
}