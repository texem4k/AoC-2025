package software.aoc.day02;

import java.util.stream.LongStream;


public record Range(long min, long max) {


    public LongStream stream() {
        return LongStream.rangeClosed(min, max);
    }
}
