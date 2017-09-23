package ca.uwo.eng.se2205.lab6;

import java.util.Comparator;


/**
 * Created by musda on 3/17/2017.
 */
public class InsertionSorter implements Sorter {

    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        if(sort.size() == 1 || sort.isEmpty())
            return;

        for(int i = 1; i <sort.size(); i++){
            if(comparator.compare(sort.get(i-1), sort.get(i)) > 0){
                for(int j = i-1; j >=0; j--){
                    if(comparator.compare(sort.get(j), sort.get(j+1)) > 0){
                        E swap1 = sort.get(j);
                        E swap2 = sort.get(j+1);
                        sort.set(j+1, swap1);
                        sort.set(j, swap2);
                    } else
                        break;
                }
            }
        }
    }

    public static void main(String[] args){
        InsertionSorter g = new InsertionSorter();
        Comparator<Integer> i = Integer::compareTo;
        DelayedList<Integer> list = DelayedList.create(Delayed.Time.Fast, RandomNumbers.get(3000));
        g.sort(list, DelayedComparator.create(i, Delayed.Time.Fast));
        for(Integer d : list){
            System.out.println(d);
        }
    }
}
