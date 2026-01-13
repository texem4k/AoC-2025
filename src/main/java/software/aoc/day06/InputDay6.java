package software.aoc.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;




public class InputDay6 {



    private Stream<String> readFile(String path) {
        try {
            return Files.lines(Paths.get("src/test/resources/Day6/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<String> getData(){
        return readFile("input.txt");
    }


    public Stream<String> getSimpleExample(){
        return Arrays.stream(new String[] {"10 4", "6  2", "+  *"});

    }


    public Stream<String> getExample(){
        return readFile("test.txt");
    }


}