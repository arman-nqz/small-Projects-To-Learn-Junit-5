package de.thab.cqt.sudoku;

import java.util.Random;

public class SudokuCreator {

    private final SudokuSolver sudokuSolver;
    private final Random random = new Random();

    public SudokuCreator(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }

    public int[][] createNewBoard(int numberOfPrefilledFields) {
        if (numberOfPrefilledFields < 1 || numberOfPrefilledFields > 10) {
            throw new IllegalArgumentException("Invalid number of prefilled fields");
        }
        int[][] board = new int[9][9];
        for (int fieldId = 0; fieldId < numberOfPrefilledFields; fieldId++) {
            int fieldColumn, fieldRow;
            do {
                fieldColumn = random.nextInt(SudokuSolver.NUMBER_COLUMNS);
                fieldRow = random.nextInt(SudokuSolver.NUMBER_ROWS);
            } while (board[fieldRow][fieldColumn] != 0);
            do {
                board[fieldRow][fieldColumn] = random.nextInt(SudokuSolver.MIN_SUDOKU_NUMBER,
                        SudokuSolver.MAX_SUDOKU_NUMBER+1);
            } while (!sudokuSolver.isSolvable(board));
        }
        return board;
    }
}
