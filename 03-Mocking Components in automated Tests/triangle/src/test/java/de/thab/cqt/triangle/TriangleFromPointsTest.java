package de.thab.cqt.triangle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TriangleFromPointsTest {

    @Mock
    private Triangle triangle;

    @Test
    void testEqualCalculatedSidesReturnsMockResult() {
        TriangleFromPoints triangleFromPoints = new TriangleFromPoints(triangle);
        when(triangle.numberOfEqualSides(5, 5, 6)).thenReturn(2);

        int equalSides = triangleFromPoints.numberOfEqualSides(0, 0, 3, 4, 6, 0);

        assertEquals(2, equalSides);
        verify(triangle).numberOfEqualSides(5, 5, 6);
    }

    @Test
    void testDifferentCalculatedSidesReturnsMockResult() {
        TriangleFromPoints triangleFromPoints = new TriangleFromPoints(triangle);
        when(triangle.numberOfEqualSides(4,3,5)).thenReturn(1);

        int equalSides = triangleFromPoints.numberOfEqualSides(0, 0, 4, 0, 4, 3);


        assertEquals(1, equalSides);
        verify(triangle).numberOfEqualSides(4,3,5);
    }

    @Test
    void testCalculatedLengthsAreRoundedBeforeCallingTriangle() {
        TriangleFromPoints triangleFromPoints = new TriangleFromPoints(triangle);
        when(triangle.numberOfEqualSides(1, 1, 2)).thenReturn(-1);

        int equalSides = triangleFromPoints.numberOfEqualSides(0, 0, 1, 1, 2, 0);

        assertEquals(-1, equalSides);
        verify(triangle).numberOfEqualSides(1, 1, 2);
    }
}
