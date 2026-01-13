package software.aoc.day06;

import java.util.*;
import java.util.stream.Collectors;

public class Calculator {



public static Calculator create() {
return new Calculator();
}

private Calculator() {}


    public long calculateA(List<String> input){
        MathProblem problem = new MathProblem();
        problem.A(input);
        long count=0;
        int n=problem.operators.size();
        for(int i=n-1;i>=0;i--){
            count+= reduceGroup(problem.values.get(i), problem.operators);
        }

        return count;

    }

    public long calculateB(List<String> input) {
        MathProblem problem = new MathProblem();
        problem.B(input);
        List<String> rows = input.subList(0, input.size() - 1);

        int cols = rows.getFirst().length();
        List<Long> numbers = new ArrayList<>();
        long total = 0;

        for (int col = cols - 1; col >= -1; col--) {
            if (col == -1 || allBlank(rows, col)) {
                total += reduceGroup(numbers, problem.operators);
                numbers.clear();
            } else {
                numbers.add(parseColumn(rows, col));
            }
        }
        return total;
    }



    private boolean allBlank(List<String> rows, int col) {
        if (col < 0) return true;
        for (String row : rows) {
            if (col < row.length() && row.charAt(col) != ' ') {
                return false;
            }
        }
        return true;
    }



    private Long parseColumn(List<String> rows, int col) {
        String digits = rows.stream()
                        .map(r -> r.charAt(col))
                        .map(ch -> Character.isDigit(ch) ? String.valueOf(ch) : " ")
                        .collect(Collectors.joining())
                        .trim();

        return digits.isEmpty() ? 0 : Long.parseLong(digits);
    }


    private long reduceGroup(List<Long> numbers, List<String> operators) {
        if (numbers.isEmpty()) return 0;

        return (operators.removeLast().charAt(0) == '+')
                ? numbers.stream().mapToLong(Long::longValue).sum()
                : numbers.stream().reduce(1L, (a, b) -> a * b);
    }



}
