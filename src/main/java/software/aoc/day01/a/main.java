package software.aoc.day01.a;

import software.aoc.day01.Dial;
import software.aoc.day01.InputLoader;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {

    public void main(String[] args){
        this.solve();

    }

    /**
     * Método que calcula la solución dado el input -> AoC 1A
     */
    public void solve(){
        Stream<String> input = new InputLoader().getData();
        Dial dial = Dial.create();
        System.out.println(dial.execute(input.collect(Collectors.joining("\n"))).count());
    }
}
