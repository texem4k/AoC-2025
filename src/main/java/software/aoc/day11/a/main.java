package software.aoc.day11.a;

import software.aoc.day11.InputDay11;
import software.aoc.day11.PathFinder;

import java.util.List;

public class main {

    public static void main(String[] args) {
        List<String> input = new InputDay11().getData().toList();
        PathFinder manager = PathFinder.create();
        System.out.println(manager.nWaysA(input).count());
    }
}
