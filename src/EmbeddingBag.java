package src;

import org.jblas.*;
import java.util.ArrayList;
import java.util.List;
import minet.layer.init.*;
import minet.layer.Layer;
import org.jblas.ranges.RangeUtils;

/**
 * A class for Embedding bag layers. Feel free to modify this class for your implementation.
 */
public class EmbeddingBag implements Layer, java.io.Serializable {			

	private static final long serialVersionUID = -10445336293457309L;
	
	DoubleMatrix W;  // weight matrix (for simplicity, we can ignore the bias term b)

    int vocabSize;

    // for backward
    List<int[]> X;  // store input X for computing backward, each element in this list is a sample (an array of word indices).
    DoubleMatrix gW;    // gradient of W

    /**
     * Constructor for EmbeddingBag
     * @param vocabSize (int) vocabulary size
     * @param outdims (int) output of this layer
     * @param wInit (WeightInit) weight initialisation method
     */
    public EmbeddingBag(int vocabSize, int outdims, WeightInit wInit) {
        this.vocabSize = vocabSize;
        this.W = wInit.generate(vocabSize, outdims);
        this.gW = DoubleMatrix.zeros(vocabSize, outdims);
    }

    /**
     * Create EmbeddingBag layer with initial weight matrix (e.g., GloVe)
     *
     * @param vocabSize (int) vocabulary size
     * @param outdims (int) output of this layer
     * @param W (DoubleMatrix) initial weight matrix
     * */
    public EmbeddingBag(int vocabSize, int outdims, DoubleMatrix W) {
        this.vocabSize = vocabSize;

        assert outdims <= W.columns;

        // if outdims smaller than the provided matrix, truncate the weight matrix
        if (outdims < W.columns) {
            int[] columns = new int[outdims];
            for (int i = 0; i < outdims; i++) columns[i] = i;
            this.W = W.getColumns(columns).dup();
        } else {
            this.W = W;
        }
        this.gW = DoubleMatrix.zeros(vocabSize, outdims);
    }

    /**
     * Forward pass
     * @param input (List<int[]>) input for forward calculation
     * @return a [batchsize x outdims] matrix, each row is the output of a sample in the batch
     */
    @Override
    public DoubleMatrix forward(Object input) {
        DoubleMatrix X = (DoubleMatrix)input;
        this.X = toExplicit(X);

        double[][] y = new double[X.rows][this.W.columns];

        for (int i = 0; i < X.rows; i++) {
            for (int j = 0; j < this.W.columns; j++) {
                y[i][j] = calcElem(i, j, this.X, this.W);
            }
        }
        return new DoubleMatrix(y);
    }

    @Override
    public DoubleMatrix backward(DoubleMatrix gY) {
        double[][] gWnew = new double[this.W.rows][this.W.columns];

        List<int[]> Xt = transposeExplicit(this.X, this.vocabSize);

        for (int i = 0; i < this.W.rows; i++) {
            for (int j = 0; j < this.W.columns; j++) {
                gWnew[i][j] = calcElem(i, j, Xt, gY);
            }
        }

        gW.addi(new DoubleMatrix(gWnew));

        return null; // there is no need to compute gX as the previous layer of this one is the input layer of the network
    }

    @Override
    public List<DoubleMatrix> getAllWeights(List<DoubleMatrix> weights) {
        weights.add(W);
        return weights;
    }

    @Override
    public List<DoubleMatrix> getAllGradients(List<DoubleMatrix> grads) {
        grads.add(gW);
        return grads;
    }

    @Override
    public String toString() {
        return String.format("Embedding: %d rows, %d dims", W.rows, W.columns);
    }

    /**
     * Compute an element as the dot product from an explicit representation (lhs)
     * and a matrix (rhs).
     * @param lhsRow the row in the lhs matrix
     * @param rhsCol the column in the rhs matrix
     * @param lhs the explicit representation of the lhs matrix
     * @param rhs the rhs matrix
     * @return dot product of the two vectors
     * */
    private double calcElem(int lhsRow, int rhsCol, List<int[]> lhs, DoubleMatrix rhs) {
        double sum = 0;
        for (int i : lhs.get(lhsRow)) {
            sum += rhs.get(i, rhsCol);
        }
        return sum;
    }

    /**
     * Convert a binary matrix (domain {0, 1}) to explicit representation.
     * The representation is a list of integer arrays. Each array corresponds to a row
     * of the original matrix and the elements of the array are the indices in the original
     * row where the element is 1.
     *
     * @param X the boolean matrix
     * @return explicit representation
     * */
    private List<int[]> toExplicit(DoubleMatrix X) {
        ArrayList<int[]> explicit = new ArrayList<>(X.rows);
        for (int i = 0; i < X.rows; i++) {
            int[] row = new int[(int) X.getRow(i).sum()];
            int idx = 0;
            for (int j = 0; j < X.columns; j ++) {
                if (X.get(i, j) == 1) {
                    row[idx] = j;
                    idx++;
                }
            }
            explicit.add(row);
        }
        return explicit;
    }

    // transpose explicit
    public static ArrayList<int[]> transposeExplicit(List<int[]> explicit, int vocabSize) {

        ArrayList<List<Integer>> transposedList = new ArrayList<>(vocabSize);

        // init
        for (int i = 0; i < vocabSize; i++) transposedList.add(new ArrayList<>());

        // transpose
        for (int i = 0; i < explicit.size(); i++) {
            for (int j : explicit.get(i)) {
                transposedList.get(j).add(i);
            }
        }

        // convert to array
        ArrayList<int[]> transposed = new ArrayList<>(vocabSize);
        for (List<Integer> list : transposedList) {
            int[] array = new int[list.size()];
            for(int j = 0; j < list.size(); j++) array[j] = list.get(j);
            transposed.add(array);
        }

        return transposed;
    }


}
