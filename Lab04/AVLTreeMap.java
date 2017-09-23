package ca.uwo.eng.se2205b.lab4;

import java.util.*;

/**
 * Created by MustafaDawoud on 2017-03-08.
 */
public class AVLTreeMap<K, V> extends AbstractMap<K, V>{
    private AVLTree<Entry<K, V>> uTree;

    public AVLTreeMap() {
        this.uTree = new AVLTree<>(new Comparator<Entry<K, V>>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return ((Comparable<K>)(o1.getKey())).compareTo(o2.getKey());
            }
        });
    }

    public AVLTreeMap(Comparator<Entry<K, V>> comp) {
        this.uTree = new AVLTree<>(comp);
    }

    @Override
    public int size() {
        return uTree.size();
    }

    @Override
    public boolean containsKey(Object key) {
        if(key==null){
            throw new NullPointerException("Key can't be null");
        }
        return uTree.contains(new SimpleEntry<K, V>((K)key, null));
    }

    @Override
    public V get(Object key) {
        if(key==null){
            throw new NullPointerException("Key cannot be null");
        }
        Entry<K, V> retEnt = uTree.get(new SimpleEntry<K, V>((K)key, null));
        if(retEnt==null){
            return null;
        }
        return retEnt.getValue();
    }

    @Override
    public Object put(Object key, Object value) {
        if(key==null){
            throw new NullPointerException("Key cannot be null");
        }
        if(uTree.put(new SimpleEntry<K, V>((K)key, (V)value))){
            return null;
        }
        return uTree.get(new SimpleEntry<K, V>((K)key, (V)value)).setValue((V)value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass()!=o.getClass()&&!(o instanceof Map))) return false;
        return this.hashCode()==o.hashCode();
    }

    @Override
    public int hashCode() {
        return uTree.hashCodeMap();
    }

    @Override
    public V remove(Object key) {
        if(key==null){
            throw new NullPointerException("Key cannot be null");
        }
        if(!containsKey(key)){
            return null;
        }
        Entry<K,V> temp = uTree.removedValue(new SimpleEntry<>((K)key, null));
        if(temp==null){
            return null;
        }
        return temp.getValue();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        EntrySet<K, V> set = new EntrySet<>();
        return set;
    }

    class EntrySet<K,V> extends AbstractSet<Entry<K,V>>{
        EntrySet(){ }

        @Override
        public int size() {
            return AVLTreeMap.this.size();
        }

        @Override
        public void clear() {
            AVLTreeMap.this.uTree.setRoot(null);
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new setIt();
        }
    }

    class setIt<K, V> implements Iterator<Entry<K,V>>{
        List<Entry<K, V>> elements;
        int count;
        Entry<K,V> curr;
        setIt(){
            elements = new ArrayList<>();
            Iterator temp = AVLTreeMap.this.uTree.iterator(Tree.Traversal.InOrder);
            while(temp.hasNext()){
                elements.add((Entry<K,V>)(temp.next()));
            }
            count = 0;
            curr = null;
        }

        @Override
        public boolean hasNext() {
            return count < elements.size();
        }

        @Override
        public Entry<K, V> next() {
            if(!hasNext()){
                throw new NoSuchElementException("no more elements");
            }
            curr = elements.get(count++);
            return curr;
        }

        @Override
        public void remove() {
            if(curr==null){
                throw new IllegalStateException("cannot remove before call to next");
            }
            AVLTreeMap.this.remove(curr.getKey());
            elements.remove(count-- - 1);
            curr=null;
        }
    }
}