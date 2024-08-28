package models;

import strategies.BotPlayingStrategy;

// The BotPlayer class represents a non-human player in the game. The bot's moves are
// determined by a strategy that is based on the difficulty level set during its initialization.
public class BotPlayer extends Player {
    private BotPlayingDifficulty botPlayingDifficulty;
    private BotPlayingStrategy botPlayingStrategy;

    // Constructor initializes the bot player with an ID, name, symbol, and difficulty level.
    // The bot's playing strategy is selected based on the difficulty level.
    public BotPlayer(String ID, String name, Character symbol, BotPlayingDifficulty botPlayingDifficulty) {
        super(ID, name, symbol, PlayerType.BOT);
        this.botPlayingDifficulty = botPlayingDifficulty;
        this.botPlayingStrategy = factories.BotPlayingStrategyFactory.getBotPlayingStrategy(botPlayingDifficulty);
    }

    // This method is responsible for making the bot's move. The bot's move is calculated
    // based on its strategy and the current state of the board.
    @Override
    public Move makeMove(Board board) {
        System.out.println(this.name + "'s move. Bot player move calculating.");
        // We write the code to make the next move - Cell/Move - Call a botPlayingStrategy
        Cell cell = botPlayingStrategy.makeMove(board);
        return new Move(cell, this);
    }
}
