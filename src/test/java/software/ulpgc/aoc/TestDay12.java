package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day12.InputDay12;
import software.aoc.day12.PresentDistributor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay12 {


    @Test
    public void given_example_should_return_correct_solution() {
        List<String> input = new InputDay12().getExample().toList();
        PresentDistributor distributor = PresentDistributor.create();
        assertThat(distributor.execute(input)).isEqualTo(2);
    }

    @Test
    public void given_input_should_return_correct_solution() {
        List<String> input = new InputDay12().getData().toList();
        PresentDistributor distributor = PresentDistributor.create();
        assertThat(distributor.execute(input)).isEqualTo(569);
    }
}
