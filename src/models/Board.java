package models;

import java.util.ArrayList;
import java.util.List;

// The Board class represents the game board. It is a grid composed of cells,
// where each cell can be empty or occupied by a player's symbol.
public class Board {
    private int dimension;
    private List<List<Cell>> grid;

    // Constructor initializes the Board object with the specified dimension.
    // It also initializes the grid, which is a 2D list representing the board's rows and cells.
    public Board(int dimension) {
        this.dimension = dimension;
        grid = new ArrayList<>(); // Initialize the grid as an empty list

        // Populate the grid with empty cells based on the given dimension.
        for (int i = 0; i < dimension; i++) {
            grid.add(new ArrayList<>()); // Add a new row to the grid
            // [[]] -> [[cell1, cell2, cell3],[]] -> [[cell1, cell2, cell3],[cell4, cell5, cell6], []]
            for (int j = 0; j < dimension; j++) {
                grid.get(i).add(new Cell(i, j)); // Add a new cell to the row
                //[[cell1, cell2, cell3]] -> [[cell1, cell2, cell3] , [cell4, cell5, cell6]]
            }
        }
    }

    // Getter for the grid of cells.
    public List<List<Cell>> getGrid() {
        return grid;
    }

    // Setter for the grid of cells.
    public void setGrid(List<List<Cell>> grid) {
        this.grid = grid;
    }

    // Getter for the board's dimension.
    public int getDimension() {
        return dimension;
    }

    // Setter for the board's dimension.
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    // Displays the current state of the board by iterating through each cell
    // and printing its content to the console.
    public void display() {
        for (List<Cell> row : grid) {
            for (Cell cell : row) {
                cell.display();
            }
            System.out.println(); // Move to the next line after displaying a row.
        }
    }
}
