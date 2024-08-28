package strategies;

import models.Board;
import models.Cell;
import models.CellState;

import java.util.Random;

public class MediumBotPlayingStrategy implements BotPlayingStrategy {

    // Bot places its move at a random and empty cell on the board.
    @Override
    public Cell makeMove(Board board) {
        Random random = new Random();

        // Loop until a valid (empty) cell is found.
        while (true) {
            int randomRow = random.nextInt(board.getDimension());  // Generate a random row index within the board dimensions.
            int randomCol = random.nextInt(board.getDimension());  // Generate a random column index within the board dimensions.
            // System.out.println("randomRow: " + randomRow + ", randomCol: " + randomCol);

            // Check if the randomly selected cell is empty.
            if (board.getGrid().get(randomRow).get(randomCol).getCellState().equals(CellState.EMPTY)) {
                return board.getGrid().get(randomRow).get(randomCol);  // Return the empty cell where the bot will place its move.
            }
        }
    }
}
