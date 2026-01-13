package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day08.InputDay8;
import software.aoc.day08.ConnectionManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay08 {


    @Test
    public void given_example_should_return_correct_solution_A() {
        List<String> input = new InputDay8().getExample().toList();
        ConnectionManager manager = ConnectionManager.create();
        assertThat(manager.threeLargestCircuits(input)).isEqualTo(40);
    }

    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay8().getData().toList();
        ConnectionManager manager = ConnectionManager.create();
        assertThat(manager.threeLargestCircuits(input)).isEqualTo(69192);
    }

    @Test
    public void given_example_should_return_correct_solution_B() {
        List<String> input = new InputDay8().getExample().toList();
        ConnectionManager manager = ConnectionManager.create();
        assertThat(manager.lastJunctionBox(input)).isEqualTo(25272);
    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay8().getData().toList();
        ConnectionManager manager = ConnectionManager.create();
        assertThat(manager.lastJunctionBox(input)).isEqualTo(7264308110L);
    }



}
