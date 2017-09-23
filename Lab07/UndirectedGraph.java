package ca.uwo.eng.se2205.lab7.graphs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by musda on 3/30/2017.
 */
public class UndirectedGraph<V, E> implements Graph<V, E>{

    private HashMap<Vertex, HashSet<UndEdge>> adjacencyList;
    private int numOfEdges;
    private int numOfVertices;

    public UndirectedGraph(){
        adjacencyList = new HashMap<>();
        numOfEdges = 0;
        numOfVertices = 0;
    }

    private class UndVertex implements Vertex{

        private V element;

        public UndVertex(){
            element = null;
        }

        @Override
        public Graph graph() {
            return UndirectedGraph.this;
        }

        @Override
        public V getElement() {
            return element;
        }

        @Override
        public Object setElement(Object element) {
            if(element == null)
                throw new NullPointerException();
            V oldElement = this.element;
            this.element = (V)element;
            return oldElement;
        }

        @Override
        public Collection<? extends Edge> incomingEdges() {return this.outgoingEdges(); }

        @Override
        public Collection<? extends Edge> outgoingEdges() {
            return adjacencyList.get(this).stream().filter(e->(e.u() == this || e.v() == this)).collect(Collectors.toSet());
        }

        @Override
        public int hashCode(){
            return System.identityHashCode(this);
        }

        @Override
        public boolean equals(Object o) {
            return o == this;
        }
    }

    private class UndEdge implements Edge.Directed<E, V> {

        private UndVertex origin;
        private UndVertex destination;
        public E weight;

        @Override
        public Graph<V, E> graph() {
            return UndirectedGraph.this;
        }

        @Override
        public E getWeight() {
            return weight;
        }

        @Override
        public void setWeight(@Nullable E weight) {
            this.weight = weight;
        }

        @Override
        public Vertex u() {
            return origin;
        }

        @Override
        public Vertex<V, E> v() {
            return destination;
        }

        @Override
        public Vertex<V, E> opposite(Vertex<V, E> vertex) {
            if(vertex.equals(origin))
                return destination;
            else if(vertex.equals(destination))
                return origin;
            else
                return null;
        }

        @Override
        public int hashCode(){
            return u().hashCode() ^ v().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }
    }

    class UndirectedVertex extends AbstractSet implements Collection{

        @Override
        public Iterator iterator() {
            return new UndirectedVertexIterator();
        }

        @Override
        public int size() {
            return numOfVertices;
        }

        @Override
        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }
    }

    class UndirectedVertexIterator implements Iterator{

        Set<Vertex> setOfVertices;
        Iterator<Vertex> itr;
        Vertex curr;

        public UndirectedVertexIterator(){
            setOfVertices = adjacencyList.keySet();
            itr = setOfVertices.iterator();
            curr = null;
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public Object next() {
            return curr = itr.next();
        }

        @Override
        public void remove() {
            if (itr == null)
                throw new NoSuchElementException();

            Vertex toRemove = curr;

            HashSet<UndEdge> vertexEdges = adjacencyList.get(toRemove);
            numOfVertices--;

            for (Vertex v : adjacencyList.keySet()){
                for (Object e : v.incomingEdges()){
                    if (vertexEdges.contains(e)){
                        v.incomingEdges().remove(e);
                        numOfEdges--;
                    }
                }

                for (Object e : v.outgoingEdges()){
                    if (vertexEdges.contains(e)){
                        v.incomingEdges().remove(e);
                        numOfEdges--;
                    }
                }
            }
            itr.remove();/*
            if(curr == null)
                throw new NoSuchElementException();
            HashSet<UndEdge> vertexEdges = adjacencyList.get(curr);
            numOfVertices--;
            for(Vertex i: setOfVertices){
                for(UndEdge e: adjacencyList.get(i)){
                    if(vertexEdges.contains(e)) {
                        adjacencyList.get(i).remove(e);
                        numOfEdges--;
                    }
                }
            }
            //adjacencyList.remove(curr, vertexEdges);
            //setOfVertices.remove(curr);
            itr.remove();*/
        }
    }

    class DirectedEdge extends AbstractSet implements Collection{

        @Override
        public Iterator iterator() {
            return new DirectedEdgeIterator();
        }

        @Override
        public int size() {
            return numOfEdges;
        }

        @Override
        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }
    }

    class DirectedEdgeIterator implements Iterator{

        Set<UndEdge> setOfEdges;
        UndEdge curr;
        Iterator<UndEdge> itr;

        public DirectedEdgeIterator(){
            setOfEdges = new HashSet<>();
            for(Vertex i: adjacencyList.keySet()){
                for(UndEdge e: adjacencyList.get(i)){
                    setOfEdges.add(e);
                }
            }
            itr = setOfEdges.iterator();
            curr = null;
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public Object next() {
            return curr = itr.next();
        }

        @Override
        public void remove() {
            if(curr == null)
                throw new NoSuchElementException();
            Vertex u, v;
            u = curr.u();
            v = curr.v();

            adjacencyList.get(u).remove(curr);
            adjacencyList.get(v).remove(curr);
            itr.remove();
            numOfEdges--;
        }
    }

    @Override
    public Collection<? extends Vertex<V, E>> vertices() {
        return new UndirectedVertex();
    }

    @Override
    public Vertex<V, E> newVertex(@Nonnull V element) {
        UndVertex newVertex = new UndVertex();
        newVertex.setElement(element);
        adjacencyList.put(newVertex, new HashSet<>());
        numOfVertices++;
        return newVertex;
    }

    @Override
    public Collection<? extends Edge<E, V>> edges() {
        return new DirectedEdge();
    }

    @Override
    public Edge<E, V> newEdge(@Nonnull Vertex<V, E> u, @Nonnull Vertex<V, E> v, @Nonnull E weight){
        UndEdge newEdge = new UndEdge();
        newEdge.setWeight(weight);
        newEdge.destination = (UndVertex) v;
        newEdge.origin = (UndVertex) u;
        adjacencyList.get(u).add(newEdge);
        adjacencyList.get(v).add(newEdge);
        numOfEdges++;
        return newEdge;
    }

    @Nullable
    @Override
    public Edge<E, V> getEdge(@Nonnull Vertex<V, E> u, @Nonnull Vertex<V, E> v) {
        if(adjacencyList.isEmpty() || !(adjacencyList.containsKey(u) && adjacencyList.containsKey(v)))
            return null;
        return adjacencyList.get(u).stream().filter(e->e.opposite(u) == v).findFirst().get();
    }

    @Override
    public Collection<? extends Edge<E, V>> incomingEdges(@Nonnull Vertex<V, E> v){
        if(adjacencyList.isEmpty() || !adjacencyList.containsKey(v))
            return null;
        return v.incomingEdges();
    }

    @Override
    public Collection<? extends Edge<E, V>> outgoingEdges(@Nonnull Vertex<V, E> v) {
        if(adjacencyList.isEmpty() || !adjacencyList.containsKey(v))
            return null;
        return v.outgoingEdges();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectedGraph<?, ?> that = (DirectedGraph<?, ?>) o;

        return edges().equals(that.edges()) && vertices().equals(o);
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
        //return edges().hashCode() + vertices().hashCode();
    }
}