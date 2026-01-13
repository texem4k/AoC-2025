package software.aoc.day05;

import java.util.stream.LongStream;

public record Range(long min, long max) {

    public LongStream getRange() {
        return LongStream.rangeClosed(min, max);
    }

    public long min(){
        return min;
    }

    public long max(){
        return max;
    }
}
