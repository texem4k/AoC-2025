package software.aoc.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class InputDay11 {


    public Stream<String> getData(){
        return readFile("input.txt");
    }

    public Stream<String> getExampleA(){
        return readFile("testA.txt");
    }

    public Stream<String> getExampleB(){
        return readFile("testB.txt");
    }

    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/Day11/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}