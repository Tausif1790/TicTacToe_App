package strategies;

import models.Board;
import models.Cell;
import models.Game;

public interface BotPlayingStrategy {
    // pass Board/Game
    public Cell makeMove(Board board);
}
