package software.aoc.day09.b;

import software.aoc.day09.AreaResolver;
import software.aoc.day09.InputDay9;

import java.util.List;

public class main {
    public static void main(String[] args) {
        List<String> input = new InputDay9().getData().toList();
        AreaResolver resolver = AreaResolver.create();
        System.out.println(resolver.findLargestAreaB(input));
    }
}
