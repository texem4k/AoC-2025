package software.aoc.day01.day01b;

import software.aoc.day01.Dial;
import software.aoc.day01.InputLoader;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {

    public void main(String[] args){
        this.solve();

    }

    public void solve(){
        Stream<String> input = new InputLoader().getExample();
        Dial dial = Dial.create();
        System.out.println(dial.execute(input.collect(Collectors.joining("\n"))).countAllClicksZeros());
    }
}
