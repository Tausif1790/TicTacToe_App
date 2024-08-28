package strategies;

import models.Board;
import models.Move;

import java.util.HashMap;

public class RowWinningStrategy implements WinningStrategy {

    // HashMap to keep track of symbol counts in each row.
    // Key: Row index, Value: HashMap of symbol and its count in that row.
    // HashMap<RowIndex, HashMap<Symbol, SymbolCount>>
    HashMap<Integer, HashMap<Character, Integer>> rowCountMap;

    public RowWinningStrategy() {
        this.rowCountMap = new HashMap<>();  // Initialize the row count map.
    }

    @Override
    public boolean checkWinner(Board board, Move move) {

        // Get the row index of the cell where the move was made.
        int r = move.getCell().getRow();

        // Get the symbol of the player who made the move.
        Character symbol = move.getPlayer().getSymbol();

        // If the row doesn't exist in the map, add it with an empty symbol count map.
        rowCountMap.putIfAbsent(r, new HashMap<>());

        // Retrieve the symbol count map for the specific row.
        HashMap<Character, Integer> countMap = rowCountMap.get(r);  // => inner map of key r

        // If the symbol isn't in the count map, add it with an initial count of 0.
        countMap.putIfAbsent(symbol, 0);

        // Increment the count of the symbol in the corresponding row.
        countMap.put(symbol, countMap.get(symbol) + 1);

        // Check if the current symbol has filled the entire row, thereby winning the game.
        if(countMap.get(symbol) == board.getDimension()){
            return true;  // Return true if the symbol fills the row completely.
        }
        return false;  // Return false if there's no win condition met yet.
    }

    @Override
    public void handleUndo(Move move) {
        // Get the row index of the cell where the undo is happening.
        int r = move.getCell().getRow();

        // Get the symbol of the player who made the move being undone.
        Character symbol = move.getPlayer().getSymbol();

        // Retrieve the symbol count map for the specific row.
        HashMap<Character, Integer> countMap = rowCountMap.get(r);

        // Decrement the count of the symbol in the corresponding row due to the undo operation.
        countMap.put(symbol, countMap.get(symbol) - 1);
    }
}
