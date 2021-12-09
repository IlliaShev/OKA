import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Digraph {
    private List<Integer>[] graph;
    private Map<Integer, Boolean> hasInEdges;


    private int countV;

    public Digraph(int countV) {
        this.countV = countV;
        graph = new List[countV];
        hasInEdges = new HashMap<>();
        for(int i = 0; i < countV; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    public void addEdge(int m, int v) {
        graph[m].add(v);
        hasInEdges.put(v, true);
    }

    public boolean isHasInEdges(int v) {
        return hasInEdges.containsKey(v);
    }

    public List<Integer> getEdgeForVertex(int v) {
        return graph[v];
    }

    public int getCountV() {
        return countV;
    }
}
