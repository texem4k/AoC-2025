package software.ulpgc.aoc;

import org.junit.Test;
import software.aoc.day03.InputDay3;
import software.aoc.day03.VoltageDetector;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDay03 {



    @Test
    public void given_one_string_should_return_correct_voltage_A() {

        VoltageDetector x = VoltageDetector.create();
        x = x.executeA("234234234234278".lines());
        VoltageDetector y = VoltageDetector.create();
        y = y.executeA("987654321111111".lines());
        assertThat(x.count()).isEqualTo(78);
        assertThat(y.count()).isEqualTo(98);

    }

    @Test
    public void given_Stream_should_return_correct_voltage_A() {
        VoltageDetector detector = VoltageDetector.create();
        Stream<String> input = new InputDay3().getExample();
        assertThat(detector.executeA(input).count()).isEqualTo(357);

    }

    @Test
    public void given_input_should_return_correct_voltage_A() {
        VoltageDetector detector = VoltageDetector.create();
        Stream<String> input = new InputDay3().getData();
        assertThat(detector.executeA(input).count()).isEqualTo(17263);

    }


//-------------------- Parte B --------------------


    @Test
    public void given_example_should_return_correct_voltage_B() {
        VoltageDetector detector = VoltageDetector.create();
        Stream<String> input = new InputDay3().getExample();
        assertThat(detector.executeB(input).count()).isEqualTo(3121910778619L);

    }


    @Test
    public void given_input_should_return_correct_voltage_B() {
        VoltageDetector detector = VoltageDetector.create();
        Stream<String> input = new InputDay3().getData();
        assertThat(detector.executeB(input).count()).isEqualTo(170731717900423L);

    }




}
