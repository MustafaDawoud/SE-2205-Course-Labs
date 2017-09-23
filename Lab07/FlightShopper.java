package ca.uwo.eng.se2205.lab7.travel;

import ca.uwo.eng.se2205.lab7.graphs.DirectedGraph;
import ca.uwo.eng.se2205.lab7.graphs.Edge;
import ca.uwo.eng.se2205.lab7.graphs.Vertex;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Optimized utility for shopping for flights.
 */
@ParametersAreNonnullByDefault
public final class FlightShopper {

    private HashMap<Airport, Vertex> airportVertexHashMap;
    private DirectedGraph<Airport, Flight> flightShopper;
    private Table<Vertex, Vertex, Double> shortestDistances;
    private Table<Vertex, Vertex, Vertex> linkToNext;

    /**
     * Builds a {@code FlightShopper} via a {@link Set} of {@link Airport}s and {@link Flight}s.
     *
     * @param airports The available airports
     * @param flights All available flights
     */
    public FlightShopper( Collection<? extends Airport> airports, Collection<? extends Flight> flights) {
        // DO NOT CHANGE THE METHOD SIGNATURE
        // Initialize your shopper
        if(airports == null || flights == null)
            throw new NullPointerException();

        flightShopper = new DirectedGraph<>();
        airportVertexHashMap = new HashMap<>();
        shortestDistances = HashBasedTable.create();
        linkToNext = HashBasedTable.create();

        for(Airport i : airports){
            Vertex newVertex = flightShopper.newVertex(i);
            airportVertexHashMap.put(i, newVertex);
        }

        for(Flight j : flights){
            flightShopper.newEdge(airportVertexHashMap.get(j.getDeparture()), airportVertexHashMap.get(j.getArrival()), j);
        }

        for(Vertex i: flightShopper.vertices()){
            for(Vertex j: flightShopper.vertices()){
                shortestDistances.put(i, j, Double.MAX_VALUE);
                linkToNext.put(i, j, i);
                if(i == j){
                    shortestDistances.put(i, i, 0.0);
                }
            }
        }

        for(Object e : flightShopper.edges()){
            shortestDistances.put(((Edge) e).u(), ((Edge) e).v(), ((Flight)((Edge) e).getWeight()).getCost());
        }

        for(Vertex k: flightShopper.vertices()){
            for(Vertex i : flightShopper.vertices()){
                for(Vertex j : flightShopper.vertices()){
                    if(shortestDistances.get(i, j) > shortestDistances.get(i, k) + shortestDistances.get(k, j)){
                        shortestDistances.put(i, j,shortestDistances.get(i, k) + shortestDistances.get(k, j));
                        linkToNext.put(i, j, k);
                    }
                }
            }
        }
    }

    /**
     * Finds the cheapest flight from two {@link Airport}s.
     * @param from Starting airport
     * @param to Ending airport
     * @return Cheapest {@code Itinerary} to fly between {@code from} and {@code to}
     */
     public Itinerary price(Airport from, Airport to) {
         if(from == to){
             List<Flight> flight = new ArrayList<>();
             flight.add(new Flight(from, from, 0.0));
             return new Itinerary(flight);
         }
        Vertex v = linkToNext.get(airportVertexHashMap.get(from), airportVertexHashMap.get(to));
        Vertex u = airportVertexHashMap.get(to);
        List<Flight> path = new ArrayList<>();
        if(shortestDistances.get(airportVertexHashMap.get(from), airportVertexHashMap.get(to)) != Double.MAX_VALUE) {
            if (u == v) {
                path.add((Flight) flightShopper.getEdge(airportVertexHashMap.get(from), airportVertexHashMap.get(to)).getWeight());
            } else {
                while (v != airportVertexHashMap.get(from)) {
                    path.add((Flight) flightShopper.getEdge(v, u).getWeight());
                    u = v;
                    v = linkToNext.get(airportVertexHashMap.get(from), v);
                }
                path.add((Flight) flightShopper.getEdge(airportVertexHashMap.get(from), u).getWeight());
            }
            Collections.reverse(path);
            return new Itinerary(path);
        }
        else{
            List<Flight> flight = new ArrayList<>();
            System.out.println("No path exists");
            flight.add(new Flight(from, to, Double.MAX_VALUE));
            return new Itinerary(flight);
        }
    }
}