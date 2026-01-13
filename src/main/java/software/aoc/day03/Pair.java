package software.aoc.day03;

public record Pair(int num1, int num2) {

    public int toInt() {
        return 10 * num1 + num2;
    }

}
