import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Graph
 */
public class Graph {

    class Edge {
        public final Vertex target;
        public final double bobot;

        public Edge(Vertex argTarget, double argBobot) {
            target = argTarget;
            bobot = argBobot;
        }
    }

    class Vertex implements Comparable<Vertex> {
        public final String name;
        public Edge[] adgacencies;
        public double minDistance = Double.POSITIVE_INFINITY;
        public Vertex previous;

        public Vertex(String argName) {
            name = argName;
        }
        @Override
        public String toString() {
            return name;
        }
        @Override
        public int compareTo(Vertex other) {
            return Double.compare(minDistance, other.minDistance);
        }
    }

    public class Djikstra {
        public static void computePaths(Vertex source) {
            source.minDistance = 0;
            PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
            vertexQueue.add(source);

            while (!vertexQueue.isEmpty()) {
                Vertex u = vertexQueue.poll();
                // mengunjungi tiap node jarak terpendek
                for (Edge e : u.adgacencies) {
                    Vertex v = e.target;
                    double bobot = e.bobot;
                    double distanceThroughU = u.minDistance + bobot;
                    if (distanceThroughU < v.minDistance) {
                        vertexQueue.remove(v);
                        v.minDistance = distanceThroughU;
                        vertexQueue.add(v);
                    }
                }
            }
        }
    }

    public static List<Vertex> getshortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }

    

    public static void main(String[] args) {
        // Create vertices
        Graph graph = new Graph();
        Vertex v1 = graph.new Vertex("A");
        Vertex v2 = graph.new Vertex("B");
        Vertex v3 = graph.new Vertex("C");
        Vertex v4 = graph.new Vertex("D");
        Vertex v5 = graph.new Vertex("E");

        // Create edges
        v1.adgacencies = new Edge[] { graph.new Edge(v2, 5), graph.new Edge(v3, 10) };
        v2.adgacencies = new Edge[] { graph.new Edge(v4, 3) };
        v3.adgacencies = new Edge[] { graph.new Edge(v5, 1) };
        v4.adgacencies = new Edge[] { graph.new Edge(v5, 2) };
        v5.adgacencies = new Edge[] {};

        System.out.println(graph);
    }    
}
// // Compute paths
// Djikstra.computePaths(v1);

// // Get the shortest path from v1 to v5
// List<Vertex> path = getshortestPathTo(v5);
// System.out.println("Path: " + path);
// // Get the shortest path from v1 to v4
// List<Vertex> path2 = getshortestPathTo(v4);
// System.out.println("Path: " + path2);