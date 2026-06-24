package de.thab.cqt.triangle;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TriangleParameterizedTest {

    @CsvSource({
            "6,6,6,3",
            "6,6,7,2",
            "6,7,6,2",
            "7,6,6,2",
            "7,8,9,1",
            "0,3,4,-1",
            "1,0,4,-1",
            "0,1,4,-1"
    })
    @ParameterizedTest
    public void testTriangleParameterized(int lengthA, int lengthB, int lengthC, int expectedEqualSides) {
        assertEquals(expectedEqualSides, Triangle.numberOfEqualSides(lengthA, lengthB, lengthC));
    }
}
