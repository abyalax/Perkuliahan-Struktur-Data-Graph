import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.text.DecimalFormat;

/**
 * Graph
 */
public class petaGraph {

    class Edge {
        public final Vertex target;
        public final double bobot;

        public Edge(Vertex argTarget, double argBobot) {
            target = argTarget;
            bobot = argBobot;
        }

        @Override
        public String toString() {
            return "Edge to " + target + " with weight " + bobot;
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

    public static class Dijkstra {
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
                        v.previous = u;
                        vertexQueue.add(v);
                    }
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;  
    }

    //method toString

    public static String pathToString(List<Vertex> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }

    private Vertex[] vertices;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");
        for (Vertex v : vertices) {
            sb.append(v).append(":\n");
            if (v.adgacencies != null) {
                for (Edge e : v.adgacencies) {
                    sb.append("  ").append(e).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Create graph instance
        petaGraph graph = new petaGraph();

        // Create vertices
        Vertex v0 = graph.new Vertex("Bekasi");
        Vertex v1 = graph.new Vertex("Bogor");
        Vertex v2 = graph.new Vertex("Jakarta");
        Vertex v3 = graph.new Vertex("Depok");
        Vertex v4 = graph.new Vertex("Tangerang");

        // Store vertices in an array
        graph.vertices = new Vertex[] { v0, v1, v2, v3, v4 };

        // Create edges
        v0.adgacencies = new Edge[] { 
            graph.new Edge(v1, 5), 
            graph.new Edge(v2, 10), 
            graph.new Edge(v3, 8) 
        };
        v1.adgacencies = new Edge[] { 
            graph.new Edge(v0, 5), 
            graph.new Edge(v2, 3), 
            graph.new Edge(v4, 7) 
        };
        v2.adgacencies = new Edge[] { 
            graph.new Edge(v0, 10), 
            graph.new Edge(v1, 3) 
        };
        v3.adgacencies = new Edge[] { 
            graph.new Edge(v0, 8), 
            graph.new Edge(v4, 2) 
        };
        v4.adgacencies = new Edge[] { 
            graph.new Edge(v1, 7), 
            graph.new Edge(v3, 2) 
        };

        // Print the graph
        System.out.println(graph);


        DecimalFormat df = new DecimalFormat("#.##");
        // Compute paths from Bekasi
        Dijkstra.computePaths(v0);
        // Print shortest paths to all vertices
        for (Vertex v : graph.vertices) {
            System.out.println("Jarak dari " + v0 + " ke " + v + ": " + df.format(v.minDistance) + " KM");
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Rute terpendek yaitu: " + pathToString(path) + "\n");
        }
    }
}
