package software.aoc.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;


public class IdValidator {


    private final List<Long> invalidIds;


    private IdValidator() {
        invalidIds =new ArrayList<>();
    }


    public static IdValidator create(){
        return new IdValidator();
    }

    private LongStream formatInput(String s){
        return Arrays.stream(s.split(","))
                .map(this::createRange)
                .flatMapToLong(Range::stream);

    }
    public IdValidator executeA(String s){
        formatInput(s).forEach(this::validateIdAPart);
        return this;
    }

    public IdValidator executeB(String s){
        formatInput(s).forEach(this::validateIdBPart);
        return this;
    }

    private long toLong(String order) {
        return Long.parseLong(order);
    }

    public long count() {
        return invalidIds.stream().mapToLong(Long::longValue).sum();
    }



    private void validateIdBPart(long num) {
        if (toStr(num).matches("^(\\d+)\\1+$")){
            invalidIds.add(num);}
    }


    private void validateIdAPart(long num) {
        String s = String.valueOf(num);
        String s1 = s.substring(0, s.length()/2);
        String s2 = s.substring(s.length()/2);

        if(s1.equals(s2)){
            invalidIds.add(num);}
    }


    private String toStr(long x) {
        return String.valueOf(x);
    }


    private Range createRange(String s){
        return new Range(toLong(s.split("-")[0]), toLong(s.split("-")[1]));
    }


}
