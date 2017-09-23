package ca.uwo.eng.se2205.lab7.graphs;

import com.sun.corba.se.spi.ior.ObjectKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by musda on 3/26/2017.
 */
public class DirectedGraph<V, E> implements Graph<V, E> {

    private HashMap<Vertex, HashSet<DEdge>> adjacencyList;
    private int numOfEdges;
    private int numOfVertices;

    public DirectedGraph(){
        adjacencyList = new HashMap<>();
        numOfEdges = 0;
        numOfVertices = 0;
    }

    private class DVertex implements Vertex{

        private V element;

        public DVertex(){
            element = null;
        }

        @Override
        public Graph graph() {
            return DirectedGraph.this;
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
        public Collection<? extends Edge> incomingEdges() {
            return adjacencyList.get(this).stream().filter(e->e.v() == this).collect(Collectors.toSet());
        }

        @Override
        public Collection<? extends Edge> outgoingEdges() {
            return adjacencyList.get(this).stream().filter(e->e.u() == this).collect(Collectors.toSet());
        }

        @Override
        public boolean equals(Object o) {
            return o == this;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }
    }

    private class DEdge implements Edge.Directed<E, V> {

        private DVertex u;
        private DVertex v;
        public E weight;

        public DEdge(){
            weight = null;
            u = null;
            v = null;
        }

        @Override
        public Graph<V, E> graph() {
            return DirectedGraph.this;
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
            return u;
        }

        @Override
        public Vertex<V, E> v() {
            return v;
        }

        @Override
        public Vertex<V, E> opposite(Vertex<V, E> vertex) {
            if(vertex.equals(u))
                return v;
            else if(vertex.equals(v))
                return u;
            else
                return null;
        }

        @Override
        public int hashCode(){
            //return System.identityHashCode(this);
            return u().hashCode() ^ v().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
            /*if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DEdge dEdge = (DEdge) o;
            if (u != null ? !u.equals(dEdge.u()) : dEdge.u() != null) return false;
            return v != null ? v.equals(dEdge.v()) : dEdge.v() == null;*/
        }
    }

    class DirectedVertex extends AbstractSet implements Collection{

