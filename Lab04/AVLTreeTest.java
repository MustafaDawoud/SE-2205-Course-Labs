package ca.uwo.eng.se2205b.lab4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ca.uwo.eng.se2205b.lab4.BinaryTreeAssertions;

/**
 * Test an AVL tree
 */
public class AVLTreeTest {
    private final AVLTree<Integer> underTest = new AVLTree<>();
    private final AVLTree<Integer> underTest2 = new AVLTree<>();

    private void makeTree1(){
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);

        underTest2.put(10);
        underTest2.put(20);
        underTest2.put(5);
        underTest2.put(3);
        underTest2.put(14);
        underTest2.put(8);
    }
    @Test
    public void removes() throws Exception {
       // Check how the AVL tree works with the `remove()` method
       makeTree1();
       underTest.remove(20);
       BinaryTreeAssertions.checkStructure(Arrays.asList(10, 5, 3, null, null, 8, null, null, 14, null, null), underTest);
       underTest.remove(14);
       BinaryTreeAssertions.checkStructure(Arrays.asList(8, 5, 3, null, null, null, 10, null, null), underTest);
       assertFalse(underTest.contains(14));
    }

    @Test
    public void puts() throws Exception {
        // Check how the AVL tree works with `put()` values in the tree
        makeTree1();
        underTest.put(13);
        BinaryTreeAssertions.checkStructure(Arrays.asList(10, 5, 3, null, null, 8, null, null, 14, 13, null, null, 20, null, null), underTest);
        underTest.put(12);
        BinaryTreeAssertions.checkStructure(Arrays.asList(10, 5, 3, null, null, 8, null, null, 14, 13, 12, null, null, null, 20, null, null), underTest);
        underTest.put(11);
        BinaryTreeAssertions.checkStructure(Arrays.asList(10, 5, 3, null, null, 8, null, null, 14, 12, 11, null, null, 13, null, null, 20, null, null), underTest);
    }

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        assertEquals(0, underTest.size());
        assertTrue(underTest.isEmpty());
        makeTree1();
        assertEquals(6, underTest.size());
        assertFalse(underTest.isEmpty());
    }

    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
        assertEquals(0, underTest.height());
        makeTree1();
        assertEquals(3, underTest.height());
    }

    @Test
    public void contains() throws Exception {
        // Actually in the tree, not in..
        assertFalse(underTest.contains(14));

        makeTree1();

        assertTrue(underTest.contains(14));
        assertFalse(underTest.contains(25));
    }

    @Test
    public void isProper() throws Exception {
        // Check if proper
        assertTrue(underTest.isProper());
        makeTree1();
        assertFalse(underTest.isProper());
        underTest.put(25);
        assertTrue(underTest.isProper());
    }

    @Test
    public void isBalanced() throws Exception {
        // Make sure it's /always/ balanced
        assertTrue(underTest.isBalanced());
        makeTree1();
        assertTrue(underTest.isBalanced());
        underTest.put(23);
        assertTrue(underTest.isBalanced());
        underTest.put(92);
        assertTrue(underTest.isBalanced());
        underTest.put(85);
        assertTrue(underTest.isBalanced());
    }

    @Test
    public void equals() throws Exception {
        // Make sure it's /always/ balanced
        makeTree1();
        assertTrue(underTest.equals(underTest2));
        underTest.remove(5);
        assertFalse(underTest.equals(underTest2));
        underTest.put(5);
        assertTrue(underTest.equals(underTest2));
    }

    @Test
    public void toStringTester() throws Exception {
        makeTree1();
        assertEquals("[3, 5, 8, 10, 14, 20]", underTest.toString());
    }


    @Test
    public void iterator() throws Exception {
        // Check the three different types of iteration
        // Check the three different types of iteration

        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);

        //InOrder iteration
        Iterator<Integer> inOrderIterator = underTest.iterator(Tree.Traversal.InOrder);

        Integer [] inOrderArray = {3,5,8,10,14,20};

        for (int i = 0; i < 6; i++)
        {
            assertEquals(inOrderArray[i], inOrderIterator.next());
        }

        //PreOrder iteration
        Iterator<Integer> preOrderIterator = underTest.iterator(Tree.Traversal.PreOrder);

        Integer [] preOrderArray = {10,5,3,8,20,14};

        for (int i = 0; i < 6; i++)
        {
            assertEquals(preOrderArray[i], preOrderIterator.next());
        }

        //PostOrder iteration
        Iterator<Integer> postOrderIterator = underTest.iterator(Tree.Traversal.PostOrder);

        Integer [] postOrderArray = {3,8,5,14,20,10};

        for (int i = 0; i < 5; i++)
        {
            assertEquals(postOrderArray[i], postOrderIterator.next());
        }
        assertTrue(underTest.contains(10));
        underTest.remove(postOrderIterator.next());
        assertFalse(underTest.contains(10));
    }

}