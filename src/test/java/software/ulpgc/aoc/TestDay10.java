package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day10.MachineManager;
import software.aoc.day10.InputDay10;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay10 {


    @Test
    public void given_example_should_return_correct_solution_A() {
        List<String> input = new InputDay10().getExample().toList();
        MachineManager resolver = MachineManager.create();
        assertThat(resolver.fewestButtonPresses(input)).isEqualTo(7);
    }

    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay10().getData().toList();
        MachineManager resolver = MachineManager.create();
        assertThat(resolver.fewestButtonPresses(input)).isEqualTo(550);
    }

    @Test
    public void given_example_should_return_correct_solution_B() {
        List<String> input = new InputDay10().getExample().toList();
        MachineManager resolver = MachineManager.create();
        assertThat(resolver.fewestButtonPressesWithRequirement(input)).isEqualTo(33);
    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay10().getData().toList();
        MachineManager resolver = MachineManager.create();
        assertThat(resolver.fewestButtonPressesWithRequirement(input)).isEqualTo(20042);
    }



}
