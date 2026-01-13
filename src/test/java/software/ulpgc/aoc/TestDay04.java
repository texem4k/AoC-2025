package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day04.InputDay4;
import software.aoc.day04.RollsGetter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay04 {


    @Test
    public void given_short_example_should_return_correct_accessed_rolls_number() {

        List<String> stream = new InputDay4().getSimpleExample().toList();
        RollsGetter rolls = RollsGetter.create(stream);
        assertThat(rolls.countRolls()).isEqualTo(4);
    }


    @Test
    public void given_example_should_return_correct_accessed_rolls_number_A() {

        List<String> stream = new InputDay4().getExample().toList();
        RollsGetter rolls = RollsGetter.create(stream);
        assertThat(rolls.countRolls()).isEqualTo(13);
    }


    @Test
    public void given_example_should_return_correct_accessed_rolls_number_B() {

        List<String> stream = new InputDay4().getExample().toList();
        RollsGetter rolls = RollsGetter.create(stream);
        assertThat(rolls.loop()).isEqualTo(43);
    }

    @Test
    public void given_input_should_return_correct_accessed_rolls_number_B() {

        List<String> stream = new InputDay4().getData().toList();
        RollsGetter rolls = RollsGetter.create(stream);
        assertThat(rolls.loop()).isEqualTo(8936);
    }





}
