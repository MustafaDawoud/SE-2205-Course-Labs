package ca.uwo.eng.se2205.lab6;

import java.util.Comparator;

/**
 * Created by musda on 3/22/2017.
 */
public class QuickSorter implements Sorter{

    @Override
    public <E> void sort(DelayedList<E> sort, DelayedComparator<E> comparator) {
        if(!(sort.size() > 1))
            return;
        quickSortInPlace(sort, comparator, 0, sort.size()-1);
    }

    public <E> void quickSortInPlace(DelayedList<E> sort, DelayedComparator<E> comparator, int a, int b) {
        if(a >= b) return;
        int left = a;
        int right = b-1;
        E pivot = sort.get(b);
        E temp;
        while(left <= right){
            while(left <= right && comparator.compare(sort.get(left), pivot)<0) left++;
            while(left <= right && comparator.compare(sort.get(right), pivot)>0) right--;
            if(left <= right) {
                temp = sort.get(left);
                sort.set(left, sort.get(right));
                sort.set(right, temp);
                left++; right--;
            }
        }
        temp = sort.get(left);
        sort.set(left, sort.get(b));
        sort.set(b, temp);
        quickSortInPlace(sort, comparator, a, left - 1);
        quickSortInPlace(sort, comparator, left + 1, b);
    }

    public static void main(String[] args){
        QuickSorter g = new QuickSorter();
        Comparator<Integer> i = Integer::compareTo;
        DelayedList<Integer> list = DelayedList.create(Delayed.Time.Fast, RandomNumbers.get(5));
        g.sort(list, DelayedComparator.create(i, Delayed.Time.Fast));
        for(Integer d: list){
            System.out.println(d);
        }
    }

}
