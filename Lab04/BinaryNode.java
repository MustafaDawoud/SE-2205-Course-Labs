package ca.uwo.eng.se2205b.lab4;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a Binary Node
 */
@ParametersAreNonnullByDefault
public abstract class BinaryNode<E, N extends BinaryNode<E, N>> implements Tree.Node<E> {
    abstract @Nullable N getLeft();

    abstract N setLeft(@Nullable N left);

    abstract @Nullable N getRight();

    abstract N setRight(@Nullable N right);

    abstract @Nullable N getParent();

    abstract @Nullable N setParent(@Nullable N parent);

    @Override
    public @Nonnull List<? extends N> children() {
        List<N> retList = new ArrayList<>();
        retList.add(getLeft());
        retList.add(getRight());
        return retList;
    }

    @Override
    public int height() {
        //base case of no children - height is 1
        if(getLeft()==null&&getRight()==null){
            return 1;
        }
        //if only one (right) child, recursive call to get height of subtree then add current node
        if(getLeft()==null){
            return getRight().height()+1;
        }
        //if only one (left) child, recursive call to get height of subtree then add current node
        if(getRight()==null){
            return getLeft().height()+1;
        }
        //if two children, recursive call to get height of both subtrees but only return the max of them plus current node
        return Math.max(getLeft().height(),getRight().height())+1;
    }

    @Override
    public int size() {
        int retSize = 0;
        //recursive call to get size of left subtree if it exists
        if(getLeft()!=null){
            retSize+=getLeft().size();
        }
        //recursive call to get size of right subtree if it exists
        if(getRight()!=null){
            retSize+=getRight().size();
        }
        //base case is size of 1 (node with no children)
        return retSize+1;
    }

    @Override
    public boolean isBalanced() {
        //node with no children is balanced
        if(getLeft()==null&&getRight()==null){
            return true;
        }
        //node with only left child - is balanced only if the left child has no children
        if(getRight()==null){
            return getLeft().height()<2;
        }
        //node with only right child - is balanced only if the right child has no children
        if(getLeft()==null){
            return getRight().height()<2;
        }
        //node with two children - recursive call to check if each of them are balanced, and also check that their heights are within 1 of each other
        return (getLeft().isBalanced()&&getRight().isBalanced()&&Math.abs(getLeft().height()-getRight().height())<2);
    }

    @Override
    public boolean isProper() {
        //node with no children is proper
        if(getLeft()==null&&getRight()==null){
            return true;
        }
        //node with only one child is not
        if(getLeft()==null||getRight()==null){
            return false;
        }
        //node with two children - recursive call on both subtrees to check if they are proper
        return (getLeft().isProper()&&getRight().isProper());
    }

    @Override
    public boolean isInternal() {
        return getLeft() != null || getRight() != null;
    }

    @Override
    public boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
