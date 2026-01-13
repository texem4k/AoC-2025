package software.aoc.day02.b;

import software.aoc.day02.IdValidator;
import software.aoc.day02.InputDay2;

import java.util.stream.Collectors;

public class main {

    static void main() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getData().collect(Collectors.joining("\n"));
        System.out.println(validator.executeB(input).count());
    }
}
