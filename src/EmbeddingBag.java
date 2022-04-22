package src;

import org.jblas.*;
import java.util.ArrayList;
import java.util.List;
import minet.layer.init.*;
import minet.layer.Layer;

/**
 * A class for Embedding bag layers. Feel free to modify this class for your implementation.
 */
public class EmbeddingBag implements Layer, java.io.Serializable {			

	private static final long serialVersionUID = -10445336293457309L;
	
	DoubleMatrix W;  // weight matrix (for simplicity, we can ignore the bias term b)

    // for backward
    List<int[]> X;  // store input X for computing backward, each element in this list is a sample (an array of word indices).
    List<int[]> Xt;  // transposed X
    DoubleMatrix gW;    // gradient of W

    /**
     * Constructor for EmbeddingBag
     * @param vocabSize (int) vocabulary size
     * @param outdims (int) output of this layer
     * @param wInit (WeightInit) weight initialisation method
     */
    public EmbeddingBag(int vocabSize, int outdims, WeightInit wInit) {
        this.W = wInit.generate(vocabSize, outdims);
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
        this.Xt = toExplicit(X.transpose());

        double[][] y = new double[X.rows][this.W.columns];

        for (int i = 0; i < X.rows; i++) {
            for (int j = 0; j < this.W.columns; j++) {
                y[i][j] = calcElem(i, j, this.X, this.W);
            }
        }
        return new DoubleMatrix(y);
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

    @Override
    public DoubleMatrix backward(DoubleMatrix gY) {
        double[][] gWnew = new double[this.W.rows][this.W.columns];

        for (int i = 0; i < this.W.rows; i++) {
            for (int j = 0; j < this.W.columns; j++) {
                gWnew[i][j] = calcElem(i, j, this.Xt, gY);
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

}