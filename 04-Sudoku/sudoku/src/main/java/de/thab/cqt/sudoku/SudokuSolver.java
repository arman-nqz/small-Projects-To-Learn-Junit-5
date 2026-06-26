package de.thab.cqt.sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuSolver {

    public static final int NUMBER_COLUMNS = 9;
    public static final int NUMBER_ROWS = 9;
    public static final int MIN_SUDOKU_NUMBER = 1;
    public static final int MAX_SUDOKU_NUMBER = 9;
    public static final int SUDOKU_REGION_SIZE = 3;

    public boolean isValidSudokuBoard(int[][] board) {
        if (board.length != NUMBER_ROWS) {
            return false;
        }
        for (int[] row : board) {
            if (row.length != NUMBER_COLUMNS) {
                return false;
            }
        }
        return true;
    }

    public boolean isSolvable(int[][] board) {
        for (int i = 0; i < NUMBER_ROWS; i++) {
            if (!isSolvableRow(board, i)) {
                return false;
            }
        }
        for (int i = 0; i < NUMBER_COLUMNS; i++) {
            if (!isSolvableColumn(board[i])) {
                return false;
            }
        }
        for (int xPosition = 0; xPosition < NUMBER_COLUMNS; xPosition += SUDOKU_REGION_SIZE) {
            for (int yPosition = 0; yPosition < NUMBER_ROWS; yPosition += SUDOKU_REGION_SIZE) {
                if (!isSolvableRegion(board, xPosition, yPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSolved(int[][] board) {
        for (int xPosition = 0; xPosition < NUMBER_COLUMNS; xPosition++) {
            for (int yPosition = 0; yPosition < NUMBER_ROWS; yPosition++) {
                if (isFieldEmpty(board[xPosition][yPosition])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSolvableColumn(int[] column) {
        Set<Integer> visitedIntegers = new HashSet<>();
        for (int fieldEntry : column) {
            if (!isFieldEmpty(fieldEntry)) {
                if (visitedIntegers.contains(fieldEntry)) {
                    return false;
                }
                visitedIntegers.add(fieldEntry);
            }
        }
        return true;
    }

    private boolean isSolvableRow(int[][] board, int rowIndex) {
        int[] rowAsColumn = new int[NUMBER_COLUMNS];
        for (int i = 0; i < board[rowIndex].length; i++) {
            rowAsColumn[i] = board[i][rowIndex];
        }
        return isSolvableColumn(rowAsColumn);
    }

    private boolean isSolvableRegion(int[][] board, int regionTopLeftX, int regionTopLeftY) {
        int[] regionAsColumn = new int[SUDOKU_REGION_SIZE * SUDOKU_REGION_SIZE];
        int i = 0;
        for (int xPosition = regionTopLeftX; xPosition < regionTopLeftX + SUDOKU_REGION_SIZE; xPosition++) {
            for (int yPosition = regionTopLeftY; yPosition < regionTopLeftY + SUDOKU_REGION_SIZE; yPosition++) {
                regionAsColumn[i] = board[xPosition][yPosition];
                i++;
            }
        }
        return isSolvableColumn(regionAsColumn);
    }

    private boolean isFieldEmpty(int fieldEntry) {
        return fieldEntry == 0;
    }
}
