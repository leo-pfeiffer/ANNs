package src.hyperparam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tuning {

    private final ArrayList<HyperParams> settings = new ArrayList<>();
    private final Map<Integer, Double> vals = new HashMap<>();
    int current = 0;
    int best = -1;
    double maxAcc = 0.0;

    public Tuning() {
    }

    public void addSetting(HyperParams setting) {
        settings.add(setting);
        vals.put(settings.size() - 1, null);
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
        if (accuracy > this.maxAcc) {
            this.maxAcc = accuracy;
            this.best = current - 1;
        }
        this.vals.put(current - 1, accuracy);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < settings.size(); i++) {
            sb.append(settings.get(i).toString()).append("\n");
            if (best == i) sb.append("* ");
            sb.append("Acc ====> ").append(vals.get(i));
            sb.append("\n\n=========================\n\n");
        }
        return sb.toString();
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
