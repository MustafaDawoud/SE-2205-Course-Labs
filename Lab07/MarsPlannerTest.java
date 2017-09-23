package ca.uwo.eng.se2205.lab7.graphs;

import ca.uwo.eng.se2205.lab7.mars.MarsPlanner;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MarsPlannerTest {
    private MarsPlanner underTest;

    private void buildSmall() {        // x: 0  1  2         y
        int[][] topologySmall = new int[][]{{6, 4, 3},    // 0
                {4, 12, 2},   // 1
                {8, 10, 3}};  // 2

        underTest = new MarsPlanner(topologySmall, Arrays.asList(new int[]{1, 0}, new int[]{2, 2}));
    }

    private void buildBig(){
        ArrayList<int[]> array = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./src/test/resources/topology.csv")) ;
            try {
                String line;
                while((line = br.readLine())!= null){

                    int[] intarr = new int[line.split(",").length];
                    for (int i =0; i < line.split(",").length ; i++){
                        intarr[i] = Integer.parseInt(line.split(",")[i]);
                    }
                    array.add(intarr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int[][] topologyBig = new int[array.size()][array.get(0).length];
        for (int i =0; i < array.size(); i++){
            topologyBig[i] = array.get(i).clone();
        }

        underTest = new MarsPlanner(topologyBig, Arrays.asList(new int[]{ 1, 0 }, new int[]{ 2, 2 }));
    }

    @Test
    public void checkBig() {
        buildBig();
        int[] coords = underTest.bestLandingSpot(100);
        assertArrayEquals(new int[]{ 1, 0 }, coords, "Invalid coords");
    }

    @Test
    public void checkSmall() {
        buildSmall();
        int[] coords = underTest.bestLandingSpot(4);
        //assertArrayEquals(new int[]{ 2, 0 }, coords, "Invalid coords");
    }
}
