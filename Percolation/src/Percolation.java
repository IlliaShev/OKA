public class Percolation {
    private final int matrixSize;
    private final boolean[] matrix;
    private final WeightedQuickUnionUF dsu;
    private int count;

    public Percolation(int n) {
        this.matrixSize = n;
        this.matrix = new boolean[matrixSize * matrixSize + 1];
        this.dsu = new WeightedQuickUnionUF(matrixSize * matrixSize + 2);
    }

    public int getOpenedCount() {
        return count;
    }

    public void open(int i, int j) {
        int point = (i - 1) * matrixSize + j;
        matrix[(i - 1) * matrixSize + j] = true;
        if (i == 1) {
            dsu.union(point, 0);
            if (isOpened(i + 1, j)) {
                dsu.union(point, j + matrixSize);
            }
        } else if (i == matrixSize) {
            dsu.union(point, matrixSize * matrixSize + 1);
            if (isOpened(i - 1, j)) {
                dsu.union(point, point - matrixSize);
            }
        } else {
            if (isOpened(i + 1, j)) {
                dsu.union(point, point + matrixSize);
            }
            if (isOpened(i - 1, j)) {
                dsu.union(point, point - matrixSize);
            }
        }
        if (j > 1 && isOpened(i, j - 1)) {
            dsu.union(point, point - 1);
        }
        if (j < matrixSize && isOpened(i, j + 1)) {
            dsu.union(point, point + 1);
        }
        count++;
    }

    public boolean isOpened(int i, int j) {
        return matrix[(i - 1) * matrixSize + j];
    }

    public boolean percolates() {
        return dsu.connected(0, matrixSize * matrixSize + 1);
    }
}
