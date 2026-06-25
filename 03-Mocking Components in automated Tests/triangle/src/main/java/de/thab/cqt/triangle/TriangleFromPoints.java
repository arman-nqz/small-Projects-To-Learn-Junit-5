package de.thab.cqt.triangle;

public class TriangleFromPoints {
    private final Triangle triangle;

    public TriangleFromPoints() {
        this(new Triangle());
    }

    public TriangleFromPoints(Triangle triangle) {
        this.triangle = triangle;
    }

    public Triangle getTriangle() {
        return triangle;
    }

    public int numberOfEqualSides(int x0, int y0, int x1, int y1, int x2, int y2) {
        int lengthA = (int) Math.round(Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2)));
        int lengthB = (int) Math.round(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
        int lengthC = (int) Math.round(Math.sqrt(Math.pow(x2 - x0, 2) + Math.pow(y2 - y0, 2)));

        return triangle.numberOfEqualSides(lengthA, lengthB, lengthC);
    }


}
