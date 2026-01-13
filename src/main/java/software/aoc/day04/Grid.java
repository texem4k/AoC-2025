package software.aoc.day04;

import java.util.HashSet;
import java.util.List;


public class Grid {


    private final List<String> gridScheme;
    public final HashSet<Position> positions;

    public Grid(List<String> input) {
        gridScheme = input;
        positions = new HashSet<>();
        setPositions();
    }


    public int cols() {
        return gridScheme.size();
    }

    public int rows() {
        return gridScheme.getFirst().length();
    }

    public void addPosition(int row, int col) {
        positions.add(new Position(row, col));
    }

    public Character getChar(Position pos) {
        return gridScheme.get(pos.row()).charAt(pos.col());
    }

    public void setChar(Position pos, char c) {
        String r = gridScheme.get(pos.row());
        char[] chars = r.toCharArray();
        chars[pos.col()] = c;
        gridScheme.set(pos.row(), new String(chars));
    }
    private void setPositions() {
        for(int i=0;i<gridScheme.size();i++){
            for(int j=0;j<gridScheme.getFirst().length();j++){
                if (gridScheme.get(i).charAt(j)=='@'){
                    addPosition(i, j);
                }
            }
        }
    }

    public int countRolls(Position pos) {
        int count=0;

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(i==0 && j==0){continue;}
                int newRow = pos.row() + i;
                int newCol = pos.col() + j;

                boolean insideRow = newRow >= 0 && newRow < this.rows();
                boolean insideCol = newCol >= 0 && newCol < this.cols();

                if (insideRow && insideCol) {
                    if (positions.contains(new Position(newRow, newCol))) {
                        count++;
                    }
                }

            }
        }
        return count;
    }

}
