package ca.uwo.eng.se2205.lab7.graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link UndirectedGraph} implementation
 */
@Testable
@DisplayName("An Undirected Graph")
public class UndirectedGraphTest {

    @Test
    public void testPresence(){

        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<Integer,Integer>();

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
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);

        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges.add(e34);
        listOfEdges.add(e47);
        listOfEdges.add(e45);
        listOfEdges.add(e58);
        listOfEdges.add(e65);

        for(Vertex v : listOfVertices)
            assertTrue(graph.vertices().contains(v));

        for(Edge e : listOfEdges)
            assertTrue(graph.edges().contains(e));
    }

    @Test
    public void incomingAndOutgoingEdges() {
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        //Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);

        assertFalse(graph.incomingEdges(vert5).contains(e47));
        assertTrue(graph.incomingEdges(vert5).contains(e65));
        assertTrue(graph.incomingEdges(vert5).contains(e45));
        assertEquals(3, graph.incomingEdges(vert5).size());

        assertTrue(graph.outgoingEdges(vert5).contains(e58));
        assertEquals(3, graph.outgoingEdges(vert5).size());
    }

    @Test
    public void testRemovals(){
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();

        Vertex<Integer,Integer> vert3 = graph.newVertex(3);
        Vertex<Integer,Integer> vert4 = graph.newVertex(4);
        Vertex<Integer,Integer> vert5 = graph.newVertex(5);
        Vertex<Integer,Integer> vert6 = graph.newVertex(6);
        Vertex<Integer,Integer> vert7 = graph.newVertex(7);
        Vertex<Integer,Integer> vert8 = graph.newVertex(8);

        Edge<Integer,Integer> e34 = graph.newEdge(vert3, vert4, 10);
        Edge<Integer,Integer> e47 = graph.newEdge(vert4, vert7, 5);
        Edge<Integer,Integer> e45 = graph.newEdge(vert4, vert5, 3);
        Edge<Integer,Integer> e58 = graph.newEdge(vert5, vert8, 2);
        Edge<Integer,Integer> e65 = graph.newEdge(vert6, vert5, 20);

        Iterator vertexIt = graph.vertices().iterator();
        Vertex nextVertex = (Vertex) vertexIt.next();
        vertexIt.remove();

        assertFalse(graph.vertices().contains(nextVertex));
        vertexIt.next();

        Iterator edgeIt = graph.edges().iterator();
        Edge nextEdge = (Edge) edgeIt.next();
        edgeIt.remove();

        assertFalse(graph.edges().contains(nextEdge));
        edgeIt.next();
    }

    private Graph<String, Integer> graph;

    @BeforeEach
    void init() {
        graph = new UndirectedGraph<>();
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
                vertex = UndirectedGraphTest.this.graph.newVertex("bar");
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
