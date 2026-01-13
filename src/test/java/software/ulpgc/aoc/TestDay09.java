package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day09.AreaResolver;
import software.aoc.day09.InputDay9;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay09 {


    @Test
    public void given_example_should_return_correct_solution_A() {
        List<String> input = new InputDay9().getExample().toList();
        AreaResolver resolver = AreaResolver.create();
        assertThat(resolver.findLargestAreaA(input)).isEqualTo(50);
    }


    @Test
    public void given_input_should_return_correct_solution_A() {
        List<String> input = new InputDay9().getData().toList();
        AreaResolver resolver = AreaResolver.create();
        assertThat(resolver.findLargestAreaA(input)).isEqualTo(4733727792L);
    }



    @Test
    public void given_example_should_return_correct_solution_B() {
        List<String> input = new InputDay9().getExample().toList();
        AreaResolver resolver = AreaResolver.create();
        assertThat(resolver.findLargestAreaB(input)).isEqualTo(24);
    }

    @Test
    public void given_input_should_return_correct_solution_B() {
        List<String> input = new InputDay9().getData().toList();
        AreaResolver resolver = AreaResolver.create();
        assertThat(resolver.findLargestAreaB(input)).isEqualTo(1566346198);
    }




}
