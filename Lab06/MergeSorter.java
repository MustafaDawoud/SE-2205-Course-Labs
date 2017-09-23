package ca.uwo.eng.se2205.lab6;

import java.util.Comparator;

/**
 * Created by musda on 3/18/2017.
 */
public class MergeSorter implements Sorter {
    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        if(!(sort.size() > 1))
            return;
        inPlacePartition(0, sort.size()-1, sort, comparator);
    }

    private  <E> void inPlacePartition(int min, int max, DelayedList<E> entries, DelayedComparator<E> comparator){
        if(max - min == 1){
            if(comparator.compare(entries.get(min), entries.get(max)) > 0){
                E temp = entries.get(min);
                entries.set(min, entries.get(max));
                entries.set(max, temp);
            }
            return;
        }
        inPlacePartition(min, (max+min)/2, entries, comparator);
        inPlacePartition((max+min)/2, max, entries, comparator);
        merge(min, (max+min)/2, max, entries, comparator);
    }

    private  <E> void merge(int min1, int max1, int max2, DelayedList<E> entries, DelayedComparator<E> comparator){
        int s1_s=min1;
        int s1_e=max1;
        int s2_s=max1;
        while(s1_s<=s1_e||s2_s<=max2){
            if(s1_s<=s1_e&&s2_s<=max2){
                if(comparator.compare(entries.get(s1_s), entries.get(s2_s))<=0){
                    //pick left element
                    s1_s++;
                }
                else{
                    //pick right element
                    E temp = entries.get(s2_s);
                    for(int i=s2_s; i>s1_s; i--){
                        entries.set(i, entries.get(i-1));
                }
                entries.set(s1_s++,temp);
                s2_s++;
                s1_e++;
                }
            }
            else if(s2_s>max2){
                s1_s++;
            }
            else{
                s2_s++;
            }
        }
    }
    public static void main(String[] args){
        MergeSorter g = new MergeSorter();
        Comparator<Integer> i = Integer::compareTo;
        DelayedList<Integer> list = DelayedList.create(Delayed.Time.Fast, RandomNumbers.get(3));
        g.sort(list, DelayedComparator.create(i, Delayed.Time.Fast));
        for(Integer d: list){
            System.out.println(d);
        }
    }
}