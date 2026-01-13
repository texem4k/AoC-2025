package software.aoc.day08;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConnectionManager {

    private final List<Circuit> circuits;

    public static ConnectionManager create(){
        return new ConnectionManager();
    }

    private ConnectionManager(){
        circuits = new ArrayList<>();
    }


    public int threeLargestCircuits(List<String> input){
        return operateA(input);
    }

    public long lastJunctionBox(List<String> input){
        return operateB(input);
    }

    private int operateA(List<String> input) {
        List<JunctionBox> junctionBoxes = generateJBList(input);
        List<PairJunctionBox> pair = sortPairsForA(generatePairs(junctionBoxes));
        comparePairs(pair, junctionBoxes);

        return circuits.stream()
                .map(c -> c.junctionBoxSet().size())
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(num -> num)
                .reduce(1, (a, b) -> a * b);
    }



    private long operateB(List<String> input) {
        List<JunctionBox> junctionBoxes = generateJBList(input);
        List<PairJunctionBox> pair = sortPairsForB(generatePairs(junctionBoxes));
        return comparePairs(pair, junctionBoxes);
    }


    private long comparePairs(List<PairJunctionBox> pairs, List<JunctionBox> jbs) {
        jbs.forEach(box -> circuits.add(new Circuit(Set.of(box))));
        PairJunctionBox actual = null;

        for (PairJunctionBox pair : pairs) {
            Circuit c1 = searchCircuit(pair.A());
            assert c1 != null;
            if (c1.junctionBoxSet().contains(pair.B())) {
                continue;
            }

            Circuit c2 = searchCircuit(pair.B());

            circuits.remove(c1);
            circuits.remove(c2);
            assert c2 != null;
            circuits.add(new Circuit(
                    Stream.concat(
                            c1.junctionBoxSet().stream(),
                            c2.junctionBoxSet().stream()
                    ).collect(Collectors.toSet())));
            actual = (pair);
        }
        assert actual != null;
        return actual.A().x()*actual.B().x();
    }

    private List<PairJunctionBox> sortPairsForA(List<PairJunctionBox> pairs) {
        return pairs.stream().sorted(Comparator.comparingLong(PairJunctionBox::euclideanDistance)).limit(1000).toList();
    }

    private List<PairJunctionBox> sortPairsForB(List<PairJunctionBox> pairs) {
        return pairs.stream().sorted(Comparator.comparingLong(PairJunctionBox::euclideanDistance)).limit(Long.MAX_VALUE).toList();
    }


    private List<PairJunctionBox> generatePairs(List<JunctionBox> jbs) {
        List<PairJunctionBox> pairs = new ArrayList<>();
        IntStream.range(0, jbs.size() - 1).forEach(i -> IntStream.range(i + 1, jbs.size()).forEach(j -> {
            PairJunctionBox pair = new PairJunctionBox(jbs.get(i), jbs.get(j));
            pairs.add(pair);

        }));
        return pairs;
    }

    private Circuit searchCircuit(JunctionBox b) {
        for(Circuit circuit : circuits) {
            if(circuit.junctionBoxSet().contains(b)) {
                return circuit;
            }
        }
        return null;
    }

    private JunctionBox createJunctionBox(String x) {
        String [] aux = x.trim().split(",");
        return new JunctionBox(Long.parseLong(aux[0]), Long.parseLong(aux[1]), Long.parseLong(aux[2]));
    }

    private List<JunctionBox> generateJBList(List<String> input) {
        return input.stream()
                .map(this::createJunctionBox)
                .toList();
    }


}
