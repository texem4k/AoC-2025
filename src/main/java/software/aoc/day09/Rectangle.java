package software.aoc.day09;


record Rectangle(long minX, long maxX, long minY, long maxY) {

    static Rectangle of(Point a, Point b) {
        return new Rectangle(
                Math.min(a.x(), b.x()),
                Math.max(a.x(), b.x()),
                Math.min(a.y(), b.y()),
                Math.max(a.y(), b.y())
        );
    }

    long areaInclusive() {
        return (maxX - minX + 1) * (maxY - minY + 1);
    }


    double centerX() {
        return (minX + maxX) / 2.0;
    }

    double centerY() {
        return (minY + maxY) / 2.0;
    }
}