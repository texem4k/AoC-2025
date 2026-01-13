package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day07.TachyonDiagram;
import software.aoc.day07.InputDay7;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay07 {


    @Test
    public void given_example_should_return_correct_number_splits_A() {
        List<String> input = new InputDay7().getExample().toList();
        TachyonDiagram diagram = TachyonDiagram.create(input);
        assertThat(diagram.splitTimes()).isEqualTo(21);

    }

    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay7().getData().toList();
        TachyonDiagram diagram = TachyonDiagram.create(input);
        assertThat(diagram.splitTimes()).isEqualTo(1560);

    }

    @Test
    public void given_example_should_return_correct_number_ways() {
        List<String> input = new InputDay7().getExample().toList();
        TachyonDiagram diagram = TachyonDiagram.create(input);
        assertThat(diagram.allPaths()).isEqualTo(40);

    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay7().getData().toList();
        TachyonDiagram diagram = TachyonDiagram.create(input);
        assertThat(diagram.allPaths()).isEqualTo(25592971184998L);

    }





}
