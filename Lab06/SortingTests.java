package ca.uwo.eng.se2205.lab6;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortingTests {
    DelayedList<Integer> sort;
    Sorter s;
    Comparator<Integer> comparator = Comparator.naturalOrder();
    DelayedComparator<Integer> comp = DelayedComparator.create(comparator, Delayed.Time.Fast);

    private void build(int n){
        List<Integer> list = RandomNumbers.get(n);
        sort = DelayedList.create(Delayed.Time.Fast, list);
    }

    private void tests(){
        //should work for empty list
        build(0);
        s.sort(sort, comp);
        assertTrue(sort.isEmpty());
        //should work for list of size 1
        build(1);
        s.sort(sort, comp);
        assertEquals(1, sort.size());
        //should work for list with things in it
        build(10);
        s.sort(sort, comp);
        for(int i=0;i<sort.size()-1;i++){
            assertTrue(sort.get(i+1)>sort.get(i));
        }
    }

    @Test
    public void insertion(){
        s = new InsertionSorter();
        tests();
    }

    @Test
    public void selection(){
        s = new SelectionSorter();
        tests();
    }

    @Test
    public void merge(){
        s = new MergeSorter();
        tests();
    }

    @Test
    public void quick(){
        s = new QuickSorter();
        tests();
    }
}