package software.aoc.day09;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class AreaResolver {

    private List<Point> positions;
    public static AreaResolver create() {
        return new AreaResolver();
    }

    private AreaResolver() {
        positions = new ArrayList<>();
    }



    public long findLargestAreaA(List<String> input) {
        positions = createPointList(input);
        List<Rectangle> r= allRectangles();
        return r.stream().sorted(Comparator.comparingLong(Rectangle::areaInclusive)).toList().get(r.size()-1).areaInclusive();
    }



    private Point createPoint(String s) {
        String[] split = formatString(s);
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private String[] formatString(String s) {
        return s.trim().split(",");
    }

    private List<Rectangle> allRectangles() {
        return IntStream.range(0, positions.size())
                .boxed()
                .flatMap(i ->
                        IntStream.range(i + 1, positions.size())
                                .mapToObj(j -> Rectangle.of(positions.get(i), positions.get(j)))
                ).toList();
    }


    //-------------------- Parte 2 --------------------



    public long findLargestAreaB(List<String> input) {
        positions = createPointList(input);

        return allRectangles().stream()
                .filter(this::isValidRectangle)
                .mapToLong(Rectangle::areaInclusive)
                .max()
                .orElse(0L);
    }



    private List<Point> createPointList(List<String> input) {
        return input.stream().map(this::createPoint).toList();
    }


    private boolean isPointInPolygon(double x, double y,boolean inside) {
        for (int i = 0, j = positions.size() - 1; i < positions.size(); j = i++) {
            if (((positions.get(i).y() > y) != (positions.get(j).y() > y)) &&
                    (x < (positions.get(j).x() - positions.get(i).x()) * (y - positions.get(i).y()) / (double)(positions.get(j).y() - positions.get(i).y()) + positions.get(i).x())) {inside = !inside;}
        }
        return inside;
    }



    private boolean intervalsOverlapOpen(long a1, long a2, long b1, long b2) {
        return Math.max(a1, b1) < Math.min(a2, b2);
    }



    private boolean boundaryCrossesInterior(Rectangle b) {
        for (int i = 0; i < positions.size(); i++) {
            Point p1 = positions.get(i);
            Point p2 = positions.get((i + 1) % positions.size());

            if (p1.x() == p2.x()) {                     // vertical
                if (p1.x() <= b.minX() || p1.x() >= b.maxX()) continue;
                if (intervalsOverlapOpen(
                        Math.min(p1.y(), p2.y()),
                        Math.max(p1.y(), p2.y()),
                        b.minY(),
                        b.maxY())) return true;

            } else {                                   // horizontal
                if (p1.y() <= b.minY() || p1.y() >= b.maxY()) continue;
                if (intervalsOverlapOpen(
                        Math.min(p1.x(), p2.x()),
                        Math.max(p1.x(), p2.x()),
                        b.minX(),
                        b.maxX())) return true;
            }
        }
        return false;
    }





    private boolean isValidRectangle(Rectangle b) {
        if (boundaryCrossesInterior(b)) return false;
        return isPointInPolygon(b.centerX(), b.centerY(), false);
    }




}
