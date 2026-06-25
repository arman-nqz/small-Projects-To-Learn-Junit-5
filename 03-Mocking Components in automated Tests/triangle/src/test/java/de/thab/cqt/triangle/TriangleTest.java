package de.thab.cqt.triangle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {

    @Test
    void testEquilateralTriangleReturns3(){
        Triangle triangle = new Triangle();

        assertEquals(3, triangle.numberOfEqualSides(6,6,6));
    }

    @Test
    void testIsoscelesTriangleReturns2() {
        Triangle triangle = new Triangle();

        assertEquals(2, triangle.numberOfEqualSides(6,6,7));
        assertEquals(2, triangle.numberOfEqualSides(6,7,6));
        assertEquals(2, triangle.numberOfEqualSides(7,6,6));
    }

    @Test
    void testUnequalTriangleReturns1() {
        Triangle triangle = new Triangle();

        assertEquals(1, triangle.numberOfEqualSides(7,8,9));
    }

    @Test
    void testInvalidInputs() {
        Triangle triangle = new Triangle();

        assertEquals(-1, triangle.numberOfEqualSides(0,3,4));
        assertEquals(-1, triangle.numberOfEqualSides(1,0,4));
        assertEquals(-1, triangle.numberOfEqualSides(4,5,0));
    }
}
