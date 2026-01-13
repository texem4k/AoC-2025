package software.aoc.day10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class JoltageSolver {


    public List<Button> buttons;
    public List<Integer> target;
    public JoltageSolver(Machine machine) {
        buttons = machine.buttons;
        target = machine.joltageRequirements;

    }

    public long solve() {
        Map<List<Integer>, Map<List<Integer>, Integer>> parityMaps =
                buildParityMaps(target.size());
        return findMinPresses(target, parityMaps, new HashMap<>());
    }

    private Map<List<Integer>, Map<List<Integer>, Integer>> buildParityMaps(int nCounters) {
        return IntStream.range(0, 1 << buttons.size())
                .mapToObj(mask -> buildPatternForMask(mask, nCounters))
                .collect(Collectors.toMap(
                        PatternInfo::parity,
                        pi -> {
                            Map<List<Integer>, Integer> inner = new HashMap<>();
                            inner.put(pi.result(), pi.presses());
                            return inner;
                        },
                        (m1, m2) -> {
                            m2.forEach((pattern, presses) ->
                                    m1.merge(pattern, presses, Math::min));
                            return m1;
                        }
                ));
    }

    private PatternInfo buildPatternForMask(int mask, int nCounters) {
        List<Integer> result = new ArrayList<>(Collections.nCopies(nCounters, 0));
        int presses = 0;
        for (int j = 0; j < buttons.size(); j++) {
            if ((mask & (1 << j)) != 0) {
                presses++;
                for (int idx : buttons.get(j).alter()) {
                    result.set(idx, result.get(idx) + 1);
                }
            }
        }
        List<Integer> parity = parityOf(result);
        return new PatternInfo(List.copyOf(result), parity, presses);
    }

    private List<Integer> parityOf(List<Integer> v) {
        return v.stream()
                .map(x -> x & 1)
                .toList();
    }

    private long findMinPresses(List<Integer> current, Map<List<Integer>,
                                        Map<List<Integer>, Integer>> parityMaps,
                                Map<List<Integer>, Long> cache) {
        if (cache.get(List.copyOf(current)) != null) return cache.get( List.copyOf(current));
        if (allZero(current)) {
            cache.put(List.copyOf(current), 0L);
            return 0L;
        }
        if (parityMaps.get(parityOf(current)) == null) {
            cache.put( List.copyOf(current), Long.MAX_VALUE);
            return Long.MAX_VALUE;
        }
        long best = parityMaps.get(parityOf(current)).entrySet().stream()
                .filter(e -> isComponentwiseLessOrEqual(e.getKey(), current))
                .mapToLong(e -> {
                    List<Integer> pattern = e.getKey();
                    int presses = e.getValue();
                    List<Integer> next = computeNext(current, pattern);
                    long sub = findMinPresses(next, parityMaps, cache);
                    return sub == Long.MAX_VALUE ? Long.MAX_VALUE : presses + 2L * sub;
                })
                .min()
                .orElse(Long.MAX_VALUE);
        cache.put(List.copyOf(current), best);
        return best;
    }

    private boolean allZero(List<Integer> v) {
        return v.stream().allMatch(x -> x == 0);
    }

    private boolean isComponentwiseLessOrEqual(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> computeNext(List<Integer> current, List<Integer> pattern) {
        List<Integer> next = new ArrayList<>(current.size());
        for (int i = 0; i < current.size(); i++) {
            next.add((current.get(i) - pattern.get(i)) / 2);
        }
        return next;
    }

    private record PatternInfo(List<Integer> result, List<Integer> parity, int presses) {
    }


}
