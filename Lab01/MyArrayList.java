package ca.uwo.eng.se2205b.lab01;

import java.util.AbstractList;
import java.util.List;
import java.lang.StringBuilder;

/**
 * Array List implementation
 */
public class MyArrayList<T> extends AbstractList<T> {
    //Field members used
    private T[] _container;
    private int _capacity;
    private int _size;

    /**
     * Default Constructor
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        _container = (T[])(new Object[10]);
        _capacity = 10;
        _size = 0;
    }

    /**
     *@throws throws an error if base points to nothing
     * @param base List of type unknown that extends T
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(List<? extends T> base) {
        if(base == null){
            throw new NullPointerException();
        }
        _container = (T[])(new Object[base.size()]);
        _capacity = base.size();
        _size = 0;
        //Copying over the array
        for(T i: base){
            _container[_size] = i;
            _size++;
        }
    }

    /**
     * @Throw throws an IllegalArgumentException if the initial capacity is less than 0
     *Used to initialize the array with a specific user input
     * @param initialCapacity used for the initial size of the array
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        if(initialCapacity < 0){
            throw new IllegalArgumentException();
        }
        _container = (T[])(new Object[initialCapacity]);
        _capacity = initialCapacity;
        _size = 0;
    }

    /**
     ** @param index
     * @return returns the value at index
     */
    @Override
    public T get(int index){
        if(index < 0 || index >= _size){
            throw new IndexOutOfBoundsException();
        }
        return _container[index];
    }

    /**
     *@return size of the arrays
     */
    @Override
    public int size(){
        return _size;
    }

    /**
     * @param index of the element that we want to change its value
     * @param element the element value that we are setting the index to
     * @return the oldvalue that was at that position before
     */
    @Override
    public T set(int index, T element){
        if(index < 0 || index >= _size){
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T)_container[index];
        _container[index] = element;
        return oldValue;
    }

    /**
     * @throws throws IndexOutOfBoundsException if index inputted is outside the range of the array size
     * @param index the index where we are adding the new value
     * @param e the value of the new thing that is being added
     */
    @Override
    public void add(int index, T e){
        if(index < 0 || index > _size){
            throw new IndexOutOfBoundsException();
        }
        //Checking if the array is empty, exceptional case, should default it to capacity of 10
        if(_capacity == 0){
            _capacity = 10;
            _container = (T[])(new Object[10]);
        }
        //If full, double its capacity
        else if(_size == _capacity)
            doublecapacity();
        System.arraycopy(_container, index, _container, index + 1, _size-index);
        _container[index] = e;
        _size++;
    }

    /**
     *Doubles the capacity of the array
     */
    public void doublecapacity(){
        T[] temp = (T[])(new Object[_capacity*2]);
        _capacity = 2*_capacity;
        System.arraycopy(_container,0, temp, 0, _size);
        _container = (T[])(new Object[_capacity]);
        System.arraycopy(temp,0, _container, 0, _size);
    }

    /**
     * Removes a specific element at a specific index
     * @param index of the element that the user wants to user
     * @return returns the element of the removed index
     */
    @Override
    public T remove(int index){
        if(index < 0 || index >= _size){
            throw new IndexOutOfBoundsException();
        }
        T removedIndex = (T)_container[index];
        System.arraycopy(_container,index+1, _container, index, _size-index-1);
        _size--;
        return removedIndex;
    }

    /**
     * Converts the list to a string, format is starts and ends with [ ], every element is separated by a comma,
     * and saves null values as null
     * @return a string of the entire of a list
     */
    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("[");
        for(int i = 0; i<_size; i++){
            //if we are at the last element, don't add a comma
            if(i == _size-1){
                if(_container[i] == null)
                    out.append("null");
                else{
                    out.append(_container[i]);
                }
                //Not at the last element, add a comma after the element
            }else {
                if (_container[i] == null)
                    out.append("null, ");
                else {
                    out.append(_container[i] + ", ");
                }
            }
        }
        out.append("]");
        return out.toString();
    }

    /**
     * Makes sure that their size equals, and that each element and if an element is null, they are both null
     * @param obj another list of type MyArrayList
     * @return true if they are equal, false otherwise
     */
    public boolean equals(MyArrayList<T> obj){
        // Check if the type of `o` is a List, if not, return false, they can't be equal
        if (!List.class.isAssignableFrom(obj.getClass())) return false;

        // Cast o to a List of Objects
        List<?> obj1 = (List<?>)obj;

        if(this.size() != obj1.size())
            return false;
        for(int i = 0; i < _size; i++){
            if(!_container[i].equals(obj1.get(i)) || !(_container[i] == null && obj1.get(i) == null)){
                return false;
            }
        }
        return true;
    }
}
