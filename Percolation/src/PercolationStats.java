import java.util.ArrayList;
import java.util.List;

public class PercolationStats {
    private final int matrixSize;
    private final int repeats;
    private final List<Double> results;

    public PercolationStats(int n, int t) {
        this.matrixSize = n;
        this.repeats = t;
        this.results = new ArrayList<>();
    }

    public double mean() {
        double mean = 0;
        for (Double result : results) {
            mean += result;
        }
        return mean / repeats;
    }

    public double stddev() {
        double mean = mean();
        double stddev = 0;
        for (Double result : results) {
            stddev += Math.pow(mean - result, 2);
        }
        stddev/=(repeats - 1);
        return Math.sqrt(stddev);
    }

    public double lowerBound() {
        return mean() - 1.96 * stddev() / Math.sqrt(repeats);
    }

    public double upperBound() {
        return mean() + 1.96 * stddev() / Math.sqrt(repeats);
    }

    public void calculus() {
        Percolation percolation;
        for(int i = 0; i < repeats; i++) {
            percolation = new Percolation(matrixSize);
            while (!percolation.percolates()) {
                int x = 1+ (int) (Math.random() * matrixSize);
                int y = 1+ (int) (Math.random() * matrixSize);
                if(!percolation.isOpened(x, y)) {
                    percolation.open(x, y);
                }
            }
            results.add(percolation.getOpenedCount()/ (double)(matrixSize * matrixSize));
        }
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(200, 100);
        percolationStats.calculus();
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.lowerBound() + ", " + percolationStats.upperBound() + "]");
    }

    /*
    * 0.5929840000000002
    * 0.00871705813478679
    * 95% confidence interval = [0.591275456605582, 0.5946925433944183]
    *
    * 0.5927615
    * 0.00990957692835521
    * 95% confidence interval = [0.5908192229220425, 0.5947037770779576]
    * */


}
