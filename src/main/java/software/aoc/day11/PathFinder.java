package software.aoc.day11;

import java.util.*;

public class PathFinder {

    public final Map<String, List<String>> graph;
    public List<List<String>> allPaths;


    public static PathFinder create(){
        return new PathFinder();
    }

    private PathFinder(){
        graph = new HashMap<>();
        allPaths = new ArrayList<>();
    }


    public PathFinder nWaysA(List<String> input){
        createNodes(input);
        List<String> l=new ArrayList<>();
        l.add("you");
        backtracking(l, "you");
        return this;

    }

    public long count(){
        return allPaths.size();
    }

    private void createNodes(List<String> input) {
        input.forEach(s -> {
            List<String> split = Arrays.stream(s.split(" ")).toList();
            graph.put(split.getFirst().substring(0, split.getFirst().length()-1), split.subList(1, split.size()));
        });
    }


    private void backtracking(List<String> currentPath, String actual){
        if(actual.equals("out")){
            allPaths.add(currentPath);
            return;
        }

        for (String s : graph.get(actual)){
            if(!currentPath.contains(s)){
                currentPath.add(s);
                backtracking(currentPath, s);
                currentPath.removeLast();
            }
        }

    }


    public long countPathsThroughDacAndFftFrom(String start, List<String> input) {
        createNodes(input);
        return countPathsWithFlags(start, false, false, new HashMap<>());
    }


    private long countPathsWithFlags(String current, boolean dac, boolean fft, Map<String, Long> cache) {
        boolean newDac = dac || current.equals("dac");
        boolean newFft = fft || current.equals("fft");
        if (cache.containsKey(key(current, newDac, newFft))) return cache.get(key(current, newDac, newFft));
        if (current.equals("out")){
            cache.put(key(current, newDac, newFft), (newDac && newFft) ? 1L : 0L);
            return cache.get(key(current, newDac, newFft));
        }
        long paths = graph.get(current).stream()
                .mapToLong(next -> countPathsWithFlags(next, newDac, newFft,cache))
                .sum();
        cache.put(key(current, newDac, newFft),paths);
        return paths;
    }
    private String key(String current, boolean dac, boolean fft) {
        return current + ":" + dac + ":" + fft;
    }


}
