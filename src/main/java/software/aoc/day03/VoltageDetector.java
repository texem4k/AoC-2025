package software.aoc.day03;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class VoltageDetector {

    private final List<Long> maxPairs;


    public static VoltageDetector create() {
        return new VoltageDetector();
    }


    private VoltageDetector() {
        maxPairs = new ArrayList<>();
    }



    public VoltageDetector executeA(Stream<String> s) {
        s.map(this::toList)
                .mapToInt(this::generatePair)
                .forEach(this::addToList);
        return this;

    }

    public VoltageDetector executeB(Stream<String> s) {
        s.map(this::toList)
                .mapToLong(this::generateSequenceDigits)
                .forEach(this::addToList);
        return this;
    }


    private int generatePair(List<Integer> list) {
        return createPair(list).mapToInt(Pair::toInt)
                .max()
                .orElse(-1);
    }


    private long generateSequenceDigits(List<Integer> list) {
        Deque<Integer> queue = new ArrayDeque<>();
        int n = list.size() - 12;
        for (Integer integer : list) {
            while (!queue.isEmpty() && n > 0 && integer > queue.getLast()) {
                queue.removeLast();
                n--;
            }
            queue.add(integer);
        }

        while (n > 0) {
            queue.removeLast();
            n--;
        }

        long result = 0;
        for (int d : queue) {
            result = result * 10 + d;
        }
        return result;
    }


    private List<Integer> toList(String s){
        return s.chars().map(c -> c - '0').boxed().toList();
    }



    public long count(){
        return maxPairs.stream().mapToLong(Long::longValue).sum();
    }


    private void addToList(long e) {
        maxPairs.add(e);
    }

    private Stream<Pair> createPair(List<Integer> list) {
        return IntStream.range(0, list.size() - 1).mapToObj(i -> {
            int first = list.get(i);
            int second = list.subList(i + 1, list.size())
                    .stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(-1);
            return new Pair(first, second);

        });
    }
}
