package ca.uwo.eng.se2205b.lab01;

import java.util.AbstractList;
import java.util.List;

/**
 * Doubly linked list implementation
 */
public class MyLinkedList<T> extends AbstractList<T> {
    //Member Variable to hold size, head and tail node
    private int m_size = 0;
    private Node m_head;
    private Node m_tail;

    /**
     * Node class that defines every index in the list
     * @param <T>
     */
    private static class Node<T> {
        private T data;
        private Node next;
        private Node prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.data = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Default Constructor
     */
    public MyLinkedList() {
        m_head = null;
        m_tail = null;
        m_size = 0;
    }

    /**
     *@throws throws an error if base points to nothing
     * @param base List of type unknown that extends T
     */
    public MyLinkedList(List<? extends T> base) {
        if (base == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < base.size(); i++) {
            add(i, base.get(i));
        }
    }

    /**
     * @throws throws IndexOutOfBoundsException if index inputted is outside the range of the array size
     * @param index the index where we are adding the new value
     * @param e the value of the new thing that is being added
     */
    @Override
    public void add(int index, T e) {
        if (index < 0 || index > m_size) {
            throw new IndexOutOfBoundsException();
        }
        //If size is 0, add a new node and make the head and the tail point ti it
        if (m_size == 0)
            m_head = m_tail = new Node(e, null, null);
        //Add a new head
        else if(index == 0){
            m_head = new Node(e, m_head, null);
            m_head.next.prev = m_head;
        }
        //Add a new tail
        else if (index == m_size) {
            m_tail.next = new Node(e, null, m_tail);
            m_tail = m_tail.next;
         //Adding at index
        } else {
            Node temp = m_head;
            for (int i = 1; i < index; i++) {
                temp = temp.next;
            }
            temp.next = new Node(e, temp.next, temp);
            temp.next.next.prev = temp.next;
        }
        m_size++;
    }

    /**
     ** @param index
     * @return returns the value at index
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= m_size) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = m_head;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return (T)temp.data;
    }


    /**
     *@return size of the arrays
     */
    @Override
    public int size(){
        return m_size;
    }

    /**
     * @param index of the element that we want to change its value
     * @param element the element value that we are setting the index to
     * @return the oldvalue that was at that position before
     */
    @Override
    public T set(int index, T element){
        if(index < 0 || index >= m_size){
            throw new IndexOutOfBoundsException();
        }
        T oldValue;
        Node temp = m_head;
        for(int i = 1; i<=index; i++)
            temp = temp.next;
        oldValue = (T)temp.data;
        temp.data = element;
        return oldValue;
    }

    /**
     * Removes a specific element at a specific index
     * @param index of the element that the user wants to user
     * @return returns the element of the removed index
     */
    @Override
    public T remove(int index){
        if(index < 0 || index >= m_size){
            throw new IndexOutOfBoundsException();
        }
        T removedValue;
        //Removing the head
        if(index == 0){
            removedValue = (T)m_head.data;
            m_head = m_head.next;
        }
        //Removing the tail
        else if(index == m_size-1){
            removedValue = (T)m_tail.data;
            m_tail = m_tail.prev;
            m_tail.next = null;
        }
        //removing at index
        else {
            Node temp = m_head;
            for (int i = 1; i <= index; i++)
                temp = temp.next;
            removedValue = (T) temp.data;
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        m_size--;
        return removedValue;
    }

    /**
     * Converts the list to a string, format is starts and ends with [ ], every element is separated by a comma,
     * and saves null values as null
     * @return a string of the entire of a list
     */
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("[");
        Node temp = m_head;
        for(int i = 1; i<= m_size; i++){
            if(i == m_size){
                if(temp.data == null)
                    out.append("null");
                else{
                    out.append(temp.data);
                }
            }else {
                if (temp.data == null)
                    out.append("null, ");
                else {
                    out.append(temp.data + ", ");
                }
            }
            temp = temp.next;
        }
        out.append("]");
        return out.toString();
    }

    /**
     * Makes sure that their size equals, and that each element and if an element is null, they are both null
     * @param obj another list of type MyArrayList
     * @return true if they are equal, false otherwise
     */
    public boolean equals(MyLinkedList<T> obj){
        // Check if the type of `o` is a List, if not, return false, they can't be equal
        if (!List.class.isAssignableFrom(obj.getClass())) return false;
        // Cast o to a List of Objects
        List<?> obj1 = (List<?>)obj;
        if(this.size() != obj.size())
            return false;
        Node temp = m_head;
        for(int i = 1; i <= m_size; i++){
            if(!temp.data.equals(obj1.get(i-1)) || !(temp.data == null && obj1.get(i-1) == null)){
                return false;
            }
            temp = temp.next;
        }
        return true;
    }
}