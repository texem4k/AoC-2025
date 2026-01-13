package software.aoc.day08.a;

import software.aoc.day08.ConnectionManager;
import software.aoc.day08.InputDay8;

import java.util.List;

public class main {

    public static void main(String[] args) {
        List<String> input = new InputDay8().getExample().toList();
        ConnectionManager manager = ConnectionManager.create();
        System.out.println(manager.threeLargestCircuits(input));

    }
}
