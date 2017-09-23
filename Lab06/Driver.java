package ca.uwo.eng.se2205.lab6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by musda on 3/22/2017.
 */
public class Driver {
    public static void main(String args[]){
        BatchRunner runner = new BatchRunner();
        FileWriter fw = null;
        BufferedWriter bw = null;
        File file = new File("./results/results_time.csv");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            bw.write("Algorithm, n, List Speed, List Ops/Sort, Comparison Speed, Comparison Ops/Sort, Time (ns) \n");
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                bw.close();
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //4 algorithms - insertion, selection, merge, quick
        for (int i = 0; i < 4; ++i) {
            //3 access speeds - fast normal slow
            for (int j = 0; j < 3; ++j) {
                //3 compare speeds - fast normal slow
                for (int k = 0; k < 3; ++k) {
                    Runnable run =  new RunnableClass(i,j,k,3);
                    runner.enqueue(run);
                }
            }
        }
        //4 algorithms - insertion, selection, merge, quick
        for (int i = 0; i< 4; ++i) {
            for (int j =0; j < 5 ; j++) {
                Runnable run = new RunnableClass(i, 0, 0, j);
                runner.enqueue(run);
            }
        }
        runner.run();
    }
}