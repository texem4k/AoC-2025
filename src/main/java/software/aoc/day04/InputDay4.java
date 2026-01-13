package software.aoc.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;



public class InputDay4 {



    public Stream<String> getData(){
        return readFile("Day4/input.txt");
    }

    public Stream<String> getExample(){
        return readFile("Day4/test.txt");
    }


    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Stream<String> getSimpleExample(){
        return Arrays.stream(new String[]{".@.", "@@@", ".@."});
    }


}