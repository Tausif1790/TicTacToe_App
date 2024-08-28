package models;

import strategies.WinningStrategy;
import validations.gameValidations.DimensionAndPlayerCount;

import java.util.ArrayList;
import java.util.List;

// The Game class encapsulates the entire game state and logic. It manages the board,
// players, moves, winning strategies, and the overall game state.
public class Game {
    private Board board;
    private List<Player> players;
    private int nextPlayerIndex;
    private Player winner;
    private List<Move> moves;
    private GameState gameState;
    private List<WinningStrategy> winningStrategies;

    // The Game constructor initializes the game with the specified board dimension,
    // list of players, and winning strategies. The game state is initially set to IN_PROGRESS.
    public Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.IN_PROGRESS;
        this.moves = new ArrayList<>();   // ==> added later
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    // Getter for the current game state (e.g., in-progress, won, or draw).
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public void displayBoard() {
        this.board.display();
    }

    public boolean validateMove(Move move){
        // Extract row and column indices from the move's cell for validation.
        int r = move.cell.getRow();
        int c = move.cell.getCol();

        // Check if the cell is within the valid grid boundaries.
        if(r < 0 || r > board.getDimension() - 1 || c < 0 || c > board.getDimension() - 1){
            System.out.println("Invalid Move, Please try again!");
            return false;  // Return false if the cell is out of bounds.
        }

        // Retrieve the actual cell from the board using the row and column indices.
        Cell cellToUpdate = board.getGrid().get(move.getCell().getRow()).get(move.getCell().getCol());

        // Update the move object with the actual cell to ensure accuracy.
        move.setCell(cellToUpdate);

        // Check if the cell is already occupied by a previous move.
        if(!move.getCell().getCellState().equals(CellState.EMPTY)){
            System.out.println("Cell already filled, Please try again!");
            return false;  // Return false if the cell is already filled.
        }

        return true;  // Return true if the move is valid.
    }

    // This method processes the player's move, updates the game state, and checks for a winner.
    public void makeMove() {
        // Identify the current player based on the index.
        Player currentPlayer = players.get(nextPlayerIndex);

        // Keep prompting the current player to make a move until a valid move is made.
        Move move;
        do{
            // Prompt the player to make a move on the board.
            move = currentPlayer.makeMove(board);
        }while(!validateMove(move));  // Repeat until a valid move is made.

        // Update the board with the player's move by setting the cell's state and associating it with the player.
        move.getCell().setCellState(CellState.FILLED);
        move.getCell().setPlayer(currentPlayer);

        // Record the move in the list of moves.
        moves.add(move);

        // Check if the move resulted in a win.
        if(checkWinner(move)){
            setGameState(GameState.GAME_WON);  // Update the game state to indicate a win.
            setWinner(currentPlayer);  // Set the current player as the winner.
        } else if(moves.size() == board.getDimension() * board.getDimension()){  // Check if the board is full.
            setGameState(GameState.DRAW);  // If the board is full and no winner, set the game state to draw.
            setWinner(null);  // No winner in case of a draw.
        }

        // Update the index to determine the next player in a round-robin fashion.
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
    }

    // This method reverts the last move made in the game, effectively performing an "undo" operation.
    public void undo(Game game){
        // Check if there are any moves to undo.
        if(moves.size() <= 0){
            System.out.println("No moves left to Undo");
            return;  // Exit if there are no moves to undo.
        }

        // Retrieve the last move made and remove it from the move list.
        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(lastMove);

        // Revert the board's state by clearing the last move's cell.
        lastMove.getCell().setCellState(CellState.EMPTY);
        lastMove.getCell().setPlayer(null);

        // Update any relevant data structures in the winning strategies to reflect the undo.
        for(WinningStrategy ws : winningStrategies){
            ws.handleUndo(lastMove);
        }

        // Reset the game state to "in progress" and clear the winner in case the game was previously won or drawn.
        setGameState(GameState.IN_PROGRESS);
        setWinner(null);

        // Update the next player index to the previous player, maintaining correct turn order.
        nextPlayerIndex--;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();  // Ensure the index is within valid range.
    }


    private boolean checkWinner(Move move) {
        //Different strategies - any one of those strategies are fulfilled we have a winner
        for(WinningStrategy ws : winningStrategies){
            if(ws.checkWinner(board, move)){
                return true;
            }
        }
        return false;
    }

    // next player pointer already updated, so to get current player we write this
    public Player getCurrentPlayer(){
        int index = ( nextPlayerIndex - 1 + players.size()) % players.size();
        return players.get(index);
    }

    // Method to display moves history
    public void gameSummary(Game game){
        System.out.println("<-------- Summary: -------->");
        for(Move currMove : game.getMoves()){
            System.out.println(currMove.getPlayer().getName() + " play '" + currMove.getPlayer().getSymbol()
                    + "' on cell: " + currMove.getCell().getRow() + ", " + currMove.getCell().getCol());
        }
    }

    // The GameBuilder class is used to construct a Game object with a fluent API.
    public static Builder getBuilder() {
        return new Builder();
    }

    //------------------------------------------------------------------------------------------//
    //----------- GameBuilder is an inner class designed to build the Game object step-by-step.
    // This pattern simplifies the creation of Game objects, especially when there are many
    // parameters or optional settings involved.
    public static class Builder {
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validateConfigParams() throws Exception {
            // Validate dimension and player count
            DimensionAndPlayerCount.validate(this.dimension, this.players);
            // Validating unique symbols - HashSet/Stream, distinct
            // Validate bot count - count number bots in playerlist, check if it is <= 1
        }

        // Builds the Game object with the specified parameters.
        public Game build() throws Exception {
            // we will also validate `this` parameter
            // Homework - write three validations
            validateConfigParams();
            return new Game(this.dimension, this.players, this.winningStrategies);
        }
    }
}