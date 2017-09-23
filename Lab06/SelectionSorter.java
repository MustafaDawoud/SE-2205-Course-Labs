package ca.uwo.eng.se2205.lab6;

import java.util.Comparator;

/**
 * Created by musda on 3/18/2017.
 */
public class SelectionSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        if(!(sort.size() > 1))
            return;
        for(int i = 0; i < sort.size(); i++){
            E min = sort.get(i);
            int minIndex = i;
            for(int j = i+1; j<sort.size(); j++){
                if(comparator.compare(min, sort.get(j)) > 0){
                    min = sort.get(j);
                    minIndex = j;
                }
            }
            E swap1 = sort.get(i);
            sort.set(i, min);
            sort.set(minIndex, swap1);
        }
    }

    public static void main(String[] args){
        SelectionSorter g = new SelectionSorter();
        Comparator<Integer> i = Integer::compareTo;
        DelayedList<Integer> list = DelayedList.create(Delayed.Time.Fast, RandomNumbers.get(3000));
        g.sort(list, DelayedComparator.create(i, Delayed.Time.Fast));
        for(Integer d : list){
            System.out.println(d);
        }
    }
}
