package software.aoc.day05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class main {
    public static void main(String[] args) {
        List<String> input = new  InputDay5().getData().toList();
        FoodAnalyzer analyzer = FoodAnalyzer.create();
        System.out.println(analyzer.add(input).count());
        System.out.println(analyzer.getRangesIds());
    }
}
