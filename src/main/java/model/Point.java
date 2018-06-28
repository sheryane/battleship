package model;

public class Point {

    private final Integer x;
    private final Integer y;

    public Point(Integer x, Integer y) {
        if(x == null || y == null) {
            throw new IllegalArgumentException("Coordinates must be provided.");
        }
        this.x = x;
        this.y = y;
    }

//    public Point(Point point) {
//        if(point == null || point.getX() == null || point.getY() == null) {
//            throw new IllegalArgumentException("Point must be provided.");
//        }
//        this.x = point.getX();
//        this.y = point.getY();
//    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
