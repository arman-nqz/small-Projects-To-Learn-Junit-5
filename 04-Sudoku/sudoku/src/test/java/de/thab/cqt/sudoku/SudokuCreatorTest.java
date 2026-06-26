package de.thab.cqt.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SudokuCreatorTest {

    @Mock
    private SudokuSolver sudokuSolver;

    private SudokuCreator sudokuCreator;

    @BeforeEach
    void setUp() {
        sudokuCreator = new SudokuCreator(sudokuSolver);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11})
    // Tests that invalid prefilled field counts are rejected before creating a board.
    void createNewBoardThrowsExceptionForInvalidNumberOfPrefilledFields(int numberOfPrefilledFields) {
        assertThrows(IllegalArgumentException.class,
                () -> sudokuCreator.createNewBoard(numberOfPrefilledFields));

        verify(sudokuSolver, never()).isSolvable(any());
    }

    @Test
    // Tests that a new board has the expected dimensions and number of prefilled fields.
    void createNewBoardReturnsNineByNineBoardWithRequestedNumberOfPrefilledFields() {
        when(sudokuSolver.isSolvable(any())).thenReturn(true);

        int[][] board = sudokuCreator.createNewBoard(5);

        assertEquals(SudokuSolver.NUMBER_ROWS, board.length);
        for (int[] row : board) {
            assertEquals(SudokuSolver.NUMBER_COLUMNS, row.length);
        }
        assertEquals(5, countFilledFields(board));
        verify(sudokuSolver, times(5)).isSolvable(board);
    }

    @Test
    // Tests that generated field values are always inside the Sudoku value range.
    void createNewBoardFillsFieldsWithValidSudokuNumbers() {
        when(sudokuSolver.isSolvable(any())).thenReturn(true);

        int[][] board = sudokuCreator.createNewBoard(10);

        for (int[] row : board) {
            for (int fieldValue : row) {
                assertTrue(fieldValue == 0
                        || fieldValue >= SudokuSolver.MIN_SUDOKU_NUMBER
                        && fieldValue <= SudokuSolver.MAX_SUDOKU_NUMBER);
            }
        }
    }

    @Test
    // Tests that the creator keeps trying values until the solver accepts the board.
    void createNewBoardRetriesUntilBoardIsSolvable() {
        when(sudokuSolver.isSolvable(any())).thenReturn(false, true);

        int[][] board = sudokuCreator.createNewBoard(1);

        assertEquals(1, countFilledFields(board));
        verify(sudokuSolver, times(2)).isSolvable(board);
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
