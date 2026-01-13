package software.aoc.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;



public class InputDay3 {


    public Stream<String> getData(){
        return readFile("input.txt");
    }

    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/Day3/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Stream<String> getExample() {
        return readFile("test.txt");
    }
}