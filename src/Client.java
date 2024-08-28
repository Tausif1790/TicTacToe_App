import controllers.GameController;
import models.*;
import strategies.ColumnWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// This class acts as the entry point of the application, simulating the user interaction part
// (analogous to the frontend in a full-stack application).
public class Client {
    public static void main(String[] args) {
        // The GameController class is stateless and is designed to manage the lifecycle of a game.
        // Since it contains all the necessary methods for game management, only one instance of
        // GameController is required (can use Singleton DP), regardless of the number of games being managed. When you
        // invoke startGame() on GameController, it constructs and returns a Game object, abstracting
        // the complexities of game initialization.
        GameController gameController = new GameController();
        Scanner sc = new Scanner(System.in);        // to take user input
        Random random = new Random();               // to select random values

        try {
            //---------------------- Creating Players
            // Ideally, player information would be fetched from a PlayerController or input by the user.
            // Here, we simulate the frontend part where player data is hardcoded.
            Player player1 = new HumanPlayer("11", "Akash", 'X');
            Player player2 = new BotPlayer("12", "Botty", 'O', BotPlayingDifficulty.MEDIUM);
            Player player3 = new HumanPlayer("13", "Tausif", 'T');

            // Create a list of players for the game. ( currently 2 players are playing "Akash" and Bot -> "Botty")
            List<Player> players = new ArrayList<>();
            players.add(player1);       // player 1 start first
            players.add(player2);

            //---------------------- Adding all Winning Strategies ( Using Strategy Pattern )
            // Instantiate and collect all the winning strategies that will be used to determine
            // the winner of the game.
            List<WinningStrategy> winningStrategies = new ArrayList<>();
            winningStrategies.add(new RowWinningStrategy());
            winningStrategies.add(new ColumnWinningStrategy());
            // winningStrategies.add(new DiagonalWinningStrategy());

            //---------------------- Starting Game
            // Think of the Game object as representing a unique game session or game ID if using APIs.
            // The Game object is initialized with a specific board dimension, a list of players, and
            // a set of winning strategies. These parameters are sufficient to start the game.
            Game game = gameController.startGame(3, players, winningStrategies);

            //---------------------- Middle Game Logic
            // In this loop, every function call can be visualized as making an API call in a
            // real-world scenario (e.g., display() might represent a GET request to retrieve
            // the current board state, and makeMove() might represent a POST request to
            // register a player's move).
            while (gameController.checkGameState(game).equals(GameState.IN_PROGRESS)) {

                // Display the current state of the game board after each move.
                gameController.display(game); // This simulates displaying the current board state.
                gameController.makeMove(game); // This simulates a player making their move.

                //------------------- Undo feature (For human player only)
                if(gameController.getCurrentPlayer(game).getPlayerType().equals(PlayerType.HUMAN)){
                    gameController.display(game);
                    System.out.println("Do you want to undo? Press 1 to confirm and 2 to continue.");
                    int undo = sc.nextInt();

                    if(undo == 1){
                        gameController.undo(game);
                        continue;
                    }
                }

                // Check the game state after each move to determine if there's a winner or a draw.
                if (gameController.checkGameState(game).equals(GameState.GAME_WON)) {
                    System.out.println(gameController.getWinner(game).getName() + " wins the game!");
                    gameController.display(game);
                    break;
                } else if (gameController.checkGameState(game).equals(GameState.DRAW)) {
                    System.out.println("The game is a draw!");
                    gameController.display(game);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}

/*
Note: If this application were designed to use HTTP requests, each game would be represented
by a game_id. After creating a game, it would be stored in a database, and when a user plays
a game, the game_id would be used to fetch the relevant game data from the database.
*/