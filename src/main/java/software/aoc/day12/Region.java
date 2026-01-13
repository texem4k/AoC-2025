package software.aoc.day12;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Region {

    private final int height;
    private final int width;
    private final List<Integer> neededPresents;


    public Region(String region){
        String [] temp = region.split(":")[0].split("x");
        height = Integer.parseInt(temp[1]);
        width = Integer.parseInt(temp[0]);
        neededPresents = setNeededPresents(region);


    }

    private List<Integer> setNeededPresents(String s){
        return Arrays.stream(s.split(":")[1].split(" ")).filter(a->!a.isEmpty()).mapToInt(s1 -> Integer.parseInt(s1.trim())).boxed().toList();
    }

    public long area(){
        return (long) width * height;
    }

    public long areaNecessary(List<Present> p){
        AtomicLong area= new AtomicLong();
        IntStream.range(0, p.size()).forEach(i -> area.addAndGet(areaPerPresent(p.get(i), i)));
        return area.get();
    }

    private long areaPerPresent(Present pres, int i){
        return pres.area()*neededPresents.get(i);
    }
}