package de.thab.cqt.triangle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {

    @Test
    void testEquilateralTriangleReturns3(){
        assertEquals(3, Triangle.numberOfEqualSides(6,6,6));
    }

    @Test
    void testIsoscelesTriangleReturns2() {
        assertEquals(2, Triangle.numberOfEqualSides(6,6,7));
        assertEquals(2, Triangle.numberOfEqualSides(6,7,6));
        assertEquals(2, Triangle.numberOfEqualSides(7,6,6));
    }

    @Test
    void testUnequalTriangleReturns1() {
        assertEquals(1, Triangle.numberOfEqualSides(7,8,9));
    }

    @Test
    void testInvalidInputs() {
        assertEquals(-1, Triangle.numberOfEqualSides(0,3,4));
        assertEquals(-1, Triangle.numberOfEqualSides(1,0,4));
        assertEquals(-1, Triangle.numberOfEqualSides(4,5,0));
    }
}
