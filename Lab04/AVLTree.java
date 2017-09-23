package ca.uwo.eng.se2205b.lab4;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Created by MustafaDawoud on 2017-03-08.
 */
@ParametersAreNonnullByDefault
public class AVLTree<E> implements Tree<E, AVLTree.AVLBinaryNode<E>> {

    private AVLBinaryNode<E> root;
    private Comparator comp;

    static class AVLBinaryNode<E> extends BinaryNode<E, AVLBinaryNode<E>> {
        private E element;
        private AVLBinaryNode<E> left;
        private AVLBinaryNode<E> right;
        private AVLBinaryNode<E> parent;
        private Comparator<E> nodeComparator;

        AVLBinaryNode(E elem, @Nullable AVLBinaryNode<E> parent, Comparator comparator) {
            element = elem;
            this.parent = parent;
            left = null;
            right = null;
            nodeComparator = comparator;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getLeft() {
            return left;
        }

        @Override
        AVLBinaryNode<E> setLeft(@Nullable AVLBinaryNode<E> left) {
            AVLBinaryNode<E> previousNode = this.left;
            this.left = left;
            if(left!=null){
                this.left.parent = this;
            }
            return previousNode;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getRight() {
            return right;
        }

        @Override
        AVLBinaryNode<E> setRight(@Nullable AVLBinaryNode<E> right) {
            AVLBinaryNode<E> retVal = this.right;
            this.right = right;
            if(right!=null) {
                this.right.parent = this;
            }
            return retVal;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getParent() {
            return parent;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> setParent(@Nullable AVLBinaryNode<E> parent) {
            AVLBinaryNode<E> previousParent = this.parent;
            this.parent = parent;
            if(parent!=null){
                if(nodeComparator.compare(this.element, parent.element)<0){
                    this.parent.left = this;
                }
                else{
                    this.parent.right = this;
                }
            }
            return previousParent;
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public E setElement(E element) {
            E oldElement = element;
            this.element = element;
            return oldElement;
        }

        private boolean contains(E element) {
            if(nodeComparator.compare(this.element, element) == 0)
                return true;
            else if(nodeComparator.compare(this.element, element) > 0 && this.left != null)
                return this.left.contains(element);
            else if(this.right != null)
                return this.right.contains(element);
            else
                return false;
        }

        private AVLBinaryNode<E> put(E element){
            if(nodeComparator.compare(this.element, element) > 0) {
                if(this.left == null) {
                    return this.left = new AVLBinaryNode<>(element, this, nodeComparator);
                }
                return this.left.put(element);
            }
            else {
                if(this.right == null) {
                    return this.right = new AVLBinaryNode<>(element, this, nodeComparator);
                }
                return this.right.put(element);
            }
        }

        private AVLBinaryNode<E> remove(E element){
            if(nodeComparator.compare(this.element, element) == 0){
                if(this.left != null && this.right != null){
                    if(this.left.height() > this.right.height()){
                        this.element = this.left.max();
                        left.remove(this.element);
                    }
                    else{
                        this.element = this.right.min();
                        right.remove(this.element);
                    }
                }
                else if(this.isLeaf()){
                    if(this.parent.left == this)
                        this.parent.left = null;
                    else
                        this.parent.right = null;
                }
                else if(this.parent.left == this){
                    AVLBinaryNode<E> temp = (left != null) ? left : right;
                    this.parent.left = temp;
                    temp.parent = this.parent;
                }
                else {
                    AVLBinaryNode<E> temp = (left != null) ? left : right;
                    this.parent.right = temp;
                    temp.parent = this.parent;
                }
                return this;
            }
            else if(nodeComparator.compare(this.element, element) > 0 && this.left != null){
                return this.left.remove(element);
            }
            else if(this.right != null)
                return this.right.remove(element);
            else
                return null;
        }

        AVLBinaryNode<E> tallerChild(){
            if(left==null){
                if(right==null){
                    return null;
                }
                return right;
            }
            else if(right == null){
                return left;
            }
            if(left.height()>right.height()){
                return left;
            }
            return right;
        }
        private E min(){
            if(left == null){
                return this.element;
            }
            return left.min();
        }
        private E max(){
            if(right == null){
                return this.element;
            }
            return right.max();
        }

        AVLBinaryNode<E> get(E element){
            if(left==null&&right==null&&nodeComparator.compare(this.element, element)!=0){
                return null;
            }
            if(nodeComparator.compare(this.element, element)==0){
                return this;
            }
            if(nodeComparator.compare(this.element, element)>0){
                if(left==null){
                    return null;
                }
                return left.get(element);
            }
            else{
                if(right==null){
                    return null;
                }
                return right.get(element);
            }
        }

    }

    public AVLTree(){
        root = null;
        comp = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return ((Comparable<E>)o1).compareTo(o2);
            }
        };
    }

    public AVLTree(Comparator<E> comparator){
        root = null;
        comp = comparator;
    }

    @Override
    public int size() {
        if(root==null){
            return 0;
        }
        return root.size();
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public int height() {
        if(root==null){
            return 0;
        }
        return root.height();
    }

    //checks if tree is balanced - difference in height of left and right subtrees is less than 1
    @Override
    public boolean isBalanced() {
        return isEmpty() ||root.isBalanced();
    }

    //checks if tree is proper - nodes have either 2 or 0 children
    @Override
    public boolean isProper() {
        return isEmpty()||root.isProper();
    }

    public class InOrderBinaryIterator<E> implements Iterator<E> {
        Stack<AVLBinaryNode<E>> stack;
        AVLBinaryNode<E> currentPos = null;

        public InOrderBinaryIterator() {
            stack = new Stack<>();
            AVLBinaryNode<E> temp = (AVLBinaryNode<E>) root;
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public E next() {
            AVLBinaryNode<E> node = stack.pop();
            currentPos = node;
            E result = node.element;
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
        @Override
        public void remove(){
            if(currentPos != null)
                currentPos.remove(currentPos.getElement());
            else
                throw new NullPointerException();
        }
    }

    public class PostOrderBinaryIterator<E> implements Iterator<E> {
        Stack<AVLBinaryNode<E>> stack = new Stack<>();
        AVLBinaryNode<E> currentPos = null;

        public PostOrderBinaryIterator() {
            findNextLeaf((AVLBinaryNode<E>) root);
        }

        private void findNextLeaf(AVLBinaryNode<E> cur) {
            while (cur != null) {
                stack.push(cur);
                if (cur.left != null) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            AVLBinaryNode<E> res = stack.pop();
            currentPos = res;
            if (!stack.isEmpty()) {
                AVLBinaryNode<E> top = stack.peek();
                if (res == top.left) {
                    findNextLeaf(top.right); // find next leaf in right sub-tree
                }
            }
            return res.element;
        }

        @Override
        public void remove(){
            if(currentPos != null)
                currentPos.remove(currentPos.getElement());
            else
                throw new NullPointerException();
        }
    }

    public class PreOrderBinaryIterator<E> implements Iterator<E> {
        ArrayDeque<AVLBinaryNode<E>> stack = new ArrayDeque<>();
        AVLBinaryNode<E> currentPos = null;

        /** Constructor */
        public PreOrderBinaryIterator() {
            if (root != null) {
                stack.push((AVLBinaryNode<E>) root); // add to end of queue
            }
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            AVLBinaryNode<E> res = stack.pop(); // retrieve and remove the head of queue
            currentPos = res;
            if (res.right != null) stack.push(res.right);
            if (res.left != null) stack.push(res.left);

            return res.element;
        }

        @Override
        public void remove(){
            if(currentPos != null)
                currentPos.remove(currentPos.getElement());
            else
                throw new NullPointerException();
        }
    }

    @Override
    public Iterator<E> iterator(Traversal how) {
        switch (how){
            case InOrder:{
                return new InOrderBinaryIterator<>();
            }
            case PostOrder: {
                return new PostOrderBinaryIterator<>();
            }
            case PreOrder: {
                return new PreOrderBinaryIterator<>();
            }
            default:
                return null;
        }
    }

    //checks if element is in a tree
    @Override
    public boolean contains(E element) {
        return !isEmpty()&&root.contains(element);
    }

    //puts all elements in a set into the tree
    @Override
    public int putAll(SortedSet<? extends E> elements) {
        int returnValue = 0;
        //put each word in set in individually
        for(E value: elements){
            if(put(value)){
                returnValue++;
            }
        }
        return returnValue;
    }

    //inserts an element in the tree and checks for
    @Override
    public boolean put(E element) {
        if(isEmpty()){
            root=new AVLBinaryNode<>(element, null, comp);
            return true;
        }
        if(!contains(element)){
            AVLBinaryNode<E> x, y, z;
            z = root.put(element);
            y=z.parent;
            if(y==null){
                x=null;
            }
            else{
                x=y.parent;
            }
            while(x!=null){
                if(!(x.isBalanced())){
                    x=rebalance(x, y, z);
                }
                z = y;
                y = x;
                x = x.parent;
            }

            return true;
        }
        return false;
    }

    //check if element is present in tree and remove
    @Override
    public boolean remove(E element){
        return (removedValue(element)!=null);
    }

    public E removedValue(E element) {
        if(isEmpty())
            return null;
        if(contains(element)){
            if(comp.compare(root.getElement(),element)==0){
                E removedValue = root.element;
                if(root.left != null && root.right != null){
                    if(root.left.height() > root.right.height()){
                        root.element = root.left.max();
                        root.left.remove(root.element);
                    }
                    else{
                        root.element = root.right.min();
                        root.remove(root.element);
                    }
                }
                else if(root.isLeaf())
                    root = null;
                else if(root.left != null){
                    root = root.left;
                    root.parent = null;
                }
                else {
                    root = root.right;
                    root.parent = null;
                }
                return removedValue;
            }
            //check if rebalancing needed - starting at action position then working way up
            AVLBinaryNode<E> x, y, z, removedNode = root.remove(element);
            x=removedNode.parent;

            while(x!=null){
                if(!(x.isBalanced())){
                    y=x.tallerChild();
                    if(y==null){
                        z=null;
                    }
                    else{
                        z=y.tallerChild();
                    }
                    x=rebalance(x, y, z);
                }
                x = x.parent;
            }
            return removedNode.getElement();
        }
        return null;
    }

    private AVLBinaryNode<E> rebalance(AVLBinaryNode<E> x, AVLBinaryNode<E> y, AVLBinaryNode<E> z){
        AVLBinaryNode<E> p, a, b, c, t0, t1, t2, t3;
        if(z==z.parent.left){
            if(y==y.parent.left ) {
                a = z;
                b = y;
                c = x;
                t0 = z.left;
                t1 = z.right;
                t2 = y.right;
                t3 = x.right;
            }
            else{
                a = x;
                b = z;
                c = y;
                t0 = x.left;
                t1 = z.left;
                t2 = z.right;
                t3 = y.right;
            }
        }
        else{
            if(y==y.parent.left) {
                a = y;
                b = z;
                c = x;
                t0 = y.left;
                t1 = z.left;
                t2 = z.right;
                t3 = x.right;
            }
            else{
                a = x;
                b = y;
                c = z;
                t0 = x.left;
                t1 = y.left;
                t2 = z.left;
                t3 = z.right;
            }
        }
        if(x!=root) {
            p = x.parent;
            if (x==x.parent.left) {
                p.setLeft(b);
            } else {
                p.setRight(b);
            }
        }
        else{
            root = b;
            b.parent=null;
        }
        b.setLeft(a);
        b.setRight(c);
        a.setLeft(t0);
        a.setRight(t1);
        c.setLeft(t2);
        c.setRight(t3);

        return b;
    }

    //sets root node
    @Override
    public void setRoot(@Nullable AVLBinaryNode<E> root) {
        this.root = root;
        if(root!=null){
            root.parent=null;
        }
    }

    //returns root node
    @Nullable
    @Override
    public AVLBinaryNode<E> getRoot() {
        return root;
    }

    public E get(E element){
        if(root==null){
            return null;
        }
        AVLBinaryNode<E> temp = root.get(element);
        if(temp == null){
            return null;
        }
        return temp.getElement();
    }

    public int hashCode(){
        int out = 0;
        Iterator<E> itr = this.iterator(Traversal.InOrder);
        while(itr.hasNext())
            out = 31*out + itr.next().hashCode();
        return out;
    }

    public int hashCodeMap(){
        int out = 0;
        Iterator<E> itr = this.iterator(Traversal.InOrder);
        while(itr.hasNext())
            out += itr.next().hashCode();
        return out;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree<E, Node<E>> that = (Tree<E, Node<E>>) o;

        if(this.hashCode() == that.hashCode())
            return true;
        else
            return false;
    }

    @Override
    public String toString(){
        Iterator<E> itr = this.iterator(Traversal.InOrder);
        StringBuilder out = new StringBuilder();
        out.append("[");
        while(itr.hasNext()){
            E elem = itr.next();
            //if we are at the last element, don't add a comma
            if(!itr.hasNext()){
                if(elem == null)
                    out.append("null");
                else{
                    out.append(elem);
                }
                //Not at the last element, add a comma after the element
            }else {
                if (elem == null)
                    out.append("null, ");
                else {
                    out.append(elem + ", ");
                }
            }
        }
        out.append("]");
        return out.toString();
    }
}
