package software.aoc.day05;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FoodAnalyzer {

    List<Range> ranges;
    List<Long> freshIngredients;
    public static FoodAnalyzer create() {
        return new FoodAnalyzer();
    }

    private FoodAnalyzer() {
        ranges = new ArrayList<>();
        freshIngredients = new ArrayList<>();
    }


    public FoodAnalyzer add(List<String> s){
        setRanges(s);
        getIDs(s).mapToLong(Long::parseLong).forEach(this::validID);
        return this;
    }

    private void setRanges(List<String> s){
        s.stream().filter(st -> st.contains("-")).forEach(this::createRange);
    }


    private Stream<String> getIDs(List<String> s){
        return s.stream().filter(st -> !st.contains("-") && !st.contains(" ")).filter(st -> !st.trim().isEmpty());

    }

    private void createRange(String s){
        ranges.add(new Range(toLong(s.split("-")[0]), toLong(s.split("-")[1])));
    }




    private void validID(long ID) {
        if (ranges.stream().anyMatch(r -> ID >= r.min() && ID <= r.max()) && !freshIngredients.contains(ID)) {
            freshIngredients.add(ID);
        }
    }

    public long count(){
        return freshIngredients.size();
    }


    public long getRangesIds(){
        long count=0;
        List<Range> sortedRanges = sortRanges();
        long curMin = sortedRanges.getFirst().min();
        long curMax = sortedRanges.getFirst().max();

        for (int i = 1; i < sortedRanges.size(); i++) {
            Range next = sortedRanges.get(i);
            if (next.min() <= curMax + 1) {
                curMax = Math.max(curMax, next.max());
            } else {
                count += curMax - curMin + 1;
                curMin = next.min();
                curMax = next.max();
            }
        }
        count += curMax - curMin + 1;
        return count;
    }



    private List<Range> sortRanges() {
        List<Range> result = new ArrayList<>(ranges);
        result.sort(Comparator.comparingLong(Range::min));
        return result;
    }


    private long toLong(String order) {
        return Long.parseLong(order);
    }

}
