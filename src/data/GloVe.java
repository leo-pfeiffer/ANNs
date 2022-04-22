package src.data;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.jblas.DoubleMatrix;

public class GloVe {
    public static DoubleMatrix fromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String[] splitted;
        String line;

        ArrayList<double[]> lines = new ArrayList<>();

        while((line = br.readLine()) != null) {
            splitted = line.split(" ");
            double[] weights = new double[splitted.length-1];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = Double.parseDouble(splitted[i+1]);
            }
            lines.add(weights);
        }
        br.close();

        double[][] matrix = new double[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            matrix[i] = lines.get(i);
        }

        return new DoubleMatrix(matrix);
    }
}