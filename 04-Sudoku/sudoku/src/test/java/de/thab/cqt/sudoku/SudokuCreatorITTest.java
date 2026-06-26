package de.thab.cqt.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuCreatorITTest {

    private final SudokuSolver sudokuSolver = new SudokuSolver();
    private final SudokuCreator sudokuCreator = new SudokuCreator(sudokuSolver);

    @Test
    // Tests that the creator produces a board accepted by the real solver.
    void createNewBoardReturnsValidSolvableBoardUsingRealSolver() {
        int[][] board = sudokuCreator.createNewBoard(5);

        assertTrue(sudokuSolver.isValidSudokuBoard(board));
        assertTrue(sudokuSolver.isSolvable(board));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    // Tests that the requested number of fields is filled when using the real solver.
    void createNewBoardReturnsBoardWithRequestedNumberOfPrefilledFields(int numberOfPrefilledFields) {
        int[][] board = sudokuCreator.createNewBoard(numberOfPrefilledFields);

        assertEquals(numberOfPrefilledFields, countFilledFields(board));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    // Tests that generated non-empty fields contain valid Sudoku values when using the real solver.
    void createNewBoardFillsFieldsWithValidSudokuNumbers(int numberOfPrefilledFields) {
        int[][] board = sudokuCreator.createNewBoard(numberOfPrefilledFields);

        for (int[] row : board) {
            for (int fieldValue : row) {
                assertTrue(fieldValue == 0
                        || fieldValue >= SudokuSolver.MIN_SUDOKU_NUMBER
                        && fieldValue <= SudokuSolver.MAX_SUDOKU_NUMBER);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11})
    // Tests that invalid prefilled field counts are rejected with real dependencies wired.
    void createNewBoardThrowsExceptionForInvalidNumberOfPrefilledFields(int numberOfPrefilledFields) {
        assertThrows(IllegalArgumentException.class,
                () -> sudokuCreator.createNewBoard(numberOfPrefilledFields));
    }

    private int countFilledFields(int[][] board) {
        int filledFields = 0;
        for (int[] row : board) {
            for (int fieldValue : row) {
                if (fieldValue != 0) {
                    filledFields++;
                }
            }
        }
        return filledFields;
    }
}
