import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BFS(Digraph G, int s) {
        marked = new boolean[G.getCountV()];
        distTo = new int[G.getCountV()];
        edgeTo = new int[G.getCountV()];
        for (int v = 0; v < G.getCountV(); v++) distTo[v] = INFINITY;
        bfs(G, s);
    }

    public BFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.getCountV()];
        distTo = new int[G.getCountV()];
        edgeTo = new int[G.getCountV()];
        for (int v = 0; v < G.getCountV(); v++) distTo[v] = INFINITY;
        bfs(G, sources);
    }


    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new LinkedList<>();
        marked[s] = true;
        distTo[s] = 0;
        q.add(s);
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int w : G.getEdgeForVertex(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }


    private void bfs(Digraph G, Iterable<Integer> sources) {
        Queue<Integer> q = new LinkedList<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.add(s);
        }
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int w : G.getEdgeForVertex(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) {
        return distTo[v];
    }
}
