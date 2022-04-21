package src.hyperparam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tuning {

    private final ArrayList<HyperParams> settings = new ArrayList<>();
    private final Map<Integer, Double> vals = new HashMap<>();
    int current = 0;

    public Tuning() {}

    public void addSetting(HyperParams setting) {
        settings.add(setting);
        vals.put(settings.size()-1, null);
    }

    public void reset() {
        current = 0;
    }

    public HyperParams getNextSetting() {
        if (current >= settings.size()) return null;
        HyperParams next = settings.get(current);
        current++;
        return next;
    }

    public void setAccuracy(double accuracy) {
        this.vals.put(current-1, accuracy);
    }

    public HyperParams getOptimalParams() {
        int best = getOptimalParamsIdx();
        return settings.get(best);
    }

    public int getOptimalParamsIdx() {
        double acc = 0;
        int best = -1;
        for (int i = 0; i < this.settings.size(); i++) {
            if (vals.get(i) > acc) {
                acc = vals.get(i);
                best = i;
            }
        }
        return best;
    }

    public String toString() {
        int best = getOptimalParamsIdx();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < settings.size(); i++) {
            sb.append(settings.get(i).toString()).append("\n");
            if (best == i) sb.append("* ");
            sb.append("Acc ====> ").append(vals.get(i));
            sb.append("\n\n=========================\n\n");
        }
        return sb.toString();
    }
}
