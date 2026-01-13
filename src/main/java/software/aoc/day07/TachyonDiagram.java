package software.aoc.day07;

import java.util.*;

public class TachyonDiagram {

    private final Set<Position> cells;
    private final List<String> grid;
    private final Map<Position, Long> mem;

    public static TachyonDiagram create(List<String> input) {
        return new TachyonDiagram(input);
    }

    private TachyonDiagram(List<String> input) {
        cells = new HashSet<>();
        grid = input;
        mem = new HashMap<>();
    }


    private Ray getStartRay(String s) {
        return new Ray(s.indexOf('S'), 0);
    }

    public long splitTimes() {
        Ray start = getStartRay(grid.getFirst());
        long count = 0;
        Queue<Ray> queue = new ArrayDeque<>();
        queue.add(new Ray(start.x, start.y+1));

        while(!queue.isEmpty()){
            Ray actual = queue.poll();
            if (actual.y >= grid.size()) continue;
            if (actual.x >= grid.getFirst().length() || actual.x<0) continue;

            if(isSplitter(actual)){
                if(!cells.contains(new Position(actual.x, actual.y))) {
                    cells.add(new Position(actual.x, actual.y));
                    count++;
                    Ray l = actual.splitLeft();
                    Ray r = actual.splitRight();
                    if (inLimits(l)) queue.add(l);
                    if (inLimits(r)) queue.add(r);
                }
            }
            else{
                actual.goDeeper();
                queue.add(actual);

            }

        }
        return count;

    }


    private boolean inLimits(Ray l){
        return l.x >= 0 && l.x < grid.getFirst().length() && l.y < grid.size();
    }
    private boolean isSplitter(Ray p) {
        return grid.get(p.y).charAt(p.x)=='^';
    }


    public long allPaths() {
        return paths(0, grid.getFirst().indexOf("S"));

    }


    private long paths(int row, int col){
        if (row>= grid.size()){
            return 1;
        }
        if(col>= grid.getFirst().length() || col<0){
            return 0;
        }

        Position pos = new Position(row, col);
        long result;

        if(mem.containsKey(pos)){
            return mem.get(pos);
        }
        if(grid.get(row).charAt(col)=='^'){
            result = paths(row, col - 1) + paths(row, col + 1);
        } else {
            result = paths(row + 1, col);
        }

        mem.put(pos, result);
        return result;
    }

}
