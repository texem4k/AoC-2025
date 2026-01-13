package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day11.InputDay11;
import software.aoc.day11.PathFinder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay11 {


    @Test
    public void given_example_should_return_correct_solution_A() {
        List<String> input = new InputDay11().getExampleA().toList();
        PathFinder resolver = PathFinder.create();
        assertThat(resolver.nWaysA(input).count()).isEqualTo(5);
    }

    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay11().getData().toList();
        PathFinder resolver = PathFinder.create();
        assertThat(resolver.nWaysA(input).count()).isEqualTo(796);
    }

    @Test
    public void given_example_should_return_correct_solution_B() {
        List<String> input = new InputDay11().getExampleB().toList();
        PathFinder resolver = PathFinder.create();
        assertThat(resolver.countPathsThroughDacAndFftFrom("svr",input)).isEqualTo(2);
    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay11().getData().toList();
        PathFinder resolver = PathFinder.create();
        assertThat(resolver.countPathsThroughDacAndFftFrom("svr",input)).isEqualTo(294053029111296L);
    }



}
