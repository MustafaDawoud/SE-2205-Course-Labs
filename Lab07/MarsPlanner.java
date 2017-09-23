package ca.uwo.eng.se2205.lab7.mars;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Calculates the best location to land a rover.
 */
@ParametersAreNonnullByDefault
public class MarsPlanner {

    private int [][] toplogymap;
    private int[][] fuelmap;
    List<int[]> dest;

    /**
     * Initializes the planner with the topology of the land and the landing sites.
     *
     * @param topology Two dimensional set of heights
     * @param sites {@code List} of coordinates that must be visited
     */
    public MarsPlanner(int[][] topology, List<int[]> sites) {
        toplogymap = new int[topology.length][topology.length];
        this.fuelmap= new int [topology.length*topology.length][topology.length*topology.length];
        for(int i=0; i < topology.length; i++) {
            this.toplogymap[i] = topology[i].clone();
        }
        dest = sites;
        System.out.println(Arrays.deepToString(toplogymap));

        for(int j =0; j< fuelmap.length; j++){ // x
            for (int k =0; k < fuelmap.length; k++){ //y
                //set its node to 0, x[0][0]
                if (j==k)
                    fuelmap[j][k] = 0;
                else if( Math.abs(j/topology.length - k/topology.length) >1 || Math.abs(j-k) == 2)
                    fuelmap[k][j] = Integer.MAX_VALUE;
                else if ((Math.abs(j%topology.length-k%topology.length) <= 1) ) {

                    if (j / topology.length - k / topology.length ==0 ||  j % topology.length -k % topology.length ==0)
                        fuelmap[k][j] = Math.abs(topology[j / topology.length][j % topology.length] - topology[k / topology.length][k % topology.length]);
                    else
                        fuelmap[k][j] = Integer.MAX_VALUE;
                }
                else
                    fuelmap[k][j] = Integer.MAX_VALUE;
            }
        }

        for (int k = 0; k < fuelmap.length; k++) {
            for (int i = 0; i < fuelmap.length; i++) {
                for (int j = 0; j < fuelmap.length; j++) {
                    if (fuelmap[i][k] == Integer.MAX_VALUE || fuelmap[k][j] == Integer.MAX_VALUE)
                        continue;
                    if (fuelmap[i][j] > fuelmap[i][k] + fuelmap[k][j]) {
                        fuelmap[i][j] = fuelmap[i][k] + fuelmap[k][j];
                    }
                }
            }
        }
    }

    /**
     * Calculates the best landing spot in the topology.
     *
     * @param fuelAvailable How much fuel is available daily when travelling
     * @return Coordinates for the best landing spot
     *
     */
    public int[] bestLandingSpot(int fuelAvailable) {
        boolean found = false;
        int mincost =Integer.MAX_VALUE;
        int [] safepoint = new int [2];
        for (int i =0; i < fuelmap.length ;i++){
            int sum=0;
            boolean ispossible = true;
            for(int[] j: dest){
                sum+=2*fuelmap[i][j[0]*toplogymap.length+j[1]];
                if (sum > fuelAvailable) {
                    ispossible = false;
                    break;
                }
            }
            if (ispossible && mincost > sum) {
                safepoint[0] = i/toplogymap.length;
                safepoint[1] = i%toplogymap.length;
                mincost = sum;
                found = true;
            }
        }
        if (found)
            return safepoint;
        else
            return null;
    }

}
