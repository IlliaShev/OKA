import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordNet {

    private final Digraph graph;
    private final Map<String, List<Integer>> wordToEntries;
    private final Map<Integer, String> idToSynonyms;
    private final SAP sap;
    private boolean[] marked;
    private int[] colors;

    public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
        wordToEntries = new HashMap<>();
        idToSynonyms = new HashMap<>();
        int v = readSynset(synsets);
        graph = new Digraph(v);
        readHypernyms(hypernyms);
        if(!checkGraph()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(graph);
    }

    private boolean checkGraph() {
        int rootNodes = 0;
        for(int i = 0; i < graph.getCountV(); i++) {
            if(graph.getEdgeForVertex(i).size() == 0)
                rootNodes++;
        }
        if(rootNodes != 1) {
            return false;
        }
        marked = new boolean[graph.getCountV()];
        colors = new int[graph.getCountV()];
        dfs(0);
        return !dfs(0);
    }

    private boolean dfs(int v) {
        marked[v] = true;
        colors[v] = 1;
        for(int w: graph.getEdgeForVertex(v)) {
            if(!marked[w]) {
                if(dfs(w)) {
                    return true;
                }
            } else {
                if(colors[w] == 1) {
                    return true;
                }
            }
        }
        colors[v] = 2;
        return false;
    }

    public Iterable<String> nouns() {
        return wordToEntries.keySet();
    }

    public boolean isNoun(String word) {
        return wordToEntries.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> nounAEntries=  wordToEntries.get(nounA);
        List<Integer> nounBEntries=  wordToEntries.get(nounB);

        return sap.length(nounAEntries, nounBEntries);
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> nounAEntries=  wordToEntries.get(nounA);
        List<Integer> nounBEntries=  wordToEntries.get(nounB);

        return idToSynonyms.get(sap.ancestor(nounAEntries, nounBEntries));
    }

    private void readHypernyms(String hypernyms) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(hypernyms));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            for(int i = 1; i < line.length; i++) {
                graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }
        }
    }

    private int readSynset(String synsets) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(synsets));
        int v = 0;
        while (scanner.hasNextLine()) {
            v++;
            String[] line = scanner.nextLine().split(",");
            int id = Integer.parseInt(line[0]);
            idToSynonyms.put(id, line[1]);
            String[] synonyms = line[1].split(" ");
            for(String word: synonyms) {
                if(!wordToEntries.containsKey(word)) {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(id);
                    wordToEntries.put(word, ids);
                } else {
                    List<Integer> ids = wordToEntries.get(word);
                    ids.add(id);
                }
            }
        }
        return v;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String inputH = "src/tests/hypernyms.txt";
        String inputS = "src/tests/synsets.txt";

        WordNet wordNet = new WordNet(inputS, inputH);
        Outcast outcast = new Outcast(wordNet);
        Scanner scanner = new Scanner(new File("src/tests/outcast.txt"));
        System.out.println(wordNet.sap("bed", "'hood"));
        while (scanner.hasNextLine()) {
            String[] nouns = scanner.nextLine().split(" ");
            System.out.println(outcast.outcast(nouns));
        }
    }
}
