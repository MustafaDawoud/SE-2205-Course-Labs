package ca.uwo.eng.se2205.lab7.graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link DirectedGraph} implementation
 */
@Testable
@DisplayName("A Directed Graph")
public class DirectedGraphTest {

    @Test
    public void testForest() {

        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer, Integer>();

        Vertex<Integer, Integer> vert3 = graph.newVertex(3);
        Vertex<Integer, Integer> vert4 = graph.newVertex(4);
        Vertex<Integer, Integer> vert5 = graph.newVertex(5);
        Vertex<Integer, Integer> vert6 = graph.newVertex(6);
        Vertex<Integer, Integer> vert7 = graph.newVertex(7);
        Vertex<Integer, Integer> vert8 = graph.newVertex(8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e37 = graph.newEdge(vert3, vert7, 13);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);
        Edge<Integer,Integer> e86 = graph.newEdge(vert8, vert6, 30);

        graph.dfs(graph, vert3);

    }

    /*
        Edge<Integer, Integer> e34 = graph.newEdge(vert3, vert4, 10000);
        Edge<Integer, Integer> e37 = graph.newEdge(vert3, vert7, 13000);
        Edge<Integer, Integer> e47 = graph.newEdge(vert4, vert7, 50000);
        Edge<Integer, Integer> e45 = graph.newEdge(vert4, vert5, 30000);*/

    //graph.dfsForestCaller(graph);
    //Map<Vertex<Integer, Integer>, Edge<Integer, Integer>> forest = graph.getForest();
        /*for(Map.Entry<Vertex<Integer, Integer>, Edge<Integer, Integer>> v: forest.entrySet()){
            System.out.println(v.getKey().getElement()+" ");
            if(v.getValue()!= null)
                System.out.println(v.getValue().getWeight());
        }*/

    @Test
    public void testPresence(){

        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer,Integer>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        ArrayList<Vertex> listOfVertices = new ArrayList<Vertex>();
        listOfVertices.add(vert3);
        listOfVertices.add(vert4);
        listOfVertices.add(vert5);
        listOfVertices.add(vert6);
        listOfVertices.add(vert7);
        listOfVertices.add(vert8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e37 = graph.newEdge(vert3, vert7, 13);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);
        Edge<Integer,Integer> e86 = graph.newEdge(vert8, vert6, 30);

        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges.add(e34);
        listOfEdges.add(e37);
        listOfEdges.add(e47);
        listOfEdges.add(e45);
        listOfEdges.add(e58);
        listOfEdges.add(e65);
        listOfEdges.add(e86);

        for(Vertex v : listOfVertices)
            assertTrue(graph.vertices().contains(v));

        for(Edge e : listOfEdges)
            assertTrue(graph.edges().contains(e));
    }

    @Test
    public void incomingAndOutgoingEdges() {
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer,Integer>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        Edge<Integer,Integer> e34 = graph.  newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e37 = graph.newEdge(vert3, vert7, 13);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);
        Edge<Integer,Integer> e86 = graph.newEdge(vert8, vert6, 30);

        assertFalse(graph.incomingEdges(vert5).contains(e47));

        assertTrue(graph.incomingEdges(vert5).contains(e65));
        assertTrue(graph.incomingEdges(vert5).contains(e45));
        assertEquals(2, graph.incomingEdges(vert5).size());

