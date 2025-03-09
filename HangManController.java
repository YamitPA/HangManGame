import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Node;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType; 


/**
 * Controller class for the hangman game. 
 * Manages user interactions and game logic.
 */


public class HangManController {
	private HangManGame game; // Handles the game logic
	private String[] words; // Array of words for the game

    @FXML
    private Label wordLabel; // Displays the word being guessed

    @FXML
    private Canvas canv; // Canvas for drawing the hangman figure

    @FXML
    private GridPane letterGrid; // Grid containing letter buttons
    
    @FXML
    private HBox guessedLettersBox; // Displays guessed letters
    
    
    
    /**
     * Initializes the controller and starts a new game.
     */
    public void initialize() {
    	try {
    		words = WordLoader.loadWords("words.txt"); // Load words from file
    		startNewGameLogic(); // Start the first game
    	} catch (IOException e) {
    		System.err.println("Failed to load words: " + e.getMessage());
    	}
    }
    
    
    /**
     * Starts a new game when the "New Game" button is clicked.
     */
    @FXML
    void startNewGame(ActionEvent event) {
    	startNewGameLogic();
    }
    
    
    /**
     * Sets up a new game, clearing the canvas, resetting the word label,
     * and populating the letter grid.
     */
    private void startNewGameLogic() {
        game = new HangManGame(words); // Create a new game instance
        wordLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: blue;");
        wordLabel.setText(game.getGuessedWord()); // Display the masked word
        guessedLettersBox.getChildren().clear(); // Clear previous guessed letters
        clearCanvas(); // Clear the drawing canvas
        populateLetterGrid(); // Populate letter buttons
    }
    
    
    /**
     * Populates the letter grid with buttons for each letter of the alphabet.
     */
    private void populateLetterGrid() {
        letterGrid.getChildren().clear();  // Clear existing buttons 

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        
        int rowIndex = 0;  
        int colIndex = 0;  
        
        // Create a button for each letter
        for (int i = 0; i < alphabet.length(); i++) {
            char letter = alphabet.charAt(i);
            Button letterButton = new Button(String.valueOf(letter));
            letterButton.setStyle("-fx-font-size: 16px; -fx-padding: 6px;"); 

            //Set the button's action to handle letter clicks
            letterButton.setOnAction(e -> handleLetterClick(letter, letterButton));

            // Add the button to the grid
            letterGrid.add(letterButton, colIndex, rowIndex);

            colIndex++;
        }
    } 
    
    
    /**
     * Handles the action when a letter button is clicked.
     *
     * @param letter The letter associated with the button
     * @param button The button that was clicked
     */
    private void handleLetterClick(char letter, Button button) {
    	if (game.isGameOver() || game.isWordGuessed()) {
    		 return; // Do nothing if the game is already over
    	}
    	
    	button.setDisable(true); // Disable the clicked button
    	button.setStyle("-fx-background-color: gray;"); // Change button appearance
    	 
    	// Check if the letter is in the word
    	if (game.guessLetter(letter)) {	
    		wordLabel.setText(game.getGuessedWord()); // Update the displayed word
    	} else {
    		drawNextPart(); // Draw the next part of the hangman
    	}
    	 
    	updateGuessedLetters(letter); // Update the guessed letters display
    	checkGameState(); // Check if the game is over
    }
    
    
    /**
     * Updates the list of guessed letters displayed to the player.
     *
     * @param letter The letter that was just guessed
     */
    private void updateGuessedLetters(char letter) {
    	 String guessedLettersText = "";
    	    
    	 	// Build a comma-separated list of guessed letters
    	    for (Character guessedLetter : game.getGuessedLetters()) {
    	        guessedLettersText += guessedLetter + ", "; 
    	    }
    	    
    	    // Remove the trailing comma and space
    	    if (guessedLettersText.length() > 0) {
    	        guessedLettersText = guessedLettersText.substring(0, guessedLettersText.length() - 2);
    	    }

    	    Label guessedLettersLabel = new Label(guessedLettersText);
    	    guessedLettersLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;"); 
    	    guessedLettersBox.getChildren().clear(); 
    	    guessedLettersBox.getChildren().add(guessedLettersLabel); 
    	}
    
    
    /**
     * Clears the drawing canvas.
     */
    private void clearCanvas() {
    	canv.getGraphicsContext2D().clearRect(0, 0, canv.getWidth(), canv.getHeight());
    }
    
    
    /**
     * Draws the next part of the hangman figure based on the number of incorrect guesses.
     */
    private void drawNextPart() {
    	int incorrectGuesses = game.getWrongGuesses();
    	GraphicsContext gc = canv.getGraphicsContext2D();
    	
    	switch (incorrectGuesses) {
    		case 1:
    			gc.strokeLine(50, 300, 150, 300); // Base
                gc.strokeLine(100, 300, 100, 50);  // Vertical pole
                gc.strokeLine(100, 50, 200, 50);  // Horizontal pole
                gc.strokeLine(200, 50, 200, 70); // Rope
                break;
             
    		case 2:
    			gc.strokeOval(175, 70, 50, 50); // Head
                break;
              
    		case 3:
    			gc.strokeLine(200, 120, 200, 200); // Body
    			break;
    			
    		case 4:
    			gc.strokeLine(200, 140, 170, 170); // Left arm
    			break;
    			
    		case 5:
    			gc.strokeLine(200, 140, 230, 170); // Right arm
    			break;
    			
    		case 6:
    			gc.strokeLine(200, 200, 180, 250);// Left leg
    			break;
    			
    		case 7:
    			gc.strokeLine(200, 200, 220, 250); // Right leg
    			break;
    			
    		default:
    			System.out.println("All parts drawn, game over!");
    			break;	
    	}
    }
    
    
    /**
     * Checks the game state and displays an appropriate message if the game is over.
     */
    private void checkGameState() {
    	if (game.isWordGuessed()) {
    		showGameOverMessage("You won! The word was: " + game.getWord());
    		disableAllLetterButtons();
    	} else if (game.isGameOver()) {
    		showGameOverMessage("Game over! The word was: " + game.getWord());
    		disableAllLetterButtons();
    	}
    }
    
    
    /**
     * Disables all letter buttons in the grid.
     */
    private void disableAllLetterButtons() {
        for (Node node : letterGrid.getChildren()) {
            if (node instanceof Button) {
                Button letterButton = (Button) node;
                letterButton.setDisable(true);
                letterButton.setStyle("-fx-background-color: gray;");
            }
        }
    }
    
    
    /**
     * Displays a game-over message in an alert dialog.
     *
     * @param message The message to display
     */
    private void showGameOverMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
