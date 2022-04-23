package src.util;

import java.io.IOException;
import java.util.ArrayList;

public class Tracker {
    private ArrayList<Double> loss = new ArrayList<>();
    private ArrayList<Double> trainAcc = new ArrayList<>();
    private ArrayList<Double> devAcc = new ArrayList<>();

    public void addLoss(double loss) {
        this.loss.add(loss);
    }

    public void addTrainAcc(double acc) {
        this.trainAcc.add(acc);
    }

    public void addDevAcc(double acc) {
        this.devAcc.add(acc);
    }

    public ArrayList<Double> getLoss() {
        return this.loss;
    }

    public ArrayList<Double> getTrainAcc() {
        return this.trainAcc;
    }

    public ArrayList<Double> getDevAcc() {
        return this.devAcc;
    }

    public void toCsv(String path) throws IOException {
        String[] headers = {"Loss", "Train Acc", "Dev Acc"};
        FileUtil.listsToCsv(path, headers, this.loss, this.trainAcc, this.devAcc);
    }
}
