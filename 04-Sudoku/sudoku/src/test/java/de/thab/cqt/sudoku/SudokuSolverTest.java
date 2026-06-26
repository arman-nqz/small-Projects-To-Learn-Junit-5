package de.thab.cqt.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuSolverTest {

    private final SudokuSolver sudokuSolver = new SudokuSolver();

    @Test
    // Tests that a board with 9 rows and 9 columns is accepted as valid.
    void isValidSudokuBoardReturnsTrueForNineByNineBoard() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];

        assertTrue(sudokuSolver.isValidSudokuBoard(board));
    }

    @Test
    // Tests that a board with fewer than 9 rows is rejected as invalid.
    void isValidSudokuBoardReturnsFalseForWrongNumberOfRows() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS - 1][SudokuSolver.NUMBER_COLUMNS];

        assertFalse(sudokuSolver.isValidSudokuBoard(board));
    }

    @Test
    // Tests that a board row with fewer than 9 columns is rejected as invalid.
    void isValidSudokuBoardReturnsFalseForWrongNumberOfColumns() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];
        board[0] = new int[SudokuSolver.NUMBER_COLUMNS - 1];

        assertFalse(sudokuSolver.isValidSudokuBoard(board));
    }

    @Test
    // Tests that an empty 9x9 board is solvable because empty fields do not count as duplicates.
    void isSolvableReturnsTrueForEmptyBoard() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];

        assertTrue(sudokuSolver.isSolvable(board));
    }

    @Test
    // Tests that two equal numbers in the same row make the board unsolvable.
    void isSolvableReturnsFalseForDuplicateNumberInSameRow() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];
        board[0][0] = 5;
        board[1][0] = 5;

        assertFalse(sudokuSolver.isSolvable(board));
    }

    @Test
    // Tests that two equal numbers in the same column make the board unsolvable.
    void isSolvableReturnsFalseForDuplicateNumberInSameColumn() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];
        board[0][0] = 7;
        board[0][1] = 7;

        assertFalse(sudokuSolver.isSolvable(board));
    }

    @Test
    // Tests that two equal numbers in the same 3x3 region make the board unsolvable.
    void isSolvableReturnsFalseForDuplicateNumberInSameRegion() {
        int[][] board = new int[SudokuSolver.NUMBER_ROWS][SudokuSolver.NUMBER_COLUMNS];
        board[0][0] = 9;
        board[2][2] = 9;

        assertFalse(sudokuSolver.isSolvable(board));
    }

    @Test
    // Tests that a partially filled board without row, column, or region duplicates is solvable.
    void isSolvableReturnsTrueForValidPartiallyFilledBoard() {
        int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        assertTrue(sudokuSolver.isSolvable(board));
    }

    @Test
    // Tests that a board is not solved when at least one field is still empty.
    void isSolvedReturnsFalseWhenAnyFieldIsEmpty() {
        int[][] board = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 0, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        assertFalse(sudokuSolver.isSolved(board));
    }

    @Test
    // Tests that a board is solved when every field contains a non-zero value.
    void isSolvedReturnsTrueWhenEveryFieldIsFilled() {
        int[][] board = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        assertTrue(sudokuSolver.isSolved(board));
    }
}
