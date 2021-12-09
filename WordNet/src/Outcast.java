public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outcast = "";
        int maxSum = -1;
        for(int i = 0; i < nouns.length; i++) {
            int currSum = 0;
            for(String word: nouns) {
                currSum+= wordNet.distance(nouns[i], word);
            }
            if(currSum > maxSum) {
                maxSum = currSum;
                outcast = nouns[i];
            }
        }
        return outcast;
    }
}
