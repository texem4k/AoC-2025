package software.aoc.day06.a;

import software.aoc.day06.Calculator;
import software.aoc.day06.InputDay6;
import software.aoc.day06.MathProblem;

import java.util.List;

public class main {
    public static void main(String[] args) {
        List<String> matrix = new InputDay6().getData().toList();
        MathProblem problem = new MathProblem();
        problem.B(matrix);
        Calculator cal = Calculator.create();
        System.out.println(cal.calculateA(matrix));

    }
}