        assertTrue(graph.outgoingEdges(vert5).contains(e58));
        assertEquals(1, graph.outgoingEdges(vert5).size());
    }

    @Test
    public void testWeightAndElement(){
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer,Integer>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);

        assertTrue(vert3.getElement() == 3);
        assertTrue(e34.getWeight() == 10);
    }

    @Test
    public void testIterators(){
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer,Integer>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        ArrayList<Vertex> listOfVertices = new ArrayList<Vertex>();
        listOfVertices.add(vert3);
        listOfVertices.add(vert4);
        listOfVertices.add(vert5);
        listOfVertices.add(vert6);
        listOfVertices.add(vert7);
        listOfVertices.add(vert8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e37 = graph.newEdge(vert3, vert7, 13);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);
        Edge<Integer,Integer> e86 = graph.newEdge(vert8, vert6, 30);

        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges.add(e34);
        listOfEdges.add(e37);
        listOfEdges.add(e47);
        listOfEdges.add(e45);
        listOfEdges.add(e58);
        listOfEdges.add(e65);
        listOfEdges.add(e86);

        Iterator vertexIt = graph.vertices().iterator();
        Vertex nextVertex = (Vertex) vertexIt.next();

        while (vertexIt.hasNext()) {
            assertTrue(listOfVertices.contains(nextVertex));
            nextVertex = (Vertex) vertexIt.next();
        }

        Iterator edgeIt = graph.edges().iterator();
        Edge nextEdge = (Edge) edgeIt.next();

        while (edgeIt.hasNext()) {
            assertTrue(listOfEdges.contains(nextEdge));
            nextEdge = (Edge) edgeIt.next();
        }
    }

    @Test
    public void testRemove(){
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<Integer,Integer>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        ArrayList<Vertex> listOfVertices = new ArrayList<Vertex>();
        listOfVertices.add(vert3);
        listOfVertices.add(vert4);
        listOfVertices.add(vert5);
        listOfVertices.add(vert6);
        listOfVertices.add(vert7);
        listOfVertices.add(vert8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e37 = graph.newEdge(vert3, vert7, 13);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);
        Edge<Integer,Integer> e86 = graph.newEdge(vert8, vert6, 30);

        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges.add(e34);
        listOfEdges.add(e37);
        listOfEdges.add(e47);
        listOfEdges.add(e45);
        listOfEdges.add(e58);
        listOfEdges.add(e65);
        listOfEdges.add(e86);

        Iterator vertexIt = graph.vertices().iterator();
        Vertex toRemoveVertex = (Vertex) vertexIt.next();

        assertTrue(listOfVertices.contains(toRemoveVertex));
        vertexIt.remove();

        assertFalse(graph.vertices().contains(toRemoveVertex));
        vertexIt.next();

        Iterator edgeIt = graph.edges().iterator();
        Edge toRemoveEdge = (Edge) edgeIt.next();

        assertTrue(listOfEdges.contains(toRemoveEdge));
        edgeIt.remove();

        assertFalse(graph.edges().contains(toRemoveEdge));
        edgeIt.next();
    }

    @Test
    public void testEquals(){
        DirectedGraph<Integer, Integer> graph1 = new DirectedGraph<Integer,Integer>();
        DirectedGraph<Integer, Integer> graph2 = new DirectedGraph<Integer,Integer>();

        graph1.newVertex(3);
        graph1.newVertex(4);
        graph1.newVertex(5);

        graph2.newVertex(3);
        graph2.newVertex(4);
        graph2.newVertex(5);

    }

    private Graph<String, Integer> graph;

    @BeforeEach
    void init() {
        graph = new DirectedGraph<>();
    }

    @Nested
    @DisplayName("creates vertices")
    public class NewVertex {

        @Test
        public void multi() {

            List<String> values = Arrays.asList("A", "B", "C", "D");
            List<Vertex<String, Integer>> verts = new ArrayList<>(values.size());

            for (String v : values) {
                Vertex<String, Integer> vert = graph.newVertex(v);
                assertNotNull(vert, () -> "Could not create vertex: " + v);
                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
                verts.add(vert);
            }

            // check they're still found
            for (Vertex<String, Integer> vert : verts) {
                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
            }
        }

        @Test
        public void duplicateElementAllowed() {
            Vertex<String, Integer> vA = graph.newVertex("A");
            assertNotNull(vA, "Could not create vertex: A");
            assertNotNull(graph.vertices().contains(vA), "Could not find vertex");

            Vertex<String, Integer> vA2 = graph.newVertex("A");
            assertNotNull(vA2, "Could not create duplicate vertex: A");
            assertNotNull(graph.vertices().contains(vA), () -> "Could not find original vertex: " + vA);
            assertNotNull(graph.vertices().contains(vA2), () -> "Could not find duplicate vertex: " + vA2);
        }

        @Nested
        @DisplayName("with Vertex properties")
        class VertexOperations {

            Vertex<String, Integer> vertex;

            @BeforeEach
            void createVertex() {
                vertex = DirectedGraphTest.this.graph.newVertex("bar");
            }

            @Test
            void canNotSetNullElement() {
                assertThrows(NullPointerException.class, () -> vertex.setElement(null));
            }

            @Test
            void elementBehaves() {
                String old = vertex.getElement();
                assertEquals(old, vertex.setElement("foo"), "Could not change element");
                assertEquals("foo", vertex.getElement());
            }
        }
    }

    @Nested
    @DisplayName("modified via edges()")
    class EdgesMethod {

        private Collection<Edge<Integer, String>> edges;

        Vertex<String, Integer> vA, vB;
        Edge<Integer, String> eAB;

        @BeforeEach
        void init() {
            vA = graph.newVertex("A");
            vB = graph.newVertex("B");

            assertNotNull(vA, "Could not create vertex: A");
            assertNotNull(vB, "Could not create vertex: B");
            this.edges = (Collection<Edge<Integer, String>>) graph.edges();

            eAB = graph.newEdge(vA, vB, 0);
            assertNotNull(eAB, () -> "Could not create edge between " + vA + " and " + vB);
        }

        private void trySuccessfulRemove(Edge<Integer, String> e) {
            assertTrue(edges.remove(e), () -> "Could not remove: " + e);
            assertFalse(edges.contains(e), () -> "Contained " + e + " after removal");
        }

        @Test
        public void removeExisting() {
            trySuccessfulRemove(eAB);
        }

        @Test
        public void addUnsupported() {
            assertThrows(UnsupportedOperationException.class, () -> edges.add(eAB));
        }
    }

    @DisplayName("supports outgoingEdges()")
    @Nested
    class OutgoingEdges {

        Vertex<String, Integer> foo, bar;

        @BeforeEach
        void init() {
            foo = graph.newVertex("foo");
            assertNotNull(foo, "Could not create vertex: foo");
            bar = graph.newVertex("bar");
            assertNotNull(bar, "Could not create vertex: bar");
        }

        @Test
        void singleIncoming() {
            Edge<Integer, String> e = graph.newEdge(bar, foo, 2);

            Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
            assertNotNull(edges, "Got null collection, should never be null");
            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);

            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e, edge, "Incorrect edge returned");
        }

        @Test
        void afterRemoval() {
            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);

            final Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
            assertNotNull(edges, "Got null collection, should never be null");

            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);
            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e1, edge, "Incorrect edge returned");

            assertTrue(graph.edges().remove(e1), () -> "Could not remove " + e1 + " from collection: " + graph.edges());

            final Collection<? extends Edge<Integer, String>> edges2 = graph.outgoingEdges(bar);
            assertNotNull(edges2, "Got null collection, should never be null");
            assertTrue(edges2.isEmpty(), () -> "Found invalid edge collection after removal of " + e1 + ": " + edges2);
        }

    }

    @DisplayName("can query edges")
    @Nested
    class EdgeQuery {

        Vertex<String, Integer> foo, bar;

        @BeforeEach
        void init() {
            foo = graph.newVertex("foo");
            assertNotNull(foo, "Could not create vertex: foo");
            bar = graph.newVertex("bar");
            assertNotNull(bar, "Could not create vertex: bar");
        }

        @Test
        void existingEdge() {
            Edge<Integer, String> e = graph.newEdge(foo, bar, 2);

            Edge<Integer, String> opt = graph.getEdge(foo, bar);
            assertEquals(e, opt, "Wrong edge returned");
        }
    }
}
