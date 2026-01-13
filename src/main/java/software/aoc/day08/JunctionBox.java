package software.aoc.day08;

public record JunctionBox(long x, long y, long z) {

    @Override
    public long x() {
        return x;
    }
    @Override
    public long y() {
        return y;
    }
    @Override
    public long z() {
        return z;
    }

}
