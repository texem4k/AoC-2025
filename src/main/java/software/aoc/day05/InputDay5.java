package software.aoc.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;



public class InputDay5 {


    public Stream<String> getData(){
        return readFile("Day5/input.txt");
    }

    public Stream<String> getExampleA(){
        return readFile("Day5/testA.txt");
    }


    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Stream<String> getSimpleExample(){
        return Arrays.stream(new String[]{
                "3-5",
                " ",
                "1",
                "5",
                });
    }




}