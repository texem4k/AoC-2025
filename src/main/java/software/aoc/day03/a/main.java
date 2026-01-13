package software.aoc.day03.a;

import software.aoc.day03.InputDay3;
import software.aoc.day03.VoltageDetector;

import java.util.stream.Stream;

public class main {

    static void main() {
        VoltageDetector detector = VoltageDetector.create();
        Stream<String> input = new InputDay3().getExample();
        System.out.println(detector.executeA(input).count());
    }
}
