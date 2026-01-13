package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day05.FoodAnalyzer;
import software.aoc.day05.InputDay5;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay05 {



    @Test
    public void given_simple_example_should_return_correct_number_fresh_ingredients() {
        FoodAnalyzer x = FoodAnalyzer.create();
        List<String> input = new InputDay5().getSimpleExample().toList();
        assertThat(x.add(input).count()).isEqualTo(1);
    }

    @Test
    public void given_exmaple_should_return_correct_number_fresh_ingredients() {
        FoodAnalyzer x = FoodAnalyzer.create();
        List<String> input = new InputDay5().getExampleA().toList();
        assertThat(x.add(input).count()).isEqualTo(3);
    }

    @Test
    public void given_input_should_return_correct_id() {
        FoodAnalyzer x = FoodAnalyzer.create();
        List<String> input = new InputDay5().getData().toList();
        assertThat(x.add(input).count()).isEqualTo(558);
    }



//------------------------------ Test 2º Parte ------------------------------

    @Test
    public void given_example_should_return_correct_idB() {
        FoodAnalyzer x = FoodAnalyzer.create();
        List<String> input = new InputDay5().getExampleA().toList();
        assertThat(x.add(input).getRangesIds()).isEqualTo(14);
    }


    @Test
    public void given_input_should_return_correct_idB() {
        FoodAnalyzer x = FoodAnalyzer.create();
        List<String> input = new InputDay5().getData().toList();
        assertThat(x.add(input).getRangesIds()).isEqualTo(344813017450467L);
    }
}

