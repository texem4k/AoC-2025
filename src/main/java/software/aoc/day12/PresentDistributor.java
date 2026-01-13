package software.aoc.day12;

import java.util.Arrays;
import java.util.List;

public class PresentDistributor {


    public static PresentDistributor create(){
        return new PresentDistributor();
    }

    private PresentDistributor(){}

    public long execute(List<String> input){
        List<Present> presents = createPresent(input);
        List<Region> regions = setRegions(input);
        return regions.stream().mapToLong(r -> isValidRegion(r,presents)).sum();
    }
    

    private List<Region> setRegions(List<String> input){
        return input.stream().filter(s -> s.contains("x")).map(Region::new).toList();
    }


    private List<Present> createPresent(List<String> input) {
        return formatInput(input).stream().filter(s->!s.isEmpty()).map(Present::new).toList();
    }

    private List<String> formatInput(List<String> input) {
        return Arrays.stream(input.stream().filter(s->!s.contains("x")).
                        reduce((a, b)->a+b).
                        stream().toList()
                        .getFirst()
                        .split("\\d:"))
                .toList();
    }

    private long isValidRegion(Region r, List<Present> presents){
        return r.area()>=r.areaNecessary(presents) ? 1:0;
    }

}
