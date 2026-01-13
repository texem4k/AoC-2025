package software.aoc.day12.a;

import software.aoc.day12.*;

import java.util.List;


public class main {
    public static void main(String[] args) {
        List<String> input = new InputDay12().getData().toList();
        PresentDistributor r = PresentDistributor.create();
        System.out.println(r.execute(input));
    }
}
