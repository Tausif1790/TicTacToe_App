package models;

import java.sql.SQLOutput;
import java.util.Scanner;

public class HumanPlayer extends Player{
    Scanner sc = new Scanner(System.in);

    public HumanPlayer(String ID, String name, Character symbol) {
        super(ID, name, symbol, PlayerType.HUMAN);
    }

    // we will wait for player input
    @Override
    public Move makeMove(Board board) {
        System.out.println(this.name + "'s move. Please make your move.");

        // We'll be waiting for player input
        System.out.println("Enter row: ");
        int r = sc.nextInt();

        System.out.println("Enter column: ");
        int c = sc.nextInt();

        // create the move - cell (dummy cell/move, will check if move is valid or not in other part
        Cell cell = new Cell(r, c);
        return new Move(cell, this);
    }
}