        @Override
        public Iterator iterator() {
            return new DirectedVertexIterator();
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

    class DirectedVertexIterator implements Iterator{

        Set<Vertex> setOfVertices;
        Iterator<Vertex> itr;
        DVertex curr;

        public DirectedVertexIterator(){
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
            return curr = (DVertex) itr.next();
        }

        @Override
        public void remove() {
            if (itr == null)
                throw new NoSuchElementException();

            DVertex toRemove = curr;

            HashSet<DEdge> vertexEdges = adjacencyList.get(toRemove);
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
            itr.remove();
        }
            /*if(curr == null)
                throw new NoSuchElementException();
            HashSet<DEdge> vertexEdges = adjacencyList.get(curr);
            numOfVertices--;
            for(Vertex i: setOfVertices){
                for(DEdge e: adjacencyList.get(i)){
                    if(vertexEdges.contains(e)) {
                        adjacencyList.get(i).remove(e);
                        numOfEdges--;
                    }
                }
            }
            //adjacencyList.remove(curr, vertexEdges);
            //setOfVertices.remove(curr);
            itr.remove();
        }*/
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

        Set<DEdge> setOfEdges;
        DEdge curr;
        Iterator<DEdge> itr;

        public DirectedEdgeIterator(){
            setOfEdges = new HashSet<>();
            for(Vertex i: adjacencyList.keySet()){
                for(DEdge e: adjacencyList.get(i)){
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
            /*adjacencyList.get(curr.u()).remove(curr);
            adjacencyList.get(curr.v()).remove(curr);
            itr.remove();*/
        }
    }

    @Override
    public Collection<? extends Vertex<V, E>> vertices() {
        return new DirectedVertex();
    }

    @Override
    public Vertex<V, E> newVertex(@Nonnull V element) {
        DVertex newVertex = new DVertex();
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
        DEdge newEdge = new DEdge();
        newEdge.setWeight(weight);
        newEdge.v = (DVertex) v;
        newEdge.u = (DVertex) u;
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
        return adjacencyList.get(u).stream().filter(e->(e.u() ==u && e.v() == v)).findFirst().get();
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

    Map<Vertex<V, E>, Edge<E, V>> forest = new HashMap<>();
    HashSet<Vertex<V, E>> known = new HashSet<>();
    Map<Vertex<V, E>, Edge<E, V>> discovery = new HashMap<>();
    /*public void dfsForest(Graph<V, E> g, Vertex<V, E> s){
        Deque<Vertex<V, E>> vertices_to_visit = new ArrayDeque<>();
        vertices_to_visit.add(s);
        while(!vertices_to_visit.isEmpty()){
            Vertex<V, E> u = vertices_to_visit.removeLast();
            known.add(u);
            forest.put(u, discovery.get(u));
            for(Edge<E, V> e : g.outgoingEdges(u)){
                if(!known.contains(e.opposite(u))){
                    discovery.put(e.opposite(u), e);
                    vertices_to_visit.addLast(e.opposite(u));
                }
            }
        }
    }
    public void dfsForestCaller(Graph<V, E> g){
        for(Vertex<V, E> v: g.vertices()){
            forest.put(v, null);
            discovery.put(v, null);
        }
        for(Vertex<V, E> v:g.vertices()){
            if(!known.contains(v))
                dfsForest(g, v);
        }
    }

    public Map<Vertex<V, E>, Edge<E, V>> getForest(){
        return forest;
    }*/

    public void dijkstras(Graph<V, Double> g, Vertex<V, Double> source){
        HashMap<Vertex<V, E>, Double> values = new HashMap<>();
        PriorityQueue<Vertex<V, Double>> pq = new PriorityQueue<>();
        for(Vertex v : g.vertices()){
            values.put(v, Double.MAX_VALUE);
        }
    }
    Deque<Integer> i = new ArrayDeque<Integer>();

    public void dfs(Graph<V, E> g, Vertex<V, E> s){
        Deque<Vertex<V, E>> vertices_to_visit = new ArrayDeque<>();
        vertices_to_visit.add(s);
        while(!vertices_to_visit.isEmpty()){
            Vertex<V, E> u = vertices_to_visit.removeFirst();
            known.add(u);
           // System.out.println(u.getElement());
            for(Edge<E, V> e : g.outgoingEdges(u)){
                if(!known.contains(e.opposite(u))){
                    System.out.println(e.opposite(u).getElement());
                    vertices_to_visit.addLast(e.opposite(u));
                }
            }
        }
    }

    public void dfsRec(Graph<V, E> G, Vertex<V, E> s, HashSet<Vertex> known1){
        known1.add(s);
        for(Edge<E, V> e: G.outgoingEdges(s)){
            if(!known.contains(e.opposite(s))){
                dfsRec(G, e.opposite(s), known1);
            }
        }
    }

    public void dfsCaller(Graph<V, E> g){
        HashSet<Vertex> known1 = new HashSet<>();
        for(Vertex<V, E> v:g.vertices()){
            if(!known1.contains(v))
                dfsRec(g, v, known1);
        }
    }

    public void buildOrder(List<Character> proj, Set<Map.Entry<Character, Character>> dependancies){
        HashSet<Vertex> known = new HashSet<>();
        Graph<Character, Character> gr = new DirectedGraph<>();
        HashMap<Character, Vertex> pv = new HashMap<>();
        for(Character c: proj){
            pv.put(c, gr.newVertex(c));
        }
        for(Map.Entry<Character, Character> d: dependancies){
            gr.newEdge(pv.get(d.getKey()), pv.get(d.getValue()), null);
        }
        List<Vertex> order = new ArrayList<>();
        for(Vertex v : gr.vertices()){
            if(!known.contains(v)){
                List <Vertex> temp = buildOrder(known, gr, v);
                Collections.reverse(temp);
                order.addAll(temp);
            }
        }
        if(!gr.edges().isEmpty())
            throw new RuntimeException("Cycle exist");
        for(Vertex v : order){
            System.out.println(v.getElement());
        }
    }

    public List<Vertex> buildOrder(HashSet<Vertex> known, Graph<Character, Character> gr, Vertex v){
        Deque<Vertex> stack = new ArrayDeque<>();
        List<Vertex> order = new ArrayList<>();
        stack.addLast(v);
        while (!stack.isEmpty()){
            Vertex u = stack.removeLast();
            known.add(u);
            for(Object e : gr.outgoingEdges(u)){
                if(!known.contains(((Edge) e).opposite(u))){
                    known.add(((Edge) e).opposite(u));
                    //gr.removeEdge(e);
                }
            }
        }
        return null;
    }

}
