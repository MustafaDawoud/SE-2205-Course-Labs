package ca.uwo.eng.se2205b.lab5;

import java.util.*;

/**
 * Created by musda on 3/14/2017.
 */
public class MyHashMap<k, v> extends AbstractMap implements IHashMap{

    @SuppressWarnings("unchecked")
    private SimpleEntry<k, v>[] hashMap = (SimpleEntry<k, v>[])(new SimpleEntry[13]);
    private SimpleEntry<k, v> DEFUNCT = new SimpleEntry<>(null, null);
    private int n;
    private double loadFactorThreshold;

    public MyHashMap(){
        n = 0;
        nullifyArray();
        loadFactorThreshold = 0.3;
    }

    public MyHashMap(double loadFactorThreshold){
        this();
        if(loadFactorThreshold <= 0 || loadFactorThreshold > 1)
            throw new IllegalArgumentException();
        this.loadFactorThreshold = loadFactorThreshold;
    }

    public void nullifyArray(){
        for(int i = 0; i < capacity(); i++)
            hashMap[i] = null;
    }

   @Override
    public int size() {
        return n;
    }

    @Override
    public boolean containsKey(Object key) {
        if(key == null)
            throw new NullPointerException();

        int i = key.hashCode()%capacity();

        if(i < 0)
            i +=capacity();

        for(int j = 0; j< capacity(); j++, i = (i+1)%capacity()) {
            if(hashMap[i] != null && hashMap[i].getKey() != null && hashMap[i].getKey().hashCode() == key.hashCode())
                return true;
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        if(key == null)
            throw new NullPointerException();

        int i = key.hashCode()%capacity();
        if(i < 0)
            i += capacity();

        for(int j = 0; j< capacity(); i = (i+1)%capacity(), j++) {
            if(hashMap[i] != null && hashMap[i].getKey() != null && hashMap[i].getKey().hashCode() == key.hashCode())
                return hashMap[i].getValue();
            else if(hashMap[i] == null)
                return null;
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) throws ClassCastException{
        if(key == null)
            throw new NullPointerException();
        SimpleEntry<k, v> entry = new SimpleEntry<>((k) key, (v)value);
        int i = key.hashCode()%capacity();

        if(i < 0)
            i +=capacity();

        for(; ; i = (i+1)%capacity()){
            if(hashMap[i] == null || hashMap[i].getKey() == null){
                hashMap[i] = entry;
                n++;
                if(loadFactor() > loadFactorThreshold())
                    rehash();
                return null;
            } else if(hashMap[i].getKey().hashCode() == key.hashCode()){
                v oldValue = hashMap[i].getValue();
                hashMap[i].setValue((v) value);
                return oldValue;
            }
        }
    }

    @Override
    public double loadFactorThreshold() {
        return loadFactorThreshold;
    }

    @Override
    public double loadFactor() {
        return (double)size()/capacity();
    }

    @Override
    public int capacity() {
        return hashMap.length;
    }

    public void rehash(){
        SimpleEntry<k, v>[] temp = (SimpleEntry<k, v>[])(new SimpleEntry[capacity()]);
        System.arraycopy(hashMap, 0, temp, 0, capacity());
        int newSize = nextDoublePrime(hashMap.length);
        hashMap = (SimpleEntry<k, v>[])(new SimpleEntry[newSize]);
        nullifyArray();
        for(int i = 0; i < temp.length; i++){
            int index = 0;
            if(temp[i] != null && temp[i].getKey() != null){
                index = temp[i].getKey().hashCode()%capacity();
                if(index < 0) index += capacity();
                if(hashMap[index] == null)
                    hashMap[index] = temp[i];
                else{
                    index = (index + 1)%hashMap.length;
                    while(hashMap[index]!=null){
                        index = (index+1)%hashMap.length;
                    }
                    hashMap[index] = temp[i];
                }
            }
        }
    }

    public boolean isPrime (int number) {
        //skip by 2 because the number we are passing odd numbers
        for (int i=3; (i*i) <= number; i+=2) {
            if (number % i == 0 )
                return false;
        }
        return true;
    }

    public int nextDoublePrime(int currentSize){
        //make it odd again by adding 1 after multiplying my 2
        //odd *2 = even +1 = odd
        currentSize = currentSize*2+1;
        while(!isPrime(currentSize)) {
            currentSize += 2;
        }
        return currentSize;
    }

    @Override
    public Object remove(Object key) {
        if(key == null)
            throw new NullPointerException();
        if(!containsKey(key))
            return null;
        int i = key.hashCode()%capacity();

        if(i < 0)
            i +=capacity();

        for(; ; i++, i = i%capacity()) {
            if(hashMap[i].getKey() != null && hashMap[i].getKey().hashCode() == key.hashCode()){
                v removedValue = hashMap[i].getValue();
                hashMap[i] = DEFUNCT;
                n--;
                return removedValue;
            }
        }
    }

    @Override
    public void clear() {
        hashMap = (SimpleEntry<k, v>[])(new SimpleEntry[capacity()]);
        n = 0;
    }

    @Override
    public Set<Entry<k, v>> entrySet() {
        EntrySet<k, v> set = new EntrySet<>();
        return set;
    }

    class EntrySet<K, V> extends AbstractSet<Entry<K,V>> {
        EntrySet(){ }

        @Override
        public int size() {
            return MyHashMap.this.size();
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new setIt();
        }
    }

    class setIt<k, v> implements Iterator<Entry<k,v>>{

        int index;
        int actSize;
        Entry<k,v> curr;

        setIt(){
            index = 0;
            actSize = 0;
            curr = null;
        }

        @Override
        public boolean hasNext() {
            return actSize < MyHashMap.this.size();
        }

        @Override
        public Entry<k, v> next() {
            if(!hasNext()){
                throw new NoSuchElementException("no more elements");
            }
            actSize++;
            while(hashMap[index] == null || hashMap[index].getKey() == null){
                index++;
            }
            return curr = (Entry<k, v>) hashMap[index++];
        }

        @Override
        public void remove() {
            if(curr==null){
                throw new IllegalStateException("cannot remove, current value is null, go to next value");
            }
            MyHashMap.this.remove(curr.getKey());
            actSize--;
            curr=null;
        }
    }

    @Override
    public int hashCode() {
        int out = 0;
        for(int i = 0; i<hashMap.length; i++){
            if(hashMap[i]!= null && hashMap[i].getKey() != null)
                out += hashMap[i].hashCode();
        }
        return out;
    }

    public boolean equals(Map<k, v> o) {
        if (this == o) return true;
        if (o == null) return false;
        return this.hashCode() == o.hashCode();
    }
}
