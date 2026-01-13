package software.aoc.day05.a;

import software.aoc.day05.FoodAnalyzer;
import software.aoc.day05.InputDay5;

import java.util.List;

public class main {
    public static void main(String[] args) {
        List<String> input = new InputDay5().getData().toList();
        FoodAnalyzer analyzer = FoodAnalyzer.create();
        System.out.println(analyzer.add(input).count());
    }
}