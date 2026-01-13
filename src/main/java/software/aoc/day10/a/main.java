package software.aoc.day10.a;

import software.aoc.day10.InputDay10;
import software.aoc.day10.MachineManager;


import java.util.List;

public class main {

    public static void main(String[] args) {
        List<String> input = new InputDay10().getData().toList();
        MachineManager manager = MachineManager.create();
        System.out.println(manager.fewestButtonPresses(input));
    }
}
