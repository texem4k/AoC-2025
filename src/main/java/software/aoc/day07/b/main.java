package software.aoc.day07.b;

import software.aoc.day07.InputDay7;
import software.aoc.day07.TachyonDiagram;

import java.util.List;

public class main {
    public static void main(String[] args) {
        List<String> input = new InputDay7().getData().toList();
        TachyonDiagram diagram = TachyonDiagram.create(input);
        System.out.println(diagram.allPaths());
    }
}
