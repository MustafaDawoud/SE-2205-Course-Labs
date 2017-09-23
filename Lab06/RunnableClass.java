package ca.uwo.eng.se2205.lab6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Created by musda on 3/22/2017.
 */
public class RunnableClass implements Runnable {
    //store type of list
    Sorter s;
    String type;
    int num =0;
    //
    List<Integer> list;
    Comparator<Integer> comp;
    //get number of operations made during sort
    DelayedComparator<Integer> comparator;
    DelayedList<Integer> sort;

    FileWriter fw = null;
    BufferedWriter bw = null;


    RunnableClass(int sType, int aTime, int cTime , int num) {
        comp = Comparator.naturalOrder();
        switch (num) {
            case 0:
                list = RandomNumbers.get(5);
                this.num= 5;
                break;
            case 1:
                list = RandomNumbers.get(10);
                this.num = 10;
                break;
            case 2:
                list = RandomNumbers.get(20);
                this.num =20;
                break;
            case 3:
                list = RandomNumbers.get(40);
                this.num = 40;
                break;
            case 4:
                list = RandomNumbers.get(100);
                this.num = 100;
                break;
        }

        //get type of sort
        switch (sType) {
            case 0:
                s = new InsertionSorter();
                type = "InsertionSorter";
                break;
            case 1:
                s = new SelectionSorter();
                type = "SelectionSorter";
                break;
            case 2:
                s = new MergeSorter();
                type = "MergeSorter";
                break;
            default:
                s = new QuickSorter();
                type = "QuickSorter";
                break;
        }
        //create delayed list and set the speed
        switch (aTime){
            case 0:
                sort = DelayedList.create(Delayed.Time.Fast, list);
                break;
            case 1:
                sort = DelayedList.create(Delayed.Time.Normal, list);
                break;
            default:
                sort = DelayedList.create(Delayed.Time.Slow, list);
                break;
        }
        //set comparator to its speed
        switch (cTime){
            case 0:
                comparator = DelayedComparator.create(comp, Delayed.Time.Fast);
                break;
            case 1:
                comparator = DelayedComparator.create(comp, Delayed.Time.Normal);
                break;
            default:
                comparator = DelayedComparator.create(comp, Delayed.Time.Slow);
                break;
        }


        try {//try opening that csv file
            fw = new FileWriter("./results/results_time.csv", true);
            //fw = new FileWriter("./results/result_n.csv", true);
            bw = new BufferedWriter(fw);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //delete for and switch when compareing slow and normal
        long time = System.nanoTime();
        s.sort(sort, comparator);
        time = System.nanoTime() - time;
        String content = type + ", " + num +", " + sort.getTime() + ", " + sort.getOperationsPerformed() + ", " + comparator.getTime() + ", " + comparator.getComparisonsPerformed() + ", " + time + "\n";
        System.out.println(content);
        try {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {//close the writer and reader
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
