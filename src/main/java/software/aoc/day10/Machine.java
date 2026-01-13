package software.aoc.day10;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Machine {

    public List<Boolean> state;
    public List<Button> buttons;
    public List<Boolean> finalState;
    public List<Integer> joltageRequirements;

    public static Machine createMachine(int numberLights, List<Button> buttons, List<Boolean> finalState,  List<Integer> joltageRequirements) {
        return new Machine(numberLights, buttons, finalState, joltageRequirements);

    }

    private Machine(int l, List<Button> buttonList,  List<Boolean> finalState,   List<Integer> joltageRequirements) {
        state=new ArrayList<>(l){};
        this.joltageRequirements=joltageRequirements;
        IntStream.range(0,l).forEach(i-> state.add(false));
        buttons=buttonList;
        this.finalState=finalState;


    }


    public List<Boolean> pressButton(Button button){
        List<Boolean> result = new ArrayList<>(state);
        button.alter().forEach(i-> result.set(i,!result.get(i)));
        return result;
    }
}
