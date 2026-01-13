package software.aoc.day04;

import java.util.ArrayList;
import java.util.List;

public class RollsGetter {

    private final Grid grid;
    private final List<Position> dropedPositions;

    public static RollsGetter create(List<String> rolls) {
        return new RollsGetter(rolls);
    }

    private RollsGetter(List<String> rolls) {
        List<String> gridLines = new ArrayList<>(rolls);
        grid = new Grid(gridLines);
        dropedPositions = new ArrayList<>();

    }

    public int countRolls(){
        int count=0;
        for (int i=0; i< grid.rows(); i++){
            for (int j=0; j< grid.cols(); j++){
                if (isRoll(grid.getChar(new Position(i, j))) && grid.countRolls(new Position(i,j))<4){
                    count++;
                    dropedPositions.add(new Position(i, j));
                }
            }

        }
        eliminateTakenPositions();

        return count;
    }


    public int loop(){
        int aux = countRolls();
        int count = aux;

        while(aux>0){
            aux = countRolls();
            count+=aux;
        }
        return count;
    }

    private boolean isRoll(char c) {
        return c=='@';
    }


    private void eliminateTakenPositions() {
        dropedPositions.forEach(this::eliminatePosition);
    }

    private void eliminatePosition(Position pos) {
        grid.setChar(pos, 'x');
        grid.positions.remove(pos);
    }
}