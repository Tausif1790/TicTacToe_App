package strategies;

import models.Board;
import models.Cell;
import models.CellState;
import models.Game;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    // Bot place its move where ever he finds first empty cell
    @Override
    public Cell makeMove(Board board) {
        for(List<Cell> row : board.getGrid()){
            for(Cell cell : row){
                if(cell.getCellState().equals(CellState.EMPTY)){
                    return  cell;
                }
            }
        }
        return null;
    }
}
