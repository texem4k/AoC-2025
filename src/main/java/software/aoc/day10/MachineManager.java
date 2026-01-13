package software.aoc.day10;

import java.util.*;

public class MachineManager {

    public static MachineManager create(){
        return new MachineManager();
    }

    private MachineManager(){}

    public long fewestButtonPresses(List<String> input){
        long count=0;
        for(Machine m : buildMachine(input)){
            count+= bfsState(m);
        }
        return count;

    }

    public long fewestButtonPressesWithRequirement(List<String> input){
        long count=0;
        for(Machine m : buildMachine(input)){
             count += new JoltageSolver(m).solve();
        }
        return count;
    }


    private long bfsState(Machine m){
        Queue<List<Boolean>> queue = new ArrayDeque<>();
        Map<List<Boolean>, Integer> map = new HashMap<>();
        map.put(m.state, 0);
        queue.add(m.state);

        while(!queue.isEmpty()){
            List<Boolean> actual = queue.poll();
            m.state=actual;
            if(actual.equals(m.finalState)){
                return map.get(m.finalState);
            }

            for(Button b: m.buttons){
                List<Boolean> alterStates = m.pressButton(b);
                if(!map.containsKey(alterStates)){
                    map.put(alterStates, map.get(actual)+1);
                    queue.add(alterStates);
                }
            }
        }
        return 0;

    }



    //-----------------------------------------------------------------------------------------------------------------




    private List<Machine> buildMachine(List<String> input) {
        return input.stream().map(this::stringToMachine).toList();
    }

    private Machine stringToMachine(String s) {
        String[] split = s.trim().split(" ");
        List<Boolean> finalState =  createFinalState(split[0].substring(1, split[0].length()-1));
        List<Integer> joltage = getValuesFromString(split, split.length-1);
        List<Button> buttonList = new ArrayList<>();

        for(int i=1;i<split.length-1;i++){
            buttonList.add(new Button (getValuesFromString(split,i)));
        }
        return Machine.createMachine(finalState.size(), buttonList, finalState, joltage);

    }

    private List<Integer> getValuesFromString(String[] split, int i){
        return Arrays.stream(split[i].substring(1, split[i].length()-1).trim().split(",")).mapToInt(Integer::parseInt).boxed().toList();
    }

    private List<Boolean> createFinalState(String input){
        List<Boolean> list = new ArrayList<>(input.length());
        for(int i=0;i<input.length();i++){
            list.add(input.charAt(i)=='#');
        }
        return list;
    }

}
