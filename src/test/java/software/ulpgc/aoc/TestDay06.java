package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day06.Calculator;
import software.aoc.day06.InputDay6;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay06 {

    @Test
    public void given_simple_example_should_return_correct_sum() {
        List<String> input = new InputDay6().getSimpleExample().toList();
        Calculator x = Calculator.create();
        System.out.println(input);
        assertThat(x.calculateA(input)).isEqualTo(24);
    }

    @Test
    public void given_example_should_return_correct_sum() {
        List<String> input = new InputDay6().getExample().toList();
        Calculator x = Calculator.create();
        assertThat(x.calculateA(input)).isEqualTo(4277556L);
    }

    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay6().getData().toList();
        Calculator x = Calculator.create();
        assertThat(x.calculateA(input)).isEqualTo(3525371263915L);
    }

    @Test
    public void given_example_should_return_correct_sum_B() {
        List<String> input = new InputDay6().getExample().toList();
        Calculator x = Calculator.create();
        assertThat(x.calculateB(input)).isEqualTo(3263827L);
    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay6().getData().toList();
        Calculator x = Calculator.create();
        assertThat(x.calculateB(input)).isEqualTo(6846480843636L);
    }


}

