# Tic-Tac-Toe Game

A Java-based Tic-Tac-Toe game demonstrating key software engineering principles and design patterns.

## Features

1. **Multiple Players**: Supports both human players and bots.
2. **Bot Difficulty Levels**: Easy, Medium, Hard, with different strategies.
3. **Design Patterns**:
   - **Strategy Pattern**: For winning conditions and bot strategies.
   - **Factory Pattern**: To create bot strategies based on difficulty.
   - **Builder Pattern**: For flexible game object creation.
4. **Variable Board Size**: NxN grid, not limited to 3x3.
5. **Winning Strategies**: Row and column-based with easy extensibility.
6. **Undo Feature**: Allows players to revert their last move.

## Code Highlights

1. **GameController**: Manages game flow and state.
2. **Player Classes**: Supports human and bot players with shared abstractions.
3. **Board Management**: Dynamic grid representation with cell validation.
4. **Winning Strategy Implementations**: Customizable and extendable.

## Running the Game

1. **Clone the Repo**:
   ```sh
   git clone <repository-url>
