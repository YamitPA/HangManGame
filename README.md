# Hangman Game in JavaFX

## Description
This is a JavaFX application implementing the classic "Hangman" game. The program randomly selects a word from a predefined word list, and the user attempts to guess it by selecting letters. The game operates as follows:

- In each turn, the user guesses a single letter.
- If the letter appears in the word, it is revealed in its correct positions (e.g., for "polymorphism", after guessing "m" and "o": `_ o _ _ m o _ _ _ _ _ m`).
- If the guess is incorrect, a part of the hangman figure is drawn.
- The game continues until the user guesses the word or the hangman figure is completed (7 incorrect guesses).
- The letters guessed so far are displayed.
- The game ends with a win (word fully guessed) or a loss (hangman completed).

## Graphical Interface Features
- **Labels**: Display the current state of the word and the guessed letters.
- **Canvas**: Used to draw the hangman figure graphically.
- **Letter Buttons**: A series of buttons for selecting letters (a-z).
- **"Start New Game" Button**: Allows the user to start a new game with a new word.

## Project Structure
The solution is designed following Object-Oriented Programming (OOP) principles:
- `HangManGame`: Manages the game logic (word selection, guess handling, and game state).
- `HangManController`: Controls user interactions with the graphical interface.
- `WordLoader`: Utility class to load words from a text file.
- `HangMan`: The application entry point, loading the FXML interface.
