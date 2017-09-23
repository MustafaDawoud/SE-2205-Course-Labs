package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Binary Search Tree
 */
@ParametersAreNonnullByDefault
public class BinarySearchTree<E> implements Tree<E> {

    /**
     * Internal Node structure used for the BinaryTree
     * @param <E>
     */
    static class BinaryNode<E> implements Tree.Node<E> {

        BinaryNode(E elem, @Nullable BinaryNode parent) {

        }

        @Nullable BinaryNode<E> getLeft() {
            return null;
        }

        @Nullable BinaryNode<E> getRight() {
            return null;
        }

        @Nullable BinaryNode<E> getParent() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int height() {
            return 0;
        }

        @Override
        public boolean isProper() {
            return false;
        }

        @Override
        public boolean isBalanced() {
            return false;
        }

        @Override
        public E getElement() {
            return null;
        }

        @Nonnull
        @Override
        public Collection<? extends Node<E>> children() {
            return Collections.emptyList();
        }

        @Override
        public boolean isInternal() {
            return false;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    }

    public BinarySearchTree() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public boolean isProper() {
        return false;
    }

    @Override
    public boolean isBalanced() {
        return false;
    }

    @Override
    public Iterator<E> iterator(Traversal how) {
        return null;
    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    @Override
    public boolean put(E element) {
        return false;
    }

    @Override
    public boolean remove(E element) {
        return false;
    }

    @Nullable
    @Override
    public BinaryNode<E> getRoot() {
        return null;
    }

}
