package software.aoc.day01;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class InputLoader {



    public Stream<String> getData(){
        return readFile("input.txt");
    }

    public Stream<String> getExample(){
        return readFile("test.txt");
    }

    private Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get("src/test/resources/Day1/"+path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

