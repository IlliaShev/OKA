import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SAP {

    private Digraph graph;

    public SAP(Digraph graph) {
        this.graph = graph;
    }

    public int length(int v, int w) {
        return sap(new ArrayList<>(List.of(v)), new ArrayList<>(List.of(w))).distance;
    }

    public int ancestor(int v, int w) {
        return sap(new ArrayList<>(List.of(v)), new ArrayList<>(List.of(w))).ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v,w).distance;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v,w).ancestor;
    }


    private Pair sap(Iterable<Integer> v, Iterable<Integer> w) {
        Pair ancestorInfo = new Pair(Integer.MAX_VALUE, -1);
        BFS vPath = new BFS(graph, v);
        BFS wPath = new BFS(graph, w);
        for(int vertex = 0; vertex < graph.getCountV(); vertex++) {
            if(vPath.hasPathTo(vertex) && wPath.hasPathTo(vertex)) {
                int dist = wPath.distTo(vertex) + vPath.distTo(vertex);
                if(dist < ancestorInfo.distance) {
                    ancestorInfo.distance = dist;
                    ancestorInfo.ancestor = vertex;
                }
            }
        }
        if(ancestorInfo.ancestor == -1) {
            ancestorInfo.distance = -1;
        }
        return ancestorInfo;
    }


    private class Pair {
        private int distance;
        private int ancestor;

        public Pair(int distance, int ancestor) {
            this.distance = distance;
            this.ancestor = ancestor;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/tests/digraph1.txt"));
        int vertices = scanner.nextInt();
        int edges = scanner.nextInt();
        Digraph g = new Digraph(vertices);
        for (int i = 0; i < edges; i++) {
            int v, w;
            v = scanner.nextInt();
            w = scanner.nextInt();
            g.addEdge(v, w);
        }
        SAP sap = new SAP(g);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            System.out.println("length = " + length + ", ancestor = " + ancestor);
        }
    }
}
