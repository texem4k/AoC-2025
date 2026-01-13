package software.aoc.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InputDay12 {


    public Stream<String> getData(){
        return readFile("Day12/input.txt");
    }

    public Stream<String> getExample(){
        return readFile("Day12/test.txt");
    }

    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}