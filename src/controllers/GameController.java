package controllers;

import models.Game;
import models.GameState;
import models.Player;
import strategies.WinningStrategy;

import java.util.List;

// GameController is the main orchestrator of the game. It manages the initialization,
// state checking, move handling, and other essential game functions.
public class GameController {

    // Starts a new game by constructing a `Game` object with the specified dimension,
    // players, and winning strategies. Throws an exception if game creation fails.
    public Game startGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws Exception {
        return Game
                .getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    // Returns the current state of the game (e.g., in-progress, won, or draw).
    public GameState checkGameState(Game game) {
        return game.getGameState();
    }

    // Retrieves the winner of the game, if there is one.
    public Player getWinner(Game game) {
        return game.getWinner();
    }

    // Displays the current state of the game board to the console.
    public void display(Game game) {
        System.out.println("The board view:");
        game.displayBoard();
    }

    // Prompts the current player to make a move. In a real application, this would
    // involve parsing an HTTP request to get the move details and then updating the game state.
    public void makeMove(Game game) {
        // Simulate parsing an HTTP request to get the player's move.
        game.makeMove();
        // Simulate creating an HTTP response to confirm the move.
    }

    // Undo move functionality
    public void undo(Game game){
        game.undo(game);
    }

    public Player getCurrentPlayer(Game game){
        return game.getCurrentPlayer();
    }
}
