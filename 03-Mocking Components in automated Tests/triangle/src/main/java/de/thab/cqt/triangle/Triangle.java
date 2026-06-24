package de.thab.cqt.triangle;

public class Triangle {

    public static int numberOfEqualSides(int lengthA, int lengthB, int lengthC) {
        if (lengthA <= 0 || lengthB <= 0 || lengthC <= 0
                || lengthA + lengthB <= lengthC
                || lengthA + lengthC <= lengthB
                || lengthB + lengthC <= lengthA) {
            return -1;
        }

        if (lengthA == lengthB && lengthA == lengthC) {
            return 3;
        } else if (lengthA == lengthB || lengthA == lengthC || lengthB == lengthC) {
            return 2;
        } else {
            return 1;
        }
    }

}
