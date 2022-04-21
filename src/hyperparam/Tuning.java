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
        double acc = 0;
        int best = -1;
        for (int i = 0; i < this.settings.size(); i++) {
            if (vals.get(i) > acc) {
                acc = vals.get(i);
                best = i;
            }
        }
        return settings.get(best);
    }
}
