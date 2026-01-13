package software.aoc.day12;

public record Present(String baseShape) {

    public long area() {
        return baseShape.chars().filter(c -> c == '#').mapToLong(_ -> 1).sum();
    }
}
