package software.aoc.day04.a;

import software.aoc.day04.InputDay4;
import software.aoc.day04.RollsGetter;

import java.util.List;

public class main {

    static void main() {
        List<String> input = new InputDay4().getData().toList();
        RollsGetter rollsGetter = RollsGetter.create(input);
        System.out.println(rollsGetter.countRolls());
    }
}
