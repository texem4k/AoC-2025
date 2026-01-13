package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day02.IdValidator;
import software.aoc.day02.InputDay2;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay02 {


    @Test
    public void given_one_range_should_detect_invalid_id() {
        IdValidator validator = IdValidator.create();
        validator.executeA("38593856-38593862");
        assertThat(validator.count()).isEqualTo(38593859);
    }

    @Test
    public void given_example_should_return_correct_sum() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getData().collect(Collectors.joining());
        validator.executeA(input);
    }


    @Test
    public void given_two_ranges_should_return_sum_invalid_ids() {
        IdValidator validator = IdValidator.create();
        validator.executeA("10-20,20-30");
        assertThat(validator.count()).isEqualTo(33);
    }


    @Test
    public void given_example_should_return_solution_A() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getExample().collect(Collectors.joining("\n"));
        assertThat(validator.executeA(input).count()).isEqualTo(1227775554);
    }

    @Test
    public void given_input_should_return_solution_A() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getData().collect(Collectors.joining("\n"));
        assertThat(validator.executeA(input).count()).isEqualTo(54641809925L);
    }


    @Test
    public void given_input_should_return_solution_B() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getData().collect(Collectors.joining());
        assertThat(validator.executeB(input).count()).isEqualTo(73694270688L);
    }

    @Test
    public void given_example_should_return_solution_B() {
        IdValidator validator = IdValidator.create();
        String input = new InputDay2().getExample().collect(Collectors.joining());
        assertThat(validator.executeB(input).count()).isEqualTo(4174379265L);
    }


}
