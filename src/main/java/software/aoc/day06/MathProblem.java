package software.aoc.day06;

import java.util.*;
import java.util.stream.IntStream;


public class MathProblem {

    public final List<String> operators;
    public final HashMap<Integer, List<Long>> values;

    public MathProblem() {
        operators = new ArrayList<>();
        values = new HashMap<>();
    }

    public void A(List<String> input){
        getOperations(input);
        getValues(input);
    }

    public void B(List<String> input){
        getOperations(input);
    }

    private void getOperations(List<String> input) {
        String[] s = input.getLast().trim().split("\\s+");
        operators.addAll(Arrays.asList(s));
    }


    private void getValues(List<String> input) {

        for(int i = 0; i < input.size()-1; i++) {
            values.putIfAbsent(i, new ArrayList<>());
            formatValues(input.get(i));
        }
    }


    private void formatValues(String s) {
        parseInt(s.trim().split("\\s+"));
    }

    private void parseInt(String[] split) {
        IntStream.rangeClosed(0, split.length-1).forEach(index -> {
            putValueInMap(split[index], index);
        });
    }


    private void putValueInMap(String input, int index) {
        values.put(index, addElementoValueList(input, index));
    }

    private List<Long> addElementoValueList(String input, int index){
        values.putIfAbsent(index, new ArrayList<>());
        values.get(index).add(Long.parseLong(input));
        return values.get(index);
    }







}
